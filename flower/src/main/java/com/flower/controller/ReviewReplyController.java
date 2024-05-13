package com.flower.controller;

import com.flower.dto.ReviewReplyDto;
import com.flower.service.ReviewReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController//ajax 댓글 작업을 위한 선언
@RequestMapping("/replies/")
@Log4j2
@RequiredArgsConstructor
public class ReviewReplyController {

    private final ReviewReplyService reviewReplyService;    //자동주입을 위해 final

    
    // # 댓글 리스트
    @GetMapping(value = "/itemReview/{irno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReviewReplyDto>> getListByItemReview(@PathVariable("irno") Long irno){
    
        log.info("$$$$Log !! iron: " + irno);
        return new ResponseEntity<>(reviewReplyService.getList(irno), HttpStatus.OK);
    }

    // # 댓글 등록
    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReviewReplyDto reviewReplyDto){

        log.info("$$$$" + reviewReplyDto);

        Long rno = reviewReplyService.register(reviewReplyDto);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    //#댓글 삭제
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {
    
        log.info("RNO:" + rno );

        reviewReplyService.remove(rno);

        return new ResponseEntity<>("success", HttpStatus.OK);

    }

    // # 댓글 수정
    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReviewReplyDto reviewReplyDto) {

        log.info(reviewReplyDto);

        reviewReplyService.modify(reviewReplyDto);

        return new ResponseEntity<>("success", HttpStatus.OK);

    }

}
