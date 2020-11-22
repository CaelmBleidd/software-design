package ru.menshutin.calendar.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.menshutin.calendar.model.TaskList

@Repository
interface TaskListRepository: JpaRepository<TaskList, Long>