package com.tenco.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository // IoC
@RequiredArgsConstructor // DI 처리됨
public class BoardPersistRepository {

    // JPA 핵심 인터페이스
    // 영속성 컨텍스트를 관리하고 엔티티의(테이블과 1:1 모델링 되어있는 Board) 생명주기(메모리에 올라갔다 없어질때까지)를 제어
//    @Autowired // DI 외부에서 의존 주입 받는 객체
    private final EntityManager em; // final 사용하면 성능개선이 조금 됨 어차피 여기 변수에 접근해서 값을 변경할 필요 없음

    // 의존 주입 (외부에서 생성 되어있는 객체의 주소값을 주입받다)
//    public BoardPersistRepository(EntityManager em) {
//        this.em = em;
//    }

    // 영속성으로 DAO(레파지 토리) 만들어 보기

    // 게시글 저장
    @Transactional
    public Board save(Board board) {
        // Board는 Entity이지만 자바에서는 객체로 볼수있다
        // 메개 변수 받은 board 는 비영속상태
        // -- 아직 영속성 켄텍스트에 관리되고 있지안은 상태
        // -- 데이터 베이스와 연관되지 않는 순수 자바객체 (아직은)
//        em.createNativeQuery(); insert into board_tb... 쓸 필요 없다
        em.persist(board); // insert 처리 완료
        // 2. 이 board 객체를 영속성 컨텍스트에 넣어 둠 (SQL 저장소에 등록)
        // -- 영속성 컨텍스트에 들어 가더라도 아직 DB 에 실제 insert를 한것은 아니다
        // -- 3. 트랜잭션 -> 커밋(물리적인 저장소에 반영)실제 DB에 접근해서 insert 구문이 수행
        // -- 4. board 객체의 id 변수 값을 1차 캐시에 보관 map(구조로보관)
        // 1차 캐시에 들어간 이제 영속 상태로 변경된 오브젝트를 전달 (리턴한다)
        return board;
    }

    // JPQL을 사용한 게시글 목록 조회
    public List<Board> findAll() {

        // JPQL : 엔티티 객체를 대상으로 하는 객체지향 쿼리
        // Board는 엔티티 클래스 명 , b는 별칭
        // JPQL 쿼리

        // Board 테이블 명이 아닌 자바 클래스명 사용  찾는것도 createdAt 자바 멤버변수로 찾음
        String jpql = """
                SELECT b FROM Board b ORDER BY b.createdAt DESC
                """;
        // jpql 이라 이 쿼리 메서드 쓴다
        List<Board> boardList = em.createQuery(jpql, Board.class).getResultList();

        return boardList;

    }


}
