package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.Service.IBackService;
import com.qf.dao.BackMapper;
import com.qf.dao.UserRoleMapper;
import com.qf.entity.BackUser;
import com.qf.entity.UserRoleTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * linzebin
 * 时间2019/7/1 20:00
 */
@Service
public class BackServiceImpl implements IBackService {



    @Autowired
    BackMapper backMapper;
    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public List<BackUser> queryAll() {
        return backMapper.selectList(null);
    }

    @Override
    public int addBackUser(BackUser backUser) {
        return backMapper.insert(backUser);
    }

    @Override
    @Transactional
    public void updateUserRelos(Integer uid, Integer[] rid) {
        //先清空所有已经存在的角色
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("uid",uid);
        userRoleMapper.delete(queryWrapper);
        //将新的角色和用户对多对保存到中间表中
        for (Integer rids : rid){
            UserRoleTable ssss=new UserRoleTable(uid,rids);
            userRoleMapper.insert(ssss);
       }

        }

    @Override
    public BackUser login(String username, String password) {
        BackUser backUser = backMapper.queryByUserName(username);
        if(backUser!=null &&backUser.getPassword().equals(password)){
                return backUser;
        }
        return null;
    }
}



