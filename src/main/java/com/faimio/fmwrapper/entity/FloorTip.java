package com.faimio.fmwrapper.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FloorTip {

    private Integer floorId;

    private Short floorIndex;

    private String floorName;


}
