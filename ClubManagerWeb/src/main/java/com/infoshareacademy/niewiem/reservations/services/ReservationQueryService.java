package com.infoshareacademy.niewiem.reservations.services;

import com.infoshareacademy.niewiem.domain.Reservation;
import com.infoshareacademy.niewiem.enums.TableType;
import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.reservations.dao.ReservationDao;
import com.infoshareacademy.niewiem.reservations.dto.ReservationInMillisDTO;
import com.infoshareacademy.niewiem.reservations.enums.Exclusivity;
import com.infoshareacademy.niewiem.reservations.mappers.ReservationInMillisDTOMapper;
import com.infoshareacademy.niewiem.services.validators.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
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

    public Reservation findById(String ridString) {
        Long rid = inputValidator.reqLongValidator(ridString);
        return findById(rid);
    }

    public Reservation findById(Long rid) {
        return reservationDao.findById(rid);
    }

    public List<ReservationInMillisDTO> findByHall(HallDTO hallDTO) {
        List<Reservation> reservations = reservationDao.findByHallId(hallDTO.getId());
        return convertToDTO(reservations);
    }

    public List<ReservationInMillisDTO> findActiveByHall(HallDTO hallDTO) {
        List<Reservation> reservations = reservationDao.findActiveByHall(hallDTO.getId());
        return convertToDTO(reservations);
    }

    public ReservationInMillisDTO findActiveForTable(Integer tid) {
        Reservation reservation = reservationDao.findActiveForTable(tid);
        return reservationInMillisDTOMapper.convertResToDTO(reservation);
    }

    public List<ReservationInMillisDTO> findByTableId(Integer tid) {
        List<Reservation> reservations = reservationDao.findByTableId(tid);
        return convertToDTO(reservations);
    }

    public List<ReservationInMillisDTO> findByHallAndType(HallDTO hallDTO, TableType type) {
        List<Reservation> reservations = reservationDao.findByHallAndType(hallDTO.getId(), type);
        return convertToDTO(reservations);
    }

    public List<ReservationInMillisDTO> findByHallAndTimeSpan(HallDTO hallDTO, LocalDateTime start, LocalDateTime end, Exclusivity exclusivity) {
        List<Reservation> reservations = reservationDao.findByHallIdAndTimeSpanExclusivityOption(hallDTO.getId(), start, end, exclusivity);
        return convertToDTO(reservations);
    }

    public List<ReservationInMillisDTO> findByTableIdAndTimeSpan(Integer tid, LocalDateTime start, LocalDateTime end, Exclusivity exclusivity) {
        List<Reservation> reservations = reservationDao.findByTableIdAndTimeSpanExclusivityOption(tid, start, end, exclusivity);
        return convertToDTO(reservations);
    }

    public List<ReservationInMillisDTO> findByHallAndTypeAndTimeSpan(HallDTO hallDTO, TableType type, LocalDateTime start, LocalDateTime end, Exclusivity exclusivity) {
        List<Reservation> reservations = reservationDao.findByHallIdAndTypeAndTimeSpanExclusivityOption(hallDTO.getId(), type, start, end, exclusivity);
        return convertToDTO(reservations);
    }

    private List<ReservationInMillisDTO> convertToDTO(List<Reservation> reservations) {
        return reservations.stream()
                .map(r -> reservationInMillisDTOMapper.convertResToDTO(r))
                .collect(Collectors.toList());
    }
}