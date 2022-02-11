package com.sbrf.reboot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sbrf.reboot.dto.Request;
import com.sbrf.reboot.dto.Response;

public class XMLUtils {
    static XmlMapper xmlMapper = new XmlMapper();

    public static String toXML(Object object) throws JsonProcessingException {
        return xmlMapper.writeValueAsString(object);
    }

    public static Request XMLtoRequest(String s) throws JsonProcessingException {
        return xmlMapper.readValue(s, Request.class);
    }

    public static Response XMLtoResponse(String s) throws JsonProcessingException {
        return xmlMapper.readValue(s, Response.class);
    }
}
