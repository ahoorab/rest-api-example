package com.example.ex.model.converter;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class TimeDeserializer extends StdDeserializer<Time> {
        
    private static final long serialVersionUID = -833912944657550162L;
    private static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9])";

    private Pattern pattern;

    private SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
     
    public TimeDeserializer() {
        this(null);
        pattern = Pattern.compile(TIME24HOURS_PATTERN);
    }
     
    public TimeDeserializer(Class<Time> t) {
        super(t);
        pattern = Pattern.compile(TIME24HOURS_PATTERN);
    }
    
    @Override
    public Time deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        try {
            if (!pattern.matcher(node.asText()).matches()) {
                throw new JsonMappingException(jp,"Invalid Time");
            }
            return new Time(formatter.parse(node.asText()).getTime());
        } catch (ParseException e) {
            throw new JsonMappingException(jp,e.getMessage(),e);
        }
    }
    
}
