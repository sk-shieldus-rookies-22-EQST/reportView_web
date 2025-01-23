package com.skrookies.dahaezlge.repository.user;

import com.skrookies.dahaezlge.entity.user.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.data.relational.core.query.Query.query;


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

    public String registerUser(String user_id, String user_pw, String user_phone, String user_email) {
        String sql = "INSERT INTO users (user_id, user_pw, user_phone, user_email, user_level, user_created_at) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            // JdbcTemplate을 사용하여 INSERT 실행
            LocalDateTime now = LocalDateTime.now();
            String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            int result = jdbcTemplate.update(sql, user_id, user_pw, user_phone, user_email, 1, formatedNow);

            // result 값이 1이면 성공
            if (result == 1) {
                return "User registered successfully";
            } else {
                return "Failed to add user";
            }
        } catch (Exception e) {
            return "Error occurred during user registration";
        }
    }


    public List<Users> userinfo_list(String user_id){
        String sql = "select * from users where user_id = '" + user_id + "';";

        List<Users> user_info = jdbcTemplate.queryForList(sql, Users.class);
        return user_info;
    }

}
