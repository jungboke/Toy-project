package com.example.baekshopV2.formatter;

import com.example.baekshopV2.domain.Address;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

@Slf4j
public class MyAddressFormatter implements Formatter<Address> {

    @Override
    public Address parse(String text, Locale locale) throws ParseException {
        log.info("text={}, locale={}", text, locale);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Address address = objectMapper.readValue(text, Address.class);
            return address;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String print(Address object, Locale locale) {
        log.info("object={}, locale={}", object, locale);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String address = objectMapper.writeValueAsString(object);
            return address;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
