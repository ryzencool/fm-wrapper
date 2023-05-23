package com.faimio.fmwrapper.common.date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {
    public static final LocalDateSerializer INSTANCE = new LocalDateSerializer();

    public LocalDateSerializer() {
    }

    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(localDate.format(DateFormatterUtil.LOCAL_DATE_FORMATTER));
    }
}
