package ru.vladp.manager.scheduler.service

import ru.vladp.manager.scheduler.interfaces.EmailCreator
import ru.vladp.manager.scheduler.model.User
import ru.vladp.manager.scheduler.model.dto.EmailDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmailCreatorService(@Autowired private val emailCreator: EmailCreator) {
    fun getEmailMessages(users: MutableList<User>): MutableList<EmailDTO> {
        val emailMessages: MutableList<EmailDTO> = mutableListOf()
        for (user in users) {
            if (user.tasks.size > 0) {
                val emailDTO = EmailDTO(
                    emailCreator.createEmailAddress(user),
                    emailCreator.createEmailTitle(user),
                    emailCreator.createEmailText(user)
                )
                emailMessages.add(emailDTO)
            }
        }
        return emailMessages
    }
}