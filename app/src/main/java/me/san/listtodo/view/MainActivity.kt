package me.san.listtodo.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import me.san.listtodo.R
import me.san.listtodo.databinding.ActivityMainBinding
import me.san.listtodo.service.listener.TaskListener
import me.san.listtodo.view.ui.AdapterTask
import me.san.listtodo.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mViewModel: MainViewModel
    private lateinit var mListener: TaskListener
    private val adapter by lazy{ AdapterTask() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.rvTasks.adapter = adapter

        mListener = object : TaskListener {
            override fun onClick(id: Int) {
                val intent = Intent(baseContext, AddTaskActivity::class.java)
                intent.putExtra(AddTaskActivity.TASK_ID, id)
                startActivityForResult(intent, CREATED_NEW_TASK)
            }

            override fun onDelete(id: Int) {
                mViewModel.delete(id)
                mViewModel.list()
            }

        }
        insertListeners()
        observe()

    }

    override fun onResume() {
        super.onResume()
        mViewModel.list()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATED_NEW_TASK && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show()
        }
    }

    fun observe() {
        mViewModel.taskList.observe(this, {
            binding.includeEmpty.emptyState.visibility = if (it.isEmpty()) View.VISIBLE
            else View.GONE
            adapter.updateTasks(it)
        })
    }

    fun insertListeners() {
        binding.fab.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATED_NEW_TASK)
        }

        adapter.attachListener(mListener)

    }

    companion object {
        private const val CREATED_NEW_TASK = 1000
        private const val TASK_ID = "task_id"
    }

}