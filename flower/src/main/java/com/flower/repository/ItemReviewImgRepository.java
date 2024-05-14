package com.flower.repository;

import com.flower.entity.ItemReviewImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemReviewImgRepository extends JpaRepository<ItemReviewImg, Long> {

    ItemReviewImg findByImgirno(Long imgirno);//이미지 read,update,list에서 조회
}
