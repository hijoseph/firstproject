package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@NoArgsConstructor
//@RequiredArgsConstructor
public class Member {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String email;
    @Column
    private String password;

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

   // public Member(){};

    /*@Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }*/
}
