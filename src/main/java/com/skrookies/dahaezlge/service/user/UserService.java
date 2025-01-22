package com.skrookies.dahaezlge.service.user;


import com.skrookies.dahaezlge.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Boolean login(String user_id, String user_pw){

        return userRepository.login(user_id, user_pw);
    }
}
