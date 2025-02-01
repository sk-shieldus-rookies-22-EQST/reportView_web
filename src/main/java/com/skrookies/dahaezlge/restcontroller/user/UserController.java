package com.skrookies.dahaezlge.restcontroller.user;

import com.skrookies.dahaezlge.controller.user.Dto.UserDto;
import com.skrookies.dahaezlge.entity.user.Users;
import com.skrookies.dahaezlge.restcontroller.auth.dto.UserIdDto;
import com.skrookies.dahaezlge.restcontroller.user.dto.*;
import com.skrookies.dahaezlge.service.book.BookService;
import com.skrookies.dahaezlge.service.purchase.PurchaseService;
import com.skrookies.dahaezlge.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PurchaseService purchaseService;
    private final BookService bookService;


    /** UserId 기반 회원 정보 반환 */
    @PostMapping("/info")
    public ResponseEntity<UserInfoDto> getUserInfo(@RequestBody UserIdDto userIdDto) {
        List<Users> userInfo = userService.userInfo(userIdDto.getUser_id());

        Users user = userInfo.getFirst();
        UserInfoDto userInfoDto = new UserInfoDto();

        userInfoDto.setUser_id(user.getUser_id());
        userInfoDto.setUser_pw(user.getUser_pw());
        userInfoDto.setUser_phone(user.getUser_phone());
        userInfoDto.setUser_email(user.getUser_email());
        userInfoDto.setUser_level(user.getUser_level());
        userInfoDto.setUser_created_at(user.getUser_created_at().toLocalDateTime());

        return ResponseEntity.ok()
                .body(userInfoDto);
    }


    /** User_id 기반 내 서재 정보 반환 */
    @PostMapping("/booklist")
    public ResponseEntity<MyBookListCapDto> getUserBookList(@RequestBody UserIdDto userIdDto) {

        MyBookListCapDto myBookListCapDto = new MyBookListCapDto();
        myBookListCapDto.setMyBookListDtoList(null);

        List<Long> books_id = purchaseService.purchaseBook_list(userIdDto.getUser_id());

        log.info("Android My Book List Size: " + books_id.size());

        List<MyBookListDto> myBookListDtoList = new ArrayList<>();
        if (!books_id.isEmpty()){
            for(int i = 0; i < books_id.size(); i++){
                List<Map<String, Object>> book_info = bookService.getMyBooks((Long)books_id.get(i));
                MyBookListDto myBookListDto = new MyBookListDto();

                myBookListDto.setBook_id((Long) book_info.get(i).get("book_id"));
                myBookListDto.setTitle(book_info.get(i).get("book_title").toString());
                myBookListDto.setWriter(book_info.get(i).get("book_auth").toString());
                myBookListDto.setBook_img_path(book_info.get(i).get("book_img_path").toString());

                myBookListDtoList.add(myBookListDto);
            }

            myBookListCapDto.setMyBookListDtoList(myBookListDtoList);
        }

        return ResponseEntity.ok()
                .body(myBookListCapDto);
    }

    @GetMapping("/purchase")
    public ResponseEntity<Map<String, Object>> getUserPurchaseHistory(@RequestParam String user_id) {
        // Replace with logic to fetch purchase history
        List<?> purchaseHistory = List.of(); // Example response
        return ResponseEntity.ok(Map.of("purchase", purchaseHistory));
    }


    @PostMapping("/point")
    public ResponseEntity<UserPointDto> getUserPoint(@RequestBody UserIdDto userIdDto) {

        int user_point = userService.userPoint(userIdDto.getUser_id());

        UserPointDto userPointDto = new UserPointDto();
        userPointDto.setUser_point(user_point);

        return ResponseEntity.ok()
                .body(userPointDto);
    }


    @PostMapping("/point/charge")
    public ResponseEntity<UserPointDto> userPointCharge(@RequestBody PointChargeDto pointChargeDto) {

        boolean result = purchaseService.chargePoint(pointChargeDto.getUser_id(), pointChargeDto.getCharge_point());

        if(result){
            int user_point = userService.userPoint(pointChargeDto.getUser_id());

            UserPointDto userPointDto = new UserPointDto();
            userPointDto.setUser_point(user_point);

            return ResponseEntity.ok()
                    .body(userPointDto);
        }
        else{

            UserPointDto userPointDto = new UserPointDto();
            userPointDto.setUser_point(-2);

            return ResponseEntity.ok()
                    .body(userPointDto);
        }
    }

}
