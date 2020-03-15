package cqu.Util.Serializer;

import cqu.Util.Serializer.imp.JSONSerializer;

public interface Serializer {

    Serializer DEFAULT=new JSONSerializer();

    byte getSerializerAlgorithm();

    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz,byte[] bytes);
}
