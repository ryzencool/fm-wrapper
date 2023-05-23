package com.faimio.fmwrapper.controller;

import com.faimio.api.domain.generate.tables.pojos.Floor;
import com.faimio.fmwrapper.common.BaseResponse;
import com.faimio.fmwrapper.request.FloorAddRequest;
import com.faimio.fmwrapper.service.FloorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class FloorController {

    private final FloorService floorService;

    public FloorController(FloorService floorService) {
        this.floorService = floorService;
    }

    @PostMapping("/addFloor")
    public BaseResponse<Object> addFloor(@RequestBody FloorAddRequest request) {
        return floorService.addFloor(request);

    }

    @GetMapping("/listFloor")
    public BaseResponse<List<Floor>> listFloor() {
        return floorService.listFloor();
    }

    @PostMapping("/deleteFloor/{id}")
    public BaseResponse<Object> deleteFloor(@PathVariable Integer id) {
        return floorService.deleteFloor(id);
    }

//    @PostMapping("/updateFloor")
//    public void updateFloor( @RequestBody FloorAddRequest request) {
//        floorService.updateFloor(request);
//    }

}
