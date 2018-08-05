package com.ace.cms.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractForeverGuavaCache<T,R> {

    private LoadingCache<T,R> cache = CacheBuilder.newBuilder()
            .initialCapacity(initialCapacity())
            .maximumSize(maximumSize()).
                    build(new CacheLoader<T, R>() {
                        @Override
                        public R load(T key) throws Exception {
                            return loadData(key);
                        }
                    });
    //从数据库获取
    protected abstract R loadData(T key);
    //初始化大小
    protected abstract int initialCapacity();
    //最大最小数量
    protected abstract int maximumSize();

    protected abstract String name();

    public R get(T key){
        try {
            return cache.get(key);
        } catch (Exception e) {
            log.error("{} get exception:{}",name(),e.getMessage());
            return null;
        }
    }
    
    public void invalidate(T key){
        try {
        	cache.invalidate(key);
        } catch (Exception e) {
            log.error("{} invalidate exception:{}",name(),e.getMessage());
        }
    }

}
