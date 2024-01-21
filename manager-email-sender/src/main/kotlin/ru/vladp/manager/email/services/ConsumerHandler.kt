package ru.vladp.manager.email.services

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ru.vladp.manager.email.interfaces.Mailer
import ru.vladp.manager.email.model.dto.EmailDTO
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConsumerHandler(@Autowired private val mailer: Mailer) {
    private val logger: Log = LogFactory.getLog(this.javaClass)

    fun handle(message: String) {
        val objectMapper = jacksonObjectMapper()

        var emailMessages = mutableListOf<EmailDTO>()
        try {
            emailMessages = objectMapper.readValue(message)
        } catch (e: JsonProcessingException) {
            logger.info("JSON parsing failed: ${e.message}")
        }

        for (emailMessage in emailMessages) {
            val emailAddress: String = emailMessage.recipientAddress
            try {
                mailer.send(emailAddress, emailMessage.title, emailMessage.text)
                logger.info("Email has been sent to address: ${emailMessage.recipientAddress}")
            } catch (e: Exception) {
                logger.error("Email sending to address \"$emailAddress\" failed. ${e.message}")
            }
        }
    }
}