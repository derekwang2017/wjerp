package com.wj.formbean;

import com.wj.entity.HcMaterialItem;

/**
 * Created by 87831 on 2017/11/14/0014.
 */
public class MaterialListFormbean extends HcMaterialItem {
    private String brandname;
    private String creatstaffname;
    private String materialcategory;

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getCreatstaffname() {
        return creatstaffname;
    }

    public void setCreatstaffname(String creatstaffname) {
        this.creatstaffname = creatstaffname;
    }

    public String getMaterialcategory() {
        return materialcategory;
    }

    public void setMaterialcategory(String materialcategory) {
        this.materialcategory = materialcategory;
    }
}
