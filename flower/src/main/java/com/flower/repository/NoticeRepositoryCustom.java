package com.flower.repository;

import com.flower.dto.NoticeSearchDto;
import com.flower.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositoryCustom {


    Page<Notice> getNoticeListPage(NoticeSearchDto noticeSearchDto, Pageable pageable);
    // 상품 조회 조건을 담고 있는 noticeSearchDto,  페이징 처리가 되는 pageable을 파라미터로 받음
    // 리턴 타입은 Page<객체>

}
