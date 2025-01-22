package com.skrookies.dahaezlge.service.user;


import com.skrookies.dahaezlge.controller.user.Dto.UserDto;
import com.skrookies.dahaezlge.entity.user.Users;
import com.skrookies.dahaezlge.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Boolean login(String user_id, String user_pw){
        return userRepository.login(user_id, user_pw);
    }

    public String findUserId(String user_phone, String user_email) {
        return userRepository.findUserid(user_phone, user_email);
    }

    public List<Users> userInfo(String user_id){
        return userRepository.userinfo_list(user_id);
    }
}
