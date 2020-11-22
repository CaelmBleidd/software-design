package ru.menshutin.calendar.service

import org.springframework.stereotype.Service
import ru.menshutin.calendar.model.TaskList
import ru.menshutin.calendar.repository.TaskListRepository

@Service
class TaskListService(private val taskService: TaskService, private val taskListRepository: TaskListRepository) {
    fun findById(id: Long) =
        taskListRepository.findById(id).orElseThrow { NoSuchElementException("Task list $id not found") }

    fun findAll() = taskListRepository.findAll()

    fun add(taskList: TaskList) = taskListRepository.save(taskList)

    fun addTask(taskListId: Long, taskId: Long): TaskList {
        val taskList = findById(taskListId)
        val task = taskService.findById(taskId)
        taskList.tasks?.add(task)
        return taskListRepository.save(taskList)
    }

    fun update(taskList: TaskList) = taskListRepository.save(taskList)

    fun deleteById(id: Long) = findById(id).let { taskListRepository.delete(it) }
}