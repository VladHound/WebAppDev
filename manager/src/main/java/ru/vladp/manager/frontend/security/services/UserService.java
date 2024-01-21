package ru.vladp.manager.frontend.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import ru.vladp.manager.frontend.security.model.User;
import ru.vladp.manager.frontend.security.repositories.UserRepository;
import ru.vladp.manager.frontend.security.securityDetails.ManagerUserDetails;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findById(int userId) {
        Optional<User> user = userRepository.findById(userId);
        return user;
    }

    public User findByUserDetails (ManagerUserDetails ManagerUserDetails) {
        int userId = ManagerUserDetails.getUser().getId();
        return findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));
    }
}
