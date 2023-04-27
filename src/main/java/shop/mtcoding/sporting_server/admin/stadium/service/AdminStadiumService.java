package shop.mtcoding.sporting_server.admin.stadium.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.admin.stadium.dto.AdminStadiumListOutDto;
import shop.mtcoding.sporting_server.admin.stadium.dto.AdminWaitStadiumListOutDto;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumStatus;
import shop.mtcoding.sporting_server.core.handler.ex.CustomException;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
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

    @Transactional
    public void stadiumApprove(Long stadiumId) {
        Stadium stadiumPS = stadiumRepository.findById(stadiumId).orElseThrow(() -> {
            throw new CustomException("존재하지 않는 경기장입니다.", HttpStatus.BAD_REQUEST);
        });
        stadiumPS.setStatus(StadiumStatus.운영중);
    }

    public void stadiumDelete(Long stadiumId) {
    }

}
