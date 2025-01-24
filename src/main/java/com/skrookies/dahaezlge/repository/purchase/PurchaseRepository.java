package com.skrookies.dahaezlge.repository.purchase;

import java.util.List;

public interface PurchaseRepository {
    List<Long> purchaseBook_list(String userId);
}
