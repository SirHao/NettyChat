package cqu.protocal.EncoderDecoder;

import cqu.Util.Serializer.Serializer;
import cqu.Util.Serializer.imp.JSONSerializer;
import cqu.protocal.Packet;
import cqu.protocal.PacketImp.LoginRequestPacket;
import cqu.protocal.PacketImp.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

import static cqu.protocal.Command.LOGIN_REQUEST;
import static cqu.protocal.Command.LOGIN_RESPONSE;

public class PacketEncDec {
    public static  PacketEncDec INSTANCE=new PacketEncDec();
    private static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public ByteBuf encode(ByteBuf byteBuf,Packet packet){

        byte[] bytes= Serializer.DEFAULT.serialize(packet);

        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommend());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf){

        byteBuf.readInt();//magic number todo:validate and resend

        byteBuf.readByte();//version todo:use multversion

        byte serializeAlgroithm=byteBuf.readByte();

        byte command=byteBuf.readByte();

        int length=byteBuf.readInt();

        byte []packetByte=new byte[length];
        byteBuf.readBytes(packetByte);

        Serializer serializer=getSerializer(serializeAlgroithm);
        Class <? extends Packet> packetType=getRequestType(command);
        Packet packet=serializer.deserialize(packetType,packetByte);

        return packet;


    }
    public Class<? extends Packet> getRequestType(byte command){
        if(!packetTypeMap.containsKey(command)){
            System.out.println("parse packer obj error");
            return null;
        }
        return packetTypeMap.get(command);
    }
    public Serializer getSerializer(byte algro){
        if(!serializerMap.containsKey(algro)){
            System.out.println("parse serillizer error");
            return null;
        }
        return serializerMap.get(algro);
    }





}
