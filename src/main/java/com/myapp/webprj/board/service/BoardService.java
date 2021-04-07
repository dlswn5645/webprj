package com.myapp.webprj.board.service;

import com.myapp.webprj.board.domain.Board;
import com.myapp.webprj.common.Criteria;

import java.util.List;

public interface BoardService {

    //게시글 등록 과정
    void register(Board board);

    //게시글 상제 조회
    Board get(Long bno);

    //게시글 수정 과정
    boolean modify(Board board);

    //게시글 삭제 과정
    boolean remove(Long bno);

    //게시글 전체 조회
    List<Board> getList(Criteria cri);

    int getTotal();
}
