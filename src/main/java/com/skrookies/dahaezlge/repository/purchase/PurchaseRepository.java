package com.skrookies.dahaezlge.repository.purchase;

import com.skrookies.dahaezlge.controller.cart.Dto.CartDto;

import java.util.List;

public interface PurchaseRepository {
        boolean purchaseCart(String user_id, List<Long> cartBookIdList);
        List<Long> purchaseBook_list(String userId);
}
