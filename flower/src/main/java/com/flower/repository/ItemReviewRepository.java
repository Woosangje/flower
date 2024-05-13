package com.flower.repository;

import com.flower.entity.ItemReview;
import com.flower.repository.search.ReviewSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemReviewRepository extends JpaRepository<ItemReview, Long>, ReviewSearch {


    ItemReview findByIrno(Long rno);//read 조회
}
