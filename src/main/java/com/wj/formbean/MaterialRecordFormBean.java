package com.wj.formbean;

/**
 * Created by 87831 on 2017/11/28/0028.
 * 材料库存变化明细记录
 */
public class MaterialRecordFormBean {
    private String orderno;  //使用材料工单号
    private String hmname;   //材料名称
    private String staffname;  //操作员工姓名
    private String hmunit;  //材料单位

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getHmname() {
        return hmname;
    }

    public void setHmname(String hmname) {
        this.hmname = hmname;
    }

    public String getStaffname() {
        return staffname;
    }

    public void setStaffname(String staffname) {
        this.staffname = staffname;
    }

    public String getHmunit() {
        return hmunit;
    }

    public void setHmunit(String hmunit) {
        this.hmunit = hmunit;
    }
}
