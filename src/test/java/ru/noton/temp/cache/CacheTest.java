package ru.noton.temp.cache;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * Test for caches/
 *
 * @author Anton Ploskirev
 */
public class CacheTest {
    
    private final Random rand;
    
    public static final char DASH = '-';
    public static final char SPACE = ' ';
    public static final String DASHLINE = String.format("%80s", SPACE).replace(SPACE, DASH);
    @Rule public TestName testName = new TestName();
    
    
    public CacheTest() {
        rand = new Random();
    }
    
    @BeforeClass
    public static void setUpClass() {
        initLog4j();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        prefixPrintForTest();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getValueFromCache method for LFU, of class Cache.
     */
    @Test
    public void testGetValueFromCache_LFU() throws Exception {
        Cache cache = Cache.getCache(Cache.TypeCache.LFU, 50);
        for (int i = 0; i< 200; i++) {
            cache.getValueFromCache(generateRand(10, 70));
        }
    }

    /**
     * Test of getValueFromCache method for LRU, of class Cache.
     */
    @Test
    public void testGetValueFromCache_LRU() throws Exception {
        Cache cache = Cache.getCache(Cache.TypeCache.LRU, 50);
        for (int i = 0; i< 200; i++) {
            cache.getValueFromCache(generateRand(10, 70));
        }
    }
    
    private long generateRand(long leftLimit, long rightLimit) {
        return leftLimit + (int) (rand.nextFloat() * (rightLimit - leftLimit));
    }

    private static void initLog4j() {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("log4j.properties"));
            PropertyConfigurator.configure(p);
            System.setProperty("log4j.configuration", "log4j.properties");
        } catch(FileNotFoundException ex) {
            System.out.println("No found log4j file.");
        } catch (IOException ex) {
            System.out.println("No correct log4j file.");
        }
    }

    private void prefixPrintForTest(){
        System.out.println(DASHLINE);
        System.out.println(String.format("%20s%-60s", SPACE, testName.getMethodName()).replace(SPACE, DASH));
        System.out.println(DASHLINE);
    }
    
}
