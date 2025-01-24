package com.skrookies.dahaezlge.repository.user;

import com.skrookies.dahaezlge.entity.user.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DBUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Boolean login(String user_id, String user_pw) {

        String sql = "Select count(*) from users where user_id = ? and user_pw =?;";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, user_id, user_pw);
            if ( count != null && count > 0 ){
                log.info("not_null");
                return true;
            } else {
                log.info("null");
                return false;
            }

        } catch (Exception e) {
            log.info("error");
            return false;
        }
    }

    @Override
    public String findUserid(String user_phone, String user_email) {

        String sql = "Select user_id from users where user_phone = ? and user_email = ?;";
        try {
            if (jdbcTemplate.queryForObject(sql, String.class, user_phone, user_email)!= null) {
                String user_id = (String) jdbcTemplate.queryForObject(sql, String.class, user_phone, user_email);
                if (user_id != null) {
                    log.info("userReposistory : user_id = " + user_id);
                    return user_id;
                } else {
                    return "no_users";
                }
            }else { return "no_users"; }
        } catch (Exception e) {
            return "no_users";
        }
    }


    public Boolean updateUserpw(String user_id, String user_pw) {
        log.info("updateUserpw user_id: " + user_id);
        log.info("updateUserpw user_pw: " + user_pw);
        String sql = "UPDATE users SET user_pw = ? WHERE user_id = ?";
        try {
            int count = jdbcTemplate.update(sql, user_pw,user_id);
            if ( count > 0 ){
                log.info("changed pw");
                return true;
            } else {
                log.info("no user");
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    public Boolean registerUser(String user_id, String user_pw, String user_phone, String user_email) {


        String sql = "INSERT INTO users (user_id, user_pw, user_phone, user_email, user_level, user_created_at) VALUES (?, ?, ?, ?, 1, ?)";
        String sql2_point = "INSERT INTO user_point (point_user_id, point) VALUES (?, 1000000)";
        log.info("user_id: "+ user_id);
        log.info("user_pw: "+ user_pw);
        log.info("user_phone: "+ user_phone);
        log.info("user_email: "+ user_email);

        try {
            // JdbcTemplate을 사용하여 INSERT 실행
            LocalDateTime now = LocalDateTime.now();
            Timestamp formatedNow = Timestamp.valueOf(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            log.info(formatedNow.toString());
            int result = jdbcTemplate.update(sql, user_id, user_pw, user_phone, user_email, formatedNow);
            log.info("sql success");


            int result2 = jdbcTemplate.update(sql2_point, user_id);
            log.info("sql2 success");
            // result 값이 1이면 성공
            if (result > 0 && result2 > 0) {
                log.info("user_id: "+ user_id);
                log.info("user_pw: "+ user_pw);
                log.info("user_phone: "+ user_phone);
                log.info("user_email: "+ user_email);
                return true;
            } else {
                log.info("registerUser : if에 안들어갔어");
                return false;
            }
        } catch (Exception e) {
            log.info("registerUser : catch로 빠졌어");
            return false;
        }
    }

    @Override
    public Boolean updateUserInfo(String user_id, String user_pw, String user_phone, String user_email) {

        String sql = "UPDATE users SET user_pw = ?, user_phone = ?, user_email = ? WHERE user_id = ?";
        try {
            int count = jdbcTemplate.update(sql, user_pw,user_phone, user_email,user_id);
            if ( count > 0 ){
                log.info("changed userInfo");
                return true;
            } else {
                log.info("not changed userInfo");
                return false;
            }

        } catch (Exception e) {
            log.info("UserRepository : exception catched");
            return false;
        }
    }

    @Override
    public Boolean checkId(String user_id) {
        String sql = "Select count(*) from users where user_id = ? ;";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, user_id);
            if ( count != null && count > 0){
                log.info("not_null");
                return true;
            } else {
                log.info("null");
                return false;
            }

        } catch (Exception e) {
            log.info("error");
            return false;
        }
    }


    public List<Users> userinfo_list(String user_id) {
        // SQL 쿼리 작성
        String sql = "SELECT * FROM users WHERE user_id = ?";

        // queryForList로 데이터를 가져옴
        try {
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, user_id);
            log.info("list 성공");
            // 반환할 Users 객체 리스트 초기화
            List<Users> user_info = new ArrayList<>();

            // Map을 Users 객체로 변환하여 리스트에 추가
            for (Map<String, Object> row : results) {
                Users user = new Users((String) row.get("user_id"),(String) row.get("user_pw"), (String) row.get("user_phone"),(String) row.get("user_email"), (Integer) row.get("user_level"),(Timestamp) row.get("user_created_at"));
                // 추가적인 필드가 있으면 여기에 세팅
                user_info.add(user);
            }

            // 변환된 Users 객체 리스트 반환
            return user_info;
        } catch (Exception e) {
            List<Users> user_test = new ArrayList<>();
            return user_test;
        }

    }

}
