package com.wj.dao;

import com.wj.entity.HcMaterialBrand;
import com.wj.entity.HcMaterialCategory;
import com.wj.entity.HcMaterialItem;
import com.wj.entity.HcMaterialStockRecord;
import com.wj.formbean.MaterialListFormbean;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 87831 on 2017/11/14/0014.
 */
@Mapper
public interface MaterialitemDao {
    @Select("select a.*, b.hmbname brandname, c.hmcname materialcategory, d.hsname creatstaffname" +
            " from hc_material_item a" +
            " LEFT JOIN hc_material_brand b on b.hmbid=a.hmhmbid" +
            " LEFT JOIN hc_material_category c on c.hmcid=a.hmhmcid" +
            " LEFT JOIN hc_staff d on d.hsid=a.hmcreatestaffid" +
            " where hmdelflag=0 ${filter}")
    List<MaterialListFormbean> getMaterialitemList(@Param("filter") String filter);


    @Select("select count(1)" +
            " from hc_material_item a" +
            " LEFT JOIN hc_material_brand b on b.hmbid=a.hmhmbid" +
            " LEFT JOIN hc_material_category c on c.hmcid=a.hmhmcid" +
            " LEFT JOIN hc_staff d on d.hsid=a.hmcreatestaffid" +
            " where hmdelflag=0 ${filter}")
    int getMaterialitemListSize(@Param("filter") String filter);

    @Select("select a.*, b.hmbname brandname, c.hmcname materialcategory, d.hsname creatstaffname" +
            " from hc_material_item a" +
            " LEFT JOIN hc_material_brand b on b.hmbid=a.hmhmbid" +
            " LEFT JOIN hc_material_category c on c.hmcid=a.hmhmcid" +
            " LEFT JOIN hc_staff d on d.hsid=a.hmcreatestaffid" +
            " where hmid=#{hmid}")
    MaterialListFormbean getMaterialitemById(@Param("hmid") int hmid);


    @Select("select * from hc_material_brand where hmbname = #{hmbname}")
    HcMaterialBrand getBrandByName(@Param("hmbname") String hmbname);

    @Insert("insert into hc_material_brand (hmbname) values (#{hmbname})")
    @Options(useGeneratedKeys = true, keyProperty = "hmbid")
    void insertHcMaterialBrand(HcMaterialBrand hcMaterialBrand);

    @Select("select count(1) from hc_material_item where hmdelflag=0 and hmname=#{hmname} and hmhmbid=#{hmhmbid} and hmid<>#{hmid}")
    int validMaterialExist(@Param("hmname") String hmname, @Param("hmhmbid") int hmhmbid, @Param("hmid") int hmid);

    @Insert("insert into hc_material_item (hmname,hmunit,hmhmbid,hmcreatedtm,hmcreatestaffid,hmhmcid,hmsaleprice)" +
            " values (#{hmname}, #{hmunit}, #{hmhmbid}, #{hmcreatedtm}, #{hmcreatestaffid}, #{hmhmcid}, #{hmsaleprice})")
    @Options(useGeneratedKeys = true, keyProperty = "hmid")
    void insertHcMaterialitem(HcMaterialItem hcMaterialItem);

    @Update("update hc_material_item set hmname=#{hmname},hmunit=#{hmunit},hmhmbid=#{hmhmbid},hmhmcid=#{hmhmcid}, hmsaleprice=#{hmsaleprice}" +
            " where hmid=#{hmid}")
    void updateHcMaterialItem(HcMaterialItem hcMaterialItem);

    @Select("select * from hc_material_category")
    List<HcMaterialCategory> getMaterialCategorylist();

    @Update("update hc_material_item set hmstock=hmstock + #{hmstock}, hmprice=#{hmprice} where hmid=#{hmid}")
    void updateMaterialStockAmount(@Param("hmstock") BigDecimal hmstock,@Param("hmprice") BigDecimal hmprice, @Param("hmid") int hmid);

    @Insert("insert into hc_material_stock_record (hmsrhmid, hmsrstocktype, hmsramount, hmsrunitprice, hmsrtotalprice, hmsrstaffid, hmsrrelationid, hmsrdtm)" +
            " values (#{hmsrhmid}, #{hmsrstocktype}, #{hmsramount}, #{hmsrunitprice}, #{hmsrtotalprice}, #{hmsrstaffid}, #{hmsrrelationid}, #{hmsrdtm})")
    @Options(useGeneratedKeys = true, keyProperty = "hmsrid")
    void insertMaterialStockRecord(HcMaterialStockRecord record);

    @Select("select * from hc_material_item where hmid in (${ids})")
    List<HcMaterialItem> getMaterialitemByIds(@Param("ids") String ids);

    @Update("update hc_material_item set hmstock=hmstock - #{hmstock} where hmid=#{hmid}")
    void useMaterialStockAmount(@Param("hmstock") BigDecimal hmstock, @Param("hmid") int hmid);

}
