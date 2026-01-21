package com.example.securetaskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securetaskmanager.entity.Task;
import com.example.securetaskmanager.entity.User;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}
