package com.skrookies.dahaezlge.repository.user;

import com.skrookies.dahaezlge.entity.loginTry.LoginTry;
import com.skrookies.dahaezlge.entity.user.Users;
import com.skrookies.dahaezlge.restcontroller.util.Bcrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DBUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public String login(String user_id, String user_pw) {

        String sql = "SELECT user_pw FROM users WHERE user_id = ?";
        try {
            // 사용자 정보 조회 (비밀번호)
            String searched_user_pw = jdbcTemplate.queryForObject(sql, String.class, user_id);
            if (searched_user_pw.equals("") || searched_user_pw == null){
                return "no_user";
            }

            // 로그인 시도 횟수와 마지막 로그인 실패 시간 조회
            String trySql = "SELECT try_count, try_time FROM login_try WHERE user_id = ?";
            LoginTry loginTry = jdbcTemplate.queryForObject(trySql, (rs, rowNum) -> {
                LoginTry lt = new LoginTry();
                lt.setTry_count(rs.getInt("try_count"));
                lt.setTry_time(rs.getTimestamp("try_time"));
                return lt;
            }, user_id);

            // 계정 잠금 여부 확인
            if (isAccountLocked(loginTry, user_id)) {
                log.info("계정이 잠겼습니다. 잠금 해제 시간까지 기다려주세요.");
                return "locked";
            }

            // 로그인 시도 횟수와 마지막 로그인 실패 시간 조회
            LoginTry loginTry2 = jdbcTemplate.queryForObject(trySql, (rs, rowNum) -> {
                LoginTry lt = new LoginTry();
                lt.setTry_count(rs.getInt("try_count"));
                lt.setTry_time(rs.getTimestamp("try_time"));
                return lt;
            }, user_id);

            // 비밀번호 비교
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            boolean isMatch = passwordEncoder.matches(user_pw, searched_user_pw);

            if (isMatch) {
                // 로그인 성공 시, 실패 횟수 초기화 및 시도 시간 초기화
                resetFailedAttempts(user_id);
                log.info("login success");
                return "true";
            } else {
                // 로그인 실패 시, 실패 횟수 증가 및 시도 시간 갱신
                incrementFailedAttempts(user_id, loginTry2);
                log.info("login failed");
                return "false";
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error during login: " + e.getMessage());
            return "false";
        }
    }

    @Override
    public List<Map<String, Object>> autoLogin(String user_id, String token) {

        String sql = "select * from auto_login where user_id = ? and token = ?";

        try {
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, user_id, token);
            return result;
        }
        catch (Exception e){
            return null;
        }
    }

    // 계정 잠금 여부 확인
    private boolean isAccountLocked(LoginTry loginTry, String user_id) {
        // 실패 횟수가 일정 이상인 경우 계정을 잠금
        final int MAX_FAILED_ATTEMPTS = 4;  // 최대 실패 횟수
        final int LOCK_TIME_MINUTES = 1;   // 잠금 시간 (10분)

        if (loginTry.getTry_count() >= MAX_FAILED_ATTEMPTS) {
            if (loginTry.getTry_time() != null) {
                log.info(String.valueOf(loginTry.getTry_time()));
                LocalDateTime lockTime = loginTry.getTry_time().toLocalDateTime().plusMinutes(LOCK_TIME_MINUTES);
                if(LocalDateTime.now().isBefore(lockTime)){
                    log.info(String.valueOf(lockTime));
                    return true; // 잠금 시간이 지나지 않았으면 잠금 상태
                } else {
                    String sql = "UPDATE login_try SET try_count = 0, try_time = NULL WHERE user_id = ?";
                    jdbcTemplate.update(sql, user_id);
                    log.info("잠금시간 해제");
                    log.info(String.valueOf(loginTry.getTry_count()));
                    return false; //잠금 시간이 지났으면 계정 잠금이 아님
                }
            }
        }
        return false;  // 실패 횟수가 부족하면 계정 잠금이 아님
    }

    // 로그인 실패 시, 실패 횟수 증가 및 시도 시간 갱신
    private void incrementFailedAttempts(String user_id, LoginTry loginTry) {
        loginTry.setTry_count(loginTry.getTry_count() + 1);
        loginTry.setTry_time(Timestamp.valueOf(LocalDateTime.now()));  // 실패 시도 시간 갱신

        // DB에 업데이트
        String updateSql = "UPDATE login_try SET try_count = ?, try_time = ? WHERE user_id = ?";
        jdbcTemplate.update(updateSql, loginTry.getTry_count(), loginTry.getTry_time(), user_id);
        log.info(String.valueOf(loginTry.getTry_count()));
        log.info(String.valueOf(loginTry.getTry_time()));
    }

    // 로그인 성공 시, 실패 횟수와 시도 시간 초기화
    private void resetFailedAttempts(String user_id) {
        String updateSql = "UPDATE login_try SET try_count = 0, try_time = NULL WHERE user_id = ?";
        jdbcTemplate.update(updateSql, user_id);
    }


    @Override
    public String findUserid(String user_phone, String user_email) {

        String sql = "Select user_id from users where user_phone = ? and user_email = ?";
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
            log.info("findUserId - phone: "+ user_phone);
            log.info("findUserId - email: "+ user_email);
            return "no_users";
        }
    }

    @Override
    public Boolean updateUserpw(String user_id, String user_pw) {
        log.info("updateUserpw user_id: " + user_id);
        log.info("updateUserpw user_pw: " + user_pw);

        String sql = "UPDATE users SET user_pw = ? WHERE user_id = ?";
        try {
            Bcrypt bcrypt = new Bcrypt();
            int count = jdbcTemplate.update(sql, bcrypt.hashPassword(user_pw), user_id);
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

    @Override
    public Boolean registerUser(String user_id, String user_pw, String user_phone, String user_email) {

        String sql = "INSERT INTO users (user_id, user_pw, user_phone, user_email, user_level, user_created_at) VALUES (?, ?, ?, ?, 1, ?)";
        String sql2_point = "INSERT INTO user_point (point_user_id, point) VALUES (?, 0)";
        String sql_login_try = "INSERT INTO login_try (user_id, try_time, try_count) VALUES (?,null,0)";
        log.info("user_id: "+ user_id);
        log.info("user_pw: "+ user_pw);
        log.info("user_phone: "+ user_phone);
        log.info("user_email: "+ user_email);

        try {
            if (user_pw.isEmpty() |user_email.isEmpty() | user_phone.isEmpty()) {
                return false;
            }

            // JdbcTemplate을 사용하여 INSERT 실행
            LocalDateTime now = LocalDateTime.now();
            Timestamp formatedNow = Timestamp.valueOf(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            log.info(formatedNow.toString());
            int result = jdbcTemplate.update(sql, user_id, user_pw, user_phone, user_email, formatedNow);
            log.info("sql success");
            int result2 = jdbcTemplate.update(sql2_point, user_id);
            log.info("sql2 success");
            int result_login_try = jdbcTemplate.update(sql_login_try, user_id);
            log.info("result_login_try success");
            // result 값이 1이면 성공
            if (result > 0 && result2 > 0 && result_login_try > 0) {
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
        String sql = "Select count(*) from users where user_id = ?";
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

    @Override
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
                Users user = new Users((String) row.get("user_id"),(String) row.get("user_pw"), (String) row.get("user_phone"),(String) row.get("user_email"), ((BigDecimal) row.get("user_level")).intValue(),(Timestamp) row.get("user_created_at"));
                // 추가적인 필드가 있으면 여기에 세팅
                user_info.add(user);
            }

            // 변환된 Users 객체 리스트 반환
            return user_info;
        } catch (Exception e) {
            e.printStackTrace();
            List<Users> user_test = new ArrayList<>();
            return user_test;
        }

    }

    @Override
    public Integer getUserLevel(String userId) {
        String sql = "SELECT user_level FROM users WHERE user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, userId);
        } catch (Exception e) {
            log.info("getUserLevel error for user: " + userId);
            return null; // 혹은 기본값 설정
        }
    }

    @Override
    public Boolean deleteUser(String user_id) {
        String sql = "delete from users where user_id = ?";
        try {
            log.info("del - try");
            int count = jdbcTemplate.update(sql, user_id);
            if ( count > 0 ){
                log.info("delete user");
                return true;
            } else {
                log.info("no delete");
                return false;
            }

        } catch (Exception e) {
            log.info("del - catch");
            return false;
        }
    }

}
