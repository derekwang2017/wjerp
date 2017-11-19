package com.wj.formbean;

import java.math.BigDecimal;

/**
 * Created by 87831 on 2017/11/19/0019.
 */
public class AddMaterialStockFormBen {
    private int hmid;
    private BigDecimal amount;
    private BigDecimal price;

    public int getHmid() {
        return hmid;
    }

    public void setHmid(int hmid) {
        this.hmid = hmid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
