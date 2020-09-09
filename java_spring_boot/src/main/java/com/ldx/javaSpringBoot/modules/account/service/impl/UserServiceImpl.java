package com.ldx.javaSpringBoot.modules.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ldx.javaSpringBoot.config.ResourceConfigBean;
import com.ldx.javaSpringBoot.modules.account.dao.UserDao;
import com.ldx.javaSpringBoot.modules.account.dao.UserRoleDao;
import com.ldx.javaSpringBoot.modules.account.entity.Role;
import com.ldx.javaSpringBoot.modules.account.entity.User;
import com.ldx.javaSpringBoot.modules.account.service.UserService;
import com.ldx.javaSpringBoot.modules.common.vo.Result;
import com.ldx.javaSpringBoot.modules.common.vo.SearchVo;
import com.ldx.javaSpringBoot.utils.MD5Util;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private ResourceConfigBean resourceConfigBean;
    @Override
    public Result<User> insertUser(User user) {
        User userTemp = userDao.getUserByUserName(user.getUserName());
        if (userTemp != null) {
            return new Result<User>(
                    Result.ResultStatus.FAILED.status, "User name is repeat.");
        }

        user.setCreateDate(LocalDateTime.now());
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        userDao.insertUser(user);

        userRoleDao.deleteUserRoleByUserId(user.getUserId());
        List<Role> roles = user.getRoles();
        if (roles != null && !roles.isEmpty()) {
            roles.stream().forEach(item -> {
                userRoleDao.addUserRole(user.getUserId(), item.getRoleId());
            });
        }


        return new Result<User>(
                Result.ResultStatus.SUCCESS.status, "Insert success.", user);
    }

    //shiro改造登录
    @Override
    public Result<User> loginUser(User user) {
        //用来装数据
        Subject subject= SecurityUtils.getSubject();
        //令牌类,得到前段传来的name和password,由于密码被加密所以要使用MD5Util
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUserName(),
                MD5Util.getMD5(user.getPassword()));
        //记住这个内容
        usernamePasswordToken.setRememberMe(user.getRememberMe());

        //将令牌进行验证与授权
        try {
            //身份验证
            subject.login(usernamePasswordToken);
            //资源验证器
            subject.checkRoles();
        }catch (Exception e){
            return new Result<User>(Result.ResultStatus.FAILED.status,"error");
        }
        //获取shiro封装的session对象
        Session session = subject.getSession();
        //包装好后传到前端heard
        session.setAttribute("user",user);
        return new Result<User>(Result.ResultStatus.SUCCESS.status,"success");
//        User user1=   userDao.getUserByUserName(user.getUserName());
//        if (user1!=null&&user1.getPassword().equals(MD5Util.getMD5(user.getPassword()))){
//            System.out.println("登录成功");
//            return new Result<User>(Result.ResultStatus.SUCCESS.status,"login success",user1);
//        }
//        return new Result<User>(Result.ResultStatus.FAILD.status,"you name or password is error");
    }

    @Override
    public PageInfo<User> getUserBySearchVo(SearchVo searchVo) {
        searchVo.initSearchVo();//初始化
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getPageSize());
        return new PageInfo<User>(Optional.ofNullable(userDao.getUserBySearchVo(searchVo)).orElse(Collections.emptyList()));
    }

    @Override
    @Transactional
    public Result<Object> deleteUser(int userId) {
     Integer user1=  userDao.deleteUser(userId);
     userRoleDao.deleteUserRoleByUserId(userId);
     if (user1!=null){
         return new Result<>(Result.ResultStatus.SUCCESS.status,"delete success",user1);
     }
        return new Result<>(Result.ResultStatus.FAILED.status,"delete error",userId);
    }

    @Override
    @Transactional
    public Result<User> updateUser(User user) {
        User userTemp = userDao.getUserByUserName(user.getUserName());
        if (userTemp != null && userTemp.getUserId() != user.getUserId()) {
            return new Result<User>(
                    Result.ResultStatus.FAILED.status, "User name is repeat.");
        }
        userDao.updateUser(user);
        //??
        userRoleDao.deleteUserRoleByUserId(user.getUserId());
        List<Role> roles = user.getRoles();
        if (roles != null && !roles.isEmpty()) {
            roles.stream().forEach(item -> {
                userRoleDao.addUserRole(user.getUserId(), item.getRoleId());
            });
        }
        return new Result<User>(
                Result.ResultStatus.SUCCESS.status, "Update success.", user);
    }

    @Override
    public User getUserByUserId(int userId) {
        return userDao.getUserByUserId(userId);
    }

    @Override
    @Transactional
    public Result<User> profile(User user) {
        User userTemp = userDao.getUserByUserName(user.getUserName());
        if (userTemp != null && userTemp.getUserId() != user.getUserId()) {
            return new Result<User>(Result.ResultStatus.FAILED.status, "User name is repeat.");
        }
        userDao.updateUser(user);
        return new Result<User>(Result.ResultStatus.SUCCESS.status, "Edit success.", user);
    }

    @Override
    public Result<String> userImg(MultipartFile file){
        //判断文件是否存在
        if (file.isEmpty()) {
            return new Result<String>(
                    Result.ResultStatus.FAILED.status, "Please select img.");
        }
        //相对路径
        String relativePath = "";
        //绝对路径
        String destFilePath = "";
        try {
            //得到本地的系统Windows7
            String osName = System.getProperty("os.name");
            //小写toLowerCase,头部startsWith
            if (osName.toLowerCase().startsWith("win")) {
                //这里做windows和linux的判断
                destFilePath = resourceConfigBean.getLocationPathForWindows() +
                        file.getOriginalFilename();
            } else {
                destFilePath = resourceConfigBean.getLocationPathForLinux()
                        + file.getOriginalFilename();
            }
            relativePath = resourceConfigBean.getRelativePath() +
                    file.getOriginalFilename();
            File destFile = new File(destFilePath);
            file.transferTo(destFile);

        } catch (IOException e) {
            e.printStackTrace();
            return new Result<String>(
                    Result.ResultStatus.FAILED.status, "Upload failed.");
        }

        return new Result<String>(
                Result.ResultStatus.SUCCESS.status, "Upload success.", relativePath);
    }

    //shiro得到user
    @Override
    public User getUserByUserName(String userName) {
        return userDao.getUserByUserName(userName);
    }

    @Override
    public void logout() {
      Subject subject=  SecurityUtils.getSubject();
      subject.logout();
      //退出后数据要清空
       Session session= subject.getSession();
        session.setAttribute("user",null);
    }
}
