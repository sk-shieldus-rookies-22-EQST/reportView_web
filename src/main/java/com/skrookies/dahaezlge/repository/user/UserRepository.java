package com.skrookies.dahaezlge.repository.user;


import com.skrookies.dahaezlge.entity.user.Users;

import java.util.List;

public interface UserRepository {

    /** 로그인
    * Id와 PW를 통해 성공 여부 확인
     * @return Boolean */
    Boolean login(String user_id, String user_pw);
    String findUserid(String user_phone, String user_email);
    List<Users> userinfo_list(String user_id);
    Boolean updateUserpw(String userId, String userPw);
    Boolean registerUser(String user_id, String user_pw, String user_phone, String user_email);
}
