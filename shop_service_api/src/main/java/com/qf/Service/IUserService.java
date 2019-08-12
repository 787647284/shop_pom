package com.qf.Service;

import com.qf.entity.User;

public interface IUserService {
    int register(User user);
    User getUserByUsername(String username);
    int update(String username,String password);
    User login(User user);
}
