package com.wj.controller;

import com.wj.entity.HcMemberCar;
import com.wj.formbean.JQListBean;
import com.wj.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 87831 on 2017/10/15/0015.
 * car controll
 */
@RestController
@RequestMapping(value = "car")
public class CarCtl {

    @Autowired
    private CarService carService;

    @RequestMapping(value = "list")
    public JQListBean getCarlist(HttpServletRequest request){
        JQListBean<HcMemberCar> rtnvalue = new JQListBean<>();
        List<HcMemberCar> list = carService.getCarlistSvc();
        rtnvalue.setRows(list);
        rtnvalue.setCurrpage(1);
        rtnvalue.setTotalpages(1);
        rtnvalue.setTotalrecords(1);
        return rtnvalue;
    }
}
