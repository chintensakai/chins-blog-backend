package com.chins.blog.backend.cache.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

//  private ObjectMapper objectMapper = new ObjectMapper();

  public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

  private Class<T> clazz;

  public FastJsonRedisSerializer(Class<T> clazz) {
    super();
    this.clazz = clazz;
  }

  @Override
  public byte[] serialize(T t) throws SerializationException {

    return (t == null) ? new byte[0]
        : JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
  }

  @Override
  public T deserialize(byte[] bytes) throws SerializationException {
    if (bytes == null || bytes.length <= 0) {
      return null;
    }

    String str = new String(bytes, DEFAULT_CHARSET);

    return JSON.parseObject(str, clazz);
  }

//  public void setObjectMapper(ObjectMapper objectMapper) {
//    Assert.notNull(objectMapper, "'objectMapper' must not be null");
//    this.objectMapper = objectMapper;
//  }
//
//  protected JavaType getJavaType(Class<?> clazz) {
//    return TypeFactory.defaultInstance().constructType(clazz);
//  }
}
