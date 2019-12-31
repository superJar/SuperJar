package com.jar.pojo;

import java.io.Serializable;

/**
 * @author:superJar
 * @date:2019/12/27
 * @time:12:06
 * @details:
 */
public class PackageReport implements Serializable {
    private String packageName;
    private Integer packageCount;

    @Override
    public String toString() {
        return "PackageReport{" +
                "packageName='" + packageName + '\'' +
                ", packageCount=" + packageCount +
                '}';
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Integer getPackageCount() {
        return packageCount;
    }

    public void setPackageCount(Integer packageCount) {
        this.packageCount = packageCount;
    }
}
