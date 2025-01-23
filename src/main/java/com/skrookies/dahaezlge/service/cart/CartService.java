package com.skrookies.dahaezlge.service.cart;

import com.skrookies.dahaezlge.entity.cart.CartId;
import com.skrookies.dahaezlge.repository.cart.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor


public class CartService {
    private final CartRepository cartRepository;

    public List<CartId> getCartList(String user_id){
        return cartRepository.getCartList(user_id);
    }
}
