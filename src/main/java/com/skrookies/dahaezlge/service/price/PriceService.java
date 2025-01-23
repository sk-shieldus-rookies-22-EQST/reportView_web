package com.skrookies.dahaezlge.service.price;

import com.skrookies.dahaezlge.repository.price.PriceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PriceService {
    private final PriceRepository priceRepository;

    public String getBookInfo(String book_id){
        return priceRepository.getBookPrice(book_id);
    }
}
