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
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yoochangwonspro.myminiproject.databinding.ActivityTodoListBinding
import com.yoochangwonspro.myminiproject.databinding.TodolistItemViewBinding

class TodoListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodoListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val model: TodoListViewModel by viewModels()

        binding.todolistMainBtn.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }

        val adapter = TodoListAdapter(
            model.todoListData,
            toggleButton = {
                model.selectTodoList(it)
                binding.todolistRecyclerView.adapter?.notifyDataSetChanged()
            })
        binding.todolistRecyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@TodoListActivity)
        }

        binding.todolistInsertBtn.setOnClickListener {
            model.addTodoList(TodoList(binding.todolistEditTextView.text.toString()))
            adapter.notifyDataSetChanged()
        }
    }
}

data class TodoList(val text: String, var isDone: Boolean = false)

class TodoListAdapter(
    private val dataSet: ArrayList<TodoList>,
    private val toggleButton: (todo: TodoList) -> Unit
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
    }

    override fun getItemCount() = dataSet.size
}

class TodoListViewModel : ViewModel() {

    val todoListData = ArrayList<TodoList>()

    fun addTodoList(todoList: TodoList) {
        todoListData.add(todoList)
    }

    fun selectTodoList(todoList: TodoList) {
        todoList.isDone = !todoList.isDone
    }
}