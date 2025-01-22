package com.skrookies.dahaezlge.service.qna;

import com.skrookies.dahaezlge.controller.qna.Dto.QnaDto;
import com.skrookies.dahaezlge.repository.qna.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor


public class QnaService {
    private final QnaRepository QnaRepository;
    public int qna(QnaDto QnaDto) {
        return QnaRepository.qna(QnaDto);
    }
}
