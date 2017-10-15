package com.wj.service;

import com.wj.dao.CarDao;
import com.wj.entity.HcMemberCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 87831 on 2017/10/15/0015.
 */
@Service
public class CarService {
    @Autowired
    private CarDao carDao;

    public List<HcMemberCar> getCarlistSvc(){
        return carDao.getCarlist();
    }
}
