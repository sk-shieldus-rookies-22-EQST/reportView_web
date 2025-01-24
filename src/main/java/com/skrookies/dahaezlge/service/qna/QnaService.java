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

    public int getTotalQnas() {
        return QnaRepository.countTotalQnas(); //전체 게시글 수 조회
    }

    public List<QnaDto> getQnasByPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize; // 가져올 데이터 시작 위치
        return QnaRepository.findQnasByPage(offset, pageSize);
    }
}
