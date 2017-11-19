package com.wj.service;

import com.wj.dao.MaterialitemDao;
import com.wj.entity.HcMaterialBrand;
import com.wj.entity.HcMaterialCategory;
import com.wj.entity.HcMaterialItem;
import com.wj.entity.HcMaterialStockRecord;
import com.wj.formbean.MaterialListFormbean;
import com.wj.tools.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 87831 on 2017/11/19/0019.
 */
@Service
public class MaterialitemService {
    @Autowired
    private MaterialitemDao materialitemDao;

    public List<MaterialListFormbean> getMaterialItemlist(String filter){
        return materialitemDao.getMaterialitemList(filter);
    }

    public int getMaterialitemlistSize(String filter){
        return materialitemDao.getMaterialitemListSize(filter);
    }

    public MaterialListFormbean getMaterialInfoByid(int hmid){
        return materialitemDao.getMaterialitemById(hmid);
    }

    public int saveMaterial(MaterialListFormbean formbean){
        if(formbean!=null){
            String hmname = formbean.getHmname();
            String brandname = formbean.getBrandname();
            int categoryid = formbean.getHmhmcid();
            String unit = formbean.getHmunit();
            BigDecimal saleprice = formbean.getHmsaleprice();
            int hmid = formbean.getHmid();

            int brandid = getMtBrandid(brandname);

            int exists = materialitemDao.validMaterialExist(hmname, brandid, hmid);
            if(exists > 0){
                return -1;
            }

            if(hmid == 0){
                HcMaterialItem hcMaterialItem = new HcMaterialItem();
                hcMaterialItem.setHmname(hmname);
                hcMaterialItem.setHmunit(unit);
                hcMaterialItem.setHmhmbid(brandid);
                hcMaterialItem.setHmhmcid(categoryid);
                hcMaterialItem.setHmcreatestaffid(1);
                hcMaterialItem.setHmcreatedtm(Util.getNowYYYYMMDDHHMMSS());
                hcMaterialItem.setHmsaleprice(saleprice);
                materialitemDao.insertHcMaterialitem(hcMaterialItem);
            } else {
                HcMaterialItem hcMaterialItem = new HcMaterialItem();
                hcMaterialItem.setHmid(hmid);
                hcMaterialItem.setHmname(hmname);
                hcMaterialItem.setHmunit(unit);
                hcMaterialItem.setHmhmbid(brandid);
                hcMaterialItem.setHmhmcid(categoryid);
                hcMaterialItem.setHmsaleprice(saleprice);
                materialitemDao.updateHcMaterialItem(hcMaterialItem);
            }
        }
        return 0;
    }

    private int getMtBrandid(String brandname){
        if(Util.isEmpty(brandname)){
            return 0;
        }
        HcMaterialBrand hcMaterialBrand = materialitemDao.getBrandByName(brandname);
        if(hcMaterialBrand!=null && hcMaterialBrand.getHmbid()>0){
            return hcMaterialBrand.getHmbid();
        } else {
            hcMaterialBrand = new HcMaterialBrand();
            hcMaterialBrand.setHmbname(brandname);
            materialitemDao.insertHcMaterialBrand(hcMaterialBrand);
            return hcMaterialBrand.getHmbid();
        }
    }

    public List<HcMaterialCategory> getCategorylist(){
        return materialitemDao.getMaterialCategorylist();
    }

    public void addMaterialStock(int hmid, BigDecimal amount, BigDecimal price, int staffid){
        materialitemDao.updateMaterialStockAmount(amount, price, hmid);
        insertMaterialStockRecord(hmid, 0, amount, price, staffid, 0);
    }

    public void insertMaterialStockRecord(int hmid, int stocktype, BigDecimal amount, BigDecimal unitprice, int staffid, int relationid){
        HcMaterialStockRecord record = new HcMaterialStockRecord();
        record.setHmsrhmid(hmid);
        record.setHmsrstocktype(stocktype);
        record.setHmsramount(amount);
        record.setHmsrunitprice(unitprice);
        record.setHmsrtotalprice(amount.multiply(unitprice).setScale(0, BigDecimal.ROUND_HALF_UP));
        record.setHmsrstaffid(staffid);
        record.setHmsrrelationid(relationid);
        record.setHmsrdtm(Util.getNowYYYYMMDDHHMMSS());

        materialitemDao.insertMaterialStockRecord(record);
    }

    public List<HcMaterialItem> getMaterialitemByIds(String ids){
        return materialitemDao.getMaterialitemByIds(ids);
    }
}
