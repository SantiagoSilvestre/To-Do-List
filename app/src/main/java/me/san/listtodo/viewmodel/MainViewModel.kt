package me.san.listtodo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.san.listtodo.model.TaskModel
import me.san.listtodo.service.repository.TaskRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val mTaskRepository = TaskRepository(mContext)

    private val mTaskList = MutableLiveData<List<TaskModel>>()
    val taskList: LiveData<List<TaskModel>> = mTaskList

    private val mTask = MutableLiveData<TaskModel>()
    val task: LiveData<TaskModel> = mTask

    fun list() {
        mTaskList.value = mTaskRepository.getAll()
    }

    fun delete(id: Int) {
        val task = mTaskRepository.get(id)
        mTaskRepository.delete(task)
    }

    fun load(id: Int) {
        mTask.value = mTaskRepository.get(id)
    }

    fun save(task: TaskModel) {
        if (task.id == 0) {
            mTaskRepository.save(task)
        } else {
            mTaskRepository.update(task)
        }

    }
}