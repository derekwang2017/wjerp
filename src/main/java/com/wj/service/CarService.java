package com.wj.service;

import com.wj.dao.CarDao;
import com.wj.entity.HcMemberCar;
import com.wj.entity.HcWorkOrder;
import com.wj.formbean.AcceptCarFormBean;
import com.wj.formbean.OrderinfoFormBean;
import com.wj.tools.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
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

    public HcWorkOrder acceptCarService(AcceptCarFormBean formBean){
        int membercarid = formBean.getMembercarid();
        String carplate = formBean.getMembercarplate();
        if(!Util.isEmpty(carplate)){
            HcMemberCar hcMemberCar = carDao.getMembercarByPlate(carplate);
            if(hcMemberCar==null){
                hcMemberCar = new HcMemberCar();
            }
            hcMemberCar.setHmccarplate(carplate);

            HcMemberCar curCar = formBean.getCarinfo();
            if(curCar!=null){
                if(!Util.isEmpty(curCar.getHmcownername())){
                    hcMemberCar.setHmcownername(curCar.getHmcownername());
                }
                if(!Util.isEmpty(curCar.getHmcownertel())){
                    hcMemberCar.setHmcownertel(curCar.getHmcownertel());
                }
                if(curCar.getHmcmile()>0){
                    hcMemberCar.setHmcmile(curCar.getHmcmile());
                }
                if(!Util.isEmpty(curCar.getHmccartype())){
                    hcMemberCar.setHmccartype(curCar.getHmccartype());
                }
            }
            //同步车辆信息
            membercarid = syncHcMemberCar(hcMemberCar);

        }
        HcWorkOrder workOrder = carDao.getCurWorkOrder(membercarid);
        if(workOrder==null || workOrder.getHwid()==0){
            workOrder = new HcWorkOrder();
            workOrder.setHwmembercarid(membercarid);
            workOrder.setHwbusinesstypeid(formBean.getBusinesstypeid());
            workOrder.setHwenterdtm(Util.getNowYYYYMMDDHHMMSS());
            workOrder.setHwdesc(formBean.getHwdesc());
            workOrder.setHwacceptstaffid(1);
            workOrder.setHwserialno(generateOrderSerialNo());
            carDao.insertWorkOrder(workOrder);
        }

        return workOrder;
    }

    public int syncHcMemberCar(HcMemberCar hcMemberCar){
        if(hcMemberCar.getHmcid()>0){
            //update
            carDao.syncMemberCar(hcMemberCar);
        } else {
            //insert
            carDao.insertMemberCar(hcMemberCar);
        }

        return hcMemberCar.getHmcid();
    }

    public String generateOrderSerialNo(){
        String today = Util.getNowYYYYMMDD();
        //查询今天进场工单数
        int todaycnt = carDao.getTodayOrdercnt(today + "000000", today + "235959");
        DecimalFormat decimalFormat = new DecimalFormat("0000");
        return today+ decimalFormat.format(todaycnt+1);
    }

    public List<OrderinfoFormBean> orderlist(String filter){
        List<OrderinfoFormBean> orderlist = carDao.getOrderinfolist(filter);
        if(orderlist!=null && orderlist.size()>0){
            for(OrderinfoFormBean order : orderlist){
                order.setHwenterdtm(Util.formartdispdate(order.getHwenterdtm()));
                int hwstatus = order.getHwstatus();
                if(hwstatus==0){
                    order.setOrderstatusname("进行中");
                } else if(hwstatus==1){
                    order.setOrderstatusname("已完工");
                } else if(hwstatus==2){
                    order.setOrderstatusname("已结算");
                } else if(hwstatus==3){
                    order.setOrderstatusname("已收银");
                } else if(hwstatus==4){
                    order.setOrderstatusname("已交车");
                }
            }
        }
        return orderlist;
    }

    public int orderlistSize(String filter){
        return carDao.getOrderinfolistsize(filter);
    }

}
