package com.service.impl;

import com.mapper.CourseMapper;
import com.model.Course;
import com.model.CourseExample;
import com.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseMapper courseMapper;

    @Override
    public int addCourse(Course course) {
        return courseMapper.insertSelective(course);
    }

    @Override
    public List<Course> search(String id, String name) {
        CourseExample example = new CourseExample();
        CourseExample.Criteria criteria = example.createCriteria();
        if (id!=null){
            criteria.andIdLike("%"+id+"%");
        }
        if (name!=null){
            criteria.andNameLike("%"+name+"%");
        }
        List<Course> courseList = courseMapper.selectByExample(example);
        return courseList;
    }

    @Override
    public List<Course> searchByName(String name) {
        CourseExample example = new CourseExample();
        CourseExample.Criteria criteria = example.createCriteria();
        criteria.andNameLike("%"+name+"%");
        List<Course> courseList = courseMapper.selectByExample(example);
        return courseList;
    }

    @Override
    public int deleteCourse(String id) {
        return courseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteBatchCourse(String[] ids) {
        int allCount = 0;
        for (String id : ids) {
            int count = courseMapper.deleteByPrimaryKey(id);
            allCount += count;
        }
        return allCount;
    }

    @Override
    public int editCourse(Course course) {
        return courseMapper.updateByPrimaryKeySelective(course);
    }
}
