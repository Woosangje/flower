package com.flower.dto;

import com.flower.entity.ItemReviewImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ItemReviewImgDto {

    private Long imgirno;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemReviewImgDto of(ItemReviewImg itemReviewImg) {
        return modelMapper.map(itemReviewImg,ItemReviewImgDto.class);
    }

}
