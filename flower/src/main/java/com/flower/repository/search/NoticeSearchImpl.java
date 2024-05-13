package com.flower.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.flower.entity.Notice;
import com.flower.entity.QNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class NoticeSearchImpl extends QuerydslRepositorySupport implements NoticeSearch {

    public NoticeSearchImpl(){
        super(Notice.class);
    }

    @Override
    public Page<Notice> searchAll(String[] types, String keyword, Pageable pageable) {
        // String[] types (제목t, 내용c, 작성자w)를 가지고 있는 문자열배열

        QNotice notice = QNotice.notice;
        JPQLQuery<Notice> query = from(notice);

        if( (types != null && types.length > 0) && keyword != null ){ //검색 조건과 키워드가 있다면

            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

            for(String type: types){
                switch (type){
                    case "t":
                        booleanBuilder.or(notice.ntitle.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(notice.ncontent.contains(keyword));
                        break;

                }
            }//end for
            query.where(booleanBuilder);
        }//end if

        //rno > 0
        query.where(notice.nno.gt(0L));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Notice> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
        // 데이터를 가져온뒤 List 를 PageImpl 로 변환하기

    }

}
