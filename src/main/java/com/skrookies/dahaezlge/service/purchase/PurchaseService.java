package com.skrookies.dahaezlge.service.purchase;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.controller.cart.Dto.CartDto;
import com.skrookies.dahaezlge.entity.purchase.Purchase;
import com.skrookies.dahaezlge.entity.user.Users;
import com.skrookies.dahaezlge.repository.book.BookRepository;
import com.skrookies.dahaezlge.repository.cart.CartRepository;
import com.skrookies.dahaezlge.repository.cartBook.CartBookRepository;
import com.skrookies.dahaezlge.repository.purchase.PurchaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final CartBookRepository cartBookRepository;
    private final CartRepository cartRepository;

    public Boolean purchaseCart(String user_id){
        List<CartDto> cartIdList = cartRepository.getCartList(user_id);
        log.info(String.valueOf(cartIdList.size()));
        List<Long> cartBookIdList = cartBookRepository.getCartBookList(cartIdList);
        log.info(String.valueOf(cartBookIdList.size()));

        if(purchaseRepository.purchaseCart(user_id, cartBookIdList)){
            return true;
        } else{
            return false;
        }
    }
    public List<Long> purchaseBook_list(String user_id) {
        return purchaseRepository.purchaseBook_list(user_id);
    }

}