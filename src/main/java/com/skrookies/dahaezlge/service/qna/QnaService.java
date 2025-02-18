package com.skrookies.dahaezlge.service.qna;

import com.skrookies.dahaezlge.controller.qna.Dto.QnaDto;
import com.skrookies.dahaezlge.controller.qna.Dto.QnaReDto;
import com.skrookies.dahaezlge.controller.user.Dto.LoginDto;
import com.skrookies.dahaezlge.entity.qnaRe.QnaRe;
import com.skrookies.dahaezlge.repository.qna.QnaRepository;
import com.skrookies.dahaezlge.restcontroller.board.dto.CommentDto;
import com.skrookies.dahaezlge.restcontroller.board.dto.QnaDetailDto;
import com.skrookies.dahaezlge.restcontroller.board.dto.QnaListDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class QnaService {
    private static final Logger log = LoggerFactory.getLogger(QnaService.class);
    private final QnaRepository qnaRepository;


    public int qna(QnaDto QnaDto) {
        return qnaRepository.qna(QnaDto);
    }


    /** Qna 작성 글 전체 반환
     * @return List<QnaListDto></> */
    public List<QnaListDto> getAllQnaList() {
        List<QnaDto> qna_datas = qnaRepository.getQnaList();

        List<QnaListDto> qna_list = new ArrayList<>();
        for(QnaDto qnaDto : qna_datas){
            QnaListDto qnaListDto = new QnaListDto();

            qnaListDto.setQna_id(qnaDto.getQna_id());
            qnaListDto.setTitle(qnaDto.getQna_title());
            qnaListDto.setUser_id(qnaDto.getQna_user_id());
            qnaListDto.setSecret(qnaDto.getSecret());
            qnaListDto.setCreated_at(qnaDto.getQna_created_at());

            qna_list.add(qnaListDto);
        }

        return qna_list;
    }


    /** Qna 게시글 상세 페이지 Data
     * 댓글 정보까지 포함된 Dto return
     * @return QnaDetailDto */
    public QnaDetailDto getQnaDetailById(int qna_id) {
        
        QnaDto qnaDto = qnaRepository.QnaById(qna_id);

        QnaDetailDto qnaDetailDto = new QnaDetailDto();

        qnaDetailDto.setQna_id(qnaDto.getQna_id());
        qnaDetailDto.setTitle(qnaDto.getQna_title());
        qnaDetailDto.setWriter(qnaDto.getQna_user_id());
        qnaDetailDto.setSecret(qnaDto.getSecret());
        qnaDetailDto.setCreated_at(qnaDto.getQna_created_at());
        qnaDetailDto.setContent(qnaDto.getQna_body());
        qnaDetailDto.setFile_name(qnaDto.getFile_name());
        qnaDetailDto.setFile_path(qnaDto.getFile_path());
        qnaDetailDto.setComment(getQnaCommentList((long) qna_id));

        return qnaDetailDto;
    }


    public QnaDto getQnaById(int qna_id) {
        return qnaRepository.QnaById(qna_id);
    }


    public void deleteQna(int qna_id) {
        qnaRepository.deleteQna(qna_id);
    }


    public int qnaUpdate(QnaDto QnaDto) {
        return qnaRepository.qnaUpdate(QnaDto);
    }


    public int qnaUpdate2(QnaDto QnaDto) {
        return qnaRepository.qnaUpdate2(QnaDto);
    }


    public int getTotalQnas() {
        return qnaRepository.countTotalQnas(); //전체 게시글 수 조회
    }


    public List<QnaDto> getQnasByPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize; // 가져올 데이터 시작 위치
        return qnaRepository.findQnasByPage(offset, pageSize);
    }


    public List<QnaDto> searchQnaByKeyword(String keyword, int page) {
        int pageSize = 10;
        int offset = (page - 1) * pageSize;
        return qnaRepository.findByKeyword(keyword, offset, pageSize);
    }


    public int addQnaReply(QnaReDto qnaReDto) {
        return qnaRepository.qnaReply(qnaReDto);
    }


    /** qna_id 기반 댓글 정보 return
     * @return List<CommentDto> */
    public List<CommentDto> getQnaCommentList(Long qna_id) {

        List<QnaRe> qnaCommentList = qnaRepository.findRepliesByQnaId(qna_id);

        List<CommentDto> commentList = new ArrayList<>();
        for(QnaRe comment : qnaCommentList){

            CommentDto comment_data = new CommentDto();
            comment_data.setQna_re_id(comment.getQna_re_id());
            comment_data.setQna_id(qna_id);
            comment_data.setQna_re_content(comment.getQna_re_body());
            comment_data.setQna_re_created_at(comment.getQna_re_created_at());

            commentList.add(comment_data);
        }

        return commentList;
    }


    public List<QnaReDto> getRepliesByQnaId(Long qna_id) {
        List<QnaRe> qnaReplies = qnaRepository.findRepliesByQnaId(qna_id);
        // 엔티티를 DTO로 변환
        return qnaReplies.stream()
                .map(qnaRe -> new QnaReDto(qnaRe))  // QnaReDto의 생성자를 사용하여 변환
                .collect(Collectors.toList());
    }


    public void deleteById(Long qna_re_id) {
        qnaRepository.deleteByQnaID(qna_re_id);
    }


    public QnaDto getQnaByFileName(String fileName) {
        return qnaRepository.findByFileName(fileName);
    }


    public int getTotalQnasByKeyword(String keyword) {
        return qnaRepository.countTotalQnasByKeyword(keyword);
    }


    /** QnA 게시글 작성 가능 여부 */
    public boolean qnaWriteTry(String user_id) {

        String sdate = Timestamp.valueOf(LocalDateTime.now().minusMinutes(10)).toString();
        String edate = Timestamp.valueOf(LocalDateTime.now()).toString();

        log.info("QnA 게시 날짜 범위");
        log.info("sdate: " + sdate);
        log.info("edate: " + edate);

        // 10분 내 몇개의 게시글을 작성했는지 확인
        Integer count = qnaRepository.countQnaByUserIdAndDate(user_id, sdate, edate);

        log.info("count repository result: " + count);

        if(count < 5 || count == null){
            return true;
        }
        else{
            return false;
        }

    }

}
