package com.yoochangwonspro.myminiproject

import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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
import com.yoochangwonspro.myminiproject.databinding.ActivityTodoListBinding
import com.yoochangwonspro.myminiproject.databinding.TodolistItemViewBinding

class TodoListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodoListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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

        val model: TodoListViewModel by viewModels()

        binding.todolistMainBtn.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }

        binding.todolistRecyclerView.apply {
            adapter = TodoListAdapter(
                model.todoListData,
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
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }
}

data class TodoList(val text: String, var isDone: Boolean = false)

class TodoListAdapter(
    private var dataSet: ArrayList<TodoList>,
    private val toggleButton: (todo: TodoList) -> Unit,
    private val deleteButton: (todo: TodoList) -> Unit
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
        viewHolder.itemViewBinding.todolistItemTextView.text = todo.text

        if (todo.isDone) {
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

    fun setData(newData : ArrayList<TodoList>) {
        dataSet = newData
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataSet.size
}

class TodoListViewModel : ViewModel() {

    val liveData = MutableLiveData<ArrayList<TodoList>>()
    val todoListData = ArrayList<TodoList>()

    fun addTodoList(todoList: TodoList) {
        todoListData.add(todoList)
        liveData.value = todoListData
    }

    fun selectTodoList(todoList: TodoList) {
        todoList.isDone = !todoList.isDone
        liveData.value = todoListData
    }

    fun deleteTodoList(todoList: TodoList) {
        todoListData.remove(todoList)
        liveData.value = todoListData
    }
}