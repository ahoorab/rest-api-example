package com.example.ex.model.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

public class TimeDeserializerTest {
    
    static {
        TimeZone.setDefault(TimeZone.getTimeZone("EST"));
    }

    private TimeDeserializer td;
    private JsonNode node;
    private JsonParser parser;
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    @Before
    public void init() throws IOException {
        td = new TimeDeserializer();
        ObjectCodec codec = Mockito.mock(ObjectCodec.class);
        parser = Mockito.mock(JsonParser.class);
        Mockito.when(parser.getCodec()).thenReturn(codec);
        node = Mockito.mock(JsonNode.class);
        Mockito.when(codec.readTree(parser)).thenReturn(node);
    }
    
    @Test(expected=JsonMappingException.class)
    public void shouldFailWithNonNumberHour() throws IOException {
        Mockito.when(node.asText()).thenReturn("AB:00:00");
        td.deserialize(parser, Mockito.mock(DeserializationContext.class));
    }

    @Test(expected=JsonMappingException.class)
    public void shouldFailWithNonNumberMinute() throws IOException {
        Mockito.when(node.asText()).thenReturn("00:CD:00");
        td.deserialize(parser, Mockito.mock(DeserializationContext.class));
    }

    @Test(expected=JsonMappingException.class)
    public void shouldFailWithNonNumberSecond() throws IOException {
        Mockito.when(node.asText()).thenReturn("00:00:EF");
        td.deserialize(parser, Mockito.mock(DeserializationContext.class));
    }

    @Test(expected=JsonMappingException.class)
    public void shouldFailWithHourOutOfRange() throws IOException {
        Mockito.when(node.asText()).thenReturn("24:00:00");
        td.deserialize(parser, Mockito.mock(DeserializationContext.class));
    }

    @Test(expected=JsonMappingException.class)
    public void shouldFailWithMinuteOutOfRange() throws IOException {
        Mockito.when(node.asText()).thenReturn("03:60:00");
        td.deserialize(parser, Mockito.mock(DeserializationContext.class));
    }

    @Test(expected=JsonMappingException.class)
    public void shouldFailWithSecondOutOfRange() throws IOException {
        Mockito.when(node.asText()).thenReturn("03:59:60");
        td.deserialize(parser, Mockito.mock(DeserializationContext.class));
    }

    @Test(expected=JsonMappingException.class)
    public void shouldFailWithBlankHour() throws IOException {
        Mockito.when(node.asText()).thenReturn(":01:01");
        td.deserialize(parser, Mockito.mock(DeserializationContext.class));
    }

    @Test(expected=JsonMappingException.class)
    public void shouldFailWithBlankMinute() throws IOException {
        Mockito.when(node.asText()).thenReturn("13::01");
        td.deserialize(parser, Mockito.mock(DeserializationContext.class));
    }

    @Test(expected=JsonMappingException.class)
    public void shouldFailWithBlankSecond() throws IOException {
        Mockito.when(node.asText()).thenReturn("18:12:");
        td.deserialize(parser, Mockito.mock(DeserializationContext.class));
    }

    @Test(expected=JsonMappingException.class)
    public void shouldFailWithBlank() throws IOException {
        Mockito.when(node.asText()).thenReturn("");
        td.deserialize(parser, Mockito.mock(DeserializationContext.class));
    }

    @Test
    public void shouldNotFailWithOneDigitHour() throws IOException, ParseException {
        String time = "1:15:16";
        Time parsedTime = new Time(timeFormat.parse(time).getTime());
        Mockito.when(node.asText()).thenReturn(time);
        Time jsonTime = td.deserialize(parser, Mockito.mock(DeserializationContext.class));
        
        assertThat(jsonTime).isEqualTo(parsedTime);
    }
    
    @Test
    public void shouldNotFailWithOneDigitMinute() throws IOException, ParseException {
        String time = "08:5:16";
        Time parsedTime = new Time(timeFormat.parse(time).getTime());
        Mockito.when(node.asText()).thenReturn(time);
        Time jsonTime = td.deserialize(parser, Mockito.mock(DeserializationContext.class));
        
        assertThat(jsonTime).isEqualTo(parsedTime);
    }

    @Test
    public void shouldNotFailWithOneDigitSecond() throws IOException, ParseException {
        String time = "08:10:0";
        Time parsedTime = new Time(timeFormat.parse(time).getTime());
        Mockito.when(node.asText()).thenReturn(time);
        Time jsonTime = td.deserialize(parser, Mockito.mock(DeserializationContext.class));
        
        assertThat(jsonTime).isEqualTo(parsedTime);
    }
    
    @Test
    public void shouldNotFailWithAllZeroes() throws IOException, ParseException {
        String time = "0:0:0";
        Time parsedTime = new Time(timeFormat.parse(time).getTime());
        Mockito.when(node.asText()).thenReturn(time);
        Time jsonTime = td.deserialize(parser, Mockito.mock(DeserializationContext.class));
        
        assertThat(jsonTime).isEqualTo(parsedTime);
    }
    
    @Test
    public void shouldNotFailWithLastTime() throws IOException, ParseException {
        String time = "23:59:59";
        Time parsedTime = new Time(timeFormat.parse(time).getTime());
        Mockito.when(node.asText()).thenReturn(time);
        Time jsonTime = td.deserialize(parser, Mockito.mock(DeserializationContext.class));

        assertThat(jsonTime).isEqualTo(parsedTime);
    }

    @Test
    public void shouldNotFailWithLastHour() throws IOException, ParseException {
        String time = "23:00:00";
        Time parsedTime = new Time(timeFormat.parse(time).getTime());
        Mockito.when(node.asText()).thenReturn(time);
        Time jsonTime = td.deserialize(parser, Mockito.mock(DeserializationContext.class));

        assertThat(jsonTime).isEqualTo(parsedTime);
    }
    
    @Test
    public void shouldNotFailWithLastMinute() throws IOException, ParseException {
        String time = "11:59:00";
        Time parsedTime = new Time(timeFormat.parse(time).getTime());
        Mockito.when(node.asText()).thenReturn(time);
        Time jsonTime = td.deserialize(parser, Mockito.mock(DeserializationContext.class));

        assertThat(jsonTime).isEqualTo(parsedTime);
    }
    
    @Test
    public void shouldNotFailWithLastSecond() throws IOException, ParseException {
        String time = "11:02:59";
        Time parsedTime = new Time(timeFormat.parse(time).getTime());
        Mockito.when(node.asText()).thenReturn(time);
        Time jsonTime = td.deserialize(parser, Mockito.mock(DeserializationContext.class));

        assertThat(jsonTime).isEqualTo(parsedTime);
    }
    
}
