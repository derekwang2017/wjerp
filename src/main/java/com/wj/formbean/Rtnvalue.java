package com.wj.formbean;

import java.util.List;

/**
 * Created by 87831 on 2017/10/15/0015.
 */
public class Rtnvalue<T> {
    private String msg;
    private int status;
    private T obj;
    private List<T> list;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
