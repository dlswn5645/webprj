package com.myapp.webprj.board.mapper;

import com.myapp.webprj.board.domain.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//자동 주입을 받을 수 있음
@SpringBootTest
class BoardMapperTest {

    @Autowired
    private BoardMapper mapper;

    @Test
    @DisplayName("데이터베이스에서 게시글 전체를 조회할 수 있어야 한다.")
    void findAllTest() {
        //when
        List<Board> boardList = mapper.getList();

        //then
        assertTrue(boardList.size() == 5);

        System.out.println("==========================");
        for(Board board : boardList){
            System.out.println(board);
        }
        System.out.println("==========================");
    }
    @Test
    @DisplayName("데이터베이스 저장에 성공해야 한다.")
    void insertTest(){
        Board newArticle = new Board();
        newArticle.setTitle("아기공룡 둘리");
        newArticle.setContent("꿀잼 만화입니다.");
        newArticle.setWriter("고길동");

        //when
        mapper.save(newArticle);

        //then
        assertTrue(mapper.getList().get(0).getBno()==6);
    }

    @Test
    @DisplayName("글 번호를 전달하면 해당 글 정보를 얻어야 한다.")
    void findOneTest() {
        Board board = mapper.findByBno(5L);
        System.out.println("board = " + board);

        assertTrue(board.getBno()==5);
    }
    @Test
    @DisplayName("글 내용, 글 제목, 작성자를 수정할 수 있어야 한다.")
    void updateTest() {
        Board newBoard = new Board();
        newBoard.setTitle("둘리와 친구들");
        newBoard.setContent("재밌는 만화입니다.");
        newBoard.setWriter("또치");
        newBoard.setBno(5L);

        mapper.update(newBoard);

        assertTrue(mapper.findByBno(5L).getWriter().equals("또치"));
    }

    @Test
    @DisplayName("글 번호를 전당하면 해당 글 정보가 삭제되어야 한다.")
    void deleteTest() {

        //when
        mapper.delete(8L);

        //then
        assertNull(mapper.findByBno(8L));

    }

}