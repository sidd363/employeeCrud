package com.paypal.bfs.test.employeeserv.model.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Converter
public class AddressConverter implements AttributeConverter<Map<String, String>, String> {
    private static ObjectMapper mapper = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(Map<String, String> stringStringMap) {

        return convertToString(stringStringMap);
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String s) {
        if(!StringUtils.isEmpty(s)){
            return convertToObject(new TypeReference< Map<String, String>>(){
            },s);
        }else{
            return new HashMap<>();
        }

    }

    private static <T> T convertToObject(TypeReference<T> type, String json) {
        if(json == null)
            return null;
        T object = null;
        try {
            object = getObjectMapper().readValue(json, type);
        } catch (IOException ex) {
            System.out.println(" io exception ");
        }
        return object;
    }

    private static ObjectMapper getObjectMapper(){
        if(mapper==null){
            mapper = new ObjectMapper();
        }
        return mapper;
    }
    private static <T> String convertToString(T object) {
        if(object == null)
            return null;
        String objectToString = null;
        try {
            objectToString = getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            System.out.println(" json processing exception");
        }
        return objectToString;
    }
}
