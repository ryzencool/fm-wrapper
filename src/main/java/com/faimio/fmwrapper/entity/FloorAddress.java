package com.faimio.fmwrapper.entity;

import com.faimio.api.domain.generate.tables.pojos.Floor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FloorAddress {


    private Integer floor_id;
    private Short floorIndex;
    private String floorName;

    private String areaId;

    private String name;
}
