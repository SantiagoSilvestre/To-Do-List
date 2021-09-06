package me.san.listtodo.service.dao

import androidx.room.*
import me.san.listtodo.model.TaskModel

@Dao
interface TaskDAO {

    @Insert
    fun save(guest: TaskModel): Long

    @Update
    fun update(guest: TaskModel): Int

    @Delete
    fun delete(guest: TaskModel)

    @Query("SELECT * FROM Task WHERE id = :id")
    fun get(id: Int): TaskModel

    @Query("SELECT * FROM Task")
    fun getAll(): List<TaskModel>



}