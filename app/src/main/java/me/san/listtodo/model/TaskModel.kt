package me.san.listtodo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Entidade
@Entity(tableName = "Task")
class TaskModel {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "title")
    var title: String = ""

    @ColumnInfo(name = "descricao")
    var descricao: String = ""

    @ColumnInfo(name = "hour")
    var hour: String = ""

    @ColumnInfo(name = "date")
    var date: String = ""

}