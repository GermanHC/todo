package com.germanhc.todo.data.repository

import com.germanhc.todo.data.model.Task
import io.reactivex.Flowable
import io.reactivex.Single

interface TaskRepository {

    fun getAll(): Single<List<Task>>

    fun observeAll(): Flowable<List<Task>>

    fun getTaskById(taskId: Long): Single<Task>

    fun insert(task: Task)

    fun delete(task: Task)

    fun update(task: Task)

}