package com.wj.formbean;

import java.util.List;

/**
 * Created by 87831 on 2017/11/12/0012.
 */
public class Orderinfo {
    private int orderid;
    private String serialno;
    private String acceptorname;
    private String entrydtm;
    private String carplate;
    private String carownername;
    private String carownertel;
    private String cartype;
    private String entermile;
    private String orderbusiness;
    private String takecardtm;
    private String orderdesc;
    private List<OrderMtinfo> mtlist;

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getAcceptorname() {
        return acceptorname;
    }

    public void setAcceptorname(String acceptorname) {
        this.acceptorname = acceptorname;
    }

    public String getEntrydtm() {
        return entrydtm;
    }

    public void setEntrydtm(String entrydtm) {
        this.entrydtm = entrydtm;
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

    public String getCarownertel() {
        return carownertel;
    }

    public void setCarownertel(String carownertel) {
        this.carownertel = carownertel;
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    public String getEntermile() {
        return entermile;
    }

    public void setEntermile(String entermile) {
        this.entermile = entermile;
    }

    public String getOrderbusiness() {
        return orderbusiness;
    }

    public void setOrderbusiness(String orderbusiness) {
        this.orderbusiness = orderbusiness;
    }

    public String getTakecardtm() {
        return takecardtm;
    }

    public void setTakecardtm(String takecardtm) {
        this.takecardtm = takecardtm;
    }

    public String getOrderdesc() {
        return orderdesc;
    }

    public void setOrderdesc(String orderdesc) {
        this.orderdesc = orderdesc;
    }

    public List<OrderMtinfo> getMtlist() {
        return mtlist;
    }

    public void setMtlist(List<OrderMtinfo> mtlist) {
        this.mtlist = mtlist;
    }
}
