package ru.vladp.manager.backend.emailgreeting.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vladp.manager.backend.emailgreeting.interfaces.EmailCreator;
import ru.vladp.manager.backend.emailgreeting.model.dto.EmailDTO;
import ru.vladp.manager.backend.security.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailCreatorService {

    private final EmailCreator emailCreator;

    public List<EmailDTO> getEmailMessages(List<User> users) {
        List<EmailDTO> emailMessages = new ArrayList<>();

        for (User user : users) {
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setRecipientAddress(emailCreator.createEmailAddress(user));
            emailDTO.setTitle(emailCreator.createEmailTitle(user));
            emailDTO.setText(emailCreator.createEmailText(user));
            emailMessages.add(emailDTO);
        }
        return emailMessages;
    }
}
