package com.example.task_manager;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "tasks") // ✅ MongoDB document annotation
public class Task {
    @Id
    private String id;
    private String name;
    private String owner;
    private String command;
    private List<TaskExecution> taskExecutions = new ArrayList<>();

    public Task() {}

    public Task(String name, String owner, String command) {
        this.name = name;
        this.owner = owner;
        this.command = command;
        this.taskExecutions = new ArrayList<>();
    }

    // ✅ Add execution results
    public void addExecution(TaskExecution execution) {
        this.taskExecutions.add(execution);
    }

    // ✅ Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public String getCommand() { return command; }
    public void setCommand(String command) { this.command = command; }

    public List<TaskExecution> getTaskExecutions() { return taskExecutions; }
    public void setTaskExecutions(List<TaskExecution> taskExecutions) { this.taskExecutions = taskExecutions; }
}
