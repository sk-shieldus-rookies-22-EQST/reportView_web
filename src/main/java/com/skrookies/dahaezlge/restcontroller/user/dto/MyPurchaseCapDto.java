package com.skrookies.dahaezlge.restcontroller.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MyPurchaseCapDto {

    private List<MyPurchaseDto> myPurchaseDto;
}
