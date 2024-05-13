package com.flower.repository.search;

import com.flower.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeSearch {

    Page<Notice> searchAll(String[] types, String keyword, Pageable pageable);
}
