package com.skrookies.dahaezlge.repository.user;


public interface UserRepository {

    /** 로그인
    * Id와 PW를 통해 성공 여부 확인
     * @return Boolean */
    Boolean login(String user_id, String user_pw);

}
