package com.yoochangwonspro.myminiproject

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yoochangwonspro.myminiproject.databinding.ActivityTodoListBinding
import com.yoochangwonspro.myminiproject.databinding.TodolistItemViewBinding

class TodoListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodoListBinding
    val model: TodoListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 로그인이 되어있지 않을 때
        if (FirebaseAuth.getInstance().currentUser == null) {
            login()
        }


        binding.todolistMainBtn.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }

        binding.todolistRecyclerView.apply {
            adapter = TodoListAdapter(
                emptyList(),
                toggleButton = {
                    model.selectTodoList(it)
                },
                deleteButton = {
                    model.deleteTodoList(it)
                }
            )
            layoutManager = LinearLayoutManager(this@TodoListActivity)
        }

        binding.todolistInsertBtn.setOnClickListener {
            model.addTodoList(TodoList(binding.todolistEditTextView.text.toString()))
        }

        model.liveData.observe(this, Observer {
            (binding.todolistRecyclerView.adapter as TodoListAdapter).setData(it)
        })

        binding.todolistLogoutBtn.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
            logout()
        }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            model.fetchData()
        } else {
            // 로그인 실패
            finish()
        }
    }

    fun login() {
        val signInLauncher = registerForActivityResult(
            FirebaseAuthUIActivityResultContract()
        ) { res ->
            this.onSignInResult(res)
        }

        val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    fun logout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {}
    }
}

data class TodoList(val text: String, var isDone: Boolean = false)

class TodoListAdapter(
    private var dataSet: List<DocumentSnapshot>,
    private val toggleButton: (todo: DocumentSnapshot) -> Unit,
    private val deleteButton: (todo: DocumentSnapshot) -> Unit
) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    class ViewHolder(val itemViewBinding: TodolistItemViewBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.todolist_item_view, viewGroup, false)

        return ViewHolder(TodolistItemViewBinding.bind(view))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val todo = dataSet[position]
        viewHolder.itemViewBinding.todolistItemTextView.text = todo.getString("text") ?: ""

        if ((todo.getBoolean("isDone") ?: false) == true) {
            viewHolder.itemViewBinding.todolistItemTextView.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                setTypeface(null, Typeface.ITALIC)
            }
        } else {
            viewHolder.itemViewBinding.todolistItemTextView.apply {
                paintFlags = 0
                setTypeface(null, Typeface.NORMAL)
            }
        }
        viewHolder.itemViewBinding.root.setOnClickListener {
            toggleButton(todo)
        }

        viewHolder.itemViewBinding.todolistItemDeleteView.setOnClickListener {
            deleteButton(todo)
        }
    }

    fun setData(newData: List<DocumentSnapshot>) {
        dataSet = newData
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataSet.size
}

@SuppressLint("LongLogTag")
class TodoListViewModel : ViewModel() {

    val db = Firebase.firestore
    val liveData = MutableLiveData<List<DocumentSnapshot>>()

    init {
        fetchData()
    }

    fun fetchData() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            db.collection(user.uid)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        return@addSnapshotListener
                    }

                    value?.let {
                        liveData.value = value.documents
                    }
                }
        }
    }

    fun addTodoList(todoList: TodoList) {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            db.collection(user.uid)
                .add(todoList)
        }
    }

    fun selectTodoList(todoList: DocumentSnapshot) {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            val isDone = todoList.getBoolean("isDone") ?: false
            db.collection(user.uid).document(todoList.id).update("isDone", !isDone)
        }
    }

    fun deleteTodoList(todoList: DocumentSnapshot) {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            db.collection(user.uid).document(todoList.id).delete()
        }
    }
}