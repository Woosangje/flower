package com.flower.repository;

import com.flower.entity.ItemReview;
import com.flower.entity.ReviewReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewReplyRepository extends JpaRepository<ReviewReply, Long> {

    //ItemReview 삭제시에 댓글들 삭제
    @Modifying
    @Query("delete from ReviewReply r where r.itemReview.irno =:irno ")
    void deleteByIrno(Long irno);//해당 리뷰의 irno를 검색하여 삭제

    List<ReviewReply> getReviewRepliesByItemReviewOrderByRno(ItemReview itemReview);//매개변수엣 rno받아서 댓글 조회

}
