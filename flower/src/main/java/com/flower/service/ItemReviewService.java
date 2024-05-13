package com.flower.service;

import com.flower.dto.ItemReviewDto;
import com.flower.dto.ItemReviewImgDto;
import com.flower.dto.PageRequestDTO;
import com.flower.dto.PageResponseDTO;
import com.flower.entity.ItemReview;
import com.flower.entity.ItemReviewImg;
import com.flower.entity.Member;
import com.flower.repository.MemberRepository;
import com.flower.repository.ItemReviewImgRepository;
import com.flower.repository.ItemReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional//로직을 처리하다 에러가 발생하면, 변경된 데이터를 수행하기 이전 상태로 콜백해준다.
@RequiredArgsConstructor
public class ItemReviewService {

    private final ModelMapper modelMapper;
    private final ItemReviewRepository itemReviewRepository;
    private final ItemReviewImgService itemReviewImgService;
    private final MemberRepository memberRepository;
    private final ItemReviewImgRepository itemReviewImgRepository;

    public void create(ItemReviewDto itemReviewDto, String email, MultipartFile itemReviewImgFile) throws Exception {

        Member member = memberRepository.findByMemail(email);
        //회원번호로 찾기
        ItemReview itemReview = ItemReview.createReview(itemReviewDto, member);//계정의 mno필요
        itemReviewRepository.save(itemReview);

        //이미지 등록
        ItemReviewImg itemReviewImg = new ItemReviewImg();
        itemReviewImg.setItemReview(itemReview);

        itemReviewImgService.saveItemReviewImg(itemReviewImg, itemReviewImgFile);

    }

    public void modify(ItemReviewDto itemReviewDto, MultipartFile itemReviewImgFile) throws Exception {

        ItemReview itemReview = itemReviewRepository.findById(itemReviewDto.getIrno())
                .orElseThrow(EntityNotFoundException::new);
        itemReview.updateReview(itemReviewDto);

        Long itemReviewImgIrno = itemReviewDto.getItemReviewImgIrno();//이미지

        //이미지 등록
        itemReviewImgService.updateItemImg(itemReviewImgIrno, itemReviewImgFile);


    }

    @Transactional//view있어서 readonly아님
    public ItemReviewDto getItemReviewRead(Long irno) {

        ItemReviewImg itemReviewImg = itemReviewImgRepository.findByImgirno(irno);
        ItemReviewImgDto itemReviewImgDto = ItemReviewImgDto.of(itemReviewImg);//이미지 검색


        ItemReview itemReview = itemReviewRepository.findByIrno(irno);//계정 찾기

        itemReview.addView(1);//댓글조회수 추가
        itemReviewRepository.save(itemReview);//조회수저장

        ItemReviewDto itemReviewDto = ItemReviewDto.of(itemReview);//리뷰엔티티 받기
        itemReviewDto.setItemReviewImgDto(itemReviewImgDto);//리뷰엔티티에 이미지dto넣기
        return itemReviewDto;

    }

    @Transactional
    public PageResponseDTO<ItemReviewDto> list(PageRequestDTO pageRequestDTO) {


        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("irno");

        Page<ItemReview> result = itemReviewRepository.searchAll(types, keyword, pageable);

        List<ItemReviewDto> dtoList = result.getContent().stream()
                .map(itemReview -> modelMapper.map(itemReview, ItemReviewDto.class)).collect(Collectors.toList());


        for (ItemReviewDto dto : dtoList) {//리스트에 이미지 넣는과정
            Long irno = dto.getIrno();
            ItemReviewImg itemReviewImg = itemReviewImgRepository.findByImgirno(irno);
            ItemReviewImgDto itemReviewImgDto2 = ItemReviewImgDto.of(itemReviewImg);//이미지 검색
            dto.setItemReviewImgDto(itemReviewImgDto2);
        }


        return PageResponseDTO.<ItemReviewDto>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();

    }

    public void remove(Long nno){

        itemReviewRepository.deleteById(nno);
    }
}
