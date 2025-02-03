package com.skrookies.dahaezlge.restcontroller.purchase;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.restcontroller.auth.dto.UserIdDto;
import com.skrookies.dahaezlge.restcontroller.purchase.dto.PurchaseAddCartDto;
import com.skrookies.dahaezlge.restcontroller.purchase.dto.PurchaseCartCapDto;
import com.skrookies.dahaezlge.restcontroller.purchase.dto.PurchaseCartDto;
import com.skrookies.dahaezlge.restcontroller.util.dto.StatusDto;
import com.skrookies.dahaezlge.service.bookDetail.BookDetailService;
import com.skrookies.dahaezlge.service.cart.CartService;
import com.skrookies.dahaezlge.service.purchase.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/purchase", produces = "application/json")
@RequiredArgsConstructor
public class PurchaseController {

    private final CartService cartService;
    private final PurchaseService purchaseService;
    private final BookDetailService bookDetailService;


    /** 장바구니에 담긴 책 리스트 반환 */
    @PostMapping("/cart")
    public ResponseEntity<PurchaseCartCapDto> getCartBookList(@RequestBody UserIdDto userIdDto) {

        PurchaseCartCapDto purchaseCartCapDto = new PurchaseCartCapDto();
        purchaseCartCapDto.setPurchaseCartDtoList(cartService.androidPurchaseCartList(userIdDto.getUser_id()));

        return ResponseEntity.ok()
                .body(purchaseCartCapDto);
    }


    /** 장바구니에 책 담기 */
    @PostMapping("/add")
    public ResponseEntity<StatusDto> addCart(@RequestBody PurchaseAddCartDto purchaseAddCartDto) {

        Boolean result = bookDetailService.addCart(purchaseAddCartDto.getUser_id(), purchaseAddCartDto.getBook_id(), 0);

        StatusDto statusDto = new StatusDto();
        statusDto.setStatus(result);

        return ResponseEntity.ok()
                .body(statusDto);
    }


    /** 장바구니에 담긴 책 삭제 */
    @PostMapping("/delete")
    public ResponseEntity<StatusDto> deleteCart(@RequestBody PurchaseAddCartDto purchaseAddCartDto) {

        Boolean result = cartService.delCartItem(purchaseAddCartDto.getUser_id(), purchaseAddCartDto.getBook_id());

        StatusDto statusDto = new StatusDto();
        statusDto.setStatus(result);

        return ResponseEntity.ok()
                .body(statusDto);
    }


    @PostMapping("/process")
    public ResponseEntity<StatusDto> processPurchase(@RequestBody UserIdDto userIdDto) {

        List<PurchaseCartDto> purchaseCartDtoList = cartService.androidPurchaseCartList(userIdDto.getUser_id());

        int total_price = 0;
        for(PurchaseCartDto book_data : purchaseCartDtoList){
            total_price += book_data.getPrice();
        }

        StatusDto statusDto = new StatusDto();
        String purchaseResult = purchaseService.purchaseCart(userIdDto.getUser_id(), total_price);

        if(purchaseResult.equals("success")){
            statusDto.setStatus(true);
        }
        else{
            statusDto.setStatus(false);
        }

        return ResponseEntity.ok()
                .body(statusDto);
    }
}