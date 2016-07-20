package com.mf.core.service.impl;

import com.mf.common.entity.AccountDto;
import com.mf.common.model.UserInfo;
import com.mf.core.dao.UserDao;
import com.mf.core.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by pony on 2016/7/5.
 */

@Service
public class UserServiceImpl implements UserService {

    /**
     * Logger
     */
    protected Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;



    @Override
    public List<UserInfo> selectUserList() {
        List<UserInfo> users = userDao.queryAllUser();
        return users;
    }


    public UserInfo getUserById(String userId){

        return userDao.queryUser(userId);

    }






}
