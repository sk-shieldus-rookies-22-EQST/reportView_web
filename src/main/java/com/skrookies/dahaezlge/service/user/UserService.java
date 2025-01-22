package com.skrookies.dahaezlge.service.user;


import com.skrookies.dahaezlge.controller.user.Dto.UserDto;
import com.skrookies.dahaezlge.entity.user.Users;
import com.skrookies.dahaezlge.repository.user.UserRepository;
import com.skrookies.dahaezlge.repository.userPoint.UserPointRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserPointRepository userPointRepository;


    public Boolean login(String user_id, String user_pw){
        return userRepository.login(user_id, user_pw);
    }


    public List<Users> userInfo(String user_id) {

        return userRepository.userinfo_list(user_id);
    }

    public int userPoint(String user_id){

        return userPointRepository.selectUserPoint(user_id);
    }

    public String findUserId(String userPhone, String userEmail) {
        return userRepository.findUserid(userPhone,userEmail);
    }
}
