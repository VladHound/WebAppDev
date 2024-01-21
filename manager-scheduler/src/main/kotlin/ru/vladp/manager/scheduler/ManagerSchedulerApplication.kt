package ru.vladp.manager.scheduler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class ManagerSchedulerApplication

fun main(args: Array<String>) {
    runApplication<ManagerSchedulerApplication>(*args)
}
