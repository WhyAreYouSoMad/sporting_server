package shop.mtcoding.sporting_server.admin.stadium.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.admin.stadium.dto.AdminStadiumListOutDto;
import shop.mtcoding.sporting_server.admin.stadium.dto.AdminWaitStadiumListOutDto;
import shop.mtcoding.sporting_server.admin.stadium.service.AdminStadiumService;
import shop.mtcoding.sporting_server.core.dto.ResponseDto;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminStadiumController {

    private final AdminStadiumService adminStadiumService;

    @GetMapping("/stadium")
    public String stadium(
            String keyword,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {

        Page<AdminStadiumListOutDto> adminStadiumListOutDto;

        if (keyword != null && !keyword.isEmpty()) {
            adminStadiumListOutDto = adminStadiumService.getStadiumListByName(keyword, pageable);
        } else {
            adminStadiumListOutDto = adminStadiumService.getStadiumList(pageable);
        }

        int nowPage = adminStadiumListOutDto.getPageable().getPageNumber() + 1;
        int startPage = ((nowPage - 1) / 5) * 5 + 1; // 버튼에서 첫 숫자
        int endPage = Math.min(nowPage + 5, adminStadiumListOutDto.getTotalPages()); // 버튼에서 마지막 숫자

        model.addAttribute("stadiumList", adminStadiumListOutDto.getContent());
        model.addAttribute("totalPage", adminStadiumListOutDto.getTotalPages());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("keyword", keyword);

        return "/admin_stadium/stadium";
    }

    @GetMapping("/stadium/wait")
    public String stadium_wait(
            String keyword,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {

        Page<AdminWaitStadiumListOutDto> adminWaitStadiumListOutDto;

        if (keyword != null && !keyword.isEmpty()) {
            adminWaitStadiumListOutDto = adminStadiumService.getWaitStadiumListByName(keyword, pageable);
        } else {
            adminWaitStadiumListOutDto = adminStadiumService.getWaitStadiumList(pageable);
        }

        int nowPage = adminWaitStadiumListOutDto.getPageable().getPageNumber() + 1;
        int startPage = ((nowPage - 1) / 5) * 5 + 1; // 버튼에서 첫 숫자
        int endPage = Math.min(nowPage + 5, adminWaitStadiumListOutDto.getTotalPages()); // 버튼에서 마지막 숫자

        model.addAttribute("stadiumWaitList", adminWaitStadiumListOutDto.getContent());
        model.addAttribute("totalPage", adminWaitStadiumListOutDto.getTotalPages());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("keyword", keyword);

        return "/admin_stadium/wait";
    }

    @GetMapping("/stadium/inactive")
    public String stadium_inactive() {
        return "/admin_stadium/inactive";
    }

    @PutMapping("/stadium/{stadiumId}")
    public ResponseEntity<?> approve(@PathVariable Long stadiumId) {
        adminStadiumService.stadiumApprove(stadiumId);

        return new ResponseEntity<>(new ResponseDto<>(), HttpStatus.OK);
    }

}
