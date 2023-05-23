package com.faimio.fmwrapper.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListDeviceRequest {


    private String name;

    private String id;

    private String areaId;

    private String manufacturer;

    private String model;

}
