
package com.jsoh.myfirstandroidapp.chat.client;

public class MsgInfo {
    private String nickName;
    private String message;
    private long time;

    public MsgInfo(String nickName, String message, long time) {
        this.nickName = nickName;
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "MsgInfo [nickName=" + nickName + ", message=" + message + ", time=" + time + "]";
    }
}
