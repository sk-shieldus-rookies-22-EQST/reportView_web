package com.skrookies.dahaezlge.restcontroller.user;

import com.skrookies.dahaezlge.entity.user.Users;
import com.skrookies.dahaezlge.restcontroller.auth.dto.UserIdDto;
import com.skrookies.dahaezlge.restcontroller.user.dto.UserPointDto;
import com.skrookies.dahaezlge.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getUserInfo(@RequestParam String user_id) {
        List<Users> userInfo = userService.userInfo(user_id);
        return ResponseEntity.ok(Map.of("user_info", userInfo));
    }

    @GetMapping("/booklist")
    public ResponseEntity<Map<String, Object>> getUserBookList(@RequestParam String user_id) {
        // Replace with logic to fetch user's book list
        List<?> bookList = List.of(); // Example response
        return ResponseEntity.ok(Map.of("book", bookList));
    }

    @GetMapping("/purchase")
    public ResponseEntity<Map<String, Object>> getUserPurchaseHistory(@RequestParam String user_id) {
        // Replace with logic to fetch purchase history
        List<?> purchaseHistory = List.of(); // Example response
        return ResponseEntity.ok(Map.of("purchase", purchaseHistory));
    }


    @PostMapping("/point")
    public ResponseEntity<UserPointDto> userPoint(@RequestBody UserIdDto userIdDto) {

        int user_point = userService.userPoint(userIdDto.getUser_id());

        UserPointDto userPointDto = new UserPointDto();
        userPointDto.setUser_point(user_point);

        return ResponseEntity.ok()
                .body(userPointDto);
    }


}
