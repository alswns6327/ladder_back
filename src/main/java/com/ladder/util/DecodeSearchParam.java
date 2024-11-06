package com.ladder.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ladder.vo.article.ArticleSearchParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class DecodeSearchParam {

    public static <T> T decodeSearchParam(String encodeParamString, Class<T> valueType) throws UnsupportedEncodingException, JsonProcessingException {
        String decodedJson = URLDecoder.decode(encodeParamString, "UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        T searchParam = objectMapper.readValue(decodedJson, valueType);

        return searchParam;
    }

}
