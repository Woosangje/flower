package com.flower.service;

import com.flower.dto.*;
import com.flower.entity.Notice;
import com.flower.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    private final ModelMapper modelMapper;
    private final NoticeRepository noticeRepository;

    public Long saveNotice(NoticeFormDto noticeFormDto) throws Exception{


        Notice notice = noticeFormDto.createNotice();// 등록 페이지에서 입력 받은 데이터를 이용하여 notice객체를 생성
        noticeRepository.save(notice);

        return notice.getNno();
    }


    //# Read
    @Transactional
    public NoticeFormDto getNoticeRead(Long nno){

        Notice notice = noticeRepository.findByNno(nno);
        notice.addView(1);

        noticeRepository.save(notice);

        NoticeFormDto noticeFormDto = NoticeFormDto.of(notice);
        return noticeFormDto;

    }

    ///# 공지사항 수정
    public Long updateNotice(NoticeFormDto noticeFormDto) throws Exception{

        Notice notice = noticeRepository.findByNno(noticeFormDto.getNno());
        notice.updateNotice(noticeFormDto);

        return notice.getNno();
    }

    public void remove(Long nno){

        noticeRepository.deleteById(nno);
    }



    @Transactional
    public PageResponseDTO<NoticeFormDto> list(PageRequestDTO pageRequestDTO) {


        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("nno");

        Page<Notice> result = noticeRepository.searchAll(types, keyword, pageable);

        List<NoticeFormDto> dtoList = result.getContent().stream()
                .map(noticeReview -> modelMapper.map(noticeReview, NoticeFormDto.class)).collect(Collectors.toList());



        return PageResponseDTO.<NoticeFormDto>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();

    }
}
