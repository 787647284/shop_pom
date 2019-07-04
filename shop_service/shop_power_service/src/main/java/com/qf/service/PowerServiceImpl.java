package com.qf.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.Service.IPowerService;
import com.qf.dao.PowerMapper;
import com.qf.dao.RolePowerMapper;
import com.qf.entity.Power;
import com.qf.entity.RolePowerTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * linzebin
 * 时间2019/7/2 16:58
 */
@Service
public class PowerServiceImpl implements  IPowerService {

    @Autowired
    PowerMapper powerMapper;
    @Autowired
    RolePowerMapper rolePowerMapper;
    @Override
    public List<Power> getPower() {
        return powerMapper.getPower();
    }

    @Override
    public int insert(Power power) {
        return powerMapper.insert(power);
    }

    @Override
    public List<Power> rolePower(Integer id) {
        //根据角色名 查询该角色所拥有的权限，以及所有权限
        return rolePowerMapper.rolePower(id);
    }

    @Override
    @Transactional
    public void updatePower(Integer rid, Integer[] pids) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("rid",rid);
        rolePowerMapper.delete(queryWrapper);

        //添加角色关系和权利
        for (Integer pid : pids) {
            rolePowerMapper.insert(new RolePowerTable(rid,pid));
        }
    }

}
