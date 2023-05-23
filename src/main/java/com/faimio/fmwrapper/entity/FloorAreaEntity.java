package com.faimio.fmwrapper.entity;

import com.faimio.api.domain.generate.tables.pojos.Area;
import com.faimio.api.domain.generate.tables.pojos.Floor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FloorAreaEntity {

    private FloorTip floor;

    private List<FloorAddress> areas;

}
