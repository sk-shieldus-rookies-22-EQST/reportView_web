package com.skrookies.dahaezlge.entity.cart;

import com.skrookies.dahaezlge.entity.user.Users;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cart_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_user_id", nullable = false)
    private Users users;

    @Column(name = "cart_total_price", nullable = false)
    private Integer cart_total_price;

    public Cart(Users users, Integer cart_total_price) {
        this.users = users;
        this.cart_total_price = cart_total_price;
    }
}



