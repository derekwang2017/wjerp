package com.wj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wj.entity.HcBusinessType;
import com.wj.entity.HcMemberCar;
import com.wj.entity.HcWorkOrder;
import com.wj.formbean.*;
import com.wj.service.CarService;
import com.wj.tools.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = "acceptcar")
    public Rtnvalue acceptCar(HttpServletRequest request){
        Rtnvalue<HcWorkOrder> rtnvalue = new Rtnvalue<>();
        String data = request.getParameter("data");
        if(!Util.isEmpty(data)){  //JSON.toJSONString(data)
            AcceptCarFormBean formBean = JSON.parseObject(data, AcceptCarFormBean.class);
            HcWorkOrder hcWorkOrder = carService.acceptCarService(formBean);
            rtnvalue.setObj(hcWorkOrder);
        } else {
            rtnvalue.setStatus(1);
            rtnvalue.setMsg("参数错误");
        }

        return rtnvalue;
    }

    //在场车辆列表
    @RequestMapping(value = "onlinelist")
    public JQListBean getOnlinelist(@RequestParam(value="page",defaultValue="0",required=false) int page,
                                    @RequestParam(value="rows",defaultValue="0",required=false) int rows,
                                    @RequestParam(value="sidx",defaultValue="riitemid",required=false) String sidx,
                                    @RequestParam(value="sord",defaultValue="asc",required=false) String sord,
                                    @RequestParam(value="_search",defaultValue="",required=false) boolean search,
                                    HttpServletRequest request){
        String data = request.getParameter("data");
        String searchFilter = " where hwstatus<4 ";
        return generageOrderlistData(data, searchFilter, page, rows);
    }


    //完成工单历史列表
    @RequestMapping(value = "historyorderlist")
    public JQListBean historyOrderlist(@RequestParam(value="page",defaultValue="0",required=false) int page,
                                    @RequestParam(value="rows",defaultValue="0",required=false) int rows,
                                    @RequestParam(value="sidx",defaultValue="riitemid",required=false) String sidx,
                                    @RequestParam(value="sord",defaultValue="asc",required=false) String sord,
                                    @RequestParam(value="_search",defaultValue="",required=false) boolean search,
                                    HttpServletRequest request){
        String data = request.getParameter("data");
        String searchFilter = " where hwstatus in (4,9) ";

        return generageOrderlistData(data, searchFilter, page, rows);
    }

    private JQListBean generageOrderlistData(String data, String searchFilter, int page, int rows){
        if(!Util.isEmpty(data)){
            OrderinfoFormBean formBean = JSON.parseObject(JSON.toJSONString(data), OrderinfoFormBean.class);
            if(formBean!=null){
                if(!Util.isEmpty(formBean.getHwserialno())){
                    searchFilter += " and hwserialno like '%" + formBean.getHwserialno() + "%'";
                }
                if(!Util.isEmpty(formBean.getCarplate())){
                    searchFilter += " and hmccarplate like '%" + formBean.getCarplate() + "%'";
                }
                if(!Util.isEmpty(formBean.getCarownername())){
                    searchFilter += " and hmcownername like '%" + formBean.getCarownername() + "%'";
                }
                if(formBean.getBusinesstypeid()>0){
                    searchFilter += " and hwbusinesstypeid =" + formBean.getBusinesstypeid();
                }
                if(!Util.isEmpty(formBean.getHwenterdtm())){
                    searchFilter += " and hwenterdtm >='" + formBean.getHwenterdtm() + "000000' and hwenterdtm<='" + formBean.getHwenterdtm() + "235959'";
                }
                if(!Util.isEmpty(formBean.getAcceptstaffname())){
                    searchFilter += " and d.hsname like '%" + formBean.getAcceptstaffname() + "%'";
                }
                if(formBean.getHwstatus()>-1){
                    searchFilter += " and hwstatus=" + formBean.getHwstatus();
                }
            }
        }
        String limitstr = Util.getLimitstr(page, rows);
        String orderstr = " order by hwid desc";
        JQListBean<OrderinfoFormBean> rtnvalue = new JQListBean<>();
        List<OrderinfoFormBean> list = carService.orderlist(searchFilter + orderstr + limitstr);
        int totalcnt = carService.orderlistSize(searchFilter);
        int totalPage = 0;
        if(totalcnt > 0) {
            totalPage = (int)Math.ceil(totalcnt / (rows * 1.0f));
        }
        rtnvalue.setRows(list);
        rtnvalue.setCurrpage(page);
        rtnvalue.setTotalpages(totalPage);
        rtnvalue.setTotalrecords(totalcnt);
        return rtnvalue;
    }

    @RequestMapping(value = "getcarinfobycarplate")
    public Rtnvalue getCarinfoByCarplate(HttpServletRequest request){
        Rtnvalue<HcMemberCar> rtnvalue = new Rtnvalue<>();
        String carplate = request.getParameter("carplate");
        HcMemberCar hcMemberCar = carService.getCarinfoByCarplate(carplate);
        rtnvalue.setObj(hcMemberCar);
        return rtnvalue;
    }


    @RequestMapping(value = "getbusinesstypelist")
    public Rtnvalue getBusinessTtypeList(HttpServletRequest request){
        Rtnvalue<HcBusinessType> rtnvalue = new Rtnvalue<>();
        List<HcBusinessType> list = carService.getBusinesstypelist();
        rtnvalue.setList(list);
        return rtnvalue;
    }

    //获取工单详情
    @RequestMapping(value = "getorderinfo")
    public Rtnvalue getOrderinfo(HttpServletRequest request){
        Rtnvalue<Orderinfo> rtnvalue = new Rtnvalue<>();
        String orderid = request.getParameter("orderid");
        if(!Util.isEmpty(orderid)){
            int orderidi = Util.converToInt(orderid);
            Orderinfo orderinfo = carService.getOrderinfoByOrderid(orderidi);
            rtnvalue.setObj(orderinfo);
        }
        return rtnvalue;
    }

    //获取工单材料
    @RequestMapping(value = "getordermtlist")
    public Rtnvalue getOrderMtlist(HttpServletRequest request){
        Rtnvalue<OrderMtinfo> rtnvalue = new Rtnvalue<>();
        String orderid = request.getParameter("orderid");
        if(!Util.isEmpty(orderid)){
            int orderidi = Util.converToInt(orderid);
            List<OrderMtinfo> mtlist = carService.getOrderMtlist(orderidi);
            rtnvalue.setList(mtlist);
        }
        return rtnvalue;
    }


    //保存工单材料
    @RequestMapping(value = "saveordermt")
    public Rtnvalue saveOrderMt(HttpServletRequest request){
        Rtnvalue<OrderMtinfo> rtnvalue = new Rtnvalue<>();
        String data = request.getParameter("data");
        if(!Util.isEmpty(data)){
            OrderMtinfo orderitem = JSON.parseObject(data, OrderMtinfo.class);
            if(orderitem != null){
                int status = carService.saveOrderItem(orderitem, 1);
                int orderid = orderitem.getHwmhwid();
                List<OrderMtinfo> mtlist = carService.getOrderMtlist(orderid);
                rtnvalue.setList(mtlist);
                if(status==-1){
                    rtnvalue.setStatus(1);
                    rtnvalue.setMsg("材料库存不足");
                }
            }
        }
        return rtnvalue;
    }


    //删除工单材料
    @RequestMapping(value = "delordermt")
    public Rtnvalue delOrderMt(HttpServletRequest request){
        Rtnvalue rtnvalue = new Rtnvalue<>();
        String hwmid = request.getParameter("hwmid");
        if(!Util.isEmpty(hwmid)){
            int hwmidi = Util.converToInt(hwmid);
            carService.setWorkorderMaterialDelSvc(hwmidi);
        }
        return rtnvalue;
    }

    //添加工单材料
    @RequestMapping(value = "addordermt")
    public Rtnvalue addOrdermt(HttpServletRequest request){
        Rtnvalue<OrderMtinfo> rtnvalue = new Rtnvalue<>();
        String data = request.getParameter("data");
        if(!Util.isEmpty(data)){
            AddOrderMaterialItemFormBean formBean = JSON.parseObject(data, AddOrderMaterialItemFormBean.class);
            if(formBean != null){
                rtnvalue = carService.addOrderSelectMaterialitem(formBean);
                int orderid = formBean.getOrderid();
                List<OrderMtinfo> mtlist = carService.getOrderMtlist(orderid);
                rtnvalue.setList(mtlist);
            }
        } else {
            rtnvalue.setStatus(1);
            rtnvalue.setMsg("参数错误");
        }
        return rtnvalue;
    }

    //工单结算
    @RequestMapping(value = "settlement")
    public Rtnvalue settlement(HttpServletRequest request){
        Rtnvalue rtnvalue = new Rtnvalue<>();
        String data = request.getParameter("data");
        if(!Util.isEmpty(data)){
            SetttlementFormBean formBean = JSON.parseObject(data, SetttlementFormBean.class);
            if(formBean != null){
                rtnvalue = carService.orderSettlement(formBean);
            }
        } else {
            rtnvalue.setStatus(1);
            rtnvalue.setMsg("参数错误");
        }
        return rtnvalue;
    }

    //工单收银
    @RequestMapping(value = "payment")
    public Rtnvalue payment(HttpServletRequest request){
        Rtnvalue rtnvalue = new Rtnvalue<>();
        String data = request.getParameter("data");
        if(!Util.isEmpty(data)){
            PaymentFormBean formBean = JSON.parseObject(data, PaymentFormBean.class);
            if(formBean != null){
                carService.orderPayment(formBean);
            }
        } else {
            rtnvalue.setStatus(1);
            rtnvalue.setMsg("参数错误");
        }
        return rtnvalue;
    }

    //工单交车
    @RequestMapping(value = "takecar")
    public Rtnvalue takecar(HttpServletRequest request){
        Rtnvalue rtnvalue = new Rtnvalue<>();
        String orderid = request.getParameter("orderid");
        int orderidi = Util.converToInt(orderid);
        if(orderidi>0){
            int status = carService.orderTakeCar(orderidi);
            if(status==-1){
                rtnvalue.setStatus(1);
                rtnvalue.setMsg("未收银不能交车");
            }
        } else {
            rtnvalue.setStatus(1);
            rtnvalue.setMsg("参数错误");
        }

        return rtnvalue;
    }

    //工单取消
    @RequestMapping(value = "ordercancel")
    public Rtnvalue ordercancel(HttpServletRequest request){
        Rtnvalue rtnvalue = new Rtnvalue<>();
        String orderid = request.getParameter("orderid");
        int orderidi = Util.converToInt(orderid);
        if(orderidi>0){
            carService.cancelOrder(orderidi);
        }

        return rtnvalue;
    }
}
