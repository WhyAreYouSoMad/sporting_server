package shop.mtcoding.sporting_server.admin.statistics.connection_data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.admin.statistics.connection_data.dto.ConnectionDataOutDTO;
import shop.mtcoding.sporting_server.modules.connection_data.repository.ConnectionDataRepository;

@RequiredArgsConstructor
@Service
public class AdminConnectionService {

    private final ConnectionDataRepository connectionDataRepository;

    public ConnectionDataOutDTO getConnectionData() {

        List<String> recentMonthsForCalcul = new ArrayList<>();
        List<String> recentMonths = new ArrayList<>();
        int currentYear = LocalDate.now().getYear(); // 2023
        int currentMonth = LocalDate.now().getMonthValue(); // 5

        // recentMonths : 오늘기준 현재 월을 제외한 최근 6개월 month들을 그래프의 x축에 표현 (ex. 11, 12, 1, 2,
        // 3, 4)
        // recentMonthsForCalcul : 202211, 202212, 202301로 저장되어 DB cnt 접속횟수 inquery에 활용
        // (DB Query에서 concat 문자열을 활용하므로 String으로 저장)
        for (int i = 1; i < 7; i++) {
            int month = currentMonth - i; // 4, 3, 2, 1, 0, -1 (현재날짜 5월 3일)
            int year = currentYear; // 2023, 2023, 2023, 2023, 2023, 2023
            if (month <= 0) {
                month += 12;
                year--;
            }
            recentMonthsForCalcul.add(String.valueOf(year * 100 + month)); // 202304, 202302, 202301, 202212, 202211,
                                                                           // 202210
            recentMonths.add(String.valueOf(month));
        }

        Collections.reverse(recentMonths);
        List<Integer> connectionPlayerDataListPS = connectionDataRepository.findByRoleAndYearMonthIn("PLAYER",
                recentMonthsForCalcul);
        List<Integer> connectionCompanyDataListPS = connectionDataRepository.findByRoleAndYearMonthIn("COMPANY",
                recentMonthsForCalcul);

        return new ConnectionDataOutDTO(connectionPlayerDataListPS, connectionCompanyDataListPS, recentMonths);
    }
}
