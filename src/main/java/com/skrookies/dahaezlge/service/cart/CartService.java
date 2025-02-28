package com.skrookies.dahaezlge.service.cart;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.controller.cart.Dto.CartDto;
import com.skrookies.dahaezlge.entity.book.Book;
import com.skrookies.dahaezlge.entity.cart.Cart;
import com.skrookies.dahaezlge.entity.cartBook.CartBook;
import com.skrookies.dahaezlge.repository.book.BookRepository;
import com.skrookies.dahaezlge.repository.cart.CartRepository;
import com.skrookies.dahaezlge.repository.cartBook.CartBookRepository;
import com.skrookies.dahaezlge.restcontroller.purchase.dto.PurchaseCartDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartBookRepository cartBookRepository;
    private final BookRepository bookRepository;

    public List<BookDto> setCartList(String user_id){
        List<CartDto> cartIdList = cartRepository.getCartList(user_id);
        log.info("cart Service");
        List<Long> cartBookIdList = cartBookRepository.getCartBookList(cartIdList);

        return bookRepository.getCartBookInfo(cartBookIdList);
    }

    /** User_id 기반 cart_id와 book_id 및 Book 정보를 반환하는 메소드 */
    public List<PurchaseCartDto> androidPurchaseCartList(String user_id){

        List<CartDto> cartIdList = cartRepository.getCartList(user_id);
        log.info("android cart Service");
        List<Long> cartBookIdList = cartBookRepository.getCartBookList(cartIdList);

        List<BookDto> book_list = bookRepository.getCartBookInfo(cartBookIdList);


        List<PurchaseCartDto> purchase_cart_list = new ArrayList<>();
        for(int i = 0; i < book_list.size(); i++){

            PurchaseCartDto purchaseCartDto = new PurchaseCartDto();

            purchaseCartDto.setCart_id(cartIdList.get(i).getCart_id());
            purchaseCartDto.setBook_id(book_list.get(i).getBook_id());
            purchaseCartDto.setTitle(book_list.get(i).getBook_title());
            purchaseCartDto.setPrice(book_list.get(i).getBook_price());
            purchaseCartDto.setBook_img_path(book_list.get(i).getBook_img_path());

            purchase_cart_list.add(purchaseCartDto);
        }

        return purchase_cart_list;
    }

    public Boolean delCartItem(String user_id, Long book_id){
        log.info("cart Service delCartItem");
        List<CartDto> cartIdList = cartRepository.getCartList(user_id);
        List<Long> deletedCartId = cartBookRepository.delCartBookItem(cartIdList, book_id);
        if(deletedCartId.size() != 0){
            return cartRepository.delCartItem(deletedCartId);
        }
        else{
            return false;
        }
    }
}
