package com.wj.formbean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 87831 on 2017/10/16/0016.
 */
public class JQListBean<T> {
    private int currpage = 0;	//当前页数
    private List<T> rows = new ArrayList<T>();
    private int totalrecords = 0;	//全件数
    private int totalpages = 0;  //全页数

    public int getCurrpage() {
        return currpage;
    }

    public void setCurrpage(int currpage) {
        this.currpage = currpage;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotalrecords() {
        return totalrecords;
    }

    public void setTotalrecords(int totalrecords) {
        this.totalrecords = totalrecords;
    }

    public int getTotalpages() {
        return totalpages;
    }

    public void setTotalpages(int totalpages) {
        this.totalpages = totalpages;
    }
}
