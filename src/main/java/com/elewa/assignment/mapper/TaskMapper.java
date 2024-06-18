package com.elewa.assignment.mapper;

import com.elewa.assignment.model.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskMapper {
    Task findById(Long id);

    List<Task> findByUserId(Long userId);

    List<Task> findAll();


    void saveTask(Task task);

    void updateTask(Task task);

    void deleteTask(Long id);

    void updateTaskStatus(@Param("id") Long id, @Param("status") String status);
}
