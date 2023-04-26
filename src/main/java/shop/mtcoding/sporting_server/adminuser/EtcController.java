package shop.mtcoding.sporting_server.adminuser;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.topic.stadium_court.StadiumCourtService;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.modules.user.repository.UserRepository;

@RequiredArgsConstructor
@Controller
public class EtcController {
    private final UserRepository userRepository;
    private final EtcService etcService;
    private final UserService2 userService;
    private final StadiumCourtService stadiumCourtService;

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
    public String user_wait(
            String keyword,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {

        Page<User> users;
        if (keyword != null && !keyword.isEmpty()) {
            users = userService.getUserListByEmailContaining(keyword, pageable);
        } else {
            users = userService.getWaitUserList(pageable);
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

        return "/admin_user/wait";
    }

    @PostMapping("/admin/company/status")
    public ResponseEntity<Object> approveUser(@RequestParam("userId") Long userId) {
        boolean isApproved = userService.approveCompany(userId);
        if (isApproved) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/admin/user/inactive")
    public String user_inactive() {
        return "/admin_user/inactive";
    }
    // ~ user 관리

    // court 관리 ~
    @GetMapping("/admin/court")
    public String courts(
            String keyword,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {

        Page<StadiumCourt> courts;
        if (keyword != null && !keyword.isEmpty()) {
            courts = stadiumCourtService.getCourtListByTitleContaining(keyword, pageable);
        } else {
            courts = stadiumCourtService.getStadiumCourtList(pageable);
        }

        int nowPage = courts.getPageable().getPageNumber() + 1;
        int startPage = ((nowPage - 1) / 5) * 5 + 1; // 버튼에서 첫 숫자
        int endPage = Math.min(nowPage + 5, courts.getTotalPages()); // 버튼에서 마지막 숫자

        model.addAttribute("courtList", courts.getContent());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPage", courts.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "/admin_court/court";
    }

    @GetMapping("/admin/court/wait")
    public String court_wait(String keyword,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {

        Page<StadiumCourt> courts;
        if (keyword != null && !keyword.isEmpty()) {
            courts = stadiumCourtService.getCourtListByTitleContaining(keyword, pageable);
        } else {
            courts = stadiumCourtService.getStadiumCourtWaitList(pageable);
        }

        int nowPage = courts.getPageable().getPageNumber() + 1;
        int startPage = ((nowPage - 1) / 5) * 5 + 1; // 버튼에서 첫 숫자
        int endPage = Math.min(nowPage + 5, courts.getTotalPages()); // 버튼에서 마지막 숫자

        model.addAttribute("courtList", courts.getContent());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPage", courts.getTotalPages());
        model.addAttribute("keyword", keyword);

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
