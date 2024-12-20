package com.example.firstproject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

//import javax.validation.constraints.Min;
//import javax.validation.constraints.NotNull;

@AllArgsConstructor
@ToString
public class UserDto {

    @NotNull(message = "번호를 입력해주세요")
    private long no;

    @NotNull(message = "이름을 입력해주세요")
    private String name;

    @Min(value = 5000, message = "금액은 5000원이상이여야 합니다") // 5000원 보다 같거나, 크면 통과
    private long Amt;

}