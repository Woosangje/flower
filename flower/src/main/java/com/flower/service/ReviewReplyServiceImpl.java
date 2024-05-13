package com.flower.service;

import com.flower.dto.ReviewReplyDto;
import com.flower.entity.ItemReview;
import com.flower.entity.ReviewReply;
import com.flower.repository.ReviewReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewReplyServiceImpl implements ReviewReplyService{

    private final ReviewReplyRepository reviewReplyRepository;

    @Override
    public Long register(ReviewReplyDto reviewReplyDto){

        ReviewReply reviewReply = dtoToEntity(reviewReplyDto);

        reviewReplyRepository.save(reviewReply);

        return reviewReply.getRno();
    }

    @Override
    public List<ReviewReplyDto> getList(Long iron){

        List<ReviewReply> result = reviewReplyRepository
                .getReviewRepliesByItemReviewOrderByRno(ItemReview.builder().irno(iron).
                build());
        return result.stream().map(reviewReply -> entityToDto(reviewReply)).
                collect(Collectors.toList());
    }

    @Override
    public void modify(ReviewReplyDto reviewReplyDto){
        ReviewReply reviewReply = dtoToEntity(reviewReplyDto);

        reviewReplyRepository.save(reviewReply);
    }


    @Override
    public void remove(Long rno){
        reviewReplyRepository.deleteByIrno(rno);
    }

}
