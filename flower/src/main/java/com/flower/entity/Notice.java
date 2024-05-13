package com.flower.entity;

import com.flower.dto.NoticeFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "notiboard")
@Getter
@Setter
@ToString
public class Notice extends BaseTimeEntity{

    @Id
    @Column(name="nno", length = 100)
    @GeneratedValue(strategy = GenerationType.AUTO)//sequence 자동생성
    private Long nno;   // 게시 순서넘버


    @Column(nullable = false, length = 200)
    private String ntitle;

    @Column(nullable = false, length = 2000 )
    private String ncontent;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view; // 조회수

    public void addView(int count){//조회수
        this.view += count;
    }
    public void updateNotice(NoticeFormDto noticeFormDto){
        this.nno = noticeFormDto.getNno();
        this.ntitle = noticeFormDto.getNtitle();
        this.ncontent = noticeFormDto.getNcontent();

    }

}
