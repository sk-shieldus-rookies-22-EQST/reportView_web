package com.skrookies.dahaezlge.repository.purchase;

import java.util.List;

public interface PurchaseRepository {
    List<Long> getDuplicateBooks(String user_id, List<Long> cartBookIdList);

    boolean purchaseCart(String user_id, List<Long> cartBookIdList);
        List<List> purchaseBook_list(String userId);


}
