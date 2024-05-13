package com.flower.service;

import com.flower.dto.ReviewReplyDto;
import com.flower.entity.ItemReview;
import com.flower.entity.ReviewReply;

import java.util.List;

public interface ReviewReplyService {

    Long register(ReviewReplyDto reviewReplyDto);   //댓글 등록

    List<ReviewReplyDto> getList(Long irno); //특정 게시물의 댓글 목록

    void modify(ReviewReplyDto reviewReplyDto); //댓글 수정

    void remove(Long rno);  //댓글 삭제

    //ReviewReplyDto를 ReviewReply객체로 변환 ItemReview 객체의 처리가 수반됨
    default ReviewReply dtoToEntity(ReviewReplyDto reviewReplyDto){

        //ReviewReplyDto에서 irno속성을 가져와서 설정
        ItemReview itemReview = ItemReview.builder().irno(reviewReplyDto.getIrno()).build();
        
        //reviewReplyDto에서 가져온 값을 입력
        ReviewReply reviewReply = ReviewReply.builder()
                .rno(reviewReplyDto.getRno())
                .text(reviewReplyDto.getText())
                .replyer(reviewReplyDto.getReplyer())
                .itemReview(itemReview)
                .build();
        return reviewReply;
    }
    // ReviewReply객체를 ReviewReplyDto로 변환 ItemReview 객체가 필요하지 않으므로 게시물 번호만
    default ReviewReplyDto entityToDto(ReviewReply reviewReply) {

        //reviewReply에서 가져온 값을 입력
        ReviewReplyDto dto = ReviewReplyDto.builder()
                .rno(reviewReply.getRno())
                .text(reviewReply.getText())
                .replyer(reviewReply.getReplyer())
                .regDate(reviewReply.getRegTime())
                .modDate(reviewReply.getUpdateTime())
                .build();

        return dto;
    }
}
