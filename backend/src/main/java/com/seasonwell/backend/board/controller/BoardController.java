package com.seasonwell.backend.board.controller;

import com.seasonwell.backend.board.dto.BoardRequest;
import com.seasonwell.backend.board.dto.AllBoardResponse;
import com.seasonwell.backend.board.dto.OneBoardResponse;
import com.seasonwell.backend.board.service.BoardService;
import com.seasonwell.backend.global.config.BaseResponse;
import com.seasonwell.backend.global.config.ResponseStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/api/community")
@RestController
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 글 등록
    @PostMapping("/write")
    public BaseResponse<String> createBoard(@RequestBody BoardRequest requestDto, HttpSession session) {
        String user = boardService.createBoard(requestDto, session);
        if (user != null) {
            String result = "게시글 생성 완료";
            return new BaseResponse<>(result);
        } else {
            return new BaseResponse<>(ResponseStatus.BAD_REQUEST);
        }
    }

    // 전체 목록 조회
    @GetMapping("/{board_type}")
    public BaseResponse<List<AllBoardResponse>> getAllBoards(@PathVariable Integer board_type) {
        List<AllBoardResponse> allBoardResponses = boardService.findAllBoard(board_type);
        return new BaseResponse<>(allBoardResponses);
    }

    // 글 하나 조회
    @GetMapping("/{board_type}/{board_no}")
    public BaseResponse<OneBoardResponse> getOneBoard(
            @PathVariable Integer board_type,
            @PathVariable Long board_no
    ) {
        OneBoardResponse oneBoardResponse = boardService.findOneBoard(board_type, board_no);
        return new BaseResponse<>(oneBoardResponse);
    }

    @GetMapping("/search/{board_title}")
public BaseResponse<List<AllBoardResponse>> getAllBoardByBoardTitle(
        @PathVariable String board_title
    ) {
        List<AllBoardResponse> allBoardResponses = boardService.findAllBoardByBoardTitle(board_title);
        return new BaseResponse<>(allBoardResponses);
    }
}
