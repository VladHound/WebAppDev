package ru.vladp.manager.scheduler.service.rabbitmq

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class RabbitProducer(
        @Autowired
        private val rabbitTemplate: RabbitTemplate,
        @Value("\${rabbit.exchange}")
        private val topicExchangeName: String,
        @Value("\${rabbit.routing-key}")
        private val routingKey: String
) {
    private val logger: Log = LogFactory.getLog(this.javaClass)

    fun sendMessage(message: String) {
        try {
            rabbitTemplate.convertAndSend(topicExchangeName, routingKey, message)
            logger.info("Scheduled email messages has been sent to RabbitMQ queue")
        } catch (e: Exception) {
            logger.error("Scheduled emails not sent. ${e.message}")
        }
    }
}