package com.skrookies.dahaezlge.entity.userKey;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserKey {

    @Id
    @GeneratedValue
    @Column(name = "key_id")
    private Integer key_id;

    @Column(name = "key_user_id")
    private String key_user_id;

    @Column(name = "key_created_at")
    private LocalDate key_created_at;

    public UserKey(Integer key_id, String key_user_id, LocalDate key_created_at) {
        this.key_id = key_id;
        this.key_user_id = key_user_id;
        this.key_created_at = key_created_at;
    }
}
