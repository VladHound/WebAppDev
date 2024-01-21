package ru.vladp.manager.frontend.emailgreeting.interfaces;

import ru.vladp.manager.frontend.security.model.User;

public interface EmailCreator {

    String createEmailAddress(User user);

    String createEmailTitle(User user);

    String createEmailText(User user);
}
