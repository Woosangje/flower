package com.flower.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.flower.dto.NoticeSearchDto;
import com.flower.entity.Notice;
import com.flower.entity.QNotice;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.util.List;

public class NoticeRepositoryCustomImpl implements NoticeRepositoryCustom {

    private JPAQueryFactory queryFactory;  // 동적으로 쿼리를 생성 JPAQueryFactory란 JPA의 엔터티를 이용하여 JPQLQuery를 보다 쉽고 편리하게 작성할 수 있는 QueryDsl의 도구라 할 수 있다.

    public NoticeRepositoryCustomImpl(EntityManager em){
        // JPA를 사용하기 위해서는 Database 구조와 맵핑된 JPA Entity 들을 먼저 생성하게 된다.
        // 그리고, 모든 JPA의 동작은 이 Entity들을 기준으로 돌아가게 되는데, 이 때 Entity들을 관리하는 역할을 하는 녀석이 바로 EntityManager인 것이다.
        // 참고 https://velog.io/@juhyeon1114/JPA-%EC%98%81%EC%86%8D%EC%84%B1-%EC%BB%A8%ED%85%8D%EC%8A%A4%ED%8A%B8-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-w.-Entity-manager

        this.queryFactory = new JPAQueryFactory(em); // JPAQueryFactory생성자로 EntityManager 객체를 넣어 줌.
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){

        if(StringUtils.equals("ntitle", searchBy)){     // 제목
            return QNotice.notice.ntitle.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("ncontent", searchBy)){       // 내용
            return QNotice.notice.ncontent.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<Notice> getNoticeListPage(NoticeSearchDto noticeSearchDto, Pageable pageable) {

        List<Notice> content = queryFactory
                .selectFrom(QNotice.notice)             // 상품 데이터를 조회하기 위해서 QNotice의 notice 지정
                .where(searchByLike(noticeSearchDto.getSearchBy(), noticeSearchDto.getSearchQuery()))
                .orderBy(QNotice.notice.nno.desc())
                .offset(pageable.getOffset())                                       // 한번에 가지고올 시작 인덱스
                .limit(pageable.getPageSize())                                      // 한번에 가지고올 최대 개수
                .fetch();

        long total = queryFactory.select(Wildcard.count).from(QNotice.notice)
                .where(searchByLike(noticeSearchDto.getSearchBy(), noticeSearchDto.getSearchQuery()))
                .fetchOne()
                ;

        return new PageImpl<>(content, pageable, total);            // 페이징 처리되어 간다.
        //public PageImpl(List<T> content, Pageable pageable, long total) {
        //
        //		super(content, pageable);
        //
        //		this.total = pageable.toOptional().filter(it -> !content.isEmpty())//
        //				.filter(it -> it.getOffset() + it.getPageSize() > total)//
        //				.map(it -> it.getOffset() + content.size())//
        //				.orElse(total);
        //	}
    }
}
