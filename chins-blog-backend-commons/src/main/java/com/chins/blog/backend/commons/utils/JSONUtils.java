package com.chins.blog.backend.commons.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class JSONUtils {

  //获取jackson对象
  private static ObjectMapper objectMapper = new ObjectMapper();

  /**
   * 将对象转换为Json字符串
   */
  public static String objectToJson(Object obj) {
    try {
      //将对象转换为Json字符串
      String jsonStr = objectMapper.writeValueAsString(obj);
      return jsonStr;
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 将Json字符串转换为对象
   */
  public static <T> T jsonToObject(String jsonStr, Class<T> clazz) {
    try {
      T t = objectMapper.readValue(jsonStr, clazz);
      return t;
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /***
   * map转json String
   */
  public static String mapToJson(Map<String, Object> map) {

    return JSON.toJSONString(map);
  }
}
