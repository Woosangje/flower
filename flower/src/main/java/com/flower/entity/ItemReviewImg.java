package com.flower.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="item_review_img")
@Getter @Setter
public class ItemReviewImg extends BaseEntity{
    // # 상품 이용 후기 이미지 1장

    @Id
    @Column(name="img_irno")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imgirno;

    private String imgName; //이미지 파일명

    private String oriImgName; //원본 이미지 파일명

    private String imgUrl; //이미지 조회 경로

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "irno")//리뷰의 rno
    private ItemReview itemReview;

    public void updateItemImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }


}
