package com.skrookies.dahaezlge.repository.user;

import com.skrookies.dahaezlge.entity.user.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

        String sql = "Select user_id from users where user_phone = '" + user_phone + "' and user_email = '" + user_email + "';";
        try {
            if (jdbcTemplate.queryForObject(sql, String.class) != null){
                return jdbcTemplate.queryForObject(sql, String.class);
            } else {
                return "no_users";
            }

        } catch (Exception e) {
            return "error";
        }
    }


    public List<Users> userinfo_list(String user_id){
        String sql = "select * from users where user_id = '" + user_id + "';";

        List<Users> user_info = jdbcTemplate.queryForList(sql, Users.class);
        return user_info;
    }

}
