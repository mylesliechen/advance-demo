package com.example.advance.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    private static final boolean isExcludeNullPropertiesOnSerialization = "TRUE".equalsIgnoreCase(
            System.getProperty("mcf.JsonUtils.ExcludeNullPropertiesOnSerialization", "FALSE"));

    //each thread has its own ObjectMapper instance
    private static ThreadLocal<ObjectMapper> objMapperLocal = ThreadLocal.withInitial(() -> {
        ObjectMapper mapper = new ObjectMapper();
        mapper.getFactory().disable(JsonFactory.Feature.INTERN_FIELD_NAMES);
        if (isExcludeNullPropertiesOnSerialization) {
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        return mapper;
    });

    public static String toJSON(Object value) {
        String result = null;
        try {
            result = objMapperLocal.get().writeValueAsString(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Fix null string
        if ("null".equals(result)) {
            result = null;
        }
        return result;
    }

    public static <T> T toT(String jsonString, Class<T> clazz) {
        try {
            return objMapperLocal.get().readValue(jsonString, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    public static <T> T toT(String jsonString, TypeReference valueTypeRef) {
        try {
            return objMapperLocal.get().readValue(jsonString, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> toTList(String jsonString, Class<T> clazz) {
        try {
            return objMapperLocal.get().readValue(jsonString,
                    TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String jsonString) {
        return toT(jsonString, Map.class);
    }

    public static String prettyPrint(Object value) {
        String result = null;
        try {
            result = objMapperLocal.get().writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Fix null string
        if ("null".equals(result)) {
            result = null;
        }
        return result;
    }

    public static void main(String[] args) {
        //Message msg1 = new Message();
        //msg1.uid = "1";
        //msg1.opr_time = new Date();
        //msg1.content = "hello world---1";
        //System.out.println(toJSON(msg1));
        //Map<String, Object> stringObjectMap = toMap(toJSON(msg1));
        //stringObjectMap.put("name","tesdt");
        //
        //;
        //System.out.println(toJSON(stringObjectMap));
        String jsonStr = "{\"uid\":\"1\",\"opr_time\":1602506313448}";
        JSONObject jsonObject = JSON.parseObject(jsonStr);

        Message messageReduce = jsonObject.toJavaObject(Message.class);
        System.out.println(messageReduce.uid);


        //jsonObject.put("name", "test");

        //jsonObject.get("name");
        //System.out.println(jsonObject.get("name1") == null);
        //System.out.println(jsonObject.toJSONString());
        //jsonObject.remove("aaaa");
        //System.out.println(jsonObject.toJSONString());

        /**
         * {"uid":"1","opr_time":1602506081540}
         */
        /**
         * {"uid":"1","opr_time":1602506081540,"name":"test"}
         */

        //Message msg2 = new Message();
        //msg2.uid = "2";
        //msg2.opr_time = new Date();
        //msg2.content = "hello world---2";
        //
        //List<Message> list = new ArrayList<Message>();
        //list.add(msg1);
        //list.add(msg2);
        //final String json = toJSON(list);
        //System.out.println(json);
        //
        ////String l = "[{\"opr_time\":\"2012-05-12 12:33:22\",\"uid\":\"akun\",\"content\":\"\u5927\u5730\u9707\u7684\u4eba\u4eec\u5b89\u606f\u5427\"},{\"opr_time\":\"2012-05-12 12:33:25\",\"uid\":\"chunlai\",\"content\":\"\u6211\u56de\u5bb6\u4e86\"},{\"opr_time\":\"2012-05-12 12:37:25\",\"uid\":\"stone\",\"content\":\"\u4eca\u5929\u4e0d\u65b9\u4fbf\u6e38\u620f\"}]";
        //final List<Message> newMsg = JsonUtils.toTList(json, Message.class);
        //System.out.println(newMsg);
        //System.out.println((newMsg.get(0).uid));
    }

    static class Message {
        String uid;
        Date opr_time;
        @JsonIgnore
        String content;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public Date getOpr_time() {
            return opr_time;
        }

        public void setOpr_time(Date opr_time) {
            this.opr_time = opr_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    static class MessageReduce {
        String uid;
    }
}