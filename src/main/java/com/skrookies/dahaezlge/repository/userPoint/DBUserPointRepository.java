package com.skrookies.dahaezlge.repository.userPoint;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DBUserPointRepository implements UserPointRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public int selectUserPoint(String user_id) {

        try {
            String sql = "select point from user_point where point_user_id = '" + user_id + "';";

            return jdbcTemplate.queryForObject(sql, Integer.class);
        }catch (Exception e){
            return  -1;
        }
    }

    @Override
    public boolean charge_point(String user_id, int charge_point) {
        String sql = "select point from user_point where point_user_id = ?";
        List<Map<String, Object>> point = jdbcTemplate.queryForList(sql, user_id);
        int charged_point = charge_point + Integer.parseInt(point.get(0).get("point").toString());
        log.info("point: "+ charged_point);
        String sql2 = "UPDATE user_point SET point = ? WHERE point_user_id = ?";

        try {
            int count =jdbcTemplate.update(sql2, charged_point, user_id);
            if ( count > 0 ){
                log.info("point charged");
                return true;
            } else {
                log.info("no user");
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean use_point(String user_id, int total_book_price) {
        String sql = "select point from user_point where point_user_id = ?";
        List<Map<String, Object>> point = jdbcTemplate.queryForList(sql, user_id);
        int changed_point = Integer.parseInt(point.get(0).get("point").toString())-total_book_price;
        log.info("changed_point: "+ changed_point);
        String sql2 = "UPDATE user_point SET point = ? WHERE point_user_id = ?";

        try {
            int count = jdbcTemplate.update(sql2, changed_point, user_id);
            if ( count > 0 ){
                log.info("point used");
                return true;
            } else {
                log.info("no user");
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

}
