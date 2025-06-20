package com.tenco.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardPersistRepository br;
    //상세보기 구현 하기
    // 주소 설계 : http://localhost:8080/board/{id}
    public String boardDetail (Model model){
        //클라이언트가 게시글{id} 클릭 -> 서버에서 detail.mustache 파일 - 화면 보여줌 . GET 방식
        //detail.mustache 파일 변환하는 기능 만들기

        model.addAttribute();
        return "detail";
    }


    //메인화면에 리스트 출력하기

    // 주소설계 : http://localhost:8080/, http://localhost:8080/index
    @GetMapping({"/", "/index"})
    public String  boardList(HttpServletRequest request) {
        //클라이언트가 메인화면 버튼 클릭 -> 서버에서 클라이언트한테 index.mustache 파일 - 화면 보여줌.Get 방식
        // 1. index.mustache 파일을 반환 시키는 기능 만들기
        List<Board> boardList = br.findAll();
        request.setAttribute("boardList", boardList);
        // 2.
        return "index";
    }
    //게시글 작성 화면 요청 처리
    @GetMapping("/board/save-form")
    public String SaveForm() {
        return "board/save-form";
    }

    //게시글 작성 액션(수행) 처리
    @PostMapping("/board/save")
    public String save(BoardRequest.saveDTO reqDTO) {
        // HTTP 요청 본문 : title="값&content=값&username=값
        // form MIME (application/x-www-form-urlencoded)

        //br.save(reqDTO); < 타입달라서 작동x   ->   DTO -- Board -- DB
//        Board board = new Board(reqDTO.getTitle(), reqDTO.getContent(), reqDTO.getUsername());
//        br.save(board);
        Board board = reqDTO.toEntity();
        br.save(board);
        // Post-Get-Redirect 패턴
        return "redirect:/";
    }




}
