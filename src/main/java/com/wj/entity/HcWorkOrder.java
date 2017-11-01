package com.wj.entity;

import java.math.BigDecimal;

/**
 * Created by 87831 on 2017/10/30/0030.
 */
public class HcWorkOrder {
    private int hwid;
    private int hwmembercarid;
    private int hwbusinesstypeid;
    private String hwenterdtm;
    private String hwtakecardtm;
    private int hwstatus;
    private String hwdesc;
    private BigDecimal hwtotalamount;
    private BigDecimal hwpayamount;
    private BigDecimal hwdiscount;
    private int hwacceptstaffid;
    private String hwserialno;

    public int getHwid() {
        return hwid;
    }

    public void setHwid(int hwid) {
        this.hwid = hwid;
    }

    public int getHwmembercarid() {
        return hwmembercarid;
    }

    public void setHwmembercarid(int hwmembercarid) {
        this.hwmembercarid = hwmembercarid;
    }

    public int getHwbusinesstypeid() {
        return hwbusinesstypeid;
    }

    public void setHwbusinesstypeid(int hwbusinesstypeid) {
        this.hwbusinesstypeid = hwbusinesstypeid;
    }

    public String getHwenterdtm() {
        return hwenterdtm;
    }

    public void setHwenterdtm(String hwenterdtm) {
        this.hwenterdtm = hwenterdtm;
    }

    public String getHwtakecardtm() {
        return hwtakecardtm;
    }

    public void setHwtakecardtm(String hwtakecardtm) {
        this.hwtakecardtm = hwtakecardtm;
    }

    public int getHwstatus() {
        return hwstatus;
    }

    public void setHwstatus(int hwstatus) {
        this.hwstatus = hwstatus;
    }

    public String getHwdesc() {
        return hwdesc;
    }

    public void setHwdesc(String hwdesc) {
        this.hwdesc = hwdesc;
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

    public BigDecimal getHwdiscount() {
        return hwdiscount;
    }

    public void setHwdiscount(BigDecimal hwdiscount) {
        this.hwdiscount = hwdiscount;
    }

    public int getHwacceptstaffid() {
        return hwacceptstaffid;
    }

    public void setHwacceptstaffid(int hwacceptstaffid) {
        this.hwacceptstaffid = hwacceptstaffid;
    }

    public String getHwserialno() {
        return hwserialno;
    }

    public void setHwserialno(String hwserialno) {
        this.hwserialno = hwserialno;
    }
}
