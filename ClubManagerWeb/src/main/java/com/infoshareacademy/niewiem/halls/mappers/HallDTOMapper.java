package com.infoshareacademy.niewiem.halls.mappers;

import com.infoshareacademy.niewiem.domain.Hall;
import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;

@Stateless
public class HallDTOMapper {
    private static final Logger LOG = LoggerFactory.getLogger(HallDTOMapper.class);

    public HallDTO convertHallToDTO(Hall h){
        HallDTO dto = new HallDTO();
        dto.setId(h.getId());
        dto.setName(h.getName());
        dto.setImageURL(h.getImageURL());

        return dto;
    }
}