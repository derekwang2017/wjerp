package com.wj.formbean;

import com.wj.entity.HcMemberCar;

/**
 * Created by 87831 on 2017/10/29/0029.
 * 接车接收对象
 */
public class AcceptCarFormBean {
    private int membercarid;
    private String membercarplate;
    private HcMemberCar carinfo;
    private int businesstypeid;
    private String hwdesc;

    public int getMembercarid() {
        return membercarid;
    }

    public void setMembercarid(int membercarid) {
        this.membercarid = membercarid;
    }

    public String getMembercarplate() {
        return membercarplate;
    }

    public void setMembercarplate(String membercarplate) {
        this.membercarplate = membercarplate;
    }

    public HcMemberCar getCarinfo() {
        return carinfo;
    }

    public void setCarinfo(HcMemberCar carinfo) {
        this.carinfo = carinfo;
    }

    public int getBusinesstypeid() {
        return businesstypeid;
    }

    public void setBusinesstypeid(int businesstypeid) {
        this.businesstypeid = businesstypeid;
    }

    public String getHwdesc() {
        return hwdesc;
    }

    public void setHwdesc(String hwdesc) {
        this.hwdesc = hwdesc;
    }
}
