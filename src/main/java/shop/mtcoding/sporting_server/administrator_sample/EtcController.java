package shop.mtcoding.sporting_server.administrator_sample;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.modules.user.entity.User;

@RequiredArgsConstructor
@Controller
public class EtcController {

    private final EtcService etcService;
    private final UserService2 userService;

    @GetMapping("/admin/loginForm")
    public String loginForm() {

        return "admin/loginForm";
    }

    @PostMapping("/login")
    public String login(AdmingLoginInDTO admingLoginInDTO) {
        etcService.로그인(admingLoginInDTO);

        return "admin/main";
    }

    @GetMapping("/admin/main")
    public String main() {

        return "admin/main";
    }

    // user 관리 ~
    @GetMapping("/admin/user/player")
    public String user_player(
            String keyword,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {

        Page<User> users;
        if (keyword != null && !keyword.isEmpty()) {
            users = userService.getUserListByEmailContaining(keyword, pageable);
        } else {
            users = userService.getPlayerUserList(pageable);
        }

        int nowPage = users.getPageable().getPageNumber() + 1;
        int startPage = ((nowPage - 1) / 5) * 5 + 1; // 버튼에서 첫 숫자
        int endPage = Math.min(nowPage + 5, users.getTotalPages()); // 버튼에서 마지막 숫자

        model.addAttribute("userList", users.getContent());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPage", users.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "/admin_user/player";

    }

    @GetMapping("/admin/user/company")
    public String user_company(
            String keyword,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {

        Page<User> users;
        if (keyword != null && !keyword.isEmpty()) {
            users = userService.getUserListByEmailContaining(keyword, pageable);
        } else {
            users = userService.getCompanyUserList(pageable);
        }

        int nowPage = users.getPageable().getPageNumber() + 1;
        int startPage = ((nowPage - 1) / 5) * 5 + 1; // 버튼에서 첫 숫자
        int endPage = Math.min(nowPage + 5, users.getTotalPages()); // 버튼에서 마지막 숫자

        model.addAttribute("userList", users.getContent());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPage", users.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "/admin_user/company";
    }

    @GetMapping("/admin/user/wait")
    public String user_wait() {
        return "/admin_user/wait";
    }

    @GetMapping("/admin/user/inactive")
    public String user_inactive() {
        return "/admin_user/inactive";
    }
    // ~ user 관리

    // court 관리 ~
    @GetMapping("/admin/court")
    public String court() {
        return "/admin_court/court";
    }

    @GetMapping("/admin/court/wait")
    public String court_wait() {
        return "/admin_court/wait";
    }

    @GetMapping("/admin/court/inactive")
    public String court_inactive() {
        return "/admin_court/inactive";
    }
    // ~ court 관리

    // stadium 관리 ~
    @GetMapping("/admin/stadium")
    public String stadium() {
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
    // ~ stadium 관리

}
