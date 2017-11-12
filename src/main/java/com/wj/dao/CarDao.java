package com.wj.dao;

import com.wj.entity.*;
import com.wj.formbean.OrderMtinfo;
import com.wj.formbean.Orderinfo;
import com.wj.formbean.OrderinfoFormBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by 87831 on 2017/10/15/0015.
 */
@Mapper
public interface CarDao {
    @Select("select * from hc_member_car")
    List<HcMemberCar> getCarlist();

    @Select("select * from hc_member_car where hmccarplate=#{carplate}")
    HcMemberCar getMembercarByPlate(@Param("carplate") String carplate);

    @Update("update hc_member_car set hmcownername=#{hmcownername}, hmcownertel=#{hmcownertel}, hmcmile=#{hmcmile}, hmccartype=#{hmccartype} where hmcid=#{hmcid}")
    void syncMemberCar(HcMemberCar hcMemberCar);

    @Insert("insert into hc_member_car (hmccarplate, hmcownername, hmcownertel, hmcmile, hmccartype) values (#{hmccarplate}, #{hmcownername}, #{hmcownertel}, #{hmcmile}, #{hmccartype})")
    @Options(useGeneratedKeys = true, keyProperty = "hmcid")
    void insertMemberCar(HcMemberCar hcMemberCar);

    @Select("select * from hc_workorder where hwmembercarid=#{membercarid} and hwstatus<9 order by hwid limit 1")
    HcWorkOrder getCurWorkOrder(@Param("membercarid") int membercarid);

    @Select("select count(1) from hc_workorder where hwenterdtm>=#{startdtm} and hwenterdtm<=#{enddtm}")
    int getTodayOrdercnt(@Param("startdtm") String startdtm, @Param("enddtm") String enddtm);

    @Insert("insert into hc_workorder (hwmembercarid,hwbusinesstypeid,hwenterdtm,hwdesc,hwacceptstaffid,hwserialno)" +
            " values (#{hwmembercarid},#{hwbusinesstypeid},#{hwenterdtm},#{hwdesc},#{hwacceptstaffid},#{hwserialno})")
    @Options(useGeneratedKeys = true, keyProperty = "hwid")
    void insertWorkOrder(HcWorkOrder workOrder);
    
    @Select("select a.hwid,a.hwserialno, b.hmccarplate carplate, b.hmcownername carownername, c.hbtname businesstypename," +
            " b.hmccartype cartypename, a.hwenterdtm, d.hsname acceptstaffname, a.hwstatus, a.hwbusinesstypeid businesstypeid" +
            " from hc_workorder a" +
            " LEFT JOIN hc_member_car b on b.hmcid=a.hwmembercarid" +
            " LEFT JOIN hc_business_type c on c.hbtid=a.hwbusinesstypeid" +
            " LEFT JOIN hc_staff d on d.hsid=a.hwacceptstaffid" +
            " where hwstatus<9 ${filter}")
    List<OrderinfoFormBean> getOrderinfolist(@Param("filter") String filter);

    @Select("select count(1)" +
            " from hc_workorder a" +
            " LEFT JOIN hc_member_car b on b.hmcid=a.hwmembercarid" +
            " LEFT JOIN hc_business_type c on c.hbtid=a.hwbusinesstypeid" +
            " LEFT JOIN hc_staff d on d.hsid=a.hwacceptstaffid" +
            " where hwstatus<9 ${filter}")
    int getOrderinfolistsize(@Param("filter") String filter);

    @Select("select * from hc_business_type")
    List<HcBusinessType> getBusinessTypelist();
    
    @Select("select a.hwid orderid, a.hwserialno serialno, c.hsname acceptorname, a.hwenterdtm entrydtm, b.hmccarplate carplate," +
            "b.hmcownername carownername, b.hmcownertel carownertel, b.hmccartype cartype, a.hwentrymile entermile, d.hbtname orderbusiness, a.hwtakecardtm takecardtm," +
            "a.hwdesc orderdesc" +
            " from hc_workorder a" +
            " LEFT JOIN hc_member_car b on b.hmcid=a.hwmembercarid" +
            " LEFT JOIN hc_staff c on c.hsid=a.hwacceptstaffid" +
            " LEFT JOIN hc_business_type d on d.hbtid=a.hwbusinesstypeid" +
            " where a.hwid=#{orderid}")
    Orderinfo getOrderinfoByOrderid(@Param("orderid") int orderid);
    
    @Select("select hwmid, hwmhmid, hwmtotalprice, c.hmcname mttype, b.hmname mtname, d.hmbname," +
            " a.hwmamount mtamount, a.hwmunitprice mtprice, b.hmunit mtunit" +
            " from hc_workorder_material a" +
            " LEFT JOIN hc_material_item b on b.hmid=a.hwmhmid" +
            " LEFT JOIN hc_material_category c on c.hmcid=b.hmhmcid" +
            " LEFT JOIN hc_material_brand d on d.hmbid=b.hmhmbid" +
            " where hwmhwid=#{orderid} and hwmdelflag=0")
    List<OrderMtinfo> getOrderMtList(@Param("orderid") int orderid);

    @Select("select * from hc_material_category where hmcname = #{hmcname}")
    HcMaterialCategory getCategoryByName(@Param("hmcname") String hmcname);

    @Insert("insert into hc_material_category (hmcname) values (#{hmcname})")
    @Options(useGeneratedKeys = true, keyProperty = "hmcid")
    void insertHcMaterialCategory(HcMaterialCategory hcMaterialCategory);

    @Select("select * from hc_material_brand where hmbname = #{hmbname}")
    HcMaterialBrand getBrandByName(@Param("hmbname") String hmbname);

    @Insert("insert into hc_material_brand (hmbname) values (#{hmbname})")
    @Options(useGeneratedKeys = true, keyProperty = "hmbid")
    void insertHcMaterialBrand(HcMaterialBrand hcMaterialBrand);

    @Insert("insert into hc_workorder_material (hwmhwid, hwmhmid, hwmamount, hwmunitprice, hwmtotalprice, hwmstaffid, hwmcreatedtm)" +
            " values (#{hwmhwid}, #{hwmhmid}, #{hwmamount}, #{hwmunitprice}, #{hwmtotalprice}, #{hwmstaffid}, #{hwmcreatedtm})")
    @Options(useGeneratedKeys = true, keyProperty = "hwmid")
    void insertWorkorderMaterial(HcWorkorderMaterial hcWorkorderMaterial);

    @Update("update hc_workorder_material set hwmdelflag = 1 where hwmid=#{hwmid}")
    void setHcWorkorderMaterialDel(@Param("hwmid") int hwmid);

    @Update("update hc_workorder_material set hwmamount=#{hwmamount}, hwmunitprice=#{hwmunitprice}, hwmtotalprice=#{hwmtotalprice} where hwmid=#{hwmid}")
    void updateHcWorkorderMaterial(HcWorkorderMaterial hcWorkorderMaterial);
}
