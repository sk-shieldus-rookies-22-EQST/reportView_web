package com.skrookies.dahaezlge.entity.user;

import jakarta.persistence.Embedded;
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
public class User {

    // users

    @Id
    @Embedded
    private String user_id;

    @Embedded
    private String user_pw;

    @Embedded
    private String user_email;

    @Embedded
    private String user_phone;

    @Embedded
    private String user_level;

    @Embedded
    private String user_created_at;

    /* getter와 setter 사용 시 안적어도 되는것으로 알지만 혹시 몰라 작성해 둠 **/
//    public User(String user_id, String user_pw, String user_email, String user_phone, String user_level, String user_created_at){
//        this.user_id = user_id;
//        this.user_pw = user_pw;
//        this.user_email = user_email;
//        this.user_phone = user_phone;
//        this.user_level = user_level;
//        this.user_created_at = user_created_at;
//    }
}
