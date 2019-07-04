package com.qf.ServiceImpl;

import com.alibaba.dubbo.common.bytecode.Wrapper;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.Service.IRoleService;
import com.qf.dao.RoleMapper;
import com.qf.dao.UserRoleMapper;
import com.qf.entity.Role;
import com.qf.entity.RolePowerTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * linzebin
 * 时间2019/7/1 21:50
 */
@Service
public class RoleServiceImpl implements IRoleService {
        @Autowired
        RoleMapper roleMapper;
        @Autowired
        UserRoleMapper userRoleMapper;

    @Override
    public List<Role> getRoleList() {
        return roleMapper.selectList(null);
    }

    @Override
    public int innsertRole(Role role) {
        return roleMapper.insert(role);
    }

    @Override
    public List<Role> queryListByUid(Integer id) {
        return roleMapper.queryListByUid(id);
    }


}
