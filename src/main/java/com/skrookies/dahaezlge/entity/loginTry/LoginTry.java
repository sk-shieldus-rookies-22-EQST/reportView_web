package com.skrookies.dahaezlge.entity.loginTry;

import com.skrookies.dahaezlge.entity.user.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LOGIN_TRY")
public class LoginTry {

    @Id
    @Column(name = "USER_ID")
    private String user_id;

    @Column(name = "TRY_TIME")
    private Timestamp try_time;

    @Column(name = "TRY_COUNT")
    private Long try_count;
}