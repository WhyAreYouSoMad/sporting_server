package shop.mtcoding.sporting_server.admin.email.dto;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class EmailDTO {
    private List<EmailObj> emailList;
    private String title;
    private String content;

    @Getter
    @Setter
    public static class EmailObj {
        private Long id;
        private String email;
    }
}
