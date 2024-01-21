package ru.vladp.manager.scheduler.interfaces

import ru.vladp.manager.scheduler.model.User

interface EmailCreator {
    fun createEmailAddress(user: User): String
    fun createEmailTitle(user: User): String
    fun createEmailText(user: User): String
}