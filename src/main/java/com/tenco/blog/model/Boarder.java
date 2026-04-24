package com.tenco.blog.model;

import com.tenco.blog.util.MyDateUtil;
import jakarta.persistence.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;

@Data // get , set, toString
// @Entity - JPA가 이 클래스를 데이터 베이스 테이블과 매핑하는 객체로 인식하게 설정
// 즉, 이 어노테이션이 있어야 JPA 가 관리함
@Entity // 자동으로 테이블 만들어주는 어노테이션
@Table(name = "board_tb") // 내가 만들고자 하는 데이터 베이스 테이블
public class Boarder {

    // 내가 만들 테이블 설정
    // @id 이 필드가 기본키임을 설정 함
    @Id // primary key
    // IDENTITY 전략: 데이터 베이스계 기본 AUTO_INCREMENT 기능 사용
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private Timestamp createdAt;
    private String username;

    // createdAt -> 포멧하는 메서드 만들어 보기

    public String getTime(){
        return MyDateUtil.timeStamoFormat(createdAt);
    }



}
