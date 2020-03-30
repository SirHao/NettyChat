package cqu.protocal.PacketImp.Response;

import cqu.protocal.Packet;


import static cqu.protocal.Command.MESSAGE_RESPONSE;

public class MessageResponsePacket extends Packet {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommend() {
        return MESSAGE_RESPONSE;
    }

}