package com.skrookies.dahaezlge.repository.userPoint;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DBUserPointRepository implements UserPointRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public int selectUserPoint(String user_id) {

        String sql = "select point from user_point where point_user_id = '" + user_id + "';";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
