package com.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.model.BaseEntity;
import com.model.User;
import com.service.StudentService;
import com.utils.MD5Util;
import com.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @RequestMapping("/studentList")
    public String studentList(){
        return "admin/studentList";
    }

    @RequestMapping("/getWorking")
    public String getWorking(){
        return "admin/working";
    }

    @RequestMapping("/getAddress")
    public String getAddress(){
        return "admin/address";
    }

    @RequestMapping("/getWork")
    public String getWork(){
        return "admin/work";
    }

    @RequestMapping("/classStudentList")
    public String classStudentList(){
        return "classAdmin/studentList";
    }

    /**
     * 根据条件模糊查询学生
     * @param username
     * @param limit
     * @param page
     * @return
     */
    @RequestMapping("getStudent")
    @ResponseBody
    public Map getStudent(String username,Integer working,String classes, int limit,int page){
        Map map = new HashMap();
        PageHelper.startPage(page,limit);
        List<User> studentList = studentService.search(username,working,classes);
        PageInfo pageInfo = new PageInfo(studentList,5);
        map.put("count",pageInfo.getTotal());
        map.put("code",0);
        map.put("data",studentList);
        return map;
    }

    /**
     * 根据id删除学生
     * @param id
     * @return
     */
    @RequestMapping("/deleteStudent")
    @ResponseBody
    public Map deleteStudent(int id){
        Map map = new HashMap();
        int i = studentService.deleteStudent(id);
        if (i>0) {
            map.put("message","成功删除学号为【"+id+"】的学生");
        }else{
            map.put("message","删除失败");
        }
        return map;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/deleteBatchStudent")
    @ResponseBody
    public Map deleteBatchStudent(Integer []ids){
        Map map = new HashMap();
        int count = studentService.deleteBatchStudent(ids);
        if(count == ids.length){
            map.put("message","删除这些学生成功");
        }else {
            map.put("message","删除这些学生失败");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/editStudent")
    public Map editStudent(User student){
        Map map = new HashMap();
        student.setPassword(MD5Util.digest(student.getPassword()));
        int i = studentService.editStudent(student);
        if(i>0){
            map.put("message","修改【"+student.getUsername()+"】学生成功");
        }else {
            map.put("message","修改失败");
        }
        return map;
    }

    /**
     * 毕业生就业地址
     * @return
     */
    @RequestMapping("/studentAddress")
    @ResponseBody
    public List<BaseEntity> StudentAddress(@RequestParam("classes") String classes){
        return studentService.getAddress(classes);
    }

    /**
     * 毕业生是否就业
     * @return
     */
    @RequestMapping("/studentWorking")
    @ResponseBody
    public List<BaseEntity> studentWorking(@RequestParam("classes") String classes){
        return studentService.getWorking(classes);
    }

    @RequestMapping("/studentWork")
    @ResponseBody
    public List<BaseEntity> StudentWork(@RequestParam("classes") String classes){
        return studentService.getWork(classes);
    }

    /**
     * 班级管理员获取学生就业信息
     * @param username
     * @param working
     * @param limit
     * @param page
     * @return
     */
    @RequestMapping("getClassStudent")
    @ResponseBody
    public Map getClassStudent(String username,Integer working, int limit,int page){
        Map map = new HashMap();
        PageHelper.startPage(page,limit);
        User user =(User) WebUtils.getHttpSession().getAttribute("currentUser");
        String classes = user.getClasses();
        List<User> studentList = studentService.csearch(username,working,classes);
        PageInfo pageInfo = new PageInfo(studentList,5);
        map.put("count",pageInfo.getTotal());
        map.put("code",0);
        map.put("data",studentList);
        return map;
    }
}
