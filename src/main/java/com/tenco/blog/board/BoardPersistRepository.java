package com.tenco.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor // ->final 필드나 @NonNull 필드를 파라미터로 받는 생성자를 자동으로 만들어줌.
@Repository // Ioc 대상이 된다 - 싱글톤 패턴으로 관리됨
public class BoardPersistRepository {

    //JPA 핵심 인터페이스

    // DI
    // @Autowired
    private final EntityManager em;
    // JPQL 을 이용한 게시글 상세 보기
    public String detail () {
        String jpql = "SELECT b.content FROM Board b WHERE id = ?";
        return em.createQuery(jpql, Board.class).toString();
    }
    // JPQL 을 사용한 게시글 목록 조회
    public List<Board> findAll() {
        // JPQL : 엔티티 객체를 대상으로 하는 객체지향 쿼리
        // Board 는 엔티티 클래스명, b는 별칭
        String jpql = " SELECT b FROM Board b ORDER BY b.createdAt DESC";
        //            " select b.* FROM board_tb b ORDER BY b.created_at DESC;
        //v1에서는 em.nativeQuery()
//        Query query = em.createQuery(jpql, Board.class);
//        List<Board> boardList = query.getResultList();
//        return boardList;
        return em.createQuery(jpql, Board.class).getResultList();
    }



    // 게시글 저장 기능 - 영속성 컨텍스트 활용
    @Transactional
    public Board save(Board board) {
        // v1 -> 네이티브 쿼리 활용했다.

        // 1. 매개변수로 받은 board 는 현재 비영속 상태이다 .
        // - 아직 영속성 컨텍스트에 관리되지 않는 상태
        // - 데이터베이스와 아직은 연관이 없는 순수 Java 객체 상태
        // 2. em.persist(board); - 이 엔티티를 영속성 컨텍스트에 저장하는 개념
        // - 영속성 컨텍스트가 board 객체를 관리하게 된다.
        em.persist(board);
        // 3. 트랜잭션 커밋 시점에 Insert 쿼리 실행
        // - 이때 영속성 컨텍스트의 변경사항이 자동으로 Db에 반영 됨.
        // - board 객체의 id 필드에 자동으로 생성된 값이 설정 됨.
        // insert --> DB --> (pk 값 알수 있다)
        // select --> DB --> (할당된 pk 값 조회) 이 과정을 거치지 않아도 된다

        // 4. 영속 상태로 된 board 객체를 반환
        // - 이 시점에는 자동으로 board id 멤버 변수에 db pk 값이 할당된 상태이다.
        return board;
    }

}
