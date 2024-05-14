package com.flower.controller;

import com.flower.dto.ItemReviewDto;
import com.flower.dto.PageRequestDTO;
import com.flower.dto.PageResponseDTO;
import com.flower.repository.ItemReviewImgRepository;
import com.flower.service.ItemReviewService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RequestMapping("/itemReview")
@Controller
@RequiredArgsConstructor
@Log4j2
public class ItemReviewController {

    private final ItemReviewService itemReviewService;

    private final ItemReviewImgRepository itemReviewImgRepository;

    @GetMapping(value="/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

       PageResponseDTO<ItemReviewDto> responseDTO = itemReviewService.list(pageRequestDTO);

       model.addAttribute("responseDTO", responseDTO);


    }


    @GetMapping(value = "/register")
    public String registerGet(Model model, Principal principal){
        model.addAttribute("itemReviewDto", new ItemReviewDto());
        model.addAttribute("userNameDto", principal.getName());
        return "/itemReview/register";
    }

    @PostMapping(value="/register")//테스트시 로그인 할것
    public String registerPost(@Valid ItemReviewDto itemReviewDto, BindingResult bindingResult
            , Principal principal, Model model, @RequestParam("itemReviewImgFile")MultipartFile itemReviewImgFile){


        log.info("###" + itemReviewDto.getRstar() +"스타");
        if(bindingResult.hasErrors()){
            log.info("###"+itemReviewImgFile.getOriginalFilename()+ "has에러");
            return "/itemReview/register";
        }
        String email = principal.getName();//로그인해야 값받아온다. getName은 사실 이메일이다.

        try{
            itemReviewService.create(itemReviewDto, email, itemReviewImgFile);
            log.info("#####"+itemReviewImgFile+ "파일경");

        }catch(Exception e){
            log.info("#####"+principal.getName()+ "에러");
            model.addAttribute("errorMessage", "에러가 발생하였습니다.");
            return "/itemReview/register";

        }
        return "redirect:/itemReview/list";

    }

    @GetMapping({"/read", "/modify"})/// http://localhost/itemReview/read?rno=1&page=1
    public void read(Long irno, PageRequestDTO pageRequestDTO, Model model) {

        log.info("@@@DTO: " + pageRequestDTO);
        try {
            ItemReviewDto itemReviewDto = itemReviewService.getItemReviewRead(irno);
            model.addAttribute("itemReviewDto", itemReviewDto);

            model.addAttribute("requestDTO", pageRequestDTO);//페이지값출력

        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않습니다.");
            log.info("!!!!! error");
        }

    }

    @PostMapping(value="/modify")//테스트시 로그인 할것
    public String modifyPost(@Valid ItemReviewDto itemReviewDto, BindingResult bindingResult
            , Model model, @RequestParam("itemReviewImgFile")MultipartFile itemReviewImgFile){

        log.info("#####"+itemReviewImgFile+ "파일정보");
        if(bindingResult.hasErrors()){
            log.info("##### has에러");
            return "/itemReview/modify";
        }


        log.info("####"+ itemReviewDto.getRtitle());
        try{
            itemReviewService.modify(itemReviewDto, itemReviewImgFile);

        }catch(Exception e){
            log.info("#####"+ "에러2");
            model.addAttribute("errorMessage", "에러가 발생하였습니다.");
            return "/itemReview/modify";

        }
        return "redirect:/itemReview/list";

    }


    @PostMapping(value="/itemReview/remove")
    public String remove(Long irno){//상품후기 삭제
        log.info("상품 후기 삭제: " +irno);
        itemReviewService.remove(irno);

        return "redirect:/notice/list";
    }


}
