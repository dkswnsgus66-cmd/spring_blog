package com.tenco.blog.board;


import lombok.Builder;
import lombok.Data;

// 요청 데이터를 담는 DTO 클래스
// 컨트롤러 .. 비즈니스 .. 데이터 계층 사이에서 데이터 전송역할 객체
public class BoardRequest {


    @Data
    @Builder
    // Board 를 안쓰고 따로 이 클래스를 만든 이유는 사용자가 해당 멤버변수 3개만 사용하기 때문에 따로 3개만 쓰는걸 만들어 놓은 것
    public static class SaveDTO{
        private String username;
        private String title;
        private String content;

        // 편의기능 설계 가능
        // DTO 에서 Entity로 변환해주는 편의 메서드일뿐
        public Board toEntity(){
            return Board.builder()
                    .username(username)
                    .title(title)
                    .content(content)
                    .build();
        }

    }
}
