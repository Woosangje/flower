package com.flower.dto;

import com.flower.entity.Notice;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter @Setter
public class NoticeFormDto {


    private Long nno;

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String ntitle;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String ncontent;

    private int view;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    private static ModelMapper modelMapper = new ModelMapper();


    public Notice createNotice() {
        return modelMapper.map(this, Notice.class); }

    public static NoticeFormDto of(Notice notice) {
        return modelMapper.map(notice, NoticeFormDto.class); }
    

}
