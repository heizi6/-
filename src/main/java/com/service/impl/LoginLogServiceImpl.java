package com.service.impl;

import com.mapper.loginLogMapper;
import com.model.LoginLog;
import com.model.LoginLogExample;
import com.service.LoginLogService;
import com.vo.LoginLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private loginLogMapper loginLogMapper;

    @Override
    public int addLoginLog(LoginLog loginLog) {
        return loginLogMapper.insert(loginLog);
    }

    @Override
    public List<LoginLog> getLoginLog(LoginLogVo loginLogVo) {
        LoginLogExample example = new LoginLogExample();
        LoginLogExample.Criteria criteria = example.createCriteria();
        if(loginLogVo.getLoginname()!=null){
            criteria.andLoginnameLike("%"+loginLogVo.getLoginname()+"%");
        }
        if (loginLogVo.getLoginip()!=null){
            criteria.andLoginipLike("%"+loginLogVo.getLoginip()+"%");
        }
        if (loginLogVo.getLoginip()!=null){
            criteria.andLoginipLike("%"+loginLogVo.getLoginip()+"%");
        }
        return loginLogMapper.selectByExample(example);
    }

    @Override
    public int deleteLoginLog(Integer id) {
        return loginLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteBatchTeacher(Integer[] ids) {
        int allCount = 0;
        for (Integer id : ids) {
            int count = loginLogMapper.deleteByPrimaryKey(id);
            allCount += count;
        }
        return allCount;
    }
}
