package com.qf.Service;

import com.qf.entity.BackUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IBackService extends UserDetailsService {
    List<BackUser> queryAll();
    int addBackUser(BackUser backUser);

    void updateUserRelos(Integer uid, Integer[] rid);

/*    BackUser login(String username,String password);*/
}
