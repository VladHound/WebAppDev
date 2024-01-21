package ru.vladp.manager.email

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ManagerEmailSenderApplication

fun main(args: Array<String>) {
    runApplication<ManagerEmailSenderApplication>(*args)
}
