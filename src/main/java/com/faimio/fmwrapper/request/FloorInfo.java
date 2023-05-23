package com.faimio.fmwrapper.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FloorInfo {

    private Integer id;

    private Short floorIndex;

    private String floorName;
}
