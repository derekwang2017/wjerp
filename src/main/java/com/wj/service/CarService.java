package com.wj.service;

import com.wj.dao.CarDao;
import com.wj.entity.*;
import com.wj.formbean.*;
import com.wj.tools.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by 87831 on 2017/10/15/0015.
 */
@Service
public class CarService {
    @Autowired
    private CarDao carDao;

    @Autowired
    private MaterialitemService materialitemService;

    public List<HcMemberCar> getCarlistSvc(){
        return carDao.getCarlist();
    }

    public HcWorkOrder acceptCarService(AcceptCarFormBean formBean){
        int membercarid = formBean.getMembercarid();
        String carplate = formBean.getMembercarplate();
        int entrymile = 0;
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

            entrymile = hcMemberCar.getHmcmile();
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
            workOrder.setHwentrymile(entrymile);
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
        if(!Util.isEmpty(orderinfo.getEntrydtm())){
            orderinfo.setEntrydtm(Util.formartdispdate(orderinfo.getEntrydtm()));
        }

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
                 hcWorkorderMaterial.setHwmtotalprice(orderitem.getMtamount().multiply(orderitem.getMtprice()).setScale(2, BigDecimal.ROUND_HALF_UP));
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

    public void setWorkorderMaterialDelSvc(int hwmid){
        carDao.setHcWorkorderMaterialDel(hwmid);
    }

    public void addOrderSelectMaterialitem(AddOrderMaterialItemFormBean formBean){
        if(Util.isEmpty(formBean.getMaterialids())){
            return;
        }
        String[] mtidarr = formBean.getMaterialids().split(",");
        List<HcMaterialItem> materiallist = materialitemService.getMaterialitemByIds(formBean.getMaterialids());
        Map<Integer, HcMaterialItem> materialItemMap = new HashMap<>();
        if(materiallist!=null && materiallist.size()>0){
            for(HcMaterialItem item : materiallist){
                materialItemMap.put(item.getHmid(), item);
            }
        }
        //判断工单是否已经有此材料，如果有，不做添加
        List<HcWorkorderMaterial> existlist = carDao.getOrderMateriallist(formBean.getOrderid());
        Set<Integer> preIdSet = new HashSet<>();
        if(existlist!=null && existlist.size()>0){
            for(HcWorkorderMaterial temp : existlist){
                preIdSet.add(temp.getHwmhmid());
            }
        }
        for(int i = 0; i < mtidarr.length; i ++){
            int hmid = Util.converToInt(mtidarr[i]);
            if(!preIdSet.contains(hmid)){
                HcMaterialItem materialItem = materialItemMap.get(hmid);
                if(materialItem!=null){
                    OrderMtinfo orderitem = new OrderMtinfo();
                    orderitem.setHwmhwid(formBean.getOrderid());
                    orderitem.setHwmhmid(hmid);
                    orderitem.setMtamount(BigDecimal.ONE);
                    orderitem.setMtprice(materialItem.getHmprice());
                    orderitem.setHwmtotalprice(materialItem.getHmprice());

                    saveOrderItem(orderitem);
                }
            }
        }
    }
}
