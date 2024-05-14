package com.flower.entity;

import com.flower.dto.ItemReviewDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemReview extends BaseTimeEntity {
    // 넘버, 제목, 내용, 게시자, 별, 업데이트일,등록일
    @Id
    @Column(name = "irno", length = 100)
    @GeneratedValue(strategy = GenerationType.AUTO)//sequence 자동생성
    private Long irno;   // 게시 순서넘버


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_mno")//member 클래스의 mno와 매핑
    private Member member;


    private String email;  // 수정버튼 노출용

    private String rname;

    @Column(nullable = false, length = 200)
    private String rtitle;

    @Column(nullable = false, length = 2000)
    private String rcontent;

    @Column(columnDefinition = "integer default 0", nullable = false)
    @Min(0)
    @Max(5)
    private int rstar; //별 몇개 줬는가

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view; // 조회수

    public void addView(int count){//조회수
        this.view += count;
    }
    public static ItemReview createReview(ItemReviewDto itemReviewDto, Member member){

        ItemReview itemReview = new ItemReview();
        itemReview.setMember(member);//mno 계정번호
        itemReview.setEmail(member.getMemail());//이메일 받아야 수정,삭제버튼 제어할수 있다.
        itemReview.setRname(member.getMname());//이름은 member에서 받아온다.
        itemReview.setIrno(itemReviewDto.getIrno());
        itemReview.setRtitle(itemReviewDto.getRtitle());
        itemReview.setRcontent(itemReviewDto.getRcontent());
        itemReview.setRstar(itemReviewDto.getRstar());

        return itemReview;

    }

    public void updateReview(ItemReviewDto itemReviewDto){
        this.rname = itemReviewDto.getRname();
        this.rtitle = itemReviewDto.getRtitle();
        this.rcontent = itemReviewDto.getRcontent();
        this.rstar = itemReviewDto.getRstar();
    }




}
