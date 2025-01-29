package com.skrookies.dahaezlge.service.purchase;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.controller.cart.Dto.CartDto;
import com.skrookies.dahaezlge.entity.purchase.Purchase;
import com.skrookies.dahaezlge.entity.user.Users;
import com.skrookies.dahaezlge.repository.book.BookRepository;
import com.skrookies.dahaezlge.repository.cart.CartRepository;
import com.skrookies.dahaezlge.repository.cartBook.CartBookRepository;
import com.skrookies.dahaezlge.repository.purchase.PurchaseRepository;
import com.skrookies.dahaezlge.repository.userPoint.UserPointRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final UserPointRepository userPointRepository;

    @Transactional
    public String purchaseCart(String user_id, int use_point) {
        List<CartDto> cartIdList = cartRepository.getCartList(user_id);
        List<Long> cartBookIdList = cartBookRepository.getCartBookList(cartIdList);

        if(!purchaseRepository.getDuplicateBooks(user_id,cartBookIdList).isEmpty()){
            return "이미 구매한 도서가 포함되어 있습니다.";
        }

        if (!purchaseRepository.purchaseCart(user_id, cartBookIdList)) {
            return "결제 도중 오류가 발생했습니다."; // 구매 실패 시 롤백
        }

        List<Long> deletedCartBookItems = cartBookRepository.delCartBookItems(cartIdList);
        if (deletedCartBookItems == null || deletedCartBookItems.isEmpty()) {
            return "결제 도중 오류가 발생했습니다."; // 삭제된 항목이 없으면 실패
        }

        if (!cartRepository.delCartItem(deletedCartBookItems)) {
            return "결제 도중 오류가 발생했습니다."; // 실패 시 롤백
        }

        if (userPointRepository.use_point(user_id, use_point)) {
            return "결제 도중 오류가 발생했습니다."; // 실패 시 롤백
        }

        return "success";
    }

    @Transactional
    public String purchaseItem(String user_id, Long book_id, int use_point) {
        List<Long> purchaseItem = new ArrayList<>();
        purchaseItem.add(book_id);

        if(!purchaseRepository.getDuplicateBooks(user_id, purchaseItem).isEmpty()){
            return "이미 구매한 도서가 포함되어 있습니다.";
        }

        if (!purchaseRepository.purchaseCart(user_id, purchaseItem)) {
            return "결제 도중 오류가 발생했습니다."; // 구매 실패 시 롤백
        }

        if (userPointRepository.use_point(user_id, use_point)) {
            return "결제 도중 오류가 발생했습니다."; // 실패 시 롤백
        }

        return "success";
    }

    public List<Long> purchaseBook_list(String user_id) {
        return purchaseRepository.purchaseBook_list(user_id);
    }

    public boolean chargePoint(String userId, int charge_point) {
        return userPointRepository.charge_point(userId, charge_point);
    }
}