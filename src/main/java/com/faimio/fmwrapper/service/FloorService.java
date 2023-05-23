package com.faimio.fmwrapper.service;

import com.faimio.api.domain.generate.Tables;
import com.faimio.api.domain.generate.tables.pojos.Floor;
import com.faimio.fmwrapper.common.BaseResponse;
import com.faimio.fmwrapper.request.FloorAddRequest;
import com.faimio.fmwrapper.request.FloorInfo;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FloorService {

    private final DSLContext dslContext;

    public FloorService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Transactional
    public BaseResponse<Object> addFloor(FloorAddRequest request) {
        for (FloorInfo floorInfo : request.getFloorInfoList()) {
            dslContext.insertInto(Tables.FLOOR, Tables.FLOOR.FLOOR_INDEX, Tables.FLOOR.FLOOR_NAME)
                    .values(floorInfo.getFloorIndex(), floorInfo.getFloorName()).execute();
        }
        return BaseResponse.success();
    }


    public BaseResponse<List<Floor>> listFloor() {
        return BaseResponse.success(dslContext.select().from(Tables.FLOOR).fetchInto(Floor.class));
    }

    public BaseResponse<Object> deleteFloor(Integer id) {
        dslContext.deleteFrom(Tables.FLOOR).where(Tables.FLOOR.ID.eq(id)).execute();
        return BaseResponse.success();
    }

//    public BaseResponse<Object> updateFloor(FloorAddRequest request) {
//
//        UpdateSetMoreStep<FloorRecord> sql = dslContext.update(Tables.FLOOR);
//        if (StringUtils.isNotEmpty(request.getFloorName())) {
//            sql.set(Tables.FLOOR.FLOOR_NAME, request.getFloorName());
//        }
//        if (request.getFloorIndex() != null) {
//            sql.set(Tables.FLOOR.FLOOR_INDEX, request.getFloorIndex());
//        }
//
//        sql.where(Tables.FLOOR.ID.eq(request.getId())).execute();
//
//        return BaseResponse.success();
//    }
}
