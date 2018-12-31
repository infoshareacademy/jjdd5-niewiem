package com.infoshareacademy.niewiem.reservations.mappers;

import com.infoshareacademy.niewiem.domain.Reservation;
import com.infoshareacademy.niewiem.reservations.dto.ReservationInMillisDTO;
import com.infoshareacademy.niewiem.tables.dto.TableDTO;
import com.infoshareacademy.niewiem.tables.mappers.TableDTOMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Stateless
public class ReservationInMillisDTOMapper {

    @Inject
    private TableDTOMapper tableDTOMapper;

    public ReservationInMillisDTO convertResToDTO(Reservation r){
        ReservationInMillisDTO dto = new ReservationInMillisDTO();
        dto.setId(r.getId());
        TableDTO table = tableDTOMapper.convertTblToDTO(r.getTable());
        dto.setTable(table);
        dto.setStartMillis(convertLDTToMillis(r.getStartTime()));
        dto.setEndMillis(convertLDTToMillis(r.getEndTime()));
        dto.setCustomer(r.getCustomer());

        return dto;
    }

    public Long convertLDTToMillis(LocalDateTime ldt){
        return ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }



}
