package com.flower.repository.search;

import com.flower.entity.ItemReview;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.flower.entity.QItemReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ReviewSearchImpl extends QuerydslRepositorySupport implements ReviewSearch {

    public ReviewSearchImpl(){
        super(ItemReview.class);
    }

    @Override
    public Page<ItemReview> searchAll(String[] types, String keyword, Pageable pageable) {
        // String[] types (제목t, 내용c, 작성자w)를 가지고 있는 문자열배열

        QItemReview itemReview = QItemReview.itemReview;
        JPQLQuery<ItemReview> query = from(itemReview);

        if( (types != null && types.length > 0) && keyword != null ){ //검색 조건과 키워드가 있다면

            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

            for(String type: types){
                switch (type){
                    case "t":
                        booleanBuilder.or(itemReview.rtitle.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(itemReview.rcontent.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(itemReview.rname.contains(keyword));//writer대신 rname
                        break;
                }
            }//end for
            query.where(booleanBuilder);
        }//end if

        //rno > 0
        query.where(itemReview.irno.gt(0L));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<ItemReview> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
        // 데이터를 가져온뒤 List 를 PageImpl 로 변환하기

    }

}
