package com.faimio.fmwrapper.common.date;

import org.springframework.format.datetime.DateFormatter;

import java.time.format.DateTimeFormatter;

public class DateFormatterUtil {

    public final static DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public final static DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public final static DateFormatter SPRING_DATE_FORMATTER = new DateFormatter("yyyy-MM-dd");
    public final static DateTimeFormatter LOCAL_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");



}
