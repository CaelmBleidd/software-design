package ru.menshutin.calendar.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import org.hibernate.annotations.CreationTimestamp

@Entity
data class Task(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String?,
    val description: String?,
    @CreationTimestamp val createdAt: LocalDateTime?,
    var completed: Boolean = false
)