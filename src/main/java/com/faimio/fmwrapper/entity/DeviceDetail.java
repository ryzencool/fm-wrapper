package com.faimio.fmwrapper.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceDetail {
    private String id;
    private String areaId;
    private String configurationUrl;
    private String configEntries;
    private String connections;
    private String disabledBy;
    private String entryType;
    private String hwVersion;
    private String identifiers;
    private String manufacturer;
    private String model;
    private String nameByUser;
    private String name;
    private String swVersion;
    private String viaDeviceId;
    private String viaDeviceName;

    private String type;

    private String picture;

}
