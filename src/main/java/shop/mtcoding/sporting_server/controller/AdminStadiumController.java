package shop.mtcoding.sporting_server.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.adminuser.dto.stadium.AdminStadiumListOutDto;
import shop.mtcoding.sporting_server.adminuser.service.AdminStadiumService;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;

@RequiredArgsConstructor
@Controller
public class AdminStadiumController {

    private final AdminStadiumService adminStadiumService;

    @GetMapping("/admin/stadium")
    public String stadium(
            String keyword,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {

        Page<AdminStadiumListOutDto> adminStadiumListOutDto;
        int nowPage = 0;
        int startPage = 0;
        int endPage = 0;
        if (keyword != null && !keyword.isEmpty()) {
            adminStadiumListOutDto = adminStadiumService.getStadiumListByName(keyword, pageable);
        } else {
            adminStadiumListOutDto = adminStadiumService.getStadiumList(pageable);
        }

        nowPage = adminStadiumListOutDto.getPageable().getPageNumber() + 1;
        startPage = ((nowPage - 1) / 5) * 5 + 1; // 버튼에서 첫 숫자
        endPage = Math.min(nowPage + 5, adminStadiumListOutDto.getTotalPages()); // 버튼에서 마지막 숫자
        model.addAttribute("stadiumList", adminStadiumListOutDto.getContent());
        model.addAttribute("totalPage", adminStadiumListOutDto.getTotalPages());

        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("keyword", keyword);

        return "/admin_stadium/stadium";
    }

    @GetMapping("/admin/stadium/wait")
    public String stadium_wait() {
        return "/admin_stadium/wait";
    }

    @GetMapping("/admin/stadium/inactive")
    public String stadium_inactive() {
        return "/admin_stadium/inactive";
    }

}
