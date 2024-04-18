package com.example.demo.services;

import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    @Override
    public List<String> getAllTaskTitles() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(Task::getTitle).collect(Collectors.toList());
    }

    @Override
    public Task getTaskByTitle(String taskTitle) {
        // Implémentation pour récupérer une tâche par son titre depuis le repository
        return taskRepository.findByTitle(taskTitle).orElse(null);
    }
}
