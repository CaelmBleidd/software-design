package ru.menshutin.calendar.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.menshutin.calendar.model.Task

@Repository
interface TaskRepository: JpaRepository<Task, Long>