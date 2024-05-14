package com.flower.dto;

import com.flower.entity.ItemReview;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemReviewDto {

    private Long irno;   //제품리뷰넘버

    private String email;

    private String rname;

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String rtitle;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String rcontent;

    private Integer rstar = 0; //별 몇개 줬는가 0 기본값

    private Integer view;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    private int replyCount; //해당 게시글의 댓글 수

    //# 후기 이미지는 한장입니다. 없으면 기본이미지로 게시됩니다.
   private ItemReviewImgDto itemReviewImgDto;

   private static ModelMapper modelMapper = new ModelMapper();


   public static ItemReviewDto of(ItemReview itemReview){
       return modelMapper.map(itemReview, ItemReviewDto.class);
   }




}
