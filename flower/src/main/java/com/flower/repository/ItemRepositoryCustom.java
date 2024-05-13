package com.flower.repository;

import com.flower.dto.ItemSearchDto;
import com.flower.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.flower.dto.MainItemDto;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
    // 상품 조회 조건을 담고 있는 itemSearchDto,  페이징 처리가 되는 pageable을 파라미터로 받음
    // 리턴 타입은 Page<객체>

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
    // QueryDsl에서 제공하는 @QueryProjection 기능 사용

}