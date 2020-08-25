package com.ldx.javaSpringBoot.modules.account.service;

import com.github.pagehelper.PageInfo;
import com.ldx.javaSpringBoot.modules.account.entity.User;
import com.ldx.javaSpringBoot.modules.common.vo.Result;
import com.ldx.javaSpringBoot.modules.common.vo.SearchVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    Result<User> insertUser(User user);

    Result<User> loginUser(User user);

    PageInfo<User> getUserBySearchVo(SearchVo searchVo);

    Result<Object> deleteUser(int userId);

    Result<User> updateUser(User user);


    User getUserByUserId(int userId);

    Result<User> profile(User user);

    Result<String> userImg(MultipartFile file);

    User getUserByUserName(String userName);

    void logout();
}
