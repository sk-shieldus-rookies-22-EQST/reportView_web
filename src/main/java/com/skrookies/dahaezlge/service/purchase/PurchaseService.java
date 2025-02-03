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
        List<CartDto> cartList = cartRepository.getCartList(user_id);
        List<Long> cartBookIdList = cartBookRepository.getCartBookList(cartList);
        log.info("User {} cart book ids: {}", user_id, cartBookIdList);
        List<Long> duplicateBooks = purchaseRepository.getDuplicateBooks(user_id, cartBookIdList);
        if (!duplicateBooks.isEmpty()){
            log.info("User {} has already purchased books: {}", user_id, duplicateBooks);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "exists";
        }
        if (!purchaseRepository.purchaseCart(user_id, cartBookIdList)) {
            log.error("Failed to process purchase for user {} with cart items: {}", user_id, cartBookIdList);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "error";
        }
        List<Long> deletedCartBookItems = cartBookRepository.delCartBookItems(cartList);
        if (deletedCartBookItems == null || deletedCartBookItems.isEmpty()) {
            log.error("No cart book items were deleted for user {}. Cart list: {}", user_id, cartList);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "error";
        }
        if (!cartRepository.delCartItem(deletedCartBookItems)) {
            log.error("Failed to delete cart items for user {}. Deleted cart ids: {}", user_id, deletedCartBookItems);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "error";
        }
        if (!userPointRepository.use_point(user_id, use_point)) {
            log.error("Failed to deduct points for user {}. Points to use: {}", user_id, use_point);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "error";
        }
        return "success";
    }

    @Transactional
    public String purchaseItem(String user_id, Long book_id, int use_point) {
        List<Long> purchaseItem = new ArrayList<>();
        purchaseItem.add(book_id);
        log.info("User {} attempting to purchase book: {}", user_id, book_id);
        List<Long> duplicateBooks = purchaseRepository.getDuplicateBooks(user_id, purchaseItem);
        if (!duplicateBooks.isEmpty()){
            log.info("User {} already purchased book(s): {}", user_id, duplicateBooks);
            return "exists";
        }
        if (!purchaseRepository.purchaseCart(user_id, purchaseItem)) {
            log.error("Failed to process purchase for user {} with book: {}", user_id, book_id);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "error";
        }
        if (!userPointRepository.use_point(user_id, use_point)) {
            log.error("Failed to deduct points for user {}. Points to use: {}", user_id, use_point);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "error";
        }
        return "success";
    }

    public boolean isBookPurchased(String user_id, Long book_id) {
        List<Long> purchaseItem = new ArrayList<>();
        purchaseItem.add(book_id);
        List<Long> duplicateBooks = purchaseRepository.getDuplicateBooks(user_id, purchaseItem);
        return !duplicateBooks.isEmpty();
    }

    public List<Long> purchaseBook_list(String user_id) {
        return purchaseRepository.purchaseBook_list(user_id);
    }

    public boolean chargePoint(String userId, int charge_point) {
        return userPointRepository.charge_point(userId, charge_point);
    }
}
