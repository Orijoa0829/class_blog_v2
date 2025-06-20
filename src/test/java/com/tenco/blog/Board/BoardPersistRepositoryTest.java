package com.tenco.blog.Board;

import com.tenco.blog.board.Board;
import com.tenco.blog.board.BoardPersistRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(BoardPersistRepository.class)
@DataJpaTest
public class BoardPersistRepositoryTest {

    @Autowired
    private BoardPersistRepository br;
    @Test
    public void findById_test () {
        //given

        //when

        //than

    }
    @Test
    public void save_test () {
        //given
        Board board = new Board("제목123","내용123","승민군");
        // 저장 전 객체의 상태값 확인
        Assertions.assertThat(board.getId()).isNull();
        System.out.println("db에 저장 전 board : " + board);
        //when
        // 영속성 컨텍스트를 통한 엔티티 저장
        Board savedBoard = br.save(board);
        //then
        // 1. 저장 후에 자동생성된 ID 확인
        Assertions.assertThat(savedBoard.getId()).isNotNull();
        Assertions.assertThat(savedBoard.getId()).isGreaterThan(0);
        // 2. 내용 일치 여부 확인
        Assertions.assertThat(savedBoard.getTitle()).isEqualTo("제목123");

        // 3. JPA 가 자동으로 생성된 생성 시간 확인
        Assertions.assertThat(savedBoard.getCreatedAt()).isNotNull();

        // 4. 원본 객체 board , 영속성 컨텍스트에 저장한 - savedBoard
        Assertions.assertThat(board).isSameAs(savedBoard);

        System.out.println(savedBoard);

    }

    @Test
    public void findAll_test() {
        // given
        // db/data.sql (4개의 더미 데이터 있음)

        // when
        List<Board> boardList = br.findAll();

        // then
        System.out.println("size 테스트 :" + boardList.size());
        System.out.println("첫번째 게시글 제목 확인 : " + boardList.get(0).getTitle());

        //네이티브 쿼리를 사용한다는 것은.. 영속성 컨텍스트를 우회 해서 일 처리 하는 것이고,
        //JPQL 도 바로 영속성 컨텍스트를 우회하지만, 조회된 이후에는 영속성 상태가 된다.

        // -> JPQL 은 처음에는 DB 에서 직접 데이터를 가져오지만, 가져온 후에는 해당 객체를
        // 영속성 컨텍스트에 등록해서 ‘영속 상태’로 관리한다는 뜻입니다.
        int cnt = 0;
        for(Board board : boardList) {
            //Assertions.assertThat(board.getId()).isNotNull();
            //System.out.println("id 값 확인 : ");
            System.out.print("");
        }
    }
}
