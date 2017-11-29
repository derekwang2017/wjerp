package com.wj.controller;

import com.alibaba.fastjson.JSON;
import com.wj.entity.HcMaterialCategory;
import com.wj.formbean.*;
import com.wj.service.MaterialitemService;
import com.wj.tools.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 87831 on 2017/11/14/0014.
 */
@RestController
@RequestMapping(value = "material")
public class MaterialitemCtl {
    @Autowired
    private MaterialitemService materialitemService;


    //当前材料列表
    @RequestMapping(value = "list")
    public JQListBean getList(@RequestParam(value="page",defaultValue="0",required=false) int page,
                                    @RequestParam(value="rows",defaultValue="0",required=false) int rows,
                                    @RequestParam(value="sidx",defaultValue="hmid",required=false) String sidx,
                                    @RequestParam(value="sord",defaultValue="asc",required=false) String sord,
                                    @RequestParam(value="_search",defaultValue="",required=false) boolean search,
                                    HttpServletRequest request){
        String data = request.getParameter("data");
        String searchFilter = "";
        if(!Util.isEmpty(data)){
            OrderinfoFormBean formBean = JSON.parseObject(JSON.toJSONString(data), OrderinfoFormBean.class);
            if(formBean!=null){
            }
        }
        String limitstr = Util.getLimitstr(page, rows);
        String orderstr = " order by " + sidx + " " + sord;
        JQListBean<MaterialListFormbean> rtnvalue = new JQListBean<>();
        List<MaterialListFormbean> list = materialitemService.getMaterialItemlist(searchFilter + orderstr + limitstr);
        int totalcnt = materialitemService.getMaterialitemlistSize(searchFilter);
        int totalPage = 0;
        if(totalcnt > 0) {
            totalPage = (int)Math.ceil(totalcnt / (rows * 1.0f));
        }
        rtnvalue.setRows(list);
        rtnvalue.setCurrpage(page);
        rtnvalue.setTotalpages(totalPage);
        rtnvalue.setTotalrecords(totalcnt);
        return rtnvalue;
    }

    @RequestMapping(value = "getmaterialinfo")
    public Rtnvalue getMaterialinfo(HttpServletRequest request){
        Rtnvalue<MaterialListFormbean> rtnvalue = new Rtnvalue<>();
        String hmid = request.getParameter("hmid");
        int hmidi = Util.converToInt(hmid);
        MaterialListFormbean data = materialitemService.getMaterialInfoByid(hmidi);
        rtnvalue.setObj(data);
        return rtnvalue;
    }

    @RequestMapping(value = "savematerialitem")
    public Rtnvalue saveMaterialinfo(HttpServletRequest request){
        Rtnvalue rtnvalue = new Rtnvalue<>();
        String data = request.getParameter("data");
        if(!Util.isEmpty(data)){
            MaterialListFormbean formBean = JSON.parseObject(data, MaterialListFormbean.class);
            int flag = materialitemService.saveMaterial(formBean);
            if(flag==-1){
                rtnvalue.setStatus(1);
                rtnvalue.setMsg("已经存在相同的材料，请修改后保存");
            } else {
                rtnvalue.setMsg("保存成功");
            }
        } else {
            rtnvalue.setStatus(1);
            rtnvalue.setMsg("参数错误");
        }
        return rtnvalue;
    }


    @RequestMapping(value = "getcategorylist")
    public Rtnvalue getCategorylist(HttpServletRequest request){
        Rtnvalue<HcMaterialCategory> rtnvalue = new Rtnvalue<>();
        List<HcMaterialCategory> list = materialitemService.getCategorylist();
        rtnvalue.setList(list);
        return rtnvalue;
    }


    @RequestMapping(value = "addmaterialstock")
    public Rtnvalue addMaterialStock(HttpServletRequest request){
        Rtnvalue rtnvalue = new Rtnvalue<>();
        String data = request.getParameter("data");
        if(!Util.isEmpty(data)){
            AddMaterialStockFormBen formBen = JSON.parseObject(data, AddMaterialStockFormBen.class);
            materialitemService.addMaterialStock(formBen.getHmid(), formBen.getAmount(), formBen.getPrice(), 1);
        } else {
            rtnvalue.setStatus(1);
            rtnvalue.setMsg("参数错误");
        }
        return rtnvalue;
    }

    //当前材料列表
    @RequestMapping(value = "recordlist")
    public JQListBean getRecordList(@RequestParam(value="page",defaultValue="0",required=false) int page,
                              @RequestParam(value="rows",defaultValue="0",required=false) int rows,
                              @RequestParam(value="sidx",defaultValue="hmid",required=false) String sidx,
                              @RequestParam(value="sord",defaultValue="asc",required=false) String sord,
                              @RequestParam(value="_search",defaultValue="",required=false) boolean search,
                              HttpServletRequest request){
        String data = request.getParameter("data");
        String searchFilter = "";
        if(!Util.isEmpty(data)){
            MaterialRecordFormBean formBean = JSON.parseObject(JSON.toJSONString(data), MaterialRecordFormBean.class);
            if(formBean!=null){

            }
        }
        String limitstr = Util.getLimitstr(page, rows);
        String orderstr = " order by " + sidx + " " + sord;
        JQListBean<MaterialRecordFormBean> rtnvalue = new JQListBean<>();
        List<MaterialRecordFormBean> list = materialitemService.getMaterialRecordlistService(searchFilter + orderstr + limitstr);
        int totalcnt = materialitemService.getMaterialRecordlistSizeService(searchFilter);
        int totalPage = 0;
        if(totalcnt > 0) {
            totalPage = (int)Math.ceil(totalcnt / (rows * 1.0f));
        }
        rtnvalue.setRows(list);
        rtnvalue.setCurrpage(page);
        rtnvalue.setTotalpages(totalPage);
        rtnvalue.setTotalrecords(totalcnt);
        return rtnvalue;
    }
}
