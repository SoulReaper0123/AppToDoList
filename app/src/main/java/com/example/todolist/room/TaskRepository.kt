package com.example.todolist.room

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao: TaskDao) {

    val readAllData: LiveData<List<Task>> = taskDao.readAllData()

    suspend fun addList(task: Task) {
        taskDao.addList(task)
    }

}
