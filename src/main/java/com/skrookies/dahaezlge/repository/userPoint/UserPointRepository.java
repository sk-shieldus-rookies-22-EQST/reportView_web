package com.skrookies.dahaezlge.repository.userPoint;

public interface UserPointRepository {

    /** 유저 포인트 반환
     * user_id를 통해 유저가 보유한 포인트 반환
     * @return List<int> */
    int selectUserPoint(String user_id);

    /** 유저 포인트 할당
     * user_id를 통해 유저가 회원가입 했을 때 포인트 할당
     * @return Boolean */
    Boolean insertUserPoint(String user_id);
}
