package com.auth0.samples.authapi.springbootauthupdated.task;

import com.auth0.samples.authapi.springbootauthupdated.service.TaskService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Method that adds tasks
     * @param task task which should be added
     */
    @PostMapping
    public void addTask(@RequestBody Task task) {
        taskService.createTask(task);
    }

    /**
     * Finds all tasks
     * @return list with all tasks
     */
    @GetMapping
    public List<Task> getTasks() {
        return taskService.findAll();
    }

    /**
     * Updates task
     * @param task task which should be updated
     */
    @PutMapping("/{id}")
    public void editTask(@RequestBody Task task) {
        System.out.println(task.getDescription());
        taskService.updateTask(task);
    }

    /**
     * Deletes task
     * @param id id of the task which should be deleted
     */
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);
    }
}