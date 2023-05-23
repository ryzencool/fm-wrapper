package com.faimio.fmwrapper.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallServiceRequest {

    private String domain;

    private String service;

    private Map<String, Object> serviceData;

    private String entityId;
}
