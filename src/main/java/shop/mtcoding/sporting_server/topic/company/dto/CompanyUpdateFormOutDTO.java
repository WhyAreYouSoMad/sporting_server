package shop.mtcoding.sporting_server.topic.company.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CompanyUpdateFormOutDTO {
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private CompanyInfoResponseDTO companyInfo;

    @QueryProjection
    public CompanyUpdateFormOutDTO(Long id, String nickname, String email, String password) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public CompanyUpdateFormOutDTO(CompanyInfoResponseDTO companyInfo) {
        this.companyInfo = companyInfo;
    }

}
