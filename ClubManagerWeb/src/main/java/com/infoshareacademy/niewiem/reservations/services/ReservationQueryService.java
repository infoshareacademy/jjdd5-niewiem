package com.infoshareacademy.niewiem.reservations.services;

import com.infoshareacademy.niewiem.domain.Reservation;
import com.infoshareacademy.niewiem.domain.Table;
import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.reservations.dao.ReservationDao;
import com.infoshareacademy.niewiem.reservations.dto.ReservationInMillisDTO;
import com.infoshareacademy.niewiem.reservations.mappers.ReservationInMillisDTOMapper;
import com.infoshareacademy.niewiem.services.validators.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ReservationQueryService {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationQueryService.class);

    @Inject
    private ReservationDao reservationDao;

    @Inject
    private ReservationInMillisDTOMapper reservationInMillisDTOMapper;

    @Inject
    private InputValidator inputValidator;

    public Reservation findById(String ridString){
        Long rid = inputValidator.reqLongValidator(ridString);
        return findById(rid);
    }

    public Reservation findById(Long rid) {
        return reservationDao.findById(rid);
    }

    public List<ReservationInMillisDTO> findAllByHall(HallDTO hallDTO) {
        List<Reservation> reservations = reservationDao.findAllByHallId(hallDTO.getId());
        return reservations.stream()
                .map(r -> reservationInMillisDTOMapper.convertResToDTO(r))
                .collect(Collectors.toList());
    }

    public List<ReservationInMillisDTO> findAllActiveByHall(HallDTO hallDTO) {
        List<Reservation> reservations = reservationDao.findAllActiveByHall(hallDTO.getId());
        return reservations.stream()
                .map(r -> reservationInMillisDTOMapper.convertResToDTO(r))
                .collect(Collectors.toList());
    }

    public Reservation findActiveForTable(Table table) {
        return reservationDao.findActiveForTable(table);
    }

    public List<ReservationInMillisDTO> findAllByTableId(Integer tid) {
        List<Reservation> reservations = reservationDao.findAllByTableId(tid);
        return reservations.stream()
                .map(r -> reservationInMillisDTOMapper.convertResToDTO(r))
                .collect(Collectors.toList());
    }
}