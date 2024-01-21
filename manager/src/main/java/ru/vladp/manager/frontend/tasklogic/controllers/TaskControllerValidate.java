package ru.vladp.manager.frontend.tasklogic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.vladp.manager.frontend.tasklogic.TaskService;
import ru.vladp.manager.frontend.tasklogic.model.dto.TaskDTO;
import ru.vladp.manager.frontend.tasklogic.model.Task;
import ru.vladp.manager.frontend.security.model.User;
import ru.vladp.manager.frontend.security.securityDetails.ManagerUserDetails;
import ru.vladp.manager.frontend.security.services.UserService;

@RequiredArgsConstructor
public class TaskControllerValidate {

    protected final TaskService taskService;
    protected final UserService userService;

    protected void validateAccess(TaskDTO taskDTO, User user) {
        Task foundedTask = taskService.findById(taskDTO.getId());

        if (foundedTask.getOwner().getId() != user.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
    }

    protected void checkAuthorization(ManagerUserDetails ManagerUserDetails) {
        if (ManagerUserDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}

