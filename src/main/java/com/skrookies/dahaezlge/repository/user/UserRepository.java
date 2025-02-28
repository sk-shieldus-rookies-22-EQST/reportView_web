package com.skrookies.dahaezlge.repository.user;


import com.skrookies.dahaezlge.entity.user.Users;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface UserRepository {

    /** 로그인
    * Id와 PW를 통해 성공 여부 확인
     * @return Boolean */
    String login(String user_id, String user_pw);

    List<Map<String, Object>> autoLogin(String user_id, String token, String uuid);

    String findUserid(String user_phone, String user_email);
    List<Users> userinfo_list(String user_id);
    Boolean updateUserpw(String user_id, String user_pw);
    Boolean registerUser(String user_id, String user_pw, String user_phone, String user_email);

    Boolean updateUserInfo(String user_id, String user_pw, String user_phone, String user_email);

    Boolean checkId(String user_id);
    Integer getUserLevel(String userId);

    Boolean deleteUser(String userId);

    Boolean insertAutoLoginToken(String user_id, String token, Timestamp login_date);

    Boolean updateAutoLoginDate(String user_id, String token, Timestamp login_data);

    Boolean deleteAutoLoginDate(String user_id);

    Boolean selectAutoLoginDate(String user_id);
}
