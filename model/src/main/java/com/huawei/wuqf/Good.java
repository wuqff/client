package com.huawei.wuqf;


/**
 * Created by wuqf on 16-9-17.
 */
public class Good {

    private Long id;
    private String name;
    private String[] regionIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getRegionIds() {
        return regionIds;
    }

    public void setRegionIds(String[] regionIds) {
        this.regionIds = regionIds;
    }


}
