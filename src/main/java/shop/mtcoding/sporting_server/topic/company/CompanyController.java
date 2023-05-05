package shop.mtcoding.sporting_server.topic.company;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shop.mtcoding.sporting_server.core.auth.MyUserDetails;
import shop.mtcoding.sporting_server.core.dto.ResponseDto;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyRequest;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyResponse;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyUpdateFormOutDTO;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyResponse.UpdateOutDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/joinCompany")
    public ResponseEntity<?> joinCompany(@RequestBody @Valid CompanyRequest.JoinInDTO joinDTO,
            BindingResult bindingResult) {

        CompanyResponse.JoinDTO data = companyService.회원가입(joinDTO);
        ResponseDto<?> responseDTO = new ResponseDto<>().data(data);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<?> updateForm(@AuthenticationPrincipal MyUserDetails myUserDetails, @PathVariable Long id) {
        CompanyUpdateFormOutDTO companyResponseOutDTO = companyService
                .getUpdateForm(myUserDetails.getUser().getId());
        return ResponseEntity.ok().body(new ResponseDto<>().data(companyResponseOutDTO));
    }

    @PutMapping("/company/update")
    public ResponseEntity<?> updateCompany(@AuthenticationPrincipal MyUserDetails myUserDetails,
            @RequestBody @Valid CompanyRequest.UpdateInDTO updateInDTO) throws IOException {
        CompanyResponse.UpdateOutDTO updateOutDTO = companyService.정보변경(myUserDetails.getUser().getId(),
                updateInDTO);
        return ResponseEntity.ok().body(new ResponseDto<>().data(updateOutDTO));
    }

}
