package com.flower.controller;

import com.flower.service.ItemReviewService;
import com.flower.dto.ItemReviewDto;
import com.flower.dto.PageRequestDTO;
import com.flower.dto.PageResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RequestMapping("/itemReview")
@Controller
@RequiredArgsConstructor
@Log4j2
public class ItemReviewController {

    private final ItemReviewService itemReviewService;


    @GetMapping(value="/list")
    public void list(PageRequestDTO pageRequestDTO, Model model ){

        PageResponseDTO<ItemReviewDto> responseDTO = itemReviewService.list(pageRequestDTO);

        model.addAttribute("responseDTO", responseDTO);//페이징, 검색, itemReviewDto가 포함


        List<ItemReviewDto> itemReviewDtoList = itemReviewService.ReadList();//pageRequest 사용안한다.

        model.addAttribute("itemReviewDtoList", itemReviewDtoList);

    }


    @GetMapping(value = "/register")
    public String registerGet(Model model, Principal principal){
        model.addAttribute("itemReviewDto", new ItemReviewDto());
        model.addAttribute("userNameDto", principal.getName());
        log.info("##새로고침 "+principal.getName());
        return "/itemReview/register";
    }

    @PostMapping(value="/register")//테스트시 로그인 할것
    public String registerPost(@Valid ItemReviewDto itemReviewDto, BindingResult bindingResult
            , Principal principal, Model model, @RequestParam("itemReviewImgFile")MultipartFile itemReviewImgFile){


        if(bindingResult.hasErrors()){
            log.info("###has에러");
            model.addAttribute("errorMessage", "에러가 발생하였습니다.");

            return "/itemReview/register";
        }
        String email = principal.getName();//로그인해야 값받아온다. getName은 사실 이메일이다.

        try{
            itemReviewService.create(itemReviewDto, email, itemReviewImgFile);
            log.info("#####"+itemReviewImgFile+ "파일");

        }catch(Exception e){
            model.addAttribute("errorMessage", "에러가 발생하였습니다.");
            return "/itemReview/register";

        }
        return "redirect:/itemReview/list";

    }

    @GetMapping({"/read", "/modify"})/// http://localhost/itemReview/read?rno=1&page=1
    public void read(Long irno, PageRequestDTO pageRequestDTO,Principal principal, Model model) {
        //principal 메소드 수정버튼 노출에 사용합니다.

        String userEmail =principal.getName();// 수정, 삭제버튼에 사용

        try {
            ItemReviewDto itemReviewDto = itemReviewService.getItemReviewRead(irno);
            model.addAttribute("itemReviewDto", itemReviewDto);//

            model.addAttribute("requestDTO", pageRequestDTO);//페이지값출력

            model.addAttribute("userEmail", userEmail);//수정 버튼 노출

        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않습니다.");
            log.info("!!!!! error");
        }

    }

    @PostMapping(value="/modify")//테스트시 로그인 할것
    public String modifyPost(@Valid ItemReviewDto itemReviewDto, BindingResult bindingResult
            , Model model, @RequestParam("itemReviewImgFile")MultipartFile itemReviewImgFile){


        if(bindingResult.hasErrors()){
            log.info("##### has에러");
            model.addAttribute("errorMessage", "에러가 발생하였습니다.");
            return "/itemReview/modify";
        }


        log.info("####"+ itemReviewDto.getRtitle());
        try{
            itemReviewService.modify(itemReviewDto, itemReviewImgFile);

        }catch(Exception e){
            model.addAttribute("errorMessage", "에러가 발생하였습니다.");
            return "/itemReview/modify";

        }
        return "redirect:/itemReview/list";

    }


    @PostMapping(value="/remove")
    public String remove(Long irno, Model model){//상품후기 삭제

        try {
            /*itemReviewService.remove(irno);*/
            //댓글 포함 삭제
            itemReviewService.removeWithReplies(irno);

            //게시물이 삭제되었다면 첨부 파일 삭제
        }catch(Exception e){
            model.addAttribute("errorMessage", "에러가 발생하였습니다.");
        }

        return "redirect:/itemReview/list";
    }


}
