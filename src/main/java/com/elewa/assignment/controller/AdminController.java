package com.elewa.assignment.controller;

import com.elewa.assignment.model.Department;
import com.elewa.assignment.model.Users;
import com.elewa.assignment.service.DepartmentService;
import com.elewa.assignment.service.TaskService;
import com.elewa.assignment.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private DepartmentService departmentService;

    public AdminController(DepartmentService departmentService, UsersService usersService, TaskService taskService) {
        this.departmentService = departmentService;
        this.usersService = usersService;
        this.taskService = taskService;
    }

    private UsersService usersService;
    private TaskService taskService;

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", usersService.findAll());
        return "users/index";
    }

    @GetMapping("/users/assign-manager/{id}")
    public String assignManagerRole(@PathVariable Long id) {
        usersService.assignManagerRole(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/assign-employee/{id}")
    public String assignEmployeeRole(@PathVariable Long id) {
        usersService.assignEmployeeRole(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new Users());
        return "users/new";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute Users users) {
        usersService.saveUser(users);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        usersService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/departments")
    public String getAllDepartments(Model model) {
        model.addAttribute("departments", departmentService.findAll());
        return "departments/index";
    }

    @GetMapping("/departments/new")
    public String showNewDepartmentForm(Model model) {
        model.addAttribute("department", new Department());
        return "departments/new";
    }

    @PostMapping("/departments")
    public String saveDepartment(@ModelAttribute Department department) {
        departmentService.saveDepartment(department);
        return "redirect:/admin/departments";
    }

    @GetMapping("/departments/edit/{id}")
    public String showEditDepartmentForm(@PathVariable Long id, Model model) {
        model.addAttribute("department", departmentService.findById(id));
        return "departments/edit";
    }

    @PostMapping("/departments/update/{id}")
    public String updateDepartment(@PathVariable Long id, @ModelAttribute Department department) {
        department.setId(id);
        departmentService.updateDepartment(department);
        return "redirect:/admin/departments";
    }

    @GetMapping("/departments/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return "redirect:/admin/departments";
    }

//    @GetMapping("/empl")
//    public String getAllEmployees(Model model) {
//
//    }
//
//    @PostMapping("/move-employee")
//    public String moveEmployee(@RequestParam Long employeeId, @RequestParam Long newDepartmentId) {
//        departmentService.moveEmployee(employeeId, newDepartmentId);
//        return "redirect:/admin/employees";
//    }
//
//    @PostMapping("/remove-employee")
//    public String removeEmployee(@RequestParam Long employeeId) {
//        departmentService.removeEmployee(employeeId);
//        return "redirect:/admin/employees";
//    }
}
