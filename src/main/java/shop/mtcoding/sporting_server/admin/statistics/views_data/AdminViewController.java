package shop.mtcoding.sporting_server.admin.statistics.views_data;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.admin.statistics.views_data.dto.PagingButtonDTO;
import shop.mtcoding.sporting_server.admin.statistics.views_data.dto.ViewDataOutDTO;
import shop.mtcoding.sporting_server.core.dto.ResponseDto;

@RequiredArgsConstructor
@RequestMapping("admin")
@Controller
public class AdminViewController {

    private final AdminViewService adminViewService;

    @GetMapping("/statistics/views")
    public String views(Model model) {
        model.addAttribute("paging", new PagingButtonDTO(false, true, 1));
        return "statistics/views";
    }

    @GetMapping("/statistics/views/data")
    public ResponseEntity<?> viewData(Integer check, Model model) {
        ViewDataOutDTO viewDataOutDTO = adminViewService.getViewsData(check);

        return ResponseEntity.ok().body(new ResponseDto<>().data(viewDataOutDTO));
    }
}
