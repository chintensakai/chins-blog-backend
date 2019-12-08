package com.chins.blog.backend.cache.utils;

import com.chins.blog.backend.commons.exception.BlogException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  /***
   * 设置过期时间
   * @param key
   * @param time
   */
  public void setExpire(String key, long time) {

    if (time > 0) {
      redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    throw new BlogException("setExpire failed.");
  }

  /**
   * 判断key是否存在
   *
   * @param key 传入ke值
   * @return true 存在  false  不存在
   */
  public Boolean existsKey(String key) {
    return redisTemplate.hasKey(key);
  }

  /**
   * 判断key存储的值类型
   *
   * @param key key值
   * @return DataType[string、list、set、zset、hash]
   */
  public DataType typeKey(String key) {
    return redisTemplate.type(key);
  }

  /**
   * 删除指定的一个数据
   *
   * @param key key值
   * @return true 删除成功，否则返回异常信息
   */
  public Boolean deleteKey(String key) {
    try {
      return redisTemplate.delete(key);
    } catch (Exception ex) {
      throw new BlogException("deleteKey failed!", ex);
    }
  }

  /**
   * 删除多个数据
   *
   * @param keys key的集合
   * @return true删除成功，false删除失败
   */
  public Boolean deleteKey(Collection<String> keys) {
    return redisTemplate.delete(keys) != 0;
  }

  //-------------------- String ----------------------------

  /**
   * 普通缓存放入
   *
   * @param key   键值
   * @param value 值
   * @return true成功 要么异常
   */
  public Boolean setString(String key, Object value) {
    try {
      redisTemplate.opsForValue().set(key, value);
      return true;
    } catch (Exception ex) {
      throw new BlogException("setString failed!", ex);
    }
  }

  /**
   * 普通缓存获取
   *
   * @param key 键
   * @return 值
   */
  public Object getString(String key) {
    return key == null ? null : redisTemplate.opsForValue().get(key);
  }

  /**
   * 设置缓存存在时间
   *
   * @param key   key值
   * @param value value值
   * @param time  时间 秒为单位
   * @return 成功返回true，失败返回异常信息
   */
  public boolean setString(String key, Object value, long time) {
    try {
      redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
      return true;
    } catch (Exception ex) {
      throw new BlogException("setStringWithExpire failed!", ex);
    }
  }

  //----------------------------- list ------------------------------

  /**
   * 将list放入缓存
   *
   * @param key   key的值
   * @param value 放入缓存的数据
   * @return true 代表成功，否则返回异常信息
   */
  public Boolean setList(String key, Object value) {
    try {
      redisTemplate.opsForList().rightPush(key, value);
      return true;
    } catch (Exception ex) {
      throw new BlogException("setList failed!", ex);
    }
  }

  /**
   * 将Object数据放入List缓存，并设置时间
   *
   * @param key   key值
   * @param value 数据的值
   * @param time  缓存的时间
   * @return true插入成功，否则返回异常信息
   */
  public Boolean setList(String key, Object value, long time) {
    try {
      if (time > 0) {
        redisTemplate.opsForList().rightPush(key, value);
        setExpire(key, time);
        return true;
      }
      return false;
    } catch (Exception ex) {
      throw new BlogException("setListWithExpire failed!", ex);
    }
  }

  /**
   * 将list集合放入List缓存，并设置时间
   *
   * @param key   key值
   * @param value 数据的值
   * @param time  缓存的时间
   * @return true插入成功，否则返回异常信息
   */
  public Boolean setListAll(String key, Object value, long time) {
    try {
      if (time > 0) {
        redisTemplate.opsForList().rightPushAll(key, value);
        this.setExpire(key, time);
        return true;
      }
      return false;
    } catch (Exception ex) {
      throw new BlogException("setListAll failed!", ex);
    }
  }

  /**
   * 根据索引获取缓存List中的内容
   *
   * @param key   key的值
   * @param start 索引开始
   * @param end   索引结束 0 到 -1代表所有值
   * @return 返回数据
   */
  public List<Object> getList(String key, long start, long end) {
    try {
      return redisTemplate.opsForList().range(key, start, end);
    } catch (Exception ex) {
      throw new BlogException("getList failed!", ex);
    }
  }

  /**
   * 删除List缓存中多个list数据
   *
   * @param key   key值
   * @param count 移除多少个
   * @param value 可以传null  或者传入存入的Value的值
   * @return 返回删除了多少个
   */
  public long deleteListIndex(String key, long count, Object value) {
    try {
      return redisTemplate.opsForList().remove(key, count, value);
    } catch (Exception ex) {
      throw new BlogException("deleteListIndex failed!", ex);
    }

  }

  /**
   * 获取List缓存的数据
   *
   * @param key key值
   * @return 返回长度
   */
  public long getListSize(String key) {
    try {
      return redisTemplate.opsForList().size(key);
    } catch (Exception ex) {
      throw new BlogException("getListSize failed! ", ex);
    }
  }

  /***
   * 将元素添加进有序集合
   * @param key
   * @param score
   * @param value
   * @return
   */
  public boolean sortSetAdd(String key, int score, Object value) {

    return redisTemplate.opsForZSet().add(key, value, score);
  }

  /***
   * 增加分数
   * @param key
   * @param value
   * @param score
   * @return 增加分数之后的元素分数
   */
  public Double sortSetZincr(String key, Object value, int score) {

    return redisTemplate.opsForZSet().incrementScore(key, value, score);
  }

  public Set sortSetRange(String key, int start, int end) {

    return redisTemplate.opsForZSet().reverseRange(key, start, end);
  }

  /***
   * 获取对应value的分数
   * @param key
   * @param value
   * @return
   */
  public Double sortSetScore(String key, Object value) {

    Double score = redisTemplate.opsForZSet().score(key, value);

    return score;
  }

}
