package com.faimio.fmwrapper.controller;


import com.faimio.api.domain.generate.tables.pojos.Area;
import com.faimio.api.domain.generate.tables.pojos.Device;
import com.faimio.api.domain.generate.tables.pojos.Entity;
import com.faimio.api.domain.generate.tables.pojos.State;
import com.faimio.fmwrapper.entity.DeviceDetail;
import com.faimio.fmwrapper.request.CallServiceRequest;
import com.faimio.fmwrapper.request.GetStateRequest;
import com.faimio.fmwrapper.request.ListDeviceRequest;
import com.faimio.fmwrapper.request.ListEntityRequest;
import com.faimio.fmwrapper.service.HaService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class HAController {

    private final HaService haService;

    public HAController(HaService haService) {
        this.haService = haService;
    }

    @GetMapping("/devices")
    public List<DeviceDetail> listDevice(ListDeviceRequest request) {
        return haService.listDevice(request);
    }

    @GetMapping("/device/{name}")
    public Device getDevice(@PathVariable("name") String name) {
        return haService.getDevice(name);
    }

    @GetMapping("/areas")
    public List<Area> listArea() {
        return haService.listArea();
    }

    @GetMapping("/area/{areaId}")
    public Area getArea(@PathVariable String areaId) {
        return haService.getArea(areaId);
    }

    @GetMapping("/entities")
    public List<Entity> listEntity(ListEntityRequest request) {
        return haService.listEntities(request);
    }

    @PostMapping("/getState")
    public List<State> getState(@RequestBody GetStateRequest request) {
        return haService.getState(request);
    }

    @PostMapping("/callService")
    public Object callService(@RequestBody CallServiceRequest request) {
        return haService.callService(request);
    }

//    @PostMapping("/signUp")
//    public Object signUp(@RequestBody ) {
//
//    }
//
//    @GetMapping("/signIn")
//    public Object signIn(@RequestBody )
}
