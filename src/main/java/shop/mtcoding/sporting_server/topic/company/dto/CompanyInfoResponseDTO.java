package shop.mtcoding.sporting_server.topic.company.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoAddress;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoAge;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoGender;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CompanyInfoResponseDTO {
    private Long id;
    private String tel;
    private String businessAdress;
    private String businessNumber;
    private CompanyFileResponseDTO sourceFile;

    @QueryProjection
    public CompanyInfoResponseDTO(Long id, String tel, String businessAdress, String businessNumber,
            CompanyFileResponseDTO sourceFile) {
        this.id = id;
        this.tel = tel;
        this.businessAdress = businessAdress;
        this.businessNumber = businessNumber;
        this.sourceFile = sourceFile;
    }

}
