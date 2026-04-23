package com.tenco.blog.repository;


import com.tenco.blog.model.Boarder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

// 이 어노테이션만 선언하면
@Repository // Ioc + DI
// 생성자 만듬
@RequiredArgsConstructor
public class BoardNativeRepository {

    // EntityManager: JPA 핵심 인터페이스
    // 데이터 베이스와 모든 작업을 담당
    private final EntityManager em;

    // DI - 생성자 의존 주입
//    public BoardNativeRepository(EntityManager em) {
//        this.em = em;
//    }

    // 트랜잭션 처리 끝
    @Transactional
    public void save(String title, String content, String username) {
        Query query = em.createNativeQuery("insert " +
                "into board_tb(title,content,username,created_at) values (?,?,?,now())");
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.setParameter(3, username);

        query.executeUpdate();
    }

    // 게시글 목록 조회 메서드
    public List<Boarder> findAll(){
        String sql = """
                select * from board_tb order by id desc
                """;
        // while(rs.next()) {Board board = new Board(); board.setTitle(rs.getString(title)))}

        // 쿼리를 넣는데 어떤 클래스에 넣을지 정해 줘야한다
        Query query = em.createNativeQuery(sql, Boarder.class);
        // rs.getString("title") ---> new Board() board.title = " rs.getString("title")"
        return query.getResultList();
    }

}
