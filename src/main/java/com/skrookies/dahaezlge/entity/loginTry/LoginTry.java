package com.skrookies.dahaezlge.entity.loginTry;

import com.skrookies.dahaezlge.entity.user.Users;
import javax.persistence.*;
import javax.validation.constraints.Size;
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
    private int try_count;
}