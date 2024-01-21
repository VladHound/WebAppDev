package ru.vladp.manager.email.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig(
        @Value("\${rabbit.exchange}") private val topicExchangeName: String,
        @Value("\${rabbit.queue}") private val queueName: String,
        @Value("\${rabbit.routing-key}") private val routingKey: String
) {
    @Bean
    fun queue() = Queue(queueName, false)

    @Bean
    fun exchange() = TopicExchange(topicExchangeName)

    @Bean
    fun binding(queue: Queue, exchange: TopicExchange): Binding {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey)
    }
}