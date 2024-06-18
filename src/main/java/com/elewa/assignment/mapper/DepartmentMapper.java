package com.elewa.assignment.mapper;

import com.elewa.assignment.model.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    List<Department> findAll();

    Department findById(Long id);
    void saveDepartment(Department department);
    void updateDepartment(Department department);
    void deleteDepartment(Long id);

    void moveEmployee(@Param("employeeId") Long employeeId, @Param("newDepartmentId") Long newDepartmentId);

    void removeEmployee(Long employeeId);
}
