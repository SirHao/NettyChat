package cqu.Util.Session;

import io.netty.util.AttributeKey;

public interface ServerAttributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
