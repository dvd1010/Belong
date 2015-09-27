package com.belonginterview.utils;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class JsonHandler {

    private static final String TAG = JsonHandler.class.getSimpleName();
    private static ObjectMapper underScoreToCamelCaseMapper;

    static {
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        underScoreToCamelCaseMapper = new ObjectMapper();
        underScoreToCamelCaseMapper.setDateFormat(df);
        underScoreToCamelCaseMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        underScoreToCamelCaseMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        underScoreToCamelCaseMapper.setPropertyNamingStrategy(
                PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
    }

    private JsonHandler() {
    }

    public static <T> T parseUnderScoredResponse(String json, Class<T> classOfT) {
        try {
            if (json == null) {
                return null;
            }
            return underScoreToCamelCaseMapper.readValue(json, classOfT);
        } catch (JsonParseException e) {
            Log.e(TAG, "JsonParseException Failed to parse json correctly. ", e);
        } catch (JsonMappingException e) {
            Log.e(TAG,"JsonMappingException Failed to parse json correctly. ", e);
        } catch (IOException e) {
            Log.e(TAG,"IOException Failed to parse json correctly. " , e);
        }
        return null;
    }
}
