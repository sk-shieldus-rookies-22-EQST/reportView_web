package com.skrookies.dahaezlge.service.user;


import com.skrookies.dahaezlge.entity.user.Users;
import com.skrookies.dahaezlge.repository.user.UserRepository;
import com.skrookies.dahaezlge.repository.userPoint.UserPointRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserPointRepository userPointRepository;


    public String login(String user_id, String user_pw){

        String result = userRepository.login(user_id, user_pw);

        if(result.equals("true")) {
            /** Token 생성 */
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String token = bCryptPasswordEncoder.encode(user_id);

            /** 로그인 시간 기록 */
            Timestamp login_date = Timestamp.valueOf(LocalDate.now().atStartOfDay());

            log.info("auto login date:{}", login_date);

            if(!userRepository.insertAutoLoginToken(user_id, token, login_date)){
                result = "false";
            }
        }

        return result;
    }


    /** 자동 로그인 */
    public Boolean auto_login(String user_id, String token){

        List<Map<String, Object>> autoLoginData = userRepository.autoLogin(user_id, token);

        log.info("auto login data:{}", autoLoginData);
        if(autoLoginData != null){
            LocalDate lastLogintDate = ((Timestamp) autoLoginData.get(0).get("token_gen_date")).toLocalDateTime().toLocalDate();

            log.info("last login date:{}", lastLogintDate);
            log.info("now data:{}", LocalDate.now().minusDays(30));

            if(lastLogintDate.isAfter(LocalDate.now().minusDays(30))){

                log.info("last login date update try");
                if(userRepository.updateAutoLoginDate(user_id, Timestamp.valueOf(LocalDate.now().atStartOfDay()))){
                    log.info("success");
                }

                return true;
            }
            else{
                log.info("last login date over 30 days");
                if(userRepository.deleteUser(user_id)){
                    log.info("delete auto login date");
                }
            }
        }

        return false;
    }

    public List<Users> userInfo(String user_id) {

        return userRepository.userinfo_list(user_id);
    }

    public int userPoint(String user_id){

        return userPointRepository.selectUserPoint(user_id);
    }

    public Boolean registerUser(String user_id, String user_pw, String user_phone, String user_email){
        return userRepository.registerUser(user_id, user_pw, user_phone, user_email);
    }

    public String findUserId(String userPhone, String userEmail) {
        return userRepository.findUserid(userPhone,userEmail);
    }

    public Boolean updateUserpw(String userId, String userPw) {
        return userRepository.updateUserpw(userId, userPw);
    }

    public Boolean updateUserInfo(String userId, String userPw, String userPhone, String userEmail) {
        return userRepository.updateUserInfo(userId, userPw, userPhone, userEmail);
    }

    public Boolean checkId(String user_id) {
        return  userRepository.checkId(user_id);
    }

    /** UserLevel 조회 실패 시 -1 반환 */
    public int getUserLevel(String userId) {
        Integer user_level = userRepository.getUserLevel(userId);

        return user_level != null ? user_level : -1;
    }

    public Boolean deleteUser(String user_id) {
        return  userRepository.deleteUser(user_id);
    }
}
