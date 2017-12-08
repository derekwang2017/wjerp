package com.wj.formbean;

import java.math.BigDecimal;

/**
 * Created by 87831 on 2017/11/1/0001.
 */
public class OrderinfoFormBean {
    private int hwid;  //工单id
    private String hwserialno;   //工单序号
    private String carplate;     //车牌
    private String carownername; //车主姓名
    private String businesstypename;  //业务类别名称
    private String cartypename;  //车型名称
    private String hwenterdtm;   //进场时间
    private String hwenterdtm2;   //进场时间  查询用
    private String acceptstaffname;  //接待人员
    private String orderstatusname;  //工单状态显示
    private int hwstatus;  //工单状态
    private int businesstypeid;  //业务类别
    private BigDecimal hwtotalamount;  //应收总金额
    private BigDecimal hwpayamount;    //实收总金额
    private String hwtakecardtm;  //交车时间
    private String hwtakecardtm2;  //交车时间   查询用

    public int getHwid() {
        return hwid;
    }

    public void setHwid(int hwid) {
        this.hwid = hwid;
    }

    public String getHwserialno() {
        return hwserialno;
    }

    public void setHwserialno(String hwserialno) {
        this.hwserialno = hwserialno;
    }

    public String getCarplate() {
        return carplate;
    }

    public void setCarplate(String carplate) {
        this.carplate = carplate;
    }

    public String getCarownername() {
        return carownername;
    }

    public void setCarownername(String carownername) {
        this.carownername = carownername;
    }

    public String getBusinesstypename() {
        return businesstypename;
    }

    public void setBusinesstypename(String businesstypename) {
        this.businesstypename = businesstypename;
    }

    public String getCartypename() {
        return cartypename;
    }

    public void setCartypename(String cartypename) {
        this.cartypename = cartypename;
    }

    public String getHwenterdtm() {
        return hwenterdtm;
    }

    public void setHwenterdtm(String hwenterdtm) {
        this.hwenterdtm = hwenterdtm;
    }

    public String getAcceptstaffname() {
        return acceptstaffname;
    }

    public void setAcceptstaffname(String acceptstaffname) {
        this.acceptstaffname = acceptstaffname;
    }

    public String getOrderstatusname() {
        return orderstatusname;
    }

    public void setOrderstatusname(String orderstatusname) {
        this.orderstatusname = orderstatusname;
    }

    public int getHwstatus() {
        return hwstatus;
    }

    public void setHwstatus(int hwstatus) {
        this.hwstatus = hwstatus;
    }

    public int getBusinesstypeid() {
        return businesstypeid;
    }

    public void setBusinesstypeid(int businesstypeid) {
        this.businesstypeid = businesstypeid;
    }

    public BigDecimal getHwtotalamount() {
        return hwtotalamount;
    }

    public void setHwtotalamount(BigDecimal hwtotalamount) {
        this.hwtotalamount = hwtotalamount;
    }

    public BigDecimal getHwpayamount() {
        return hwpayamount;
    }

    public void setHwpayamount(BigDecimal hwpayamount) {
        this.hwpayamount = hwpayamount;
    }

    public String getHwenterdtm2() {
        return hwenterdtm2;
    }

    public void setHwenterdtm2(String hwenterdtm2) {
        this.hwenterdtm2 = hwenterdtm2;
    }

    public String getHwtakecardtm() {
        return hwtakecardtm;
    }

    public void setHwtakecardtm(String hwtakecardtm) {
        this.hwtakecardtm = hwtakecardtm;
    }

    public String getHwtakecardtm2() {
        return hwtakecardtm2;
    }

    public void setHwtakecardtm2(String hwtakecardtm2) {
        this.hwtakecardtm2 = hwtakecardtm2;
    }
}
