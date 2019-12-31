package com.jar.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 菜单
 */
public class Menu implements Serializable {

    private Integer id;
    private String name; //菜单名称
    private String linkUrl; //访问路径
    private String path; //菜单项所对应的路由路径
    private String priority; //优先级(用于排序)
    private String icon; //图标
    private String description; //描述

    private Set<Role> roles = new HashSet<Role>(0); //角色集合
    private List<Menu> children = new ArrayList<>(); //子菜单集合
    private String parentMenuId; //父菜单ID

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", path='" + path + '\'' +
                ", priority='" + priority + '\'' +
                ", icon='" + icon + '\'' +
                ", description='" + description + '\'' +
                ", parentMenuId='" + parentMenuId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(String parentMenuId) {
        this.parentMenuId = parentMenuId;
    }


}
