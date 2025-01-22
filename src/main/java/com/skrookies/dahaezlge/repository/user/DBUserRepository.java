package com.skrookies.dahaezlge.repository.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static org.springframework.data.relational.core.query.Query.query;


@Slf4j
@Repository
@RequiredArgsConstructor
public class DBUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Boolean login(String user_id, String user_pw) {

        String sql = "Select * from users where user_id = '" + user_id + "' and user_pw = '" + user_pw + "';";

        int count = jdbcTemplate.queryForObject(sql, int.class);

        return count > 0;
    }

}
