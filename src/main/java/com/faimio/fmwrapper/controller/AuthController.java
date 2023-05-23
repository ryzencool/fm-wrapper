package com.faimio.fmwrapper.controller;

import com.faimio.api.domain.generate.tables.pojos.Member;
import com.faimio.fmwrapper.common.BaseResponse;
import com.faimio.fmwrapper.request.SignInRequest;
import com.faimio.fmwrapper.request.UpdateUserRequest;
import com.faimio.fmwrapper.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signIn")
    public  BaseResponse signIn(@RequestBody SignInRequest request) {
        return authService.login(request.getUsername(), request.getPassword());
    }

    @PostMapping("/addUser")
    public BaseResponse<Object> addUser(@RequestBody SignInRequest request) {
        return authService.addUser(request.getUsername(), request.getPassword());

    }

    @GetMapping("/deleteUser/{id}")
    public BaseResponse<Object> deleteUser(@PathVariable Integer id) {
        return authService.deleteMember(id);
    }


    @GetMapping("/listUser")
    public BaseResponse<List<Member>> listUser() {
        return authService.listMember();
    }

    @PostMapping("/updateUser")
    public BaseResponse<Object> updateUser(@RequestBody UpdateUserRequest request) {
       return  authService.updateUser(request.getId(), request.getPassword());
    }
}
