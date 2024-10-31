package com.mapper;

import com.model.BaseEntity;
import com.model.User;
import com.model.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<BaseEntity> getWork();

    List<BaseEntity> getWorkWithClasses(String classes);

    List<BaseEntity> getAddress();

    List<BaseEntity> getAddressWithClasses(String classes);

    List<BaseEntity> getWorking();

    List<BaseEntity> getWorkingWithClasses(String classes);
}