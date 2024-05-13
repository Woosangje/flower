package com.flower.controller;



import com.flower.dto.NoticeFormDto;
import com.flower.dto.PageRequestDTO;
import com.flower.dto.PageResponseDTO;
import com.flower.repository.NoticeRepository;
import com.flower.service.NoticeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
@Log4j2
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    NoticeRepository noticeRepository;

    @GetMapping(value = "/notice/register")
    public String registerGet(Model model){
        model.addAttribute("noticeFormDto", new NoticeFormDto());
        return "/notice/register";
    }

    //# 등록
   @PostMapping(value = "/notice/register")
    public String registerPost(@Valid NoticeFormDto noticeFormDto, BindingResult bindingResult
            , Model model){

        if(bindingResult.hasErrors()){//필수값 없으면 페이지 이동
            log.info("에러11111");
            return "/notice/register";
        }

        try{
            noticeService.saveNotice(noticeFormDto);
            log.info("성공2222");
        }catch(Exception  e){
            model.addAttribute("errorMessage", "등록중 에러가 발생하였습니다.");
            return "notice/register";
        }

        return "redirect:/notice/notice";
    }





    @GetMapping({ "/notice/read","/notice/modify"})/// http://localhost/itemReview/read?rno=1&page=1
    public void read(Long nno, PageRequestDTO pageRequestDTO, Model model){

        try{
            NoticeFormDto noticeFormDto = noticeService.getNoticeRead(nno);
            model.addAttribute("noticeFormDto", noticeFormDto);


            model.addAttribute("requestDTO", pageRequestDTO);//페이지값출력

            log.info("####" + noticeFormDto);
        }catch(EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않습니다.");
            model.addAttribute("noticeFormDto", new NoticeFormDto());
            log.info("####");
        }

    }




    @PostMapping(value="/notice/modify")
    public String modifyPost(@Valid NoticeFormDto noticeFormDto, PageRequestDTO pageRequestDTO, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){

            return "notice/modify";
        }

        try{

            noticeService.updateNotice(noticeFormDto);
           /* model.addAttribute("requestDTO", pageRequestDTO);*/

        } catch(Exception e){

            model.addAttribute("errorMessage", "수정 중 에러가 발생하였습니다.");
             return "notice/modify";
        }
        return "redirect:/notice/notice";

    }

    @PostMapping(value="/notice/remove")
    public String remove(Long nno){
        log.info("remove post.. " + nno);
        noticeService.remove(nno);

        return "redirect:/notice/notice";
    }





     @GetMapping(value = "/notice/notice") //페이징이 없는경우, 있는 경우
    public void listGet(PageRequestDTO pageRequestDTO, Model model){


         PageResponseDTO<NoticeFormDto> responseDTO = noticeService.list(pageRequestDTO);
         model.addAttribute("notice", responseDTO);


    }



}
