package com.example.firstproject.controller;

import com.example.firstproject.dto.UserDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @PostMapping("/user")
    public String insertUser(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) {
        log.info("@Param : {} ", userDto);
        return userDto + "success";
    }
}
