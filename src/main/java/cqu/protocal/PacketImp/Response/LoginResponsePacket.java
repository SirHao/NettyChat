package cqu.protocal.PacketImp.Response;

import cqu.protocal.Packet;

import static cqu.protocal.Command.LOGIN_RESPONSE;

public class LoginResponsePacket extends Packet {
    private String userId;

    private String userName;

    private boolean success;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    private String reason;


    @Override
    public Byte getCommend() {
        return LOGIN_RESPONSE;
    }
}
