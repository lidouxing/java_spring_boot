package com.ldx.javaSpringBoot.config.shiro;


import com.ldx.javaSpringBoot.modules.account.entity.Resource;
import com.ldx.javaSpringBoot.modules.account.entity.Role;
import com.ldx.javaSpringBoot.modules.account.entity.User;
import com.ldx.javaSpringBoot.modules.account.service.ResourceService;
import com.ldx.javaSpringBoot.modules.account.service.RoleService;
import com.ldx.javaSpringBoot.modules.account.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * shiro授权验证
 * 1 首先配置jar
 * 2 配置全局变量
 * 3 写授权验证的内容
 */
@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //new一个授权的对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //getPrimaryPrincipal这个的数据其实已经拿到了下面认证的一个user数据，不过不是用User来装，所以需要强转
        User user = (User) principalCollection.getPrimaryPrincipal();
        List<Role> roles = user.getRoles();
        if (roles != null && !roles.isEmpty()) {
            //？？？
            roles.stream().forEach(item -> {
                simpleAuthorizationInfo.addRole(item.getRoleName());
                List<Resource> resources =
                        resourceService.getResourcesByRoleId(item.getRoleId());
                if (resources != null && !resources.isEmpty()) {
                    resources.stream().forEach(resource -> {
                        simpleAuthorizationInfo.addStringPermission(resource.getPermission());
                    });
                }
            });
        }

        return simpleAuthorizationInfo;
    }
    //身份认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //前端传来一个username进行查询，（username的是自定义的，用来查询或者验证的参数）
        String username=(String) authenticationToken.getPrincipal();
        //使用username调用到方法查询
        User user=userService.getUserByUserName(username);
        //判断是否为空
        if (user==null){
            //使用自带的异常
            throw new UnknownAccountException("the account do not exist.");
        }
        //查看SimpleAuthenticationInfo内部方法，user调用后的数据传到USer中，而第二个参数就是资格的验证（相当于密码）
        //第三个参数就是MrRealm本身,可以点击SimpleAuthenticationInfo查看参数
        return new SimpleAuthenticationInfo(user,user.getPassword(),getName());
    }
}
