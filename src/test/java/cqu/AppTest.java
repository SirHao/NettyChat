package cqu;

import static org.junit.Assert.assertTrue;

import cqu.protocal.EncoderDecoder.PacketEncDec;
import cqu.protocal.Packet;
import cqu.protocal.PacketImp.LoginRequestPacket;
import io.netty.buffer.ByteBuf;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test  //测试包的序列化以及字符流的解析 done
    public void testPacket(){
        LoginRequestPacket packet=new LoginRequestPacket();
        packet.setUserId("fadsfas");
        packet.setUsername("233");
        packet.setPassword("pwd");
        PacketEncDec packetEncDec=new PacketEncDec();
//        ByteBuf byteBuf=packetEncDec.encode(packet);
//        Packet packetnew=packetEncDec.decode(byteBuf);
        int a=0;
    }
}
