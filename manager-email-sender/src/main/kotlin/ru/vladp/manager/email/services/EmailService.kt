package ru.vladp.manager.email.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(
        @Value("\${sender.address}") private val senderAddress: String,
        @Autowired private val emailSender: JavaMailSender
) {
    fun sendSimpleMessage(recipientAddress: String, title: String, text: String) {
        val simpleMail = SimpleMailMessage()
        simpleMail.from = senderAddress
        simpleMail.setTo(recipientAddress)
        simpleMail.subject = title
        simpleMail.text = text
        emailSender.send(simpleMail)
    }
}