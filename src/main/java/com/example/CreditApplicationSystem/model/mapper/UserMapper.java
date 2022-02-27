package com.example.CreditApplicationSystem.model.mapper;

import com.example.CreditApplicationSystem.model.dto.CreditApplicationDto;
import com.example.CreditApplicationSystem.model.dto.UserRegisterDto;
import com.example.CreditApplicationSystem.model.entity.CreditApplication;
import com.example.CreditApplicationSystem.model.entity.User;

public class UserMapper {
    public static User toEntity(UserRegisterDto userRegisterDto){
       User user=new User();
       user.setUsername(userRegisterDto.getUsername());
       user.setPassword(userRegisterDto.getPassword());
       user.setEmail(userRegisterDto.getEmail());
       return user;
    }
}
