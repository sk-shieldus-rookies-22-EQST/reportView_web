package com.skrookies.dahaezlge.entity.userKey;

import com.skrookies.dahaezlge.entity.user.Users;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_key")
public class UserKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "key_id")
    private Long key_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "key_book_id", nullable = false)
    private Users users;

    @Column(name = "key_value")
    private String key_value;

    @Column(name = "key_created_at")
    private Timestamp key_created_at;

    public UserKey(Users users, String key_value, Timestamp key_created_at) {
        this.users = users;
        this.key_value = key_value;
        this.key_created_at = key_created_at;
    }
}


