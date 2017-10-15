package com.wj.dao;

import com.wj.entity.HcMemberCar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by 87831 on 2017/10/15/0015.
 */
@Mapper
public interface CarDao {
    @Select("select * from hc_member_car")
    List<HcMemberCar> getCarlist();
}
