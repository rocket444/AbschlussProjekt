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

    /**
     * Method that creates a task
     * @param task task which should be crated
     */
    public Task createTask(Task task) {
        return taskRepository.saveAndFlush(task);
    }

    /**
     * Deletes task
     * @param id id of the task which should be deleted
     */
    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }

    /**
     * Updates task
     * @param task task which should be updated
     */
    public Task updateTask(Task task) {
        Task entryFromDB = taskRepository.getOne(task.getId());
        entryFromDB.setDescription(task.getDescription());
        return taskRepository.saveAndFlush(entryFromDB);
    }

    /**
     * Finds all tasks
     * @return list with all tasks
     */
    public List<Task> findAll() {
        return taskRepository.findAll();
    }
}
