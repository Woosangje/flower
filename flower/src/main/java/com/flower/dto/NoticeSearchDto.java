package com.flower.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeSearchDto {


    private String searchBy;//상품을 조회할 때 어떤 유형으로 조회할지 선택

    private String searchQuery = "";
    //조회할 검색어

}
