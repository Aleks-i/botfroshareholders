package ru.bot.valera.bot.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@UtilityClass
public class JsonUtil {
    private static ObjectMapper mapper;

    public static void setMapper(ObjectMapper mapper) {
        JsonUtil.mapper = mapper;
    }

    public static <T> T readValue(String url, Class<T> tClass) {
        try {
            return mapper.readValue(new URL(url), tClass);
        } catch (IOException e) {
            log.error(e.toString());
            return null;
        }
    }

    public static <T> List<T> readValuesList(String url, TypeReference<List<T>> tTypeReference) {
        try {
            return mapper.readValue(new URL(url), tTypeReference);

        } catch (IOException e) {
            log.error(e.toString());
            return Collections.emptyList();
        }
    }

    public static <T> Map<String, T> readValuesMap(String url, TypeReference<Map<String, T>> tTypeReference) {
        try {
            return mapper.readValue(new URL(url), tTypeReference);

        } catch (IOException e) {
            log.error(e.toString());
            return Collections.emptyMap();
        }
    }

    public static <T> String writeValue(T obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }
}
