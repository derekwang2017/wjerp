package com.wj.entity;

import java.math.BigDecimal;

/**
 * Created by 87831 on 2017/11/14/0014.
 */
public class HcMaterialItem {
    private int hmid;
    private String hmname;
    private BigDecimal hmstock;
    private String hmunit;
    private int hmhmbid;
    private int hmdelflag;
    private String hmcreatedtm;
    private int hmcreatestaffid;
    private int hmhmcid;
    private BigDecimal hmprice;
    private BigDecimal hmsaleprice;

    public int getHmid() {
        return hmid;
    }

    public void setHmid(int hmid) {
        this.hmid = hmid;
    }

    public String getHmname() {
        return hmname;
    }

    public void setHmname(String hmname) {
        this.hmname = hmname;
    }

    public BigDecimal getHmstock() {
        return hmstock;
    }

    public void setHmstock(BigDecimal hmstock) {
        this.hmstock = hmstock;
    }

    public String getHmunit() {
        return hmunit;
    }

    public void setHmunit(String hmunit) {
        this.hmunit = hmunit;
    }

    public int getHmhmbid() {
        return hmhmbid;
    }

    public void setHmhmbid(int hmhmbid) {
        this.hmhmbid = hmhmbid;
    }

    public int getHmdelflag() {
        return hmdelflag;
    }

    public void setHmdelflag(int hmdelflag) {
        this.hmdelflag = hmdelflag;
    }

    public String getHmcreatedtm() {
        return hmcreatedtm;
    }

    public void setHmcreatedtm(String hmcreatedtm) {
        this.hmcreatedtm = hmcreatedtm;
    }

    public int getHmcreatestaffid() {
        return hmcreatestaffid;
    }

    public void setHmcreatestaffid(int hmcreatestaffid) {
        this.hmcreatestaffid = hmcreatestaffid;
    }

    public int getHmhmcid() {
        return hmhmcid;
    }

    public void setHmhmcid(int hmhmcid) {
        this.hmhmcid = hmhmcid;
    }

    public BigDecimal getHmprice() {
        return hmprice;
    }

    public void setHmprice(BigDecimal hmprice) {
        this.hmprice = hmprice;
    }

    public BigDecimal getHmsaleprice() {
        return hmsaleprice;
    }

    public void setHmsaleprice(BigDecimal hmsaleprice) {
        this.hmsaleprice = hmsaleprice;
    }
}
