package ru.vladp.manager.backend.emailgreeting.interfaces;

import ru.vladp.manager.backend.security.model.User;

public interface EmailCreator {

    String createEmailAddress(User user);

    String createEmailTitle(User user);

    String createEmailText(User user);
}
