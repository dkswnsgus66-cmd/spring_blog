package com.tenco.blog.board;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final BoardPersistRepository boardPersistRepository;

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
    // 사용자 요청 -> HTTP 요청 메세지(Post)
    public String saveProc(BoardRequest.SaveDTO saveDTO){

        Board board = saveDTO.toEntity();
        boardPersistRepository.save(board);
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

//       List<Board> boarderList = boardNativeRepository.findAll();
       List<Board> boarderList = boardPersistRepository.findAll();
       model.addAttribute("boardList",boarderList);

        return "board/list";
    }

    // 게시글 상세보기 화면요청 처리

    // http://localhost:8080/board/1
    @GetMapping("/board/{id}")
    public String detail(@PathVariable(name = "id") Integer id,Model model){
        // 유효성 검사 , 인증 검사
       Board boarder = boardNativeRepository.findById(id);
       model.addAttribute("board",boarder);
        return"board/detail";
    }


    // /board/{{board.id}}/delete
    @PostMapping("/board/{id}/delete")
    public String deleteProc(@PathVariable(name = "id") Integer id){
        // 유효성 검사 , 인증 검사
        boardNativeRepository.deleteById(id);

        // post --> redirect --> get요청으로 변한다
        // PRG 패턴
        // 삭제하고 다시 리스트 화면으로 돌아가게 만듬
        return "redirect:/";
    }

    //http://localhost:8080/board/1/update-form


    @GetMapping("/board/{id}/update-form")
    public String updateFormPage(@PathVariable(name = "id") Integer id, Model model){
        // 사용자 에게 해당 게시물 내용을 보여 줘야 한다.

        // 조회 기능 - 게시글 아이디로
        Board boarder = boardNativeRepository.findById(id);
        model.addAttribute("board",boarder);

        return "board/update-form";
    }

    // /board/{id}/update
    @PostMapping("/board/{id}/update")
    public String updateProc(@PathVariable(name = "id") Integer id, @RequestParam(name = "username") String username,
    @RequestParam(name = "title") String title,
    @RequestParam(name = "content") String content
    ){
        log.info("username: " +username);
        log.info("title: " +title);
        log.info("content: " +content);
        log.info("id: " +id);

        boardNativeRepository.updateById(username,title,content,id);
        // 게시글 수정 요청 완료 --> 게시글 목록 , 게시글 상세보기화면 으로 보낼지 결정
        // 리다이렉트는 뷰 리졸브 동작이 아닌 (내부 파일 찾는것이 아니고)
        // 그냥 새로운 HTTP Get 요청이다
        //redirect: 찍고 주소를 넣으면 해당주소로 get요청으로 넘어간다
        return "redirect:/board/" + id;
    }

}
