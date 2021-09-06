package me.san.listtodo.service.repository

import android.content.Context
import me.san.listtodo.model.TaskModel
import me.san.listtodo.service.TaskDataBase

class TaskRepository (context: Context) {

    //Acesso ao banco de dados
    private val mDatabase = TaskDataBase.getDatabase(context).taskDAO()

    /**
     * Carrega tarefa
     */
    fun get(id: Int): TaskModel {
        return mDatabase.get(id)
    }

    /**
     * Insere tarefa
     */
    fun save(task: TaskModel): Boolean {
        return mDatabase.save(task) > 0
    }

    /**
     * Faz a listagem de todos as tarefas
     */
    fun getAll(): List<TaskModel> {
        return mDatabase.getAll()
    }

    /**
     * Atualiza a tarefa
     */
    fun update(task: TaskModel): Boolean {
        return mDatabase.update(task) > 0
    }

    /**
     * Deleta a tarefa
     */
    fun delete(task: TaskModel) {
        return mDatabase.delete(task)
    }

}