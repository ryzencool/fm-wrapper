package com.faimio.fmwrapper.controller;

import com.faimio.fmwrapper.common.BaseResponse;
import com.faimio.fmwrapper.entity.FloorAreaEntity;
import com.faimio.fmwrapper.request.BindFloorRequest;
import com.faimio.fmwrapper.request.RoomAddRequest;
import com.faimio.fmwrapper.service.AreaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AreaController {

    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }


    @PostMapping("/addArea")
    public BaseResponse<Object> addRoom(@RequestBody RoomAddRequest request) {

        areaService.addRoom(request.getAreaId(), request.getAreaName());
        return BaseResponse.success();

    }

    @PostMapping("/updateArea")
    public BaseResponse<Object> updateRoom(@RequestBody RoomAddRequest request) {
        areaService.updateRoom(request.getAreaId(), request.getAreaName());
        return BaseResponse.success();

    }


    @PostMapping("/deleteArea/{id}")
    public BaseResponse<Object> deleteArea(@PathVariable String id) {
        areaService.deleteRoom(id);
        return BaseResponse.success();
    }

    @PostMapping("/bindFloor")
    public BaseResponse<Object> bindFloor(@RequestBody BindFloorRequest request) {
        return areaService.bindAreaFloor(request.getAreaId(), request.getFloorId());
    }

    @GetMapping("/floorAreas")
    public BaseResponse<List<FloorAreaEntity>> getFloorAreas() {
        var result = areaService.floorAreas();
        return BaseResponse.success(result);

    }
}
