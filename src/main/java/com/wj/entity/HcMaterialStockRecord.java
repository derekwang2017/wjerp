package com.wj.entity;

import java.math.BigDecimal;

/**
 * Created by 87831 on 2017/11/19/0019.
 */
public class HcMaterialStockRecord {
    private int hmsrid;
    private int hmsrhmid;
    private int hmsrstocktype;
    private BigDecimal hmsramount;
    private BigDecimal hmsrunitprice;
    private BigDecimal hmsrtotalprice;
    private int hmsrstaffid;
    private int hmsrrelationid;
    private String hmsrdtm;

    public int getHmsrid() {
        return hmsrid;
    }

    public void setHmsrid(int hmsrid) {
        this.hmsrid = hmsrid;
    }

    public int getHmsrhmid() {
        return hmsrhmid;
    }

    public void setHmsrhmid(int hmsrhmid) {
        this.hmsrhmid = hmsrhmid;
    }

    public int getHmsrstocktype() {
        return hmsrstocktype;
    }

    public void setHmsrstocktype(int hmsrstocktype) {
        this.hmsrstocktype = hmsrstocktype;
    }

    public BigDecimal getHmsramount() {
        return hmsramount;
    }

    public void setHmsramount(BigDecimal hmsramount) {
        this.hmsramount = hmsramount;
    }

    public BigDecimal getHmsrunitprice() {
        return hmsrunitprice;
    }

    public void setHmsrunitprice(BigDecimal hmsrunitprice) {
        this.hmsrunitprice = hmsrunitprice;
    }

    public BigDecimal getHmsrtotalprice() {
        return hmsrtotalprice;
    }

    public void setHmsrtotalprice(BigDecimal hmsrtotalprice) {
        this.hmsrtotalprice = hmsrtotalprice;
    }

    public int getHmsrstaffid() {
        return hmsrstaffid;
    }

    public void setHmsrstaffid(int hmsrstaffid) {
        this.hmsrstaffid = hmsrstaffid;
    }

    public int getHmsrrelationid() {
        return hmsrrelationid;
    }

    public void setHmsrrelationid(int hmsrrelationid) {
        this.hmsrrelationid = hmsrrelationid;
    }

    public String getHmsrdtm() {
        return hmsrdtm;
    }

    public void setHmsrdtm(String hmsrdtm) {
        this.hmsrdtm = hmsrdtm;
    }
}
