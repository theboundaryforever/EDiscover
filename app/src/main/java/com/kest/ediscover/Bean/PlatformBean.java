package com.kest.ediscover.Bean;

/**
 * Created by dk on 2017/12/21.
 */

public class PlatformBean {
    private String id;
    private String bid;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBid(){
        return bid;
    }
    public void setBid(String bid){
        this.bid = bid;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public PlatformBean(String id,String bid, String name){
        this.id = id;
        this.bid = bid;
        this.name = name;

    }
}
