package com.skrookies.dahaezlge.entity.userPoint;

import com.skrookies.dahaezlge.entity.user.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_point")
public class UserPoint {

    @Id
    @Column(name = "point_user_id")
    private String point_user_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_user_id", insertable = false, updatable = false)
    private Users users;

    @Column(name = "point", nullable = false)
    private Integer point;

    public UserPoint(Users users, Integer point) {
        this.users = users;
        this.point_user_id = users.getUser_id();
        this.point = point;
    }
}

