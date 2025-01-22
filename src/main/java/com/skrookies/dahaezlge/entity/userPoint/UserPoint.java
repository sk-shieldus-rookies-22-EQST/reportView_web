package com.skrookies.dahaezlge.entity.userPoint;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPoint {

    @Id
    @Column(name = "point_user_id")
    private String pointUserId;

    @Column(name = "point")
    private Integer point;


    public UserPoint(String pointUserId, Integer point) {
        this.pointUserId = pointUserId;
        this.point = point;
    }

}
