package ru.vladp.manager.scheduler.model

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
        @Column(name = "email")
        val email: String,

        @Column(name = "password")
        val password: String,

        @Column(name = "role")
        val role: String,

        @OneToMany(cascade = [CascadeType.ALL], mappedBy = "owner")
        val tasks: MutableList<Task> = mutableListOf()
) : BaseEntity<Int>(), Serializable