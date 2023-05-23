package com.faimio.fmwrapper.common.date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalTime;

public final class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {
    public static final LocalTimeDeserializer INSTANCE = new LocalTimeDeserializer();

    public LocalTimeDeserializer() {
    }
    @Override
    public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String text = jsonParser.getText();
        return LocalTime.parse(text, DateFormatterUtil.LOCAL_TIME_FORMATTER);
    }
}