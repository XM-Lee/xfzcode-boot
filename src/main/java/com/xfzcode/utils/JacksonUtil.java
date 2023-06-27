package com.xfzcode.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author : XMLee
 * @date : 2021-05-18 23:57
 */
@Slf4j
public class JacksonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
//        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, false);
//        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    private JacksonUtil() {
    }

    public static <T> String toJson(T entity) {
        String json = null;

        try {
            if(entity instanceof  String){
                json=(String) entity;
            }else {
                json = mapper.writeValueAsString(entity);
            }
        } catch (JsonProcessingException var3) {
            var3.printStackTrace();
        }

        return json;
    }

    public static <T> String toString(T entity) {
        return toJson(entity);
    }

    public static <T> byte[] toJsonBytes(T entity) throws Exception {
        return mapper.writeValueAsBytes(entity);
    }

    public static <T> JsonNode toNode(T entity) throws Exception {
        return mapper.valueToTree(entity);
    }

    public static <T> boolean toJsonFile(String filepath, T entity) throws Exception {
        File file = new File(filepath);
        if (!file.exists()) {
            try {
                boolean b = file.createNewFile();
            } catch (IOException var4) {
                var4.printStackTrace();
                return false;
            }
        }

        return toJsonFile(new File(filepath), entity);
    }

    public static <T> boolean toJsonFile(File file, T entity) throws Exception {
        try {
            mapper.writeValue(file, entity);
            return true;
        } catch (FileNotFoundException var3) {
            log.error("File not exists");
            var3.printStackTrace();
            return false;
        }
    }

    public static <T> T fromJson(String json, Class<T> type) {
        T entity = null;

        try {
            entity = mapper.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entity;
    }

    public static <T> T fromJson(String json, TypeReference<T> type) {
        T entity = null;

        try {
            entity = mapper.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entity;
    }

    public static Map<String, Object> jsonToMap(String json) throws Exception {
        return (Map)mapper.readValue(json, Map.class);
    }

    public static <T> Map<String, T> jsonToMap(String json, Class<T> type) throws Exception {
        return (Map)mapper.readValue(json, new TypeReference<Map<String, T>>() {
        });
    }

    public static <T> T mapToObj(Map map, Class<T> type) throws Exception {
        return mapper.convertValue(map, type);
    }

    public static <T> T jsonNodeToObj(JsonNode jsonNode, Class<T> type) {
        return mapper.convertValue(jsonNode, type);
    }

    public static <T> List<T> jsonNodeToList(JsonNode jsonNode, Class<T> T) {
        CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, T);
        return mapper.convertValue(jsonNode, type);
    }

    public static <T> T parseJSON(File json, Class<T> type) throws Exception {
        return mapper.readValue(json, type);
    }

    public static <T> T parseJSON(URL url, Class<T> type) throws Exception {
        return mapper.readValue(url, type);
    }

    public static <T> List<T> jsonToList(String json, Class<T> T) {
        CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, T);
        List<T> list = null;
        try {
            list = mapper.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static <T> List<T> strToList(String json, Class<T> T) {
        return jsonToList(json, T);
    }

    public static JsonNode jsonToNode(String json) {
        JsonNode node = null;
        try {
            node = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return node;
    }

    public static JsonNode strToNode(String json) throws Exception {
        return jsonToNode(json);
    }

    public static ObjectNode objectNode() {
        return JsonNodeFactory.instance.objectNode();
    }

    public static boolean isJsonString(String json) {
        try {
            mapper.readTree(json);
            return true;
        } catch (Exception var2) {
            if (log.isDebugEnabled()) {
                log.debug("check input string is json format;json : " + json + " ; exception;" + var2.getMessage());
            }

            return false;
        }
    }

    public static ArrayNode arrayNode() {
        return JsonNodeFactory.instance.arrayNode();
    }
}
