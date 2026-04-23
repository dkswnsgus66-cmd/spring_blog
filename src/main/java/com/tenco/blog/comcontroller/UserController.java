package com.tenco.blog.comcontroller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {


    // 회원 가입 페이지 요청
    // 주소설계 : http://localhost:8080/join-form 이경로로 오면 회원가입 페이지 반환하게 설계 할거임

    @GetMapping("/join-form")
    public String joinForm(){

        // 뷰 리졸브 동작
        // classPath: src/main/resource/templates/ 가 기본이라 user부터 적어준거임
        return "user/join-form";
    }



}
