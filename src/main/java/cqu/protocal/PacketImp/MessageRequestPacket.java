package cqu.protocal.PacketImp;

import cqu.protocal.Packet;


import static cqu.protocal.Command.MESSAGE_REQUEST;

public class MessageRequestPacket extends Packet {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommend() {
        return MESSAGE_REQUEST;
    }

}
