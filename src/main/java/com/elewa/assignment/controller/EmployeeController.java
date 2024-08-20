package com.elewa.assignment.controller;

import com.elewa.assignment.service.DepartmentService;
import com.elewa.assignment.service.TaskService;
import com.elewa.assignment.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private DepartmentService departmentService;
    private UsersService usersService;
    private TaskService taskService;

    public EmployeeController(DepartmentService departmentService, UsersService usersService, TaskService taskService) {
        this.departmentService = departmentService;
        this.usersService = usersService;
        this.taskService = taskService;
    }

    @GetMapping("/profile")
    public String getProfile(@RequestParam Long userId, Model model) {
        model.addAttribute("users", usersService.findById(userId));
        model.addAttribute("tasks", taskService.findByUserId(userId));
        model.addAttribute("departments", departmentService.findAll());
        return "employee/index";
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

//    @GetMapping("/tasks")
//    public String getAllTasks(Model model) {
//        model.addAttribute("tasks", profileService.findAll());
//        model.addAttribute("users", profileService.findAll());
//        model.addAttribute("departments", profileService.findAll());
//        return "employee/index";
//    }
//
//    @PostMapping("/mark-done/{id}")
//    public String markTaskAsDone(@PathVariable("id") Long id) {
//        taskService.markTaskAsDone(id);
//        return "redirect:/employee/tasks";
//    }
//
//    @PostMapping("/mark-in-progress/{id}")
//    public String markTaskInProgress(@PathVariable("id") Long id) {
//        taskService.markTaskInProgress(id);
//        return "redirect:/employee/tasks";
//    }
//
//    @PostMapping("/delete/{id}")
//    public String deleteProfile(@PathVariable("id") Long id) {
//        profileService.deleteProfile(id);
//        return "redirect:/employee/index";
//    }
}
