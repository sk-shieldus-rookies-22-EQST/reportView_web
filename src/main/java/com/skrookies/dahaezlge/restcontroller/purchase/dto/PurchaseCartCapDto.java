package com.skrookies.dahaezlge.restcontroller.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseCartCapDto {

    List<PurchaseCartDto> purchaseCartDtoList;
}
