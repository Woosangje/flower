package com.flower.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewReplyDto {

    private Long rno;

    private String text;

    private String replyer;

    private Long irno;//후기 게시글 번호

    private LocalDateTime regDate, modDate;//등록일 수정일

    private int replyCount; //해당 게시글의 댓글 수

}
