package com.hyphenate.easeui.domain;


import com.hyphenate.easeui.utils.EaseCommonUtils;

/**
 * Created by Administrator on 2018\3\29 0029.
 */

public class UserClass2 {

    String user_id;

    String username;

    String hx_username;

    String nickname;

    String img;

    String initialLetter;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHx_username() {
        return hx_username;
    }

    public void setHx_username(String hx_username) {
        this.hx_username = hx_username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getInitialLetter() {
        if(initialLetter == null){
            EaseCommonUtils.setUserInitialLetter2(this);
        }
        return initialLetter;
    }

    public void setInitialLetter(String initialLetter) {
        this.initialLetter = initialLetter;
    }
}
