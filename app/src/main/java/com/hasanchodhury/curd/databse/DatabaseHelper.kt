package com.hasanchodhury.curd.databse

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.number.IntegerWidth
import com.hasanchodhury.curd.model.TaskListModel

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        val DB_NAME = "task"
        val DB_VERSION = 1
        val TABLE_NAME = "tasklist"
        val ID = "id"
        val TASK_NAME = "taskname"
        val TASK_DETAILS = "taskdetails"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $TASK_NAME TEXT, $TASK_DETAILS TEXT)"
        p0?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val DROP_TABLE = ("DROP TABLE IF EXISTS $TABLE_NAME")
        p0?.execSQL(DROP_TABLE)
        onCreate(p0)
    }

    fun GetAllTask(): List<TaskListModel> {
        val tasklist = ArrayList<TaskListModel>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null && cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(ID)
            val nameIndex = cursor.getColumnIndex(TASK_NAME)
            val detailsIndex = cursor.getColumnIndex(TASK_DETAILS)

            do {
                val task = TaskListModel()
                task.id = cursor.getInt(idIndex)
                task.name = cursor.getString(nameIndex)
                task.details = cursor.getString(detailsIndex)
                tasklist.add(task)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return tasklist
    }

    //insert
    fun addTask(task: TaskListModel): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TASK_NAME, task.name)
        values.put(TASK_DETAILS, task.details)
        val success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    //Select Data of an ID
    fun getTask(id: Int): TaskListModel {
        val task = TaskListModel()

        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = $id"
        val cursor = db.rawQuery(selectQuery, null)
        cursor?.moveToFirst()

        val idIndex = cursor.getColumnIndex(ID)
        val nameIndex = cursor.getColumnIndex(TASK_NAME)
        val detailsIndex = cursor.getColumnIndex(TASK_DETAILS)
        task.id = cursor.getInt(idIndex)
        task.name = cursor.getString(nameIndex)
        task.details = cursor.getString(detailsIndex)

        cursor.close()
        return task
    }

    //Delete a Task
    fun deleteTask(id: Int): Boolean {
        val db = this.writableDatabase
        val success = db.delete(TABLE_NAME, ID + "=?", arrayOf(id.toString())).toLong()
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    //Update a Task
    fun updateTask(task: TaskListModel): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(TASK_NAME, task.name)
        values.put(TASK_DETAILS, task.details)
        val success = db.update(TABLE_NAME, values, ID + "=?", arrayOf(task.id.toString())).toLong()
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

}