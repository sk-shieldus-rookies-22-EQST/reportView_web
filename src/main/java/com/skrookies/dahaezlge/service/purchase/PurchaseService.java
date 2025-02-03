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
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 강제 롤백
            return "exists";
        }

        if (!purchaseRepository.purchaseCart(user_id, cartBookIdList)) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "error";
        }

        List<Long> deletedCartBookItems = cartBookRepository.delCartBookItems(cartIdList);
        if (deletedCartBookItems == null || deletedCartBookItems.isEmpty()) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "error"; // 삭제된 항목이 없으면 실패
        }

        if (!cartRepository.delCartItem(deletedCartBookItems)) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "error";
        }

        if (!userPointRepository.use_point(user_id, use_point)) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "error";
        }

        return "success";
    }

    @Transactional
    public String purchaseItem(String user_id, Long book_id, int use_point) {
        List<Long> purchaseItem = new ArrayList<>();
        purchaseItem.add(book_id);

        if(!purchaseRepository.getDuplicateBooks(user_id, purchaseItem).isEmpty()){
            return "exists";
        }

        if (!purchaseRepository.purchaseCart(user_id, purchaseItem)) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 강제 롤백
            return "error";
        }

        if (!userPointRepository.use_point(user_id, use_point)) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "error";
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