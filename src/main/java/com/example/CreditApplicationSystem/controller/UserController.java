package com.example.CreditApplicationSystem.controller;

import com.example.CreditApplicationSystem.model.dto.UserRegisterDto;
import com.example.CreditApplicationSystem.model.dto.UserLoginDto;
import com.example.CreditApplicationSystem.model.entity.User;
import com.example.CreditApplicationSystem.model.mapper.UserMapper;
import com.example.CreditApplicationSystem.service.iml.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public String login(@Valid @RequestBody UserLoginDto userLoginDTO) {
        return userService.signin(userLoginDTO.getUsername(), userLoginDTO.getPassword());
    }

    @PostMapping("/signup")
    public String signup(@RequestBody @Valid UserRegisterDto userRegisterDto) {
        return userService.signup(UserMapper.toEntity(userRegisterDto));
    }


}
