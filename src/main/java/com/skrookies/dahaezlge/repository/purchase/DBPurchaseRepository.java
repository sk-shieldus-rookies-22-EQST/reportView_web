package com.skrookies.dahaezlge.repository.purchase;

import com.skrookies.dahaezlge.controller.cart.Dto.CartDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Repository
@RequiredArgsConstructor
public class DBPurchaseRepository {
    private final JdbcTemplate jdbcTemplate;


}
