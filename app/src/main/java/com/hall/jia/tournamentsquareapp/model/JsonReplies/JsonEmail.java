package com.hall.jia.tournamentsquareapp.model.JsonReplies;

public class JsonEmail {
    private boolean success;
    private String uname;



    public String getUname() {
        return uname;
    }

    public void setErr(String uname) {
        this.uname = uname;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}