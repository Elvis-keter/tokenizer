package com.elewa.assignment.service;

import com.elewa.assignment.mapper.DepartmentMapper;
import com.elewa.assignment.model.Department;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    public DepartmentService(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    private DepartmentMapper departmentMapper;

    public List<Department> findAll() {return departmentMapper.findAll();}

    public Department findById(Long departmentId) {return departmentMapper.findById(departmentId);}
    public void saveDepartment(Department department) {
        departmentMapper.saveDepartment(department);
    }

    public void updateDepartment(Department department) {
        departmentMapper.updateDepartment(department);
    }

    public void deleteDepartment(Long departmentId) {
        departmentMapper.deleteDepartment(departmentId);
    }

    public void moveEmployee(Long employeeId, Long newDepartmentId) {
        departmentMapper.moveEmployee(employeeId, newDepartmentId);
    }

    public void removeEmployee(Long employeeId) {
        departmentMapper.removeEmployee(employeeId);
    }
}
