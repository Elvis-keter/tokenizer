package com.elewa.assignment.controller;

import com.elewa.assignment.model.Task;
import com.elewa.assignment.service.TaskService;
import com.elewa.assignment.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager/tasks")
public class ManagerController {
    public ManagerController(TaskService taskService, UsersService usersService) {
        this.taskService = taskService;
        this.usersService = usersService;
    }

    private TaskService taskService;

    private UsersService usersService;

    @GetMapping
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("users", usersService.findAll());
        return "tasks/index";
    }

    @GetMapping("/new")
    public String showNewTaskForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("users", usersService.findAll());
        return "tasks/new";
    }

    @PostMapping
    public String saveTask(@ModelAttribute Task task) {
        taskService.saveTask(task);
        return "redirect:/manager/tasks";
    }

    @GetMapping("/edit/{id}")
    public String showEditTaskForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("task", taskService.findById(id));
        model.addAttribute("users", usersService.findAll());
        return "tasks/edit";
    }

    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable("id") Long id, @ModelAttribute Task task) {
        task.setId(id);
        taskService.updateTask(task);
        return "redirect:/manager/tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return "redirect:/manager/tasks";
    }
}
