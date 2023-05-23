package com.faimio.fmwrapper.service;

import com.faimio.api.domain.generate.Tables;
import com.faimio.fmwrapper.common.BaseResponse;
import com.faimio.fmwrapper.common.MessageUtil;
import com.faimio.fmwrapper.entity.FloorAddress;
import com.faimio.fmwrapper.entity.FloorAreaEntity;
import com.faimio.fmwrapper.entity.FloorTip;
import com.faimio.fmwrapper.ws.WsTask;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AreaService {

    private final DSLContext dslContext;

    public AreaService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }


    public void addRoom(String areaId, String areaName) {

        var msgId = MessageUtil.getId();
        var msg = """
                  {
                 "id": %d,
                 "type": "config/area_registry/create",
                "name": "%s"
                }
                """.formatted(msgId, areaName);
        WsTask.CLIENT.send(msg);
        WsTask.EXECUTE_MAP.put(msgId, data -> {
            try {
                var mapper = new ObjectMapper();
                var es = mapper.writeValueAsString(data);

                log.info("call service success: {}", es);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public void updateRoom(String areaId, String areaName) {
        var msgId = MessageUtil.getId();
        var msg = """
                  {
                 "id": %d,
                 "type": "config/area_registry/update",
                 "area_id": "%s",
                "name": "%s"
                }
                """.formatted(msgId, areaId, areaName);
        WsTask.CLIENT.send(msg);
        WsTask.EXECUTE_MAP.put(msgId, data -> {
            try {
                var mapper = new ObjectMapper();
                var es = mapper.writeValueAsString(data);

                log.info("call service success: {}", es);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

    }


    public void deleteRoom(String areaId) {
        var msgId = MessageUtil.getId();
        var msg = """
                  {
                 "id": %d,
                 "type": "config/area_registry/update",
                 "area_id": "%s"
                }
                """.formatted(msgId, areaId);
        WsTask.CLIENT.send(msg);
        WsTask.EXECUTE_MAP.put(msgId, data -> {
            try {
                var mapper = new ObjectMapper();
                var es = mapper.writeValueAsString(data);

                log.info("call service success: {}", es);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public BaseResponse<Object> bindAreaFloor(String areaId, Integer floorId) {
        dslContext.update(Tables.AREA).set(Tables.AREA.FLOOR_ID, floorId).where(Tables.AREA.AREA_ID.eq(areaId)).execute();
        return BaseResponse.success();

    }

    public List<FloorAreaEntity> floorAreas() {
        var result = dslContext.select().from(Tables.AREA).join(Tables.FLOOR).on(Tables.AREA.FLOOR_ID.eq(Tables.FLOOR.ID)).orderBy(Tables.FLOOR.ID.asc()).fetchInto(FloorAddress.class);
        var res = result.stream().collect(Collectors.groupingBy(FloorAddress::getFloor_id));
        return res.keySet().stream().map((key -> {
            var value = res.get(key);
            var ft = FloorTip.builder().floorId(key).build();
            if (value.size() > 0) {
                var area0 = value.get(0);
                ft.setFloorName(area0.getFloorName());
                ft.setFloorIndex(area0.getFloorIndex());
            }
            return FloorAreaEntity.builder().floor(ft).areas(value).build();
        })).collect(Collectors.toList());
    }
}
