package com.hall.jia.tournamentsquareapp.model.JsonReplies;

public class JsonRep {
    private String action;
    private Boolean success;
    private String err;

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String message) {
        this.action = message;
    }

    public Boolean getSuccess() {
        return success;

    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
