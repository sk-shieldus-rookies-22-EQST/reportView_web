package com.skrookies.dahaezlge.service.qnaRe;

import com.skrookies.dahaezlge.controller.qna.Dto.QnaReDto;
import com.skrookies.dahaezlge.repository.qnaRe.QnaReRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaReService {
    private final QnaReRepository qnaReRepository;

    public int saveReply(QnaReDto qnaReDto) {
        return qnaReRepository.saveReply(qnaReDto);
    }

    public List<QnaReDto> getRepliesByQnaId(Long qnaId) {
        return qnaReRepository.findRepliesByQnaId(qnaId);
    }
}
