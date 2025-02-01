package com.skrookies.dahaezlge.restcontroller.purchase;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.restcontroller.auth.dto.UserIdDto;
import com.skrookies.dahaezlge.restcontroller.purchase.dto.PurchaseCartCapDto;
import com.skrookies.dahaezlge.restcontroller.purchase.dto.PurchaseCartDto;
import com.skrookies.dahaezlge.restcontroller.util.dto.StatusDto;
import com.skrookies.dahaezlge.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<PurchaseCartCapDto> getCartBookList(@RequestBody UserIdDto userIdDto) {

        PurchaseCartCapDto purchaseCartCapDto = new PurchaseCartCapDto();
        purchaseCartCapDto.setPurchaseCartDtoList(cartService.androidPurchaseCartList(userIdDto.getUser_id()));
        
        return ResponseEntity.ok()
                .body(purchaseCartCapDto);
    }

    @PostMapping("/process")
    public ResponseEntity<StatusDto> processPurchase(@RequestBody Map<String, Object> purchaseData) {
        String userId = (String) purchaseData.get("user_id");
        List<Integer> bookIds = (List<Integer>) purchaseData.get("book_id");
        int totalPrice = (int) purchaseData.get("totalprice");

        // Add logic for processing the purchase here
        boolean isSuccessful = true; // Replace with actual processing result

        return ResponseEntity.ok(new StatusDto(isSuccessful));
    }
}