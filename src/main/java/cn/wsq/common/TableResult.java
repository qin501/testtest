package cn.wsq.common;

import java.util.List;

/*
* boostrap通用table对象
* */
public class TableResult<T>{
    private int  total;//总条数
    private List<T> rows;//总记录数

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
