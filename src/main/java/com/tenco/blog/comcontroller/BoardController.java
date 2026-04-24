package com.tenco.blog.comcontroller;


import com.tenco.blog.model.Boarder;
import com.tenco.blog.repository.BoardNativeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j

@Controller
// 생성자 의존주입으로 객체 생성
@RequiredArgsConstructor
public class BoardController {
    // DI 처리 해야함 외부에서 가지고 와야함
    private final BoardNativeRepository boardNativeRepository;


    /**
     * 게시글 작성 화면 요청
     * @return 페이지 반환
     * 주소설계 : http://localhost:8080/board/save-form
     */

    @GetMapping("/board/save-form")
    public String saveForm(){
        return "board/save-form";
    }

    /**
     * 게시글 작성 기능 요청
     * @return 페이지 반환
     * 주소설계 : http://localhost:8080/board/save-form
     */
    // 데이터 post하고 다시 그걸 던진다
    // 주소가 같아도 get post 방식 구분 되어서 적용가능
    // 데이터 들어오게 하기
    // 주소가 던져지면 해당 주소를 할당받은 함수 호출
    @PostMapping("/board/save")
    public String saveProc(
            @RequestParam("username") String username,
            @RequestParam("title") String title,
            @RequestParam("content") String content
    ){
        log.info("username: " + username);
        log.info("title: " + title);
        log.info("content: " + content);

        // insert + 트랜잭션처리까지 해줌
        boardNativeRepository.save(title,content,username);

        // redirect <-- 다시 url 요청 해
//        return "redirect:/";
        return "redirect:/";
    }

    /**
     * 게시글 목록 화면 요청
     * @return
     *
     * 주소설계 : http://localhost:8080/
     */
    @GetMapping({"/","index"})
    public String list(Model model){

       List<Boarder> boarderList = boardNativeRepository.findAll();
       model.addAttribute(boarderList);
        System.out.println(boarderList.toString());
        for (int i = 0 ; i < boarderList.size(); i++){
            System.out.println(boarderList.get(i).getTitle());
            System.out.println("-----------------------");
        }
        return "board/list";
    }

}
