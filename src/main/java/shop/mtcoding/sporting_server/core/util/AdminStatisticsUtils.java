package shop.mtcoding.sporting_server.core.util;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.modules.connection_data.entity.ConnectionData;
import shop.mtcoding.sporting_server.modules.connection_data.repository.ConnectionDataRepository;
import shop.mtcoding.sporting_server.modules.user.entity.User;

@RequiredArgsConstructor
@Component
public class AdminStatisticsUtils {

    private final ConnectionDataRepository connectionDataRepository;

    public void manageConnectionsData(User userPS) {

        int currentYear = LocalDate.now().getYear(); // 2023
        int currentMonth = LocalDate.now().getMonthValue(); // 5
        Optional<ConnectionData> connectionDataPS = connectionDataRepository
                .findByYearAndMonthAndRole(currentYear, currentMonth, userPS.getRole());
        if (!connectionDataPS.isPresent()) {
            ConnectionData connectionData = ConnectionData
                    .builder()
                    .cnt(1L)
                    .year(currentYear)
                    .month(currentMonth)
                    .role(userPS.getRole())
                    .build();
            connectionDataRepository.save(connectionData);
        } else {
            ConnectionData connectionDataPS2 = connectionDataPS.get();
            connectionDataPS2.setCnt(connectionDataPS2.getCnt() + 1);
        }
    }
}
