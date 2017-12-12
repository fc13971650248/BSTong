package com.llf.common.entity;




/**
 * Created by Administrator on 2017/12/5 0005.
 */
 public class User  {
     //extends BmobUser
    private String nick;
    private String headPath;

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

}
