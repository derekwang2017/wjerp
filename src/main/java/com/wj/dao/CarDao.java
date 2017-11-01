package com.wj.dao;

import com.wj.entity.HcMemberCar;
import com.wj.entity.HcWorkOrder;
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
    
    @Select("select a.hwid,a.hwserialno, b.hmccarplate carplate, b.hmcownername carownername, c.hbtname, b.hmccartype, a.hwenterdtm, d.hsname, a.hwstatus, a.hwbusinesstypeid businesstypeid" +
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
}
