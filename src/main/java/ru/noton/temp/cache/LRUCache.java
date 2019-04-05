package ru.noton.temp.cache;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Impl for LRU.
 *
 * @author Anton Ploskirev
 */
public class LRUCache implements Cache {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LRUCache.class);
    
    private final int size;
    private final Map<Long, Long> valueTimeMap;
    
    public LRUCache(int size) {
        this.size = size;
        valueTimeMap = new HashMap<>();
    }

    @Override
    public long getValueFromCache(long val) throws Exception {
        
        long time = new Date().getTime();
        
        if (valueTimeMap.containsKey(val)) {
            valueTimeMap.replace(val, time);
            LOGGER.debug(new StringBuilder("getFromCache ").append(val).append(" ").append(valueTimeMap.get(val)).toString());
            return valueTimeMap.keySet().stream().filter(key -> key == val).findFirst().orElseThrow(() -> new Exception("Cache was modifire")); // return val;?
        }
        testOccupancyCache();
        valueTimeMap.put(val, time);
        LOGGER.debug(new StringBuilder("putToCache ").append(val).append(" ").append(valueTimeMap.get(val)).toString());
        return val;
    }
    
    private void testOccupancyCache() throws Exception {
        if (valueTimeMap.size() == size) {
            Map.Entry<Long, Long> val = valueTimeMap.entrySet().stream().min(Comparator.comparing(entry -> entry.getValue())).orElseThrow(() -> new Exception("Cache is empty"));
            LOGGER.debug("remove " + val + " " + valueTimeMap.get(val.getKey()));
            valueTimeMap.remove(val.getKey());
        }
    }
    
}