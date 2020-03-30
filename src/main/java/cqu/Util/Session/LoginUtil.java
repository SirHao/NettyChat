package cqu.Util.Session;

import io.netty.channel.Channel;
import io.netty.util.Attribute;


public class LoginUtil {
    public static void markAsLogin(Channel channel){
        channel.attr(ServerAttributes.LOGIN).set(true);
    }
    public static boolean hasLogin(Channel channel){
        Attribute<Boolean> loginAttr=channel.attr(ServerAttributes.LOGIN);
        if(loginAttr.get()!=null){
            return loginAttr.get();
        }else return false;
    }
}
