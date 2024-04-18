package com.example.demo.services;

import com.example.demo.entity.Task;

import java.util.List;

public interface TaskService {
    Task getTaskById(Long taskId);
    List<String> getAllTaskTitles(); // Mise à jour pour obtenir tous les titres des tâches

    Task getTaskByTitle(String taskTitle); // Mise à jour pour obtenir une tâche par son titre
    // Ajoutez d'autres méthodes au besoin
}
