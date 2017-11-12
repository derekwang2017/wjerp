package com.wj.entity;

import java.math.BigDecimal;

/**
 * Created by 87831 on 2017/11/12/0012.
 */
public class HcWorkorderMaterial {
    private int hwmid;
    private int hwmhwid;
    private int hwmhmid;
    private BigDecimal hwmamount;
    private BigDecimal hwmunitprice;
    private BigDecimal hwmtotalprice;
    private int hwmstaffid;
    private String hwmcreatedtm;

    public int getHwmid() {
        return hwmid;
    }

    public void setHwmid(int hwmid) {
        this.hwmid = hwmid;
    }

    public int getHwmhwid() {
        return hwmhwid;
    }

    public void setHwmhwid(int hwmhwid) {
        this.hwmhwid = hwmhwid;
    }

    public int getHwmhmid() {
        return hwmhmid;
    }

    public void setHwmhmid(int hwmhmid) {
        this.hwmhmid = hwmhmid;
    }

    public BigDecimal getHwmamount() {
        return hwmamount;
    }

    public void setHwmamount(BigDecimal hwmamount) {
        this.hwmamount = hwmamount;
    }

    public BigDecimal getHwmunitprice() {
        return hwmunitprice;
    }

    public void setHwmunitprice(BigDecimal hwmunitprice) {
        this.hwmunitprice = hwmunitprice;
    }

    public BigDecimal getHwmtotalprice() {
        return hwmtotalprice;
    }

    public void setHwmtotalprice(BigDecimal hwmtotalprice) {
        this.hwmtotalprice = hwmtotalprice;
    }

    public int getHwmstaffid() {
        return hwmstaffid;
    }

    public void setHwmstaffid(int hwmstaffid) {
        this.hwmstaffid = hwmstaffid;
    }

    public String getHwmcreatedtm() {
        return hwmcreatedtm;
    }

    public void setHwmcreatedtm(String hwmcreatedtm) {
        this.hwmcreatedtm = hwmcreatedtm;
    }
}
