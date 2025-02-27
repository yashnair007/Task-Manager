package com.example.task_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Get all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // Get task by ID
    @GetMapping("/{id}")
    public Optional<Task> getTaskById(@PathVariable String id) {
        return taskService.getTaskById(id);
    }

    // Find task by name
    @GetMapping("/search")
    public List<Task> findTasksByName(@RequestParam String name) {
        return taskService.findTasksByName(name);
    }

    // Create a new task (POST)
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.saveTask(task);
    }

    // Create or update a task (PUT)
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable String id, @RequestBody Task task) {
    task.setId(id); // Ensure the ID is set
    return taskService.saveTask(task);
}

    // Delete a task by ID
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
    }

    @PutMapping("/{id}/execute")
public Task executeTask(@PathVariable String id) {
    id = id.trim(); // Ensure no newline or space issues
    return taskService.executeTask(id);
}

}
