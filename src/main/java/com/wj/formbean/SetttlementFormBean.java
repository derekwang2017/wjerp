package com.wj.formbean;

import java.math.BigDecimal;

/**
 * Created by 87831 on 2017/11/23/0023.
 */
public class SetttlementFormBean {
    private int orderid;
    private BigDecimal workhourprice;  //工时费

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public BigDecimal getWorkhourprice() {
        return workhourprice;
    }

    public void setWorkhourprice(BigDecimal workhourprice) {
        this.workhourprice = workhourprice;
    }
}
