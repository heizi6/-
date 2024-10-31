package com.service;

import com.model.User;
import com.vo.UserVo;

import java.util.List;


public interface UserService {
    List<User> selectByUP(int number, String password);

    User selectUserById(int id);

    int updateByPrimaryKeySelective(User user);

    List<User> selectByExample(UserVo userVo,Integer role);

    int deleteUsers(int id);

    int updateUser(User user);

    int deleteBatchUser(Integer[] ids);

    int addUser(User user);

    List<User> selectByClassExample(UserVo userVo);

    List<User> selectMeByExample(UserVo userVo);
}
