package com.zuhriddin.controller.admin_controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuhriddin.model.User;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public interface BaseController {
    ObjectMapper objectMapper = new ObjectMapper();

    default User returnUserFromJson(String encodingJson) throws JsonProcessingException {
        String decode = URLDecoder.decode(encodingJson, StandardCharsets.UTF_8);
        return objectMapper.readValue(decode, User.class);
    }
}
