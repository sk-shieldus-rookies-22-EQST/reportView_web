package com.skrookies.dahaezlge.restcontroller.purchase;

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
    public ResponseEntity<Map<String, Object>> getCartDetails(@RequestParam String user_id) {
        List<?> bookList = cartService.setCartList(user_id);
        int totalPrice = bookList.stream()
                .mapToInt(book -> (int) ((Map<String, Object>) book).get("price"))
                .sum();

        return ResponseEntity.ok(Map.of(
                "book_list", bookList,
                "total_price", totalPrice
        ));
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