package ru.vladp.manager.scheduler.model

import com.fasterxml.jackson.annotation.JsonIgnore


import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tasks")
class Task(
        @Column(name = "header")
        val header: String,

        @Column(name = "description", length = 4096)
        val description: String,

        @Column(name = "status_active")
        var statusActive: Boolean,

        @Column(name = "modified")
        var modified: LocalDateTime,

        @ManyToOne
        @JsonIgnore
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        val owner: User
) : BaseEntity<Int>(), Serializable