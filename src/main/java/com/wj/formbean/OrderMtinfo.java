package com.wj.formbean;

import java.math.BigDecimal;

/**
 * Created by 87831 on 2017/11/12/0012.
 */
public class OrderMtinfo {
    private int hwmid;
    private int hwmhmid;
    private int hwmhwid;
    private String mttype;
    private String mtname;
    private String mtbrandname;
    private BigDecimal mtamount;
    private BigDecimal mtprice;
    private BigDecimal hwmtotalprice;
    private String mtunit;

    public int getHwmid() {
        return hwmid;
    }

    public void setHwmid(int hwmid) {
        this.hwmid = hwmid;
    }

    public String getMttype() {
        return mttype;
    }

    public void setMttype(String mttype) {
        this.mttype = mttype;
    }

    public String getMtname() {
        return mtname;
    }

    public void setMtname(String mtname) {
        this.mtname = mtname;
    }

    public String getMtbrandname() {
        return mtbrandname;
    }

    public void setMtbrandname(String mtbrandname) {
        this.mtbrandname = mtbrandname;
    }

    public int getHwmhmid() {
        return hwmhmid;
    }

    public void setHwmhmid(int hwmhmid) {
        this.hwmhmid = hwmhmid;
    }

    public BigDecimal getHwmtotalprice() {
        return hwmtotalprice;
    }

    public void setHwmtotalprice(BigDecimal hwmtotalprice) {
        this.hwmtotalprice = hwmtotalprice;
    }

    public BigDecimal getMtamount() {
        return mtamount;
    }

    public void setMtamount(BigDecimal mtamount) {
        this.mtamount = mtamount;
    }

    public BigDecimal getMtprice() {
        return mtprice;
    }

    public void setMtprice(BigDecimal mtprice) {
        this.mtprice = mtprice;
    }

    public int getHwmhwid() {
        return hwmhwid;
    }

    public void setHwmhwid(int hwmhwid) {
        this.hwmhwid = hwmhwid;
    }
}
