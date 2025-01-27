package com.skrookies.dahaezlge.repository.purchase;

import com.skrookies.dahaezlge.controller.cart.Dto.CartDto;

import java.util.List;

public interface PurchaseRepository {
    List<Long> getDuplicateBooks(String user_id, List<Long> cartBookIdList);

    boolean purchaseCart(String user_id, List<Long> cartBookIdList);
        List<Long> purchaseBook_list(String userId);


}
