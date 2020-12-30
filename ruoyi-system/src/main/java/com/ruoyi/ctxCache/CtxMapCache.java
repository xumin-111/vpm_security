package com.ruoyi.ctxCache;

import org.springframework.stereotype.Component;

import java.lang.ref.SoftReference;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用  ConcurrentHashMap，线程安全的要求。
 * 我使用SoftReference <Object>  作为映射值，因为软引用可以保证在抛出OutOfMemory之前，如果缺少内存，将删除引用的对象。
 * 在构造函数中，我创建了一个守护程序线程，每5秒扫描一次并清理过期的对象。
 */
@Component
public class CtxMapCache {
    private static final int CLEAN_UP_PERIOD_IN_SEC = 5;

    private final ConcurrentHashMap<String, SoftReference<CacheObject>> cache = new ConcurrentHashMap<>();

    public CtxMapCache() {
        Thread cleanerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(CLEAN_UP_PERIOD_IN_SEC * 1000);
                    cache.entrySet().removeIf(entry ->
                            Optional.ofNullable(entry.getValue())
                                    .map(SoftReference::get)
                                    .map(CtxMapCache.CacheObject::isExpired)
                                    .orElse(false));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        cleanerThread.setDaemon(true);
        cleanerThread.start();
    }

    public void add(String key, Object value, long periodInMillis) {
        if (key == null) {
            return;
        }
        if (value == null) {
            cache.remove(key);
        } else {
            long expiryTime = System.currentTimeMillis() + periodInMillis;
            cache.put(key, new SoftReference<>(new CtxMapCache.CacheObject(value, expiryTime)));
        }
    }

    public void remove(String key) {
        cache.remove(key);
    }

    public Object get(String key) {
        return Optional.ofNullable(cache.get(key)).map(SoftReference::get).filter(cacheObject -> !cacheObject.isExpired()).map(CtxMapCache.CacheObject::getValue).orElse(null);
    }

    public void clear() {
        cache.clear();
    }

    public long size() {
        return cache.entrySet().stream().filter(entry -> Optional.ofNullable(entry.getValue()).map(SoftReference::get).map(cacheObject -> !cacheObject.isExpired()).orElse(false)).count();
    }

    /**
     * 缓存对象value
     */
    public static class CacheObject {
        private Object value;
        private long expiryTime;

        private CacheObject(Object value, long expiryTime) {
            this.value = value;
            this.expiryTime = expiryTime;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

/*    public static void main(String[] args) throws InterruptedException {
        CtxMapCache mapCacheDemo = new CtxMapCache();
        *//*mapCacheDemo.add("uid_10001", "{1}", 10 * 1000);
        mapCacheDemo.add("uid_10002", "{2}", 5 * 1000);
        mapCacheDemo.add("uid_10003", "{3}", 5 * 1000);
        System.out.println("从缓存中取出值:" + mapCacheDemo.get("uid_10001"));
        Thread.sleep(4000L);
        System.out.println("从缓存中取出值:" + mapCacheDemo.get("uid_10001"));
        Thread.sleep(1000L);
        System.out.println("5秒钟过后");
        System.out.println("从缓存中取出值:" + mapCacheDemo.get("uid_10001"));
        Thread.sleep(1000L);
        System.out.println("5秒钟过后");
        System.out.println("从缓存中取出值:" + mapCacheDemo.get("uid_10001"));*//*
        System.out.println("从缓存中取出值:" + mapCacheDemo.get("RscContext"));
        // 5秒后数据自动清除了~
    }*/
}
