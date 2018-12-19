package com.infoshareacademy.niewiem.tables.mappers;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.halls.mappers.HallDTOMapper;
import com.infoshareacademy.niewiem.pojo.Table;
import com.infoshareacademy.niewiem.tables.dto.TableDTO;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TableDTOMapper {

    @Inject
    HallDTOMapper hallDTOMapper;

    public TableDTO convertTblToDTO(Table t){
        TableDTO dto = new TableDTO();
        dto.setId(t.getId());
        HallDTO hall = hallDTOMapper.convertHallToDTO(t.getHall());
        dto.setHall(hall);
        dto.setName(t.getName());
        dto.setType(t.getType());

        return dto;
    }
}
