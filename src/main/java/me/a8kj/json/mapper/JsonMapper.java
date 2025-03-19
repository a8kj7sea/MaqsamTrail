package me.a8kj.json.mapper;

// mapper like a bridge which used to convert an object class fields to json 
public interface JsonMapper<U> {

    String serialize(U object);

    U deserialize(String json, Class<U> clazz);
}
