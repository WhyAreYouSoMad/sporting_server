package shop.mtcoding.sporting_server.core.util;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.modules.connection_data.entity.ConnectionData;
import shop.mtcoding.sporting_server.modules.connection_data.repository.ConnectionDataRepository;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.modules.view_data.entity.ViewData;
import shop.mtcoding.sporting_server.modules.view_data.repository.ViewDataRepository;

@RequiredArgsConstructor
@Component
public class AdminStatisticsUtils {

    private final ConnectionDataRepository connectionDataRepository;
    private final ViewDataRepository viewDataRepository;

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

    public void manageViewsData(Stadium stadiumPS) {

        int currentYear = LocalDate.now().getYear(); // 2023
        int currentMonth = LocalDate.now().getMonthValue(); // 5
        Optional<ViewData> viewDataPS = viewDataRepository
                .findByYearAndMonthAndStadium_Id(currentYear, currentMonth, stadiumPS.getId());
        if (!viewDataPS.isPresent()) {
            ViewData viewData = ViewData
                    .builder()
                    .cnt(1L)
                    .year(currentYear)
                    .month(currentMonth)
                    .stadium(stadiumPS)
                    .build();
            viewDataRepository.save(viewData);
        } else {
            ViewData viewDataPS2 = viewDataPS.get();
            viewDataPS2.setCnt(viewDataPS2.getCnt() + 1);
        }
    }
}
