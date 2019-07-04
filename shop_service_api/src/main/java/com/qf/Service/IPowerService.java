package com.qf.Service;

import com.qf.entity.Power;

import java.util.List;

public interface IPowerService {

    List<Power> getPower();
    int insert(Power power);
    List<Power> rolePower(Integer id);
    void updatePower(Integer rid,Integer pids[]);

}
