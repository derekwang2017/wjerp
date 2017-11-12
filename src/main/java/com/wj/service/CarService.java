package com.wj.service;

import com.wj.dao.CarDao;
import com.wj.entity.*;
import com.wj.formbean.AcceptCarFormBean;
import com.wj.formbean.OrderMtinfo;
import com.wj.formbean.Orderinfo;
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

    public HcMemberCar getCarinfoByCarplate(String carplate){
        return carDao.getMembercarByPlate(carplate);
    }

    public List<HcBusinessType> getBusinesstypelist(){
        return carDao.getBusinessTypelist();
    }

    public Orderinfo getOrderinfoByOrderid(int orderid){
        Orderinfo orderinfo = carDao.getOrderinfoByOrderid(orderid);
        List<OrderMtinfo> orderlist = carDao.getOrderMtList(orderid);
        orderinfo.setMtlist(orderlist);

        return orderinfo;
    }

    public List<OrderMtinfo> getOrderMtlist(int orderid){
        return carDao.getOrderMtList(orderid);
    }

    public void saveOrderItem(OrderMtinfo orderitem){
        /*String mttype = orderitem.getMttype();
        int mttypeid = 0;
        if(!Util.isEmpty(mttype)){
            mttypeid = getMttypeid(mttype);
        }
        String brandname = orderitem.getMtbrandname();
        int brandid = 0;
        if(!Util.isEmpty(brandname)){
            brandid = getMtBrandid(brandname);
        }*/
        if(orderitem!=null){
             int hwmid = orderitem.getHwmid();
             if(hwmid==0){
                 HcWorkorderMaterial hcWorkorderMaterial = new HcWorkorderMaterial();
                 hcWorkorderMaterial.setHwmhwid(orderitem.getHwmhwid());
                 hcWorkorderMaterial.setHwmhmid(orderitem.getHwmhmid());
                 hcWorkorderMaterial.setHwmamount(orderitem.getMtamount());
                 hcWorkorderMaterial.setHwmunitprice(orderitem.getMtprice());
                 hcWorkorderMaterial.setHwmtotalprice(orderitem.getHwmtotalprice());
                 hcWorkorderMaterial.setHwmstaffid(1);
                 hcWorkorderMaterial.setHwmcreatedtm(Util.getNowYYYYMMDDHHMMSS());
                 carDao.insertWorkorderMaterial(hcWorkorderMaterial);
             } else {
                 HcWorkorderMaterial hcWorkorderMaterial = new HcWorkorderMaterial();
                 hcWorkorderMaterial.setHwmid(hwmid);
                 hcWorkorderMaterial.setHwmamount(orderitem.getMtamount());
                 hcWorkorderMaterial.setHwmunitprice(orderitem.getMtprice());
                 hcWorkorderMaterial.setHwmtotalprice(orderitem.getHwmtotalprice());
                 carDao.updateHcWorkorderMaterial(hcWorkorderMaterial);
             }
        }
    }

    private int getMttypeid(String mttypename){
        if(Util.isEmpty(mttypename)){
            return 0;
        }
        HcMaterialCategory hcMaterialCategory = carDao.getCategoryByName(mttypename);
        if(hcMaterialCategory!=null && hcMaterialCategory.getHmcid()>0){
            return hcMaterialCategory.getHmcid();
        } else {
            hcMaterialCategory = new HcMaterialCategory();
            hcMaterialCategory.setHmcname(mttypename);
            carDao.insertHcMaterialCategory(hcMaterialCategory);
            return hcMaterialCategory.getHmcid();
        }
    }

    private int getMtBrandid(String brandname){
        if(Util.isEmpty(brandname)){
            return 0;
        }
        HcMaterialBrand hcMaterialBrand = carDao.getBrandByName(brandname);
        if(hcMaterialBrand!=null && hcMaterialBrand.getHmbid()>0){
            return hcMaterialBrand.getHmbid();
        } else {
            hcMaterialBrand = new HcMaterialBrand();
            hcMaterialBrand.setHmbname(brandname);
            carDao.insertHcMaterialBrand(hcMaterialBrand);
            return hcMaterialBrand.getHmbid();
        }
    }

    public void setWorkorderMaterialDelSvc(int hwmid){
        carDao.setHcWorkorderMaterialDel(hwmid);
    }
}
