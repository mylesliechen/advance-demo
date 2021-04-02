package com.example.advance;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class MatcherTest {
    @Test
    public void testMatcher() {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher("aaa2223bb");
        log.info("{}, {}, {}, {}", m.find(), m.start(), m.end(), m.group());
        //m.find();//匹配2223
        //m.start();//返回3
        //m.end();//返回7,返回的是2223后的索引号
        //m.group();//返回2223

        //Matcher m2 = p.matcher("2223bb");
        //m.lookingAt();   //匹配2223
        //m.start();   //返回0,由于lookingAt()只能匹配前面的字符串,所以当使用lookingAt()匹配时,start()方法总是返回0
        //m.end();   //返回4
        //m.group();   //返回2223
        //
        Matcher m3 = p.matcher("2223bb");
        //log.info("{} {}", m3.matches(), m3.start());//
        log.info("{} {}", m3.find(), m3.start());
        //m.matches();   //匹配整个字符串
        //m.start();   //返回0,原因相信大家也清楚了
        //m.end();   //返回6,原因相信大家也清楚了,因为matches()需要匹配所有字符串
        //m.group();   //返回2223bb
    }
}
