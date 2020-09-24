package com.auth0.samples.authapi.springbootauthupdated.service;

import com.auth0.samples.authapi.springbootauthupdated.task.Task;
import com.auth0.samples.authapi.springbootauthupdated.task.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        return taskRepository.saveAndFlush(task);
    }

    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }

    public Task updateTask(long idToEdit, Task task) {
        Task entryFromDB = taskRepository.getOne(idToEdit);
        entryFromDB.setDescription(task.getDescription());
        return taskRepository.saveAndFlush(entryFromDB);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }
}
