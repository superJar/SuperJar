package com.jar.entity;

import java.io.Serializable;

/**
 * 封装对象: 查询条件
 */
public class QueryPageBean implements Serializable {
    private Integer currentPage; //页码
    private Integer pageSize; //每页数量
    private String queryString; //查询条件

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}
