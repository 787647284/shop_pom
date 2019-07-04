package com.qf.Service;

import com.qf.entity.Role;

import java.util.List;

public interface IRoleService {
    List<Role> getRoleList();
    int innsertRole(Role role);
    List<Role> queryListByUid(Integer id);
}
