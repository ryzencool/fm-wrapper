package com.faimio.fmwrapper.common.date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalTime;

public class LocalTimeSerializer extends JsonSerializer<LocalTime> {

    public static final LocalTimeSerializer INSTANCE = new LocalTimeSerializer();

    public LocalTimeSerializer() {
    }

    @Override
    public void serialize(LocalTime localTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        localTime.format(DateFormatterUtil.LOCAL_TIME_FORMATTER);
    }
}
