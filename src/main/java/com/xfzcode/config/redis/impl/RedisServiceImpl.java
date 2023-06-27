package com.xfzcode.config.redis.impl;

import com.xfzcode.config.redis.RedisService;
import com.xfzcode.utils.JacksonUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: XMLee
 * @Date: 2023/6/27 16:17
 * @Description:
 */
@Service
@Profile({"dev", "prev"})
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public String ping() {
        return redisTemplate.execute((RedisCallback<String>) (redisConnection) -> {
            return redisConnection.ping();
        });
    }

    @Override
    public void del(String key) {
        Objects.requireNonNull(key);
        redisTemplate.delete(key);
    }

    @Override
    public Long ttl(String key) {
        Objects.requireNonNull(key);
        return ttlInternal(key, TimeUnit.SECONDS);
    }

    @Override
    public Long pttl(String key) {
        Objects.requireNonNull(key);
        return ttlInternal(key, TimeUnit.MILLISECONDS);
    }

    private Long ttlInternal(String key, TimeUnit timeUnit) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(timeUnit);
        return redisTemplate.getExpire(key, timeUnit);
    }

    @Override
    public Boolean expire(String key, long timeOut) throws IllegalArgumentException, NullPointerException {
        Objects.requireNonNull(key);
        if (timeOut < 0) {
            throw new IllegalArgumentException("指定的过期时间不得小于 0！");
        }
        return redisTemplate.expire(key, timeOut, TimeUnit.SECONDS);
    }

    @Override
    public Boolean expireAt(String key, long timestamp) throws IllegalArgumentException, NullPointerException {
        Objects.requireNonNull(key);
        if (timestamp < 0) {
            throw new IllegalArgumentException("指定的时间戳不得小于 0");
        }
        return redisTemplate.expireAt(key, new Date(timestamp));
    }

    @Override
    public Boolean exists(String key) {
        Objects.requireNonNull(key);
        return redisTemplate.hasKey(key);
        //        //这样的实现是啥意思？
//        Object obj = redisTemplate.execute((RedisCallback) connection -> connection.exists(key.getBytes()));
//        boolean flag = true;
//        if (obj.toString().equals("false")) {
//            return false;
//        }
//        return flag;
    }

    @Override
    public Set<Object> scan(String pattern) {
        Objects.requireNonNull(pattern);
        return redisTemplate.execute((RedisCallback<Set<Object>>) connection -> {
            Set<Object> result = new HashSet<>();
            try (Cursor<byte[]> cursor = connection.scan
                    (
                            ScanOptions.scanOptions()
                                    .match(pattern)
                                    .count(10000)
                                    .build()
                    )
            ) {
                while (cursor.hasNext()) {
                    result.add(cursor.next());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return (result.size() > 0) ? result : Collections.emptySet();
        });
    }

    @Override
    public void set(String key, Object obj) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(obj);
        //获取用于操纵字符串键的 ValueOperations 对象
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        stringObjectValueOperations.set(key, obj);
    }

    @Override
    public void set(String key, Object obj, long timeOut) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(obj);
        if (timeOut < 0) {
            throw new IllegalArgumentException("指定的时间不得小于 0");
        }
        //获取用于操纵字符串键的 ValueOperations 对象
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        stringObjectValueOperations.set(key, obj, timeOut, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        Objects.requireNonNull(key);
        //获取用于操纵字符串键的 ValueOperations 对象
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        Object val = stringObjectValueOperations.get(key);
        if (val == null) {
            return null;
        }
        return JacksonUtil.toJson(val);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) throws IllegalArgumentException, NullPointerException {
        Objects.requireNonNull(key);
        Objects.requireNonNull(clazz);
        //获取用于操纵字符串键的 ValueOperations 对象
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        Object result = stringObjectValueOperations.get(key);
        if (result == null) {
            return null;
        } else if (!(clazz.isAssignableFrom(result.getClass()))) {
            throw new IllegalArgumentException("给定的键的值对象并非指定的类型:" + key);
        }
        return (T) result;
    }

    @Override
    public Long sAdd(String key, Object obj) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(obj);
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        return setOperations.add(key, obj);
    }


    @Override
    public Boolean sIsMember(String key, Object obj) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(obj);
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        return setOperations.isMember(key, obj);
    }

    @Override
    public String sMembers(String key) {
        Objects.requireNonNull(key);
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        Set<Object> members = setOperations.members(key);
        if (members != null && members.size() > 0) {
            String result = JacksonUtil.toJson(members);
            return result.substring(1, result.length() - 1);
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Set<T> sMembers(String key, Class<T> clazz) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(clazz);
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        Set<Object> members = setOperations.members(key);
        Set<T> result = new HashSet<>();
        if (members != null && members.size() > 0) {
            members.stream()
                    .filter(member -> clazz.isAssignableFrom(member.getClass()))
                    .forEach(member -> result.add((T) member));
        }
        return (result.size() > 0) ? result : Collections.emptySet();
    }

    @Override
    public void hSet(String key, String hashKey, Object hashVal) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(hashKey);
        Objects.requireNonNull(hashVal);
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, hashKey, hashVal);
    }

    @Override
    public void hSet(String key, Map<String, Object> value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        if (value.size() == 0) {
            throw new IllegalArgumentException("给出的 value 参数中应当至少存在一个元素！");
        }
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(key, value);
    }

    @Override
    public String hGet(String key, String hashKey) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(hashKey);
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        Object val = hashOperations.get(key, hashKey);
        if (val != null) {
            return JacksonUtil.toJson(val);
        } else {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T hGet(String key, String hashKey, Class<T> clazz) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(hashKey);
        Objects.requireNonNull(clazz);
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        Object val = hashOperations.get(key, hashKey);
        if (val != null) {
            if (clazz.isAssignableFrom(val.getClass())) {
                return (T) val;
            } else {
                throw new IllegalArgumentException("给定的键的值对象并非指定的类型:" + hashKey);
            }
        } else {
            return null;
        }
    }

    @Override
    public String hGetAll(String key) {
        Objects.requireNonNull(key);
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        Map<Object, Object> entries = hashOperations.entries(key);
        if (entries != null && entries.size() > 0) {
            return JacksonUtil.toJson(entries);
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Map<String, T> hGetAll(String key, Class<T> clazz) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(clazz);
        Map<String, T> result = new HashMap<>();
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        Map<Object, Object> entries = hashOperations.entries(key);
        if (entries != null) {
            Set<Map.Entry<Object, Object>> entrySet = entries.entrySet();
            for (Map.Entry<Object, Object> entry : entrySet) {
                Object entryKey = entry.getKey();
                Object entryVal = entry.getValue();
                if (entryKey instanceof String && clazz.isAssignableFrom(entryVal.getClass())) {
                    result.put((String) entryKey, (T) entryVal);
                }
            }
            return (result.size() > 0) ? result : Collections.emptyMap();
        } else {
            return Collections.emptyMap();
        }
    }

    @Override
    public Long lPush(String key, Object obj) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(obj);
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        return listOperations.leftPush(key, obj);
    }

    @Override
    public String lRange(String key, long start, long end) {
        Objects.requireNonNull(key);
        if (end < 0 || start > end) {
            throw new IllegalArgumentException("给定的 start 或者 end 参数有误！");
        }
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        List<Object> range = listOperations.range(key, start, end);
        if (range != null && range.size() > 0) {
            String result = JacksonUtil.toJson(range);
            return result.substring(1, result.length() - 1);
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> lRange(String key, long start, long end, Class<T> clazz) {
        Objects.requireNonNull(key);
        if (end < 0 || start > end) {
            throw new IllegalArgumentException("给定的 start 或者 end 参数有误！");
        }
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        List<Object> range = listOperations.range(key, start, end);
        List<T> result = new ArrayList<>();
        if (range != null && range.size() > 0) {
            for (Object obj : range) {
                if (clazz.isAssignableFrom(obj.getClass())) {
                    result.add((T) obj);
                }
            }
        }
        return (result.size() > 0) ? result : Collections.emptyList();
    }

    @Override
    public void hSet(String key, String hashKey, String hashValue, int timeout, TimeUnit timeUnit) {
        redisTemplate.opsForHash().put(key, hashKey, hashValue);
        redisTemplate.expire(key, timeout, timeUnit);
    }

    @Override
    public Set<String> keys(String s) {
        return redisTemplate.keys(s);
    }
}

