package com.skrookies.dahaezlge.service.cart;

import com.skrookies.dahaezlge.entity.cart.CartId;
import com.skrookies.dahaezlge.entity.cartBook.CartBookId;
import com.skrookies.dahaezlge.repository.cart.CartRepository;
import com.skrookies.dahaezlge.repository.cartBook.CartBookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor


public class CartService {
    private final CartRepository cartRepository;
    private final CartBookRepository cartBookRepository;

    public List<CartBookId> setCartList(String user_id){
        List<CartId> cartIdList = cartRepository.getCartList(user_id);
        return cartBookRepository.getCartBookList(cartIdList);
    }
}
