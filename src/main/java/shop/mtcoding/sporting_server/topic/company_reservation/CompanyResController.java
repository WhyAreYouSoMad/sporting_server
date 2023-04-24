package shop.mtcoding.sporting_server.topic.company_reservation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CompanyResController {

    private final CompanyResService companyResService;
}
