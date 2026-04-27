package com.tenco.blog.board;

import com.tenco.blog.util.MyDateUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data // get , set, toString
// @Entity - JPA가 이 클래스를 데이터 베이스 테이블과 매핑하는 객체로 인식하게 설정
// 즉, 이 어노테이션이 있어야 JPA 가 관리함
@Entity // 자동으로 테이블 만들어주는 어노테이션
@Table(name = "board_tb") // 내가 만들고자 하는 데이터 베이스 테이블
@AllArgsConstructor // 전체 멤버변수를 넣을수 있는 생성자 이거 선언하면 컴파일러가 기본 생성자 안들어주기때문에 따로 기본 생성자 어노테이션 선언
@NoArgsConstructor // 기본 생성자 (필수) Entity 동작 필수
@Builder // 빌더 패턴
public class Board {

    // 내가 만들 테이블 설정
    // @id 이 필드가 기본키임을 설정 함
    @Id // primary key
    // IDENTITY 전략: 데이터 베이스계 기본 AUTO_INCREMENT 기능 사용
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private String username;
    //@CreationTimestamp : 하이버 네이트가 제공하는 어노테이션
    // 특정 하나의 엔티티가 저장이 될때 (insert 될때) 시간을 자동으로 저장해 설정
    // 즉 이 어노테이션만 지정해놓으면 insert에 now() 쓸 필요없음 default 같은거0
    @CreationTimestamp
    private Timestamp createdAt;

    // createdAt -> 포멧하는 메서드 만들어 보기

    public String getTime(){
        return MyDateUtil.timeStamoFormat(createdAt);
    }



}
