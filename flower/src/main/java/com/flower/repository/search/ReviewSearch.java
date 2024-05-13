package com.flower.repository.search;

import com.flower.entity.ItemReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewSearch {//우상제작업
    /*Page<ItemReview> search1(Pageable pageable);*/

    Page<ItemReview> searchAll(String[] types, String keyword, Pageable pageable);
}
