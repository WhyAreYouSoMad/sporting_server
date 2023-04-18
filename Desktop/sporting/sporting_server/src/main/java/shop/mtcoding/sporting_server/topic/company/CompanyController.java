package shop.mtcoding.sporting_server.topic.company;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.mtcoding.sporting_server.core.dto.ResponseDto;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyRequest;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/joinCompany")
    public ResponseEntity<?> joinCompany (@RequestBody CompanyRequest.JoinInDTO joinDTO) {

        CompanyResponse.JoinDTO data = companyService.회원가입(joinDTO);
        ResponseDto<?> responseDTO = new ResponseDto<>().data(data);
        return ResponseEntity.ok().body(responseDTO);
    }
}
