package shop.mtcoding.sporting_server.adminuser.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.adminuser.dto.stadium.AdminStadiumListOutDto;
import shop.mtcoding.sporting_server.adminuser.dto.stadium.AdminWaitStadiumListOutDto;
import shop.mtcoding.sporting_server.modules.stadium.repository.StadiumRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminStadiumService {

    private final StadiumRepository stadiumRepository;

    public Page<AdminStadiumListOutDto> getStadiumListByName(String name, Pageable pageable) {
        return stadiumRepository.findAllForAdminByName(pageable, name);
    }

    public Page<AdminStadiumListOutDto> getStadiumList(Pageable pageable) {
        return stadiumRepository.findAllForAdmin(pageable);
    }

    public Page<AdminWaitStadiumListOutDto> getWaitStadiumListByName(String name, Pageable pageable) {
        return stadiumRepository.findAllWaitForAdminByName(pageable, name);
    }

    public Page<AdminWaitStadiumListOutDto> getWaitStadiumList(Pageable pageable) {
        return stadiumRepository.findAllWaitForAdmin(pageable);
    }

}
