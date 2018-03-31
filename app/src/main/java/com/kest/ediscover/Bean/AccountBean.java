package com.kest.ediscover.Bean;

/**
 * Created by dk on 2017/12/22.
 */

public class AccountBean {
    private String id;
    private String bid;
    private String name;//phone
    private String platformName;
    private String url;
    private String ptoken;

    public AccountBean(String id, String bid, String name, String platformName, String url, String ptoken) {
        this.id = id;
        this.bid = bid;
        this.name = name;
        this.platformName = platformName;
        this.url = url;
        this.ptoken = ptoken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPtoken() {
        return ptoken;
    }

    public void setPtoken(String ptoken) {
        this.ptoken = ptoken;
    }
}
