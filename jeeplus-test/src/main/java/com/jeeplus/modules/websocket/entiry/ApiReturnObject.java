package com.jeeplus.modules.websocket.entiry;

/**
 * @author WangGuipin
 * @ClassName ApiReturnObject
 * @Description TODO
 * @create 2019-05-21 11:52
 * @desc ApiReturnObject
 **/
public class ApiReturnObject {
    private int cid;
    private String message;

    public ApiReturnObject(){}
    public ApiReturnObject(int cid, String message) {
        this.cid = cid;
        this.message = message;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiReturnObject{" +
                "cid=" + cid +
                ", message='" + message + '\'' +
                '}';
    }
}
