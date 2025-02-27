package com.doston.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.doston.model.User;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public interface BaseController {
    ObjectMapper objectMapper = new ObjectMapper();

    default User returnUserFromJson(String encodingJson) throws JsonProcessingException {
        String decode = URLDecoder.decode(encodingJson, StandardCharsets.UTF_8);
        return objectMapper.readValue(decode, User.class);
    }
}