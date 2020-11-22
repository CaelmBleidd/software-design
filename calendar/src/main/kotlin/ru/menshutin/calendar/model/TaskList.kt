package ru.menshutin.calendar.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import org.hibernate.annotations.CreationTimestamp

@Entity
class TaskList(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String?,
    val description: String?,
    @OneToMany val tasks: MutableList<Task>?,
    @CreationTimestamp val createdAt: LocalDateTime?
)