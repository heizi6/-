package com.service.impl;

import com.mapper.UserMapper;
import com.model.User;
import com.model.UserExample;
import com.service.UserService;
import com.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class UserserviceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> selectByUP(int number, String password) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNumberEqualTo(number);
        criteria.andPasswordEqualTo(password);
        criteria.andStatusEqualTo(0);
        return userMapper.selectByExample(userExample);
    }

    @Override
    public User selectUserById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public List<User> selectByExample(UserVo userVo,Integer role) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        criteria.andUsernameLike("%"+userVo.getUsername()+"%");
        if(userVo.getClasses() != null && userVo.getClasses() != "") {
            criteria.andClassesLike("%" + userVo.getClasses() + "%");
        }
        if(userVo.getSex() != null){
            criteria.andSexEqualTo(userVo.getSex());
        }
        if (userVo.getRole() != null) {
            criteria.andRoleEqualTo(userVo.getRole());
        }
        if(userVo.getStatus() != null){
            criteria.andStatusEqualTo(userVo.getStatus());
        }
        criteria.andRoleGreaterThan(role);
        return userMapper.selectByExample(example);
    }

    @Override
    public int deleteUsers(int id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int deleteBatchUser(Integer[] ids) {
        int totalCount = 0;
        for (Integer id : ids) {
            int count = userMapper.deleteByPrimaryKey(id);
            totalCount += count;
        }
        return totalCount;
    }

    @Override
    public int addUser(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public List<User> selectByClassExample(UserVo userVo) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameLike("%"+userVo.getUsername()+"%");
        criteria.andClassesLike(userVo.getClasses());
        if(userVo.getSex() != null){
            criteria.andSexEqualTo(userVo.getSex());
        }
        if(userVo.getStatus() != null){
            criteria.andStatusEqualTo(userVo.getStatus());
        }
        criteria.andRoleEqualTo(3);

        return userMapper.selectByExample(example);
    }
    @Override
    public List<User> selectMeByExample(UserVo userVo) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(userVo.getId());
        return userMapper.selectByExample(example);
    }

}
