package com.example.task_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    public List<Task> findTasksByName(String name) {
        return taskRepository.findByNameContaining(name);
    }

    public Task saveTask(Task task) {
        if (task.getTaskExecutions() == null) {
            task.setTaskExecutions(new ArrayList<>()); // Ensure it's not null
        }
        return taskRepository.save(task);
    }

    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }

    public Task executeTask(String id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
    
            if (task.getCommand() == null || task.getCommand().trim().isEmpty()) {
                throw new RuntimeException("Command is missing for task: " + id);
            }
    
            try {
                Date startTime = new Date(); // Start time
                ProcessBuilder processBuilder = new ProcessBuilder(task.getCommand().split(" "));
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();
    
                // Capture the output
                StringBuilder output = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
    
                process.waitFor();
                Date endTime = new Date(); // End time
    
                // Create execution record
                TaskExecution taskExecution = new TaskExecution(startTime, endTime, output.toString().trim());
    
                // Ensure taskExecutions list is initialized
                if (task.getTaskExecutions() == null) {
                    task.setTaskExecutions(new ArrayList<>());
                }
                task.getTaskExecutions().add(taskExecution);
    
                return taskRepository.save(task);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    }

