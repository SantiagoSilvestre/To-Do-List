package me.san.listtodo.service.listener

interface TaskListener {
    fun onClick(id: Int)
    fun onDelete(id: Int)
}