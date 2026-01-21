package com.example.securetaskmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.securetaskmanager.entity.Task;
import com.example.securetaskmanager.entity.User;
import com.example.securetaskmanager.repository.TaskRepository;
import com.example.securetaskmanager.repository.UserRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // create task
    public Task createTask(String title, String description, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setUser(user);

        return taskRepository.save(task);
    }

    // get task
    public List<Task> getTasks(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return taskRepository.findByUser(user);
    }

    // update task
    public Task updateTask(Long taskId, String title, String description, String username) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // Ownership check
        if (!task.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized to update this task");
        }

        task.setTitle(title);
        task.setDescription(description);

        return taskRepository.save(task);
    }

    // Delete Task
    public void deleteTask(Long taskId, String username) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // Ownership check
        if (!task.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized to delete this task");
        }

        taskRepository.delete(task);
    }
}
