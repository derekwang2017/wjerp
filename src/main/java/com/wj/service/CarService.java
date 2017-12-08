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
        List<OrderMtinfo> ordermtlist = carDao.getOrderMtList(orderid);
        orderinfo.setMtlist(ordermtlist);
        BigDecimal totalMaterialPrice = calOrderMaterialTotalPrice(ordermtlist);
        orderinfo.setTotalmaterialprice(totalMaterialPrice);
        if(orderinfo.getHwworkhourprice()==null){
            orderinfo.setHwworkhourprice(BigDecimal.ZERO);
        }
        if(!Util.isEmpty(orderinfo.getEntrydtm())){
            orderinfo.setEntrydtm(Util.formartdispdate(orderinfo.getEntrydtm()));
        }

        return orderinfo;
    }

    public List<OrderMtinfo> getOrderMtlist(int orderid){
        return carDao.getOrderMtList(orderid);
    }

    //flag 1表示从页面直接修改工单材料信息，需要判断库存，0表示添加工单材料，已经判断过了， 这里不需要判断
    public int saveOrderItem(OrderMtinfo orderitem, int flag){
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
            int orderid = orderitem.getHwmhwid();
            HcWorkOrder hcWorkOrder = carDao.getOrderById(orderid);
            if(hcWorkOrder!=null && hcWorkOrder.getHwstatus()!=0){
                return -2;
            }
             int hwmid = orderitem.getHwmid();
             int hmid = orderitem.getHwmhmid();
             if(hmid>0 && flag==1){
                 HcMaterialItem hcMaterialItem = materialitemService.getMaterialInfoByid(hmid);
                 if(hcMaterialItem!=null){
                     BigDecimal hmstock = hcMaterialItem.getHmstock();
                     if(hmstock==null){
                         hmstock = BigDecimal.ZERO;
                     }
                     BigDecimal useamount = orderitem.getMtamount();
                     if(useamount==null){
                         useamount = BigDecimal.ZERO;
                     }
                     if(useamount.compareTo(hmstock) > 0){
                        return -1;
                     }
                 }
             }
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
        return 0;
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

    public Rtnvalue<OrderMtinfo> addOrderSelectMaterialitem(AddOrderMaterialItemFormBean formBean){
        Rtnvalue<OrderMtinfo> rtnvalue = new Rtnvalue<>();
        if(Util.isEmpty(formBean.getMaterialids())){
            rtnvalue.setStatus(1);
            rtnvalue.setMsg("参数错误");
            return rtnvalue;
        }

        HcWorkOrder hcWorkOrder = carDao.getOrderById(formBean.getOrderid());
        if(hcWorkOrder!=null && hcWorkOrder.getHwstatus()!=0){
            rtnvalue.setStatus(1);
            rtnvalue.setMsg("工单已经结算，不可修改工单材料");
            return rtnvalue;
        }

        String[] mtidarr = formBean.getMaterialids().split(",");
        List<HcMaterialItem> materiallist = materialitemService.getMaterialitemByIds(formBean.getMaterialids());
        Map<Integer, HcMaterialItem> materialItemMap = new HashMap<>();
        if(materiallist!=null && materiallist.size()>0){
            for(HcMaterialItem item : materiallist){
                if(item.getHmstock()==null || item.getHmstock().compareTo(BigDecimal.ZERO)==0){
                    rtnvalue.setStatus(1);
                    rtnvalue.setMsg("材料[" + item.getHmname() + "]库存不足");
                    return rtnvalue;
                }
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
                    orderitem.setMtprice(materialItem.getHmsaleprice());  //默认使用材料销价
                    orderitem.setHwmtotalprice(materialItem.getHmsaleprice());

                    saveOrderItem(orderitem, 0);
                }
            }
        }

        return rtnvalue;
    }

    //计算工单使用材料总金额
    private BigDecimal calOrderMaterialTotalPrice(List<OrderMtinfo> mtlist){
        BigDecimal totalmtprice = BigDecimal.ZERO;
        if(mtlist!=null && mtlist.size()>0){
            for(OrderMtinfo mt : mtlist){
                BigDecimal mtprice = mt.getHwmtotalprice();
                if(mtprice==null){
                    mtprice = BigDecimal.ZERO;
                }
                totalmtprice = totalmtprice.add(mtprice);
            }
        }
        return totalmtprice;
    }

    //判断工单使用材料库存是否充足
    private String validOrderMaterialStockAmount(List<OrderMtinfo> mtlist){
        if(mtlist!=null && mtlist.size()>0){
            for(OrderMtinfo mt : mtlist){
                BigDecimal mtstock = mt.getHmstock();  //库存
                BigDecimal useamount = mt.getMtamount(); //使用数量
                if(mtstock==null){
                    mtstock = BigDecimal.ZERO;
                }
                if(useamount==null){
                    useamount = BigDecimal.ZERO;
                }
                if(useamount.compareTo(mtstock) > 0){
                    return mt.getMtname();
                }
            }
        }

        return "";
    }

    //工单结算
    public Rtnvalue orderSettlement(SetttlementFormBean formBean){
        Rtnvalue rtnvalue = new Rtnvalue<>();
        int orderid = formBean.getOrderid();
        List<OrderMtinfo> mtlist = getOrderMtlist(orderid);
        String notStockMt = validOrderMaterialStockAmount(mtlist);
        if(!Util.isEmpty(notStockMt)){
            rtnvalue.setStatus(1);
            rtnvalue.setMsg("材料[" + notStockMt + "]库存不足，结算失败");
            return rtnvalue;
        }
        BigDecimal totalmtprice = calOrderMaterialTotalPrice(mtlist);
        BigDecimal workhourprice = formBean.getWorkhourprice();
        if(workhourprice==null){
            workhourprice = BigDecimal.ZERO;
        }
        BigDecimal totalprice = totalmtprice.add(workhourprice);

        carDao.orderSettlement(totalprice, workhourprice, orderid);

        rtnvalue.setMsg("结算成功");
        return rtnvalue;
    }

    //工单收银
    public Rtnvalue orderPayment(PaymentFormBean formBean){
        Rtnvalue rtnvalue = new Rtnvalue<>();
        int orderid = formBean.getOrderid();
        HcWorkOrder hcWorkOrder = getOrderByid(orderid);
        if(hcWorkOrder!=null && hcWorkOrder.getHwstatus()!=2){  //未结算不能收银
            rtnvalue.setStatus(1);
            rtnvalue.setMsg("未结算不能收银");
            return rtnvalue;
        }
        List<OrderMtinfo> mtlist = getOrderMtlist(orderid);
        //验证库存是否充足
        String notStockMt = validOrderMaterialStockAmount(mtlist);
        if(!Util.isEmpty(notStockMt)){
            rtnvalue.setStatus(1);
            rtnvalue.setMsg("材料[" + notStockMt + "]库存不足，收银失败");
            return rtnvalue;
        }

        //扣除材料库存
        if(mtlist!=null && mtlist.size()>0){
            for(OrderMtinfo mt : mtlist){
                //扣除库存
                materialitemService.orderUseMtAmount(mt.getHwmhmid(), mt.getMtamount());
                //使用记录
                materialitemService.insertMaterialStockRecord(mt.getHwmhmid(), 1, mt.getMtamount(), mt.getMtprice(), 1, orderid);
            }
        }


        BigDecimal payprice = formBean.getRealpayamount();
        if(payprice==null){
            payprice = BigDecimal.ZERO;
        }

        carDao.orderPayment(payprice, orderid);

        //插入收银记录
        insertOrderSettlementRecord(orderid, payprice);

        rtnvalue.setMsg("收银成功");
        return rtnvalue;
    }


    //插入收银记录
    public void insertOrderSettlementRecord(int orderid, BigDecimal price){
        HcSettlementRecord record = new HcSettlementRecord();
        record.setHsrorderid(orderid);
        record.setHsrprice(price);
        record.setHsrdtm(Util.getNowYYYYMMDDHHMMSS());
        record.setHsrstaffid(1);
        carDao.insertSettlementRecord(record);
    }


    //工单交车
    public int orderTakeCar(int orderid){
        HcWorkOrder hcWorkOrder = getOrderByid(orderid);
        if(hcWorkOrder!=null && hcWorkOrder.getHwstatus()!=3){  //未收银不能交车
            return -1;
        }
        carDao.orderTakeCar(Util.getNowYYYYMMDDHHMMSS(), orderid);
        return 0;
    }

    public HcWorkOrder getOrderByid(int orderid){
        return carDao.getOrderById(orderid);
    }

    public void cancelOrder(int orderid){
        carDao.cancelOrder(orderid);
    }
}
