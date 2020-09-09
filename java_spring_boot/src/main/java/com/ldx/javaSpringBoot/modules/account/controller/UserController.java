package com.ldx.javaSpringBoot.modules.account.controller;

import com.github.pagehelper.PageInfo;
import com.ldx.javaSpringBoot.modules.account.entity.User;
import com.ldx.javaSpringBoot.modules.account.service.UserService;
import com.ldx.javaSpringBoot.modules.common.vo.Result;
import com.ldx.javaSpringBoot.modules.common.vo.SearchVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    /**
   127.0.0.1/api/user
     {"username":"admin","password":"111"}
     */
    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<User> insertUser(@RequestBody User user) {
        return userService.insertUser(user);
    }

    @PostMapping(value = "/login",consumes =MediaType.APPLICATION_JSON_VALUE )
    public Result<User> loginUser(@RequestBody  User user){
       return userService.loginUser(user);
    }

    /**
     127.0.0.1/api/users
     {"username":"admin","password":"111"}
     */
    @PostMapping(value = "/users",consumes = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<User> getUserBySearchVo(@RequestBody SearchVo searchVo){
        return userService.getUserBySearchVo(searchVo);
    }

    @DeleteMapping(value = "/delete/{userId}")
    //@RequiresPermissions(value = "/api/user")注解就是满足于/api/delete就可以执行下面的操作，而这里就是权限人员得有
    @RequiresPermissions(value = "/api/delete")
    public Result<Object> deleteUser(@PathVariable int userId){
        return userService.deleteUser(userId);
    }

    /**
     * 127.0.0.1/api/update   {"userName":"ldx3","userImg":"ldx3.jpg"}
     */
    @PutMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<User> updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @GetMapping("/user/{userId}")
    public User getUserByUserId(@PathVariable int userId) {
        return userService.getUserByUserId(userId);
    }

    @PostMapping(value = "/userImg",consumes = "multipart/form-data")
    public Result<String> userImg(@RequestParam MultipartFile file){
        return userService.userImg(file);
    }

    @PutMapping(value = "/profile",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<User> profile(@PathVariable User user){
            return userService.profile(user);
    }

    @GetMapping("/logout")
    public void logout(){
         userService.logout();
    }
}
