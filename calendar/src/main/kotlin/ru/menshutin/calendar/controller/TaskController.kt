package ru.menshutin.calendar.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.menshutin.calendar.model.Task
import ru.menshutin.calendar.service.TaskService

@RestController
@RequestMapping("/api/v1/task")
class TaskController(private val taskService: TaskService) {
    @PostMapping
    fun create(task: Task) = taskService.add(task)

    @GetMapping("/{id}")
    fun read(@PathVariable id: Long) = taskService.findById(id)

    @PutMapping
    fun update(@RequestBody task: Task) = taskService.update(task)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = taskService.deleteById(id)

    @GetMapping
    fun findAll() = taskService.findAll()
}