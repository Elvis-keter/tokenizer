package com.elewa.assignment.controller;

import com.elewa.assignment.model.Task;
import com.elewa.assignment.model.Users;
import com.elewa.assignment.service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/employee")
public class EmployeeController {
    public EmployeeController(TaskService taskService) {
        this.taskService = taskService;
    }

    //    @GetMapping("/tasks")
//    public String viewTasks(Principal principal, Model model) {
//        Users users = (Users) ((Authentication) principal).getPrincipal();
//        model.addAttribute("tasks", taskService.findByUserId(users.getId()));
//        return "employee-tasks";
//    }
//
//    @PostMapping("/tasks/update")
//    public String updateTaskStatus(@RequestParam Long taskId, @RequestParam String status) {
//        Task task = taskService.findById(taskId);
//        task.setStatus(status);
//        taskService.updateTask(task);
//        return "redirect:/employee/tasks";
//    }
    TaskService taskService;

    @GetMapping("/tasks")
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "index";
    }

    @PostMapping("/mark-done/{id}")
    public String markTaskAsDone(@PathVariable("id") Long id) {
        taskService.markTaskAsDone(id);
        return "redirect:/employee/tasks";
    }

    @PostMapping("/mark-in-progress/{id}")
    public String markTaskInProgress(@PathVariable("id") Long id) {
        taskService.markTaskInProgress(id);
        return "redirect:/employee/tasks";
    }
}
