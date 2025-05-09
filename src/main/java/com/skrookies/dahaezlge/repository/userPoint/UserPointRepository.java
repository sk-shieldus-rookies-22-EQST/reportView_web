package com.skrookies.dahaezlge.repository.userPoint;

public interface UserPointRepository {

    /** 유저 포인트 반환
     * user_id를 통해 유저가 보유한 포인트 반환
     * @return List<int> */
    int selectUserPoint(String user_id);
    boolean charge_point(String userId, int charge_point);
    boolean use_point (String user_id, int use_point);
}
