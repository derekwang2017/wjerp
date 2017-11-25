package com.wj.formbean;

import java.math.BigDecimal;

/**
 * Created by 87831 on 2017/11/23/0023.
 */
public class PaymentFormBean {
    private int orderid;
    private BigDecimal realpayamount;  //实付金额

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public BigDecimal getRealpayamount() {
        return realpayamount;
    }

    public void setRealpayamount(BigDecimal realpayamount) {
        this.realpayamount = realpayamount;
    }
}
