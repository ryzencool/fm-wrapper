package com.faimio.fmwrapper.common.date;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Configuration
public class JacksonConfig implements Jackson2ObjectMapperBuilderCustomizer {

    @Override
    public void customize(Jackson2ObjectMapperBuilder jomb) {
        jomb.serializerByType(Long.class, ToStringSerializer.instance);
        jomb.serializerByType(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);
        jomb.serializerByType(LocalDate.class, LocalDateSerializer.INSTANCE);
        jomb.serializerByType(LocalTime.class, LocalTimeSerializer.INSTANCE);
        jomb.deserializerByType(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
        jomb.deserializerByType(LocalDate.class, LocalDateDeserializer.INSTANCE);
        jomb.deserializerByType(LocalTime.class, LocalTimeDeserializer.INSTANCE);
    }

}
