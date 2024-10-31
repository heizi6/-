package com.service;

import com.model.Course;

import java.util.List;


public interface CourseService {
    int addCourse(Course course);

    List<Course> search(String id, String name);

    List<Course> searchByName(String name);

    int deleteCourse(String id);

    int deleteBatchCourse(String[] ids);

    int editCourse(Course course);
}
