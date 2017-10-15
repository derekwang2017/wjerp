package com.wj.controller;

import com.wj.entity.HcMemberCar;
import com.wj.formbean.Rtnvalue;
import com.wj.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 87831 on 2017/10/15/0015.
 */
@RestController
@RequestMapping(value = "car")
public class CarCtl {

    @Autowired
    private CarService carService;

    @RequestMapping(value = "list")
    public Rtnvalue getCarlist(HttpServletRequest request){
        Rtnvalue<HcMemberCar> rtnvalue = new Rtnvalue<>();
        List<HcMemberCar> list = carService.getCarlistSvc();
        rtnvalue.setList(list);
        return rtnvalue;
    }
}
