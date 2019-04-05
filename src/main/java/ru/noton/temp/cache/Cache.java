package ru.noton.temp.cache;

/**
 * Interface for different cache.
 *
 * @author Anton Ploskirev
 */
public interface Cache {
    
    public static Cache getCache(TypeCache type, int initSize) {
        if (type.equals(TypeCache.LFU)) {
            return new LFUCache(initSize);
        }
        return new LRUCache(initSize);
    }
    
    /**
     * Varible types of cahce.
     */
    public enum TypeCache {LFU, LRU};
    
    /**
     * Get value from cache. If value is't exist in cache, adding his.
     *
     * @param val - test value.
     * @return Value, exist in cache.
     * @throws java.lang.Exception - Ex by cache.
     */
    public long getValueFromCache(long val) throws Exception;
    
}
