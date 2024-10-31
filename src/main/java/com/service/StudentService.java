package com.service;

import com.model.BaseEntity;
import com.model.User;

import java.util.List;


public interface StudentService {
    List<User> search(String username, Integer working, String classes);

    int deleteStudent(int id);

    int deleteBatchStudent(Integer[] ids);

    int editStudent(User student);

    List<BaseEntity> getAddress(String classes);

    List<BaseEntity> getWorking(String classes);

    List<BaseEntity> getWork(String classes);

    List<User> csearch(String username, Integer working, String classes);
}
