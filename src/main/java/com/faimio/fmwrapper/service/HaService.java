package com.faimio.fmwrapper.service;

import com.faimio.api.domain.generate.Tables;
import com.faimio.api.domain.generate.tables.pojos.Area;
import com.faimio.api.domain.generate.tables.pojos.Device;
import com.faimio.api.domain.generate.tables.pojos.Entity;
import com.faimio.api.domain.generate.tables.pojos.State;
import com.faimio.api.domain.generate.tables.records.AreaRecord;
import com.faimio.api.domain.generate.tables.records.DeviceRecord;
import com.faimio.api.domain.generate.tables.records.EntityRecord;
import com.faimio.api.domain.generate.tables.records.StateRecord;
import com.faimio.fmwrapper.common.MessageUtil;
import com.faimio.fmwrapper.entity.DeviceDetail;
import com.faimio.fmwrapper.request.CallServiceRequest;
import com.faimio.fmwrapper.request.GetStateRequest;
import com.faimio.fmwrapper.request.ListDeviceRequest;
import com.faimio.fmwrapper.request.ListEntityRequest;
import com.faimio.fmwrapper.ws.WsTask;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.faimio.api.domain.generate.tables.Area.AREA;
import static com.faimio.api.domain.generate.tables.Device.DEVICE;


@Service
@Slf4j
public class HaService {


    private final DSLContext dslContext;

    public HaService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }


    public void batchInsertArea(ArrayNode data) {
        List<AreaRecord> list = new ArrayList<>();
        data.forEach(it -> {
            var area   = dslContext.selectFrom(Tables.AREA).where(Tables.AREA.AREA_ID.eq(it.get("area_id").asText())).fetchOneInto(Area.class);
            var floorId = 0;
            if (area != null) {
                if (area.getFloorId()!=null) {
                    floorId = area.getFloorId();
                };
            }
            list.add(new AreaRecord(
                    it.get("area_id").asText(),
                    it.get("name").asText(),
                    it.get("picture").asText(),
                    floorId

            ));

            dslContext.batchMerge(list).execute();
        });

    }

    public void batchInsertDevice(ArrayNode result) {
        List<DeviceRecord> list = new ArrayList<>();
        result.forEach(it -> {
            list.add(new DeviceRecord(
                    it.get("id").asText(),
                    it.get("area_id").asText(),
                    it.get("configuration_url").asText(),
                    "",
                    "",
                    it.get("disabled_by").asText(),
                    it.get("entry_type").asText(),
                    it.get("hw_version").asText(),
                    "",
                    it.get("manufacturer").asText(),
                    it.get("model").asText(),
                    it.get("name_by_user").asText(),
                    it.get("name").asText(),
                    "",
                    it.get("via_device_id").asText()
            ));
        });
        dslContext.batchMerge(list).execute();

    }

    public List<Area> listArea() {
        return dslContext.selectFrom(Tables.AREA).fetchInto(Area.class);
    }

    public List<DeviceDetail> listDevice(ListDeviceRequest request) {
        var cond = DSL.and(
                        StringUtils.isNotEmpty(request.getId()) ? DEVICE.ID.eq(request.getId()) : DSL.noCondition())
                .and(StringUtils.isNotEmpty(request.getName()) ? DEVICE.NAME.eq(request.getName()) : DSL.noCondition());
        return dslContext.select(Tables.DEVICE.MODEL.as("model"),
                        Tables.DEVICE.MANUFACTURER.as("manufacturer"),
                        Tables.DEVICE.DISABLED_BY.as("disabled_by"),
                        Tables.DEVICE.ENTRY_TYPE.as("entry_type"),
                        Tables.DEVICE.HW_VERSION.as("hw_version"),
                        Tables.DEVICE.VIA_DEVICE_ID.as("via_device_id"),
                        Tables.DEVICE.NAME_BY_USER.as("name_by_user"),
                        Tables.DEVICE.NAME.as("name"),
                        Tables.DEVICE.CONFIG_ENTRIES.as("configuration_entries"),
                        Tables.DEVICE.SW_VERSION.as("sw_version"),
                        Tables.DEVICE.AREA_ID.as("area_id"),
                        Tables.DEVICE.CONFIGURATION_URL.as("configuration_url"),
                        Tables.MODEL.TYPE.as("type"),
                        Tables.MODEL.PICTURE.as("picture"),
                        Tables.DEVICE.ID.as("id")
                ).from(Tables.DEVICE.leftJoin(Tables.MODEL)
                        .on(Tables.DEVICE.MODEL.eq(Tables.MODEL.NAME)))
                .where(cond).fetchInto(DeviceDetail.class);

    }

    public Device getDevice(String name) {
        return dslContext.fetchSingle(DEVICE, DEVICE.ID.eq(name)).into(Device.class);
    }


    public List<Entity> listEntities(ListEntityRequest request) {
        var cond = DSL.and(StringUtils.isNotEmpty(request.getDeviceName()) ?
                Tables.ENTITY.ENTITY_ID.like("%" + request.getDeviceName() + "%") : DSL.noCondition());

        return dslContext.selectFrom(Tables.ENTITY).where(cond).fetchInto(Entity.class);
    }

    public List<State> getState(GetStateRequest request) {
        var cond = DSL.and(request.getEntityIds() != null ? Tables.STATE.ENTITY_ID.in(request.getEntityIds()) : DSL.noCondition());
        return dslContext.selectFrom(Tables.STATE).where(cond).fetchInto(State.class);
    }

    @SneakyThrows
    public Object callService(CallServiceRequest request) {
        var mapper = new ObjectMapper();
        var serviceData = mapper.writeValueAsString(request.getServiceData());
        var msgId = MessageUtil.getId();
        var msg = """
                {
                 "id": %d,
                 "type": "call_service",
                 "domain": "%s",
                 "service": "%s",
                 "service_data": %s,
                 "target": {
                     "entity_id": "%s"
                     }
                }""".formatted(msgId, request.getDomain(), request.getService(), serviceData, request.getEntityId());
        log.info(msg);
        WsTask.CLIENT.send(msg);
        WsTask.EXECUTE_MAP.put(msgId, data -> {
            try {
                var es = mapper.writeValueAsString(data);

                log.info("call service success: {}", es);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        return null;
    }


    public void batchInsertEntity(ArrayNode arr) {
        List<EntityRecord> list = new ArrayList<>();
        arr.forEach(it -> {
            list.add(new EntityRecord(
                    it.get("id").asText(),
                    it.get("area_id").asText(),
                    it.get("config_entry_id").asText(),
                    it.get("device_id").asText(),
                    it.get("disabled_by").asText(),
                    it.get("has_entity_name").asBoolean(),
                    it.get("entity_category").asText(),
                    it.get("entity_id").asText(),
                    it.get("hidden_by").asText(),
                    it.get("icon").asText(),
                    it.get("unique_id").asText(),
                    it.get("name").asText(),
                    it.get("original_name").asText(),
                    it.get("platform").asText()

            ));
        });
        dslContext.batchMerge(list).execute();
    }

    public void batchInsertState(ArrayNode arr) {
        List<StateRecord> list = new ArrayList<>();
        arr.forEach(it -> {
            list.add(new StateRecord(

                    it.get("entity_id").asText(),
                    it.get("state").asText(),
                    it.get("attributes").toString(),
                    it.get("lastChanged") != null ? it.get("lastChanged").asText() : "",
                    it.get("lastUpdated") != null ? it.get("lastUpdated").asText() : "",
                    it.get("context").toString()
            ));
        });

        dslContext.batchMerge(list).execute();
    }

    public Area getArea(String areaId) {
        return dslContext.fetchSingle(AREA, AREA.AREA_ID.eq(areaId)).into(Area.class);
    }

}
