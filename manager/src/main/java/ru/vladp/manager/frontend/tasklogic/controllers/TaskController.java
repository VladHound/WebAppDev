package ru.vladp.manager.frontend.tasklogic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.vladp.manager.frontend.tasklogic.TaskService;
import ru.vladp.manager.frontend.tasklogic.model.dto.TaskDTO;
import ru.vladp.manager.frontend.tasklogic.model.dto.UserDTO;
import ru.vladp.manager.frontend.tasklogic.utils.MapperUtil;
import ru.vladp.manager.frontend.security.securityDetails.ManagerUserDetails;
import ru.vladp.manager.frontend.security.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("http://localhost:63342/")
@RestController
public class TaskController extends TaskControllerValidate {

    private final MapperUtil mapperUtil;

    @Autowired
    public TaskController(TaskService taskService, UserService userService, MapperUtil mapperUtil) {
        super(taskService, userService);
        this.mapperUtil = mapperUtil;
    }

    @GetMapping("/user")
    public UserDTO getUser(@AuthenticationPrincipal ManagerUserDetails ManagerUserDetails) {
        this.checkAuthorization(ManagerUserDetails);

        return mapperUtil.convertToUserDTO(ManagerUserDetails.getUser());
    }

    @GetMapping("/tasks")
    public List<TaskDTO> getTasks(@AuthenticationPrincipal ManagerUserDetails ManagerUserDetails) {
        this.checkAuthorization(ManagerUserDetails);

        return taskService.findByOwnerId(ManagerUserDetails.getUser().getId()).stream()
                .map(mapperUtil::convertToTaskDTO)
                .collect(Collectors.toList());
    }

    @PostMapping(path = "/tasks", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<HttpStatus> addTask(@AuthenticationPrincipal ManagerUserDetails ManagerUserDetails,
                                              @ModelAttribute TaskDTO taskDTO) {
        this.checkAuthorization(ManagerUserDetails);

        taskService.save(mapperUtil.convertToTask(taskDTO), userService.findByUserDetails(ManagerUserDetails));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping(path = "/tasks", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<HttpStatus> updateTask(@AuthenticationPrincipal ManagerUserDetails ManagerUserDetails,
                                                 @ModelAttribute TaskDTO taskDTO) {
        this.checkAuthorization(ManagerUserDetails);
        this.validateAccess(taskDTO, ManagerUserDetails.getUser());

        taskService.update(mapperUtil.convertToTask(taskDTO), userService.findByUserDetails(ManagerUserDetails));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(path = "/tasks", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<HttpStatus> deleteTask(@AuthenticationPrincipal ManagerUserDetails ManagerUserDetails,
                                                 @ModelAttribute TaskDTO taskDTO) {
        this.checkAuthorization(ManagerUserDetails);
        this.validateAccess(taskDTO, ManagerUserDetails.getUser());

        taskService.delete(taskDTO.getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
