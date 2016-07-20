package com.mf.core.dao;

import com.mf.common.entity.AccountDto;
import com.mf.common.model.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by pony on 2016/7/5.
 */

public interface UserDao {



    /**
     * 通过um账号查询用户信息
     * @param userId um账号
     * @return
     */
    UserInfo queryUser(@Param("userId") String userId);


    List<UserInfo> queryAllUser();



}
