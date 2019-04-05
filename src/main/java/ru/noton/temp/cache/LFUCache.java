package ru.noton.temp.cache;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Impl for LFU.
 *
 * @author Anton Ploskirev
 */
public class LFUCache implements Cache {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LFUCache.class);
    
    private final int size;
    private final Map<Long, Long> valueFruqMap;
    
    public LFUCache(int size) {
        this.size = size;
        valueFruqMap = new HashMap<>();
    }

    @Override
    public long getValueFromCache(long val) throws Exception {
        if (valueFruqMap.containsKey(val)) {
            valueFruqMap.replace(val, valueFruqMap.get(val) + 1);
            LOGGER.debug(new StringBuilder("getFromCache ").append(val).append(" ").append(valueFruqMap.get(val)).toString());
            return valueFruqMap.keySet().stream().filter(key -> key == val).findFirst().orElseThrow(() -> new Exception("Cache was modifire"));
        }
        testOccupancyCache();
        valueFruqMap.put(val, (long)1);
        LOGGER.debug(new StringBuilder("putToCache ").append(val).append(" ").append(valueFruqMap.get(val)).toString());
        return val;
    }
    
    private void testOccupancyCache() throws Exception {
        if (valueFruqMap.size() == size) {
            Map.Entry<Long, Long> val = valueFruqMap.entrySet().stream().min(Comparator.comparing(entry -> entry.getValue())).orElseThrow(() -> new Exception("Cache is empty"));
            LOGGER.debug("remove " + val);
            valueFruqMap.remove(val.getKey());
        }
    }
    
}
