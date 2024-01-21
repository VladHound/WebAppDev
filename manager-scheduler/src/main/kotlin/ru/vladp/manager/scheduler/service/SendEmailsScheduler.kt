package ru.vladp.manager.scheduler.service

import com.fasterxml.jackson.core.JsonProcessingException
import ru.vladp.manager.scheduler.repositories.UserRepository
import ru.vladp.manager.scheduler.service.rabbitmq.RabbitProducer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Service
class SendEmailsScheduler(
        @Autowired private val userRepository: UserRepository,
        @Autowired private val emailCreatorService: EmailCreatorService,
        @Autowired private val rabbitProducer: RabbitProducer,
) {
    private val logger: Log = LogFactory.getLog(this.javaClass)

    @Scheduled(cron = "@midnight")
    fun sendEmails() {
        val users = userRepository.findAll()
        val emailMessages = emailCreatorService.getEmailMessages(users)

        val mapper = jacksonObjectMapper()
        try {
            val json = mapper.writeValueAsString(emailMessages)
            rabbitProducer.sendMessage(json)
        } catch (e: JsonProcessingException) {
            logger.error("Scheduled emails not sent. ${e.message}")
        }
    }
}