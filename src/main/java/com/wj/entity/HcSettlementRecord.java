package com.wj.entity;

import java.math.BigDecimal;

/**
 * Created by 87831 on 2017/11/23/0023.
 */
public class HcSettlementRecord {
    private int hsrid;
    private int hsrorderid;
    private BigDecimal hsrprice;
    private String hsrdtm;
    private int hsrstaffid;

    public int getHsrid() {
        return hsrid;
    }

    public void setHsrid(int hsrid) {
        this.hsrid = hsrid;
    }

    public int getHsrorderid() {
        return hsrorderid;
    }

    public void setHsrorderid(int hsrorderid) {
        this.hsrorderid = hsrorderid;
    }

    public BigDecimal getHsrprice() {
        return hsrprice;
    }

    public void setHsrprice(BigDecimal hsrprice) {
        this.hsrprice = hsrprice;
    }

    public String getHsrdtm() {
        return hsrdtm;
    }

    public void setHsrdtm(String hsrdtm) {
        this.hsrdtm = hsrdtm;
    }

    public int getHsrstaffid() {
        return hsrstaffid;
    }

    public void setHsrstaffid(int hsrstaffid) {
        this.hsrstaffid = hsrstaffid;
    }
}
