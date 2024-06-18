package com.elewa.assignment.service;

import com.elewa.assignment.mapper.TaskMapper;
import com.elewa.assignment.model.Department;
import com.elewa.assignment.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    public TaskService(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    private TaskMapper taskMapper;
    public List<Task> findAll() {
        return taskMapper.findAll();
    }

    public void saveTask(Task task) {
        taskMapper.saveTask(task);
    }

    public List<Task> findByUserId(Long userId) {
        return taskMapper.findByUserId(userId);
    }
    public void updateTask(Task task) {
        taskMapper.updateTask(task);
    }

    public void deleteTask(Long taskId) {
        taskMapper.deleteTask(taskId);
    }

    public Task findById(Long taskId) {
        return taskMapper.findById(taskId);
    }

    public void markTaskAsDone(Long id) {
        taskMapper.updateTaskStatus(id, "DONE");
    }

    public void markTaskInProgress(Long id) {
        taskMapper.updateTaskStatus(id, "IN_PROGRESS");
    }
}
