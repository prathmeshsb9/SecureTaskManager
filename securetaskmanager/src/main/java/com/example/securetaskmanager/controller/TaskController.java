package com.example.securetaskmanager.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.securetaskmanager.entity.Task;
import com.example.securetaskmanager.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //  CREATE TASK
    @PostMapping
    public Task createTask(
            @RequestBody Task task,
            Principal principal
    ) {
        return taskService.createTask(
                task.getTitle(),
                task.getDescription(),
                principal.getName()
        );
    }

    //  GET ALL TASKS
    @GetMapping
    public List<Task> getTasks(Principal principal) {
        return taskService.getTasks(principal.getName());
    }

    //  UPDATE TASK
    @PutMapping("/{id}")
    public Task updateTask(
            @PathVariable Long id,
            @RequestBody Task task,
            Principal principal
    ) {
        return taskService.updateTask(
                id,
                task.getTitle(),
                task.getDescription(),
                principal.getName()
        );
    }

    //  DELETE TASK
    @DeleteMapping("/{id}")
    public void deleteTask(
            @PathVariable Long id,
            Principal principal
    ) {
        taskService.deleteTask(id, principal.getName());
    }
}
