package me.san.listtodo.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.san.listtodo.model.TaskModel
import me.san.listtodo.service.dao.TaskDAO

@Database(entities = [TaskModel::class], version = 1)
abstract class TaskDataBase : RoomDatabase() {

    abstract fun taskDAO(): TaskDAO

    companion object {
        private lateinit var INSTANCE: TaskDataBase
        fun getDatabase(context: Context): TaskDataBase {
            if (!::INSTANCE.isInitialized) {
                synchronized(TaskDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context, TaskDataBase::class.java, "taskDB")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }

}