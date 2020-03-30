package cqu.protocal;

import io.netty.buffer.ByteBuf;

public interface Command {
    Byte LOGIN_REQUEST=1;
    Byte LOGIN_RESPONSE=2;
    Byte MESSAGE_REQUEST=3;
    Byte MESSAGE_RESPONSE=4;

}
