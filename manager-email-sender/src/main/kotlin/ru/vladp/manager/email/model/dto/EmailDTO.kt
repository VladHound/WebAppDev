package ru.vladp.manager.email.model.dto

data class EmailDTO(
        val recipientAddress: String,
        val title: String,
        val text: String
)


