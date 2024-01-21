package ru.vladp.manager.email.interfaces

interface Mailer {
    fun send(recipientAddress: String, title: String, text: String)
}