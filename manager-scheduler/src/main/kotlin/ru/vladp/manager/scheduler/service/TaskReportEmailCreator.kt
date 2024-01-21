package ru.vladp.manager.scheduler.service

import ru.vladp.manager.scheduler.interfaces.EmailCreator
import ru.vladp.manager.scheduler.model.Task
import ru.vladp.manager.scheduler.model.User
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Service
class TaskReportEmailCreator : EmailCreator {
    override fun createEmailAddress(user: User): String {
        return user.email
    }

    override fun createEmailTitle(user: User): String {
        return "Отчет о выполнении задач пользователем \"${user.email}\" " +
                "за ${LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))}"

    }

    override fun createEmailText(user: User): String {
        val emailText = StringBuilder()
        addUncompletedTasksInfo(emailText, user.tasks);
        addCompletedTasksInfo(emailText, user.tasks);
        return emailText.toString()
    }
}

private fun addCompletedTasksInfo(emailText: StringBuilder, tasks: MutableList<Task>) {
    // Если есть задачи, выполненные в течение предыдущих 24 часов -> добавляем информацию о них в текст письма
    val completedTaskPerDayHeaders = tasks
            .filter { ChronoUnit.HOURS.between(it.modified, LocalDateTime.now()) < 24 }
            .filter { !it.statusActive }
            .map { it.header }

    val completedTaskPerDayCount = completedTaskPerDayHeaders.size
    if (completedTaskPerDayCount > 0) {
        emailText.append("Количество выполненных задач за сегодня: $completedTaskPerDayCount")
                .append("!\n\nЗа сегодня выполнены следующие задачи:\n")
        completedTaskPerDayHeaders.forEach {
            emailText.append("— $it\n")
        }
    }
}

private fun addUncompletedTasksInfo(emailText: StringBuilder, tasks: MutableList<Task>) {
    // Если есть невыполненные задачи -> добавляем информацию о них в текст письма
    val uncompletedTaskHeaders = tasks
            .filter { it.statusActive }
            .map { it.header }

    val uncompletedTaskCount = uncompletedTaskHeaders.size
    if (uncompletedTaskCount > 0) {
        emailText.append("Общее количество невыполненных задач: $uncompletedTaskCount")
                .append("!\n\nСписок невыполненных задач (выводится не более 5):\n")
        uncompletedTaskHeaders.take(5).forEach { emailText.append("— $it\n") }
        emailText.append("\n")
    }
}