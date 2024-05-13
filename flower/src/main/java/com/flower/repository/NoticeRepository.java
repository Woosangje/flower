package com.flower.repository;


import com.flower.entity.Notice;
import com.flower.repository.search.NoticeSearch;
import com.flower.repository.search.ReviewSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeSearch { //

    Notice findByNno(Long nno);


}
