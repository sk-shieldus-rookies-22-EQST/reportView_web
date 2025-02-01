package com.skrookies.dahaezlge.restcontroller.user.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@NoArgsConstructor
public class MyBookListCapDto {
    private List<MyBookListDto> myBookListDtoList;
}
