package ru.vladp.manager.backend.tasklogic.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.vladp.manager.backend.tasklogic.TaskService;
import ru.vladp.manager.backend.tasklogic.model.dto.TaskDTO;
import ru.vladp.manager.backend.tasklogic.model.dto.UserDTO;
import ru.vladp.manager.backend.tasklogic.utils.MapperUtil;
import ru.vladp.manager.backend.security.securityDetails.ManagerUserDetails;
import ru.vladp.manager.backend.security.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Планировщик задач", description = "Точка входа для работы с задачами пользователя")
@CrossOrigin("http://localhost:80/")
@RestController
@SecurityRequirement(name = "basicAuth")
public class TaskController extends TaskControllerValidate {

    private final MapperUtil mapperUtil;

    @Autowired
    public TaskController(TaskService taskService, UserService userService, MapperUtil mapperUtil) {
        super(taskService, userService);
        this.mapperUtil = mapperUtil;
    }

    @Operation(summary = "Получение пользователя")
    @GetMapping("/user")
    public UserDTO getUser(@AuthenticationPrincipal ManagerUserDetails ManagerUserDetails) {
        this.checkAuthorization(ManagerUserDetails);

        return mapperUtil.convertToUserDTO(ManagerUserDetails.getUser());
    }

    @Operation(summary = "Получение списка задач пользователя")
    @GetMapping("/tasks")
    public List<TaskDTO> getTasks(@AuthenticationPrincipal ManagerUserDetails ManagerUserDetails) {
        this.checkAuthorization(ManagerUserDetails);

        return taskService.findByOwnerId(ManagerUserDetails.getUser().getId()).stream()
                .map(mapperUtil::convertToTaskDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Создание задачи")
    @PostMapping(path = "/tasks", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<HttpStatus> addTask(@AuthenticationPrincipal ManagerUserDetails ManagerUserDetails,
                                              @ModelAttribute TaskDTO taskDTO) {
        this.checkAuthorization(ManagerUserDetails);

        taskService.save(mapperUtil.convertToTask(taskDTO), userService.findByUserDetails(ManagerUserDetails));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Изменение задачи")
    @PatchMapping(path = "/tasks", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<HttpStatus> updateTask(@AuthenticationPrincipal ManagerUserDetails ManagerUserDetails,
                                                 @ModelAttribute TaskDTO taskDTO) {
        this.checkAuthorization(ManagerUserDetails);
        this.validateAccess(taskDTO, ManagerUserDetails.getUser());

        taskService.update(mapperUtil.convertToTask(taskDTO), userService.findByUserDetails(ManagerUserDetails));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Удаление задачи")
    @DeleteMapping(path = "/tasks", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<HttpStatus> deleteTask(@AuthenticationPrincipal ManagerUserDetails ManagerUserDetails,
                                                 @ModelAttribute TaskDTO taskDTO) {
        this.checkAuthorization(ManagerUserDetails);
        this.validateAccess(taskDTO, ManagerUserDetails.getUser());

        taskService.delete(taskDTO.getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
