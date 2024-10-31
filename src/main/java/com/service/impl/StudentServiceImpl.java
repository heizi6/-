package com.service.impl;

import com.mapper.UserMapper;
import com.model.BaseEntity;
import com.model.User;
import com.model.UserExample;
import com.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> search(String username, Integer working, String classes) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameLike("%"+username+"%");
        criteria.andRoleEqualTo(3);
        if(working != null){
            criteria.andWorkingEqualTo(working);
        }
        if(classes != null){
            if(classes.equals("无")){

            }else {
                criteria.andClassesLike(classes);
            }
        }
        List<User> userList = userMapper.selectByExample(example);
        return userList;
    }

    @Override
    public int deleteStudent(int id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteBatchStudent(Integer[] ids) {
        int allCount = 0;
        for (Integer id : ids) {
            int count = userMapper.deleteByPrimaryKey(id);
            allCount += count;
        }
        return allCount;
    }

    @Override
    public int editStudent(User student) {
        return userMapper.updateByPrimaryKeySelective(student);
    }

    @Override
    public List<BaseEntity> getAddress(String classes) {
        if(classes.equals("无")||classes.equals("")){
            return userMapper.getAddress();
        }else {
            return userMapper.getAddressWithClasses(classes);
        }
    }

    @Override
    public List<BaseEntity> getWorking(String classes) {
        if(classes==null||classes.equals("")||classes.equals("无")){
            return userMapper.getWorking();
        }else {
            return userMapper.getWorkingWithClasses(classes);
        }
    }

    @Override
    public List<BaseEntity> getWork(String classes) {
        if(classes.equals("无")||classes.equals("")){
            return userMapper.getWork();
        }else {
            return userMapper.getWorkWithClasses(classes);
        }
    }

    @Override
    public List<User> csearch(String username, Integer working, String classes) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameLike("%"+username+"%");
        criteria.andClassesLike(classes);
        criteria.andRoleEqualTo(3);
        if(working != null){
            criteria.andWorkingEqualTo(working);
        }
        List<User> userList = userMapper.selectByExample(example);
        return userList;
    }
}
