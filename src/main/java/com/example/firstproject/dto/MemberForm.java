package com.example.firstproject.dto;

import com.example.firstproject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class MemberForm {
   // private int id;
    private String email;
    private String password;

    public Member toEntity(){
        return new Member(email, password);
    }
}
