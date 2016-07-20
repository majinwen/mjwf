package com.mf.core.service;

import com.mf.common.entity.AccountDto;
import com.mf.common.model.UserInfo;
import com.mf.core.exception.BusinessException;

import java.util.List;

/**
 * Created by pony on 2016/7/5.
 */


public interface UserService {

    public List<UserInfo> selectUserList();


    UserInfo getUserById(String userId);

}
