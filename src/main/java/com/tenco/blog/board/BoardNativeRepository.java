package com.tenco.blog.board;


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
    public List<Board> findAll() {
        String sql = """
                select * from board_tb order by id desc
                """;
        // while(rs.next()) {Board board = new Board(); board.setTitle(rs.getString(title)))}

        // 쿼리를 넣는데 어떤 클래스에 넣을지 정해 줘야한다
        Query query = em.createNativeQuery(sql, Board.class);
        // rs.getString("title") ---> new Board() board.title = " rs.getString("title")"
        return query.getResultList();
    }

    // 게시글 상세보기 특정 아이디로 조회
    public Board findById(Integer id) {

        String strQuery = """
                select * from board_tb where id = ?
                """;
        try {
            Query query = em.createNativeQuery(strQuery, Board.class);
            query.setParameter(1, id);
            return (Board) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    // 특정게시글 삭제 요청
    public void deleteById(Integer id) {
        String sql = """
                delete from Board_tb where id = ?
                """;
        Query query = em.createNativeQuery(sql, Board.class);
        query.setParameter(1, id);
        query.executeUpdate();
    }

    // 게시글 수정하기
    @Transactional
    public boolean updateById(String username, String title, String content, Integer id) {
        String sql = """
                update Board_tb set username = ?, title = ?, content = ? where id = ?
                """;

        Query query = em.createNativeQuery(sql, Board.class);
        query.setParameter(1, username);
        query.setParameter(2, title);
        query.setParameter(3, content);
        query.setParameter(4, id);

        // executeUpdate(); 몇개의 row가 수정됐다고 반환 int
       int rows = query.executeUpdate();
       if(rows > 0){
           return true;
       }else {
           return false;
       }

    }


}
