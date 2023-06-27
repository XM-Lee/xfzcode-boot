package com.xfzcode.config.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: XMLee
 * @Date: 2023/6/27 16:16
 * @Description: 抽象redis服务，方便不同的实现
 */
public interface RedisService {
    String ping();

    /**
     * 删除 redis 中的某个键
     * @param key redis 中的某个键的名字
     * @throws NullPointerException 当给出的 key 为空时，抛出该异常
     */
    void del(String key);

    /**
     * 返回 redis 中的某个键的剩余生存时间(TTL, time to live)，单位为秒。
     * @param key redis 中的某个键的名字
     * @return 某个键的剩余生存时间。
     *         当返回值为 -2 时，代表给出的键在 redis 中不存在;
     *         当返回值为 -1 时，代表给出的键在 redis 中存在，但是没有为该键设置剩余生存时间
     *         当返回值为 null 时，代表此时在 pipeline / transaction 中执行该操作
     * @throws NullPointerException 当给出的 key 为空时，抛出该异常
     */
    Long ttl(String key);

    /**
     * 返回 redis 中的某个键的剩余生存时间(TTL, time to live)，单位为毫秒。
     * @param key redis 中的某个键的名字
     * @return 某个键的剩余生存时间。
     *         当返回值为 -2 时，代表给出的键在 redis 中不存在;
     *         当返回值为 -1 时，代表给出的键在 redis 中存在，但是没有为该键设置剩余生存时间
     *         当返回值为 null 时，代表此时在 pipeline / transaction 中执行该操作
     * @throws NullPointerException 当给出的 key 为空时，抛出该异常
     */
    Long pttl(String key);

    /**
     * 为给定 key 设置生存时间，单位为秒
     * 完成该指令之后，key 将会在 timeOut 指定的秒数之后被 redis 删除
     * @param key redis 中的某个键的名字
     * @param timeOut 生存时间
     * @return 当设定过期时间成功时，返回 true。
     *         当返回 null 时，代表此时在 pipeline / transaction 中执行该操作
     * @throws IllegalArgumentException 当输入的 timeOut 小于 0 时，抛出该异常
     * @throws NullPointerException 当给出的 key 为空时，抛出该异常
     */
    Boolean expire(String key, long timeOut);

    /**
     * 为给定 key 设置生存时间，单位为秒
     * 完成该指令之后，key 将会在 timestamp 所指定的时刻到达的时候被 redis 删除
     * @param key redis 中的某个键的名字
     * @param timestamp UNIX 时间戳
     * @return 当设定过期时间成功时，返回 true
     *         当返回 null 时，代表此时在 pipeline / transaction 中执行该操作
     * @throws IllegalArgumentException 当给定的 timestamp 小于 0 时，抛出该异常
     * @throws NullPointerException 当给出的 key 为空时，抛出该异常
     */
    Boolean expireAt(String key, long timestamp);

    /**
     * 判定给定的 key 是否存在于 redis 中
     * @param key redis 中的某个键的名字
     * @return 存在则返回 true。
     *         当返回 null 时，代表此时在 pipeline / transaction 中执行该操作
     * @throws NullPointerException 当给出的 key 为空时，抛出该异常
     */
    Boolean exists(String key);

    /**
     * 根据 pattern 所指定的模式来扫描 redis 中所有的键，而后，返回所有符合该模式的键。
     * 由于 keys 指令可能会导致 redis 阻塞，故该指令底层采用的指令为 scan。
     * @param pattern 指定的模式。如：abc*，则返回所有以abc开始的键
     * @return 符合该模式的所有的键。
     *         假设没有找到任何元素，则返回空的集合(并非 null)。
     * @throws NullPointerException 当给出的 pattern 为空时，抛出该异常
     */
    Set<Object> scan(String pattern);

    //===========================================操纵 redis 中的字符串键的相关方法===========================================

    /**
     * 将给定的字符串键插入到 redis 中。
     * 其中，值对象将会被 RedisTemplate 使用序列化器 GenericJackson2JsonRedisSerializer 序列化之后，再储存到 redis 中。
     * @param key 字符串键的名字
     * @param obj 字符串键的值
     * @throws NullPointerException 当给出的 key，或者 obj 为空时，抛出该异常
     */
    void set(String key, Object obj) ;

    /**
     * 将给定的字符串键插入到 redis 中，同时指定该字符串键的过期时间。
     * 完成该指令之后，key 将会在 timeOut 指定的秒数之后被 redis 删除。
     * 其中，值对象将会被 RedisTemplate 使用序列化器 GenericJackson2JsonRedisSerializer 序列化之后，再储存到 redis 中。
     * 该指令的底层实现为 setEx。
     * @param key 字符串键的名字
     * @param obj 字符串键的值
     * @param timeOut 过期时间
     * @throws IllegalArgumentException 当输入的 timeOut 参数小于 0 时，抛出该异常
     * @throws NullPointerException 当给出的 key，或者 obj 为空时，抛出该异常
     */
    void set(String key, Object obj, long timeOut);

    /**
     * 假定给定的是一个字符串键，则返回键的值所对应的 JSON 字符串。
     * @param key 字符串键的名字
     * @return 字符串键的值所对应的 JSON 字符串。
     *         当查找不到该键所对应的值时，返回 null。
     *         当在 pipeline / transaction 中执行该操作时，也会返回 null。
     * @throws NullPointerException 给出的 key，或者 clazz 参数为空时，抛出该异常
     */
    String get(String key);

    /**
     * 假定给定的是一个字符串键，并且该键的值对象的类型为 clazz 所指代的类型时，返回键所对应的值。
     * 请不要在 pipeline / transaction 中执行该操作，因为返回结果会延迟给出。
     * 仅需保证指定键的值的类型为 clazz 的子类型即可。
     * @param key 字符串键的名字
     * @param clazz 字符串键所对应的值对象的类对象
     * @param <T> 字符串键所对应的值对象的类型
     * @return 字符串键的值。
     *         当查找不到该键所对应的值时，返回 null。
     *         当在 pipeline / transaction 中执行该操作时，也会返回 null。
     * @throws IllegalArgumentException 给定的键的值对象并非 clazz 所指代的类型时，抛出该异常
     * @throws NullPointerException 给出的 key，或者 clazz 参数为空时，抛出该异常
     */
    <T> T get(String key, Class<T> clazz);


    //===========================================操纵 redis 中的集合键的相关方法===========================================

    /**
     * 将 obj 添加进 key 所指定的集合键中
     * @param key 集合键的名字
     * @param obj 集合键所对应的值对象的类对象。它将被 GenericJackson2JsonRedisSerializer 序列化之后在存入 redis
     * @return 返回成功添加进入集合的元素数目。返回 null 时，意味着该指令是在 pipeline / transaction 中被调用的
     * @throws NullPointerException 当给定的 key，或者 obj 为空时，抛出该异常
     */
    Long sAdd(String key, Object obj);

    /**
     * 判定某个集合键中是否存在指定的对象
     * @param key 集合键的名字
     * @param obj 需要判断是否存在于集合键的对象
     * @return 存在则返回 true。返回 null 时，意味着该指令是在 pipeline / transaction 中被调用的
     * @throws NullPointerException 当给定的 key，或者 obj 为空时，抛出该异常
     */
    Boolean sIsMember(String key, Object obj);

    /**
     * 获取集合键中的所有元素
     * @param key 集合键的名字
     * @return 当给定键键存在且集合键本身非空时，返回该集合键中所有键构成的 JSON 字符串，
     *         否则，返回 null。
     *         注意，该指令不应当在 pipeline / transaction 中使用。
     * @throws NullPointerException 当给定的 key 为空时，抛出该异常
     */
    String sMembers(String key);

    /**
     * 获取集合键中所有类型为 clazz 所指定的类型的元素
     * 仅需保证指定键的值的类型为 clazz 的子类型即可。
     * @param key 集合键的名字
     * @param clazz 指定的类型
     * @param <T> 指定的类型
     * @return 当给定键键存在且集合键本身非空时，返回该集合键中所有类型为 clazz 所指定的类型的元素。
     *         假如最终得到的集合中不存在任何元素，则返回空的列表(并非 null)。
     *         在 pipeline / transaction 中使用时，也返回空的列表(并非 null)。
     * @throws NullPointerException 当 key 或者 clazz 为空时，抛出该异常
     */
    <T> Set<T> sMembers(String key, Class<T> clazz);

    //===========================================操纵 redis 中的哈希表键的相关方法===========================================

    /**
     * 将 hashKey 和 hashVal 组成的 entity 放入哈希键中
     * @param key 哈希键的名字
     * @param hashKey 即将放入哈希键的 entity 的键。它将会被 StringRedisSerializer 序列化之后存入 redis
     * @param hashVal 即将放入哈希键的 entity 的值。它将会被 GenericJackson2JsonRedisSerializer 序列化之后存入 redis
     * @throws NullPointerException 当 key，hashKey，hashVal 中的某一个为空时，都将抛出该异常
     */
    void hSet(String key, String hashKey, Object hashVal);

    /**
     * 将 value 中所有的 entity 放入哈希键中
     * @param key 哈希键的名字
     * @param value 即将放入哈希键中的 map。
     *              map 中的 entity 的键，将会被 StringRedisSerializer 序列化之后存入 redis；
     *              值，将会被 GenericJackson2JsonRedisSerializer 序列化之后存入 redis。
     * @throws NullPointerException 当给定的 key 或者 value 中有一个为空时，抛出该异常
     * @throws IllegalArgumentException 当给出的 value 中不含任何元素时，抛出该异常
     */
    void hSet(String key, Map<String, Object> value);

    /**
     * 从给定的哈希键中取出具有指定键的值，并且将其转换成为 JSON 字符串
     * @param key 哈希键的名字
     * @param hashKey 哈希键中的某个 entity 的键
     * @return JSON 字符串。当查找不到该对象的时候，返回 null。
     *         当在 pipeline / transaction 中调用时，也会返回 null。
     * @throws NullPointerException 当给定的 key 或者 hashKey 有一个为空时抛出该异常
     */
    String hGet(String key, String hashKey);

    /**
     * 从给定的哈希键中取出具有指定键的值，并且将其转换成为 clazz 指定的对象。
     * 仅需保证指定键的值的类型为 clazz 的子类型即可。
     * @param key 哈希键的名字
     * @param hashKey 哈希键中的某个 entity 的键
     * @param clazz 指定的类型
     * @param <T> 指定的类型
     * @return 指定类型的实例。当查找不到该对象的时候，返回 null。
     *         当在 pipeline / transaction 中调用时，也会返回 null。
     * @throws NullPointerException 当 key、hashKey、clazz 中有一个为空时，抛出该异常
     * @throws IllegalArgumentException 当给出的 clazz 的类型，与最终查询出来的元素的类型不匹配时，抛出该异常
     */
    <T> T hGet(String key, String hashKey, Class<T> clazz);

    /**
     * 取出 key 所指向的哈希键中的所有元素，并且返回一个 JSON 字符串
     * @param key 哈希键的名字
     * @return JSON 字符串。当该查找不到指定的哈希键，或者指定的哈希键中不存在任何元素时，返回 null。
     *         当在 pipeline / transaction 中调用时，也会返回 null。
     * @throws NullPointerException 当给定的 key 为空时，抛出该异常
     */
    String hGetAll(String key);

    /**
     * 取出 key 所指向的哈希键中的所有类型为 clazz 所指定的类型的元素，并返回一个集合。
     * 仅需保证指定键的值的类型为 clazz 的子类型即可。
     * @param key 哈希键的名字
     * @param clazz 指定的名字
     * @param <T> 指定的名字
     * @return key 所指向的哈希键中所有类型为 clazz 所指定的类型的元素构成的集合。
     *             当查找不到任何类型为 clazz 的元素时，返回空的列表(并非 null)。
     *             当在 pipeline / transaction 中调用时，也会返回空的列表(并非 null)。
     * @throws NullPointerException 当给定的 key 为空时，抛出该异常
     */
    <T> Map<String,T> hGetAll(String key, Class<T> clazz);

    //===========================================操纵 redis 中的列表键的相关方法===========================================

    /**
     * 将给定的元素插入到 key 所表征的列表键中。
     * @param key 列表键的名字
     * @param obj 即将插入进列表键的元素
     * @return 成功插入列表键的元素个数。假设在 pipeline / transaction 时候使用该指令，返回 null
     * @throws NullPointerException 当给定的 key 或者 obj 为空时，抛出该异常
     */
    Long lPush(String key, Object obj);

    /**
     * 列表键中指定区间内的元素所对应的 JSON 字符串，区间以偏移量 start 和 end 指定。
     * @param key 列表键的名字
     * @param start 偏移量起始
     * @param end 偏移量结束
     * @return JSON 字符串。当查找不到该列表键，或者 range 指令没有找到任何元素时，返回 null。
     *         当在 pipeline / transaction 中调用时，也会返回 null。
     * @throws NullPointerException 当给定的 key 为空时，抛出该异常
     * @throws IllegalArgumentException 当给定的 start 或者 end 参数有误时，抛出该异常
     */
    String lRange(String key, long start, long end);

    /**
     * 列表键中指定区间内，类型为 clazz 的元素，区间以偏移量 start 和 end 指定。
     * 仅需保证指定键的值的类型为 clazz 的子类型即可。
     * @param key 列表键的名字
     * @param start 偏移量起始
     * @param end 偏移量结束
     * @param clazz 指定的类型
     * @param <T> 指定的类型
     * @return 返回类型为 clazz 的元素的列表。
     *         当在 pipeline / transaction 中调用时，也会返回空的列表(并非 null)。
     *         当查找不到该列表键，或者 range 指令没有找到任何元素时，返回空的列表(并非 null)。
     * @throws NullPointerException 当给定的 key 为空时，抛出该异常
     * @throws IllegalArgumentException 当给定的 start 或者 end 参数有误时，抛出该异常
     */
    <T> List<T> lRange(String key, long start, long end, Class<T> clazz);

    void hSet(String key, String hashKey, String hashValue, int timeout , TimeUnit timeUnit);

    Set<String> keys(String s);

}
