package com.example.task_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    // ✅ Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // ✅ Get task by ID
    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    // ✅ Find tasks by name
    public List<Task> findTasksByName(String name) {
        return taskRepository.findByNameContaining(name);
    }

    // ✅ Create a task
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    // ✅ Execute a task (Runs shell command and stores execution output)
    public Task executeTask(String id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            String executionOutput = "";

            try {
                // ✅ Detect OS and use appropriate shell
                ProcessBuilder builder;
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    builder = new ProcessBuilder("cmd.exe", "/c", task.getCommand());
                } else {
                    builder = new ProcessBuilder("/bin/sh", "-c", task.getCommand());
                }

                builder.redirectErrorStream(true);
                Process process = builder.start();

                // ✅ Capture command output
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                executionOutput = output.toString().trim();

                process.waitFor();
            } catch (IOException | InterruptedException e) {
                executionOutput = "Error executing command: " + e.getMessage();
            }

            // ✅ Create TaskExecution entry
            TaskExecution execution = new TaskExecution(
                LocalDateTime.now(),
                LocalDateTime.now().plusSeconds(1),
                executionOutput
            );

            // ✅ Add execution record and save
            task.addExecution(execution);
            return taskRepository.save(task);
        } else {
            throw new RuntimeException("Task not found!");
        }
    }

    // ✅ Update task (Only updates name, owner, and command)
    public Task updateTask(String id, Task updatedTask) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();

            // ✅ Update only allowed fields
            if (updatedTask.getName() != null) {
                task.setName(updatedTask.getName());
            }
            if (updatedTask.getOwner() != null) {
                task.setOwner(updatedTask.getOwner());
            }
            if (updatedTask.getCommand() != null) {
                task.setCommand(updatedTask.getCommand());
            }

            return taskRepository.save(task);
        } else {
            throw new RuntimeException("Task not found!");
        }
    }

    // ✅ Delete task
    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }
}
