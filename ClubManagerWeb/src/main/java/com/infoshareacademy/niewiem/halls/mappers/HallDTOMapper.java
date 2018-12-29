package com.infoshareacademy.niewiem.halls.mappers;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.domain.Hall;

import javax.ejb.Stateless;

@Stateless
public class HallDTOMapper {

    public HallDTO convertHallToDTO(Hall h){
        HallDTO dto = new HallDTO();
        dto.setId(h.getId());
        dto.setName(h.getName());
        dto.setImageURL(h.getImageURL());

        return dto;
    }

}
