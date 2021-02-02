package com.example.advance;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;

@Slf4j
public class CommonUtilTest {
    @Test
    public void testMaps() {
        Map<String, String> map = Maps.newHashMap();
        map.put("abc", "abc");
        Map<String, String> map1 = Maps.newHashMap();
        map1.put("abc", "adf");
        MapDifference<String, String> difference = Maps.difference(map, map1);
        difference.areEqual();
        log.info("diff:{}", difference);
    }
}
