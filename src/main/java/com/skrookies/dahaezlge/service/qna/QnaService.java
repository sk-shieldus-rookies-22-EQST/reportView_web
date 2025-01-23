package com.skrookies.dahaezlge.service.qna;

import com.skrookies.dahaezlge.controller.qna.Dto.QnaDto;
import com.skrookies.dahaezlge.repository.qna.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor


public class QnaService {
    private final QnaRepository QnaRepository;
    public int qna(QnaDto QnaDto) {
        return QnaRepository.qna(QnaDto);
    }

    public List<QnaDto> getQnaList() {
        return QnaRepository.getQnaList();
    }


    public QnaDto getQnaById(int qna_id) {
        return QnaRepository.QnaById(qna_id);
    }

    public void deleteQna(int qna_id) {
        QnaRepository.deleteQna(qna_id);
    }

    public int qnaUpdate(QnaDto QnaDto) {
        return QnaRepository.qnaUpdate(QnaDto);
    }
}
