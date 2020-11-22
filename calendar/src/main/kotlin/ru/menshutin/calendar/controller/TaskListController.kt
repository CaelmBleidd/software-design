package ru.menshutin.calendar.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.menshutin.calendar.model.TaskList
import ru.menshutin.calendar.service.TaskListService

@RestController
@RequestMapping("/api/v1/taskList")
class TaskListController(private val taskListService: TaskListService) {
    @PostMapping
    fun create(taskList: TaskList) = taskListService.add(taskList)

    @GetMapping("/{id}")
    fun read(@PathVariable id: Long) = taskListService.findById(id)

    @PutMapping
    fun update(taskList: TaskList) = taskListService.update(taskList)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = taskListService.deleteById(id)

    @GetMapping
    fun findAll() = taskListService.findAll()

    @PostMapping("/{listId}/add/{taskId}")
    fun addTask(@PathVariable listId: Long, @PathVariable taskId: Long) = taskListService.addTask(listId, taskId)
}