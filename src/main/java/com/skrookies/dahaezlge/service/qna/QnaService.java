package com.skrookies.dahaezlge.service.qna;

import com.skrookies.dahaezlge.controller.qna.Dto.QnaDto;
import com.skrookies.dahaezlge.controller.qna.Dto.QnaReDto;
import com.skrookies.dahaezlge.entity.qnaRe.QnaRe;
import com.skrookies.dahaezlge.repository.qna.QnaRepository;
import com.skrookies.dahaezlge.restcontroller.board.dto.QnaListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor


public class QnaService {
    private final QnaRepository QnaRepository;
    public int qna(QnaDto QnaDto) {
        return QnaRepository.qna(QnaDto);
    }

    /** Qna 작성 글 전체 반환
     * @return List<QnaListDto></> */
    public List<QnaListDto> getAllQnaList() {
        List<QnaDto> qna_datas = QnaRepository.getQnaList();

        List<QnaListDto> qna_list = new ArrayList<>();
        for(QnaDto qnaDto : qna_datas){
            QnaListDto qnaListDto = new QnaListDto();

            qnaListDto.setQna_id(qnaDto.getQna_id());
            qnaListDto.setTitle(qnaDto.getQna_title());
            qnaListDto.setUser_id(qnaDto.getQna_user_id());
            qnaListDto.setCreated_at(qnaDto.getQna_created_at());

            qna_list.add(qnaListDto);
        }

        return qna_list;
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

    public int qnaUpdate2(QnaDto QnaDto) {
        return QnaRepository.qnaUpdate2(QnaDto);
    }

    public int getTotalQnas() {
        return QnaRepository.countTotalQnas(); //전체 게시글 수 조회
    }

    public List<QnaDto> getQnasByPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize; // 가져올 데이터 시작 위치
        return QnaRepository.findQnasByPage(offset, pageSize);
    }

    public List<QnaDto> searchQnaByKeyword(String keyword, int page) {
        int pageSize = 10;
        int offset = (page - 1) * pageSize;
        return QnaRepository.findByKeyword(keyword, offset, pageSize);
    }

    public int addQnaReply(QnaReDto qnaReDto) {
        return QnaRepository.qnaReply(qnaReDto);
    }

    public List<QnaRe> getRepliesByQnaId(int qna_id) {
        return QnaRepository.findRepliesByQnaId((long) qna_id);
    }

    public void deleteById(Long qna_re_id) {
        QnaRepository.deleteByQnaID(qna_re_id);
    }

    public QnaDto getQnaByFileName(String fileName) {
        return QnaRepository.findByFileName(fileName);
    }
}
