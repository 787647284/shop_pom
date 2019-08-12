package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.Service.IUserService;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.pass.jbcryptUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * linzebin
 * 时间2019/7/19 1:02
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public int register(User user) {
        //判断用户名是否存在
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",user.getUsername());
        User user2 = userMapper.selectOne(queryWrapper);
        if(user2!=null){
                    return -1; //用户已存在
        }
        QueryWrapper queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("email",user.getEmail());
        User user3 = userMapper.selectOne(queryWrapper1);
        if(user3!=null){
            return -2;//邮箱已注册
        }
        user.setPassword(jbcryptUtil.hashPassword(user.getPassword()));
     /*   user.setPassword();*/
        return userMapper.insert(user);
    }

    @Override
    public User getUserByUsername(String username) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",username);
        return  userMapper.selectOne(queryWrapper);
    }

    @Override
    public int update(String username, String password) {
        User user = getUserByUsername(username);
        String s = jbcryptUtil.hashPassword(password);
        System.out.println("修改后的密码"+s);
        user.setPassword(s);
        return userMapper.updateById(user);
    }

    @Override
    public User login(User user) {
        System.out.println("进入了");
        User user1 = getUserByUsername(user.getUsername());
        System.out.println("提交的密码"+user.getPassword()+"获得的密码"+user1.getPassword());
        if (user1!=null){
            //该用户名存在对应的用户
            boolean b = jbcryptUtil.cheackPassword(user1.getPassword(), user.getPassword());
            System.out.println("验证结果"+b);
            if(b){
                return user1;
            }
        }
        return null;
    }
}
