package ru.vladp.manager.backend.emailgreeting.services;

import org.springframework.stereotype.Service;
import ru.vladp.manager.backend.emailgreeting.interfaces.EmailCreator;
import ru.vladp.manager.backend.security.model.User;

@Service
public class GreetingEmailCreator implements EmailCreator {

    @Override
    public String createEmailAddress(User user) {
        return user.getEmail();
    }

    @Override
    public String createEmailTitle(User user) {
        return "Спасибо за регистрацию в сервисе <Manager>!";
    }

    @Override
    public String createEmailText(User user) {
        return "Добро пожаловать в наш сервис по отслеживанию задач! Желаем приятного использования!\n" +
                "Ваш логин для авторизации: " + user.getEmail();
    }
}
