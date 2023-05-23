package com.faimio.fmwrapper.common.date;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {


    public static final LocalDateTimeDeserializer INSTANCE = new LocalDateTimeDeserializer();

    public LocalDateTimeDeserializer() {
    }
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return LocalDateTime.parse(jsonParser.getText(),DateFormatterUtil.LOCAL_DATE_TIME_FORMATTER);
    }
}
