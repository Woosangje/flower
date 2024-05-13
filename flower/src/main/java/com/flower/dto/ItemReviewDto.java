package com.flower.dto;

import com.flower.entity.ItemReview;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemReviewDto {

    private Long irno;   //제품리뷰넘버

    private String member;    //구매자 아이디

    private String rname;
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String rtitle;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String rcontent;

    @NotNull(message = "평점을 꼭 입력해주세요")
    private Integer rstar; //별 몇개 줬는가

    private Integer view;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    private int replyCount; //해당 게시글의 댓글 수

    //# 후기 이미지는 한장입니다. 없으면 기본이미지로 게시됩니다.
   private ItemReviewImgDto itemReviewImgDto;

   private Long itemReviewImgIrno;

   private static ModelMapper modelMapper = new ModelMapper();


   public static ItemReviewDto of(ItemReview itemReview){
       return modelMapper.map(itemReview, ItemReviewDto.class);
   }




}
