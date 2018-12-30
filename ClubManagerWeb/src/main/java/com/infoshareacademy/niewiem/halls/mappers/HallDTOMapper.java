package com.infoshareacademy.niewiem.halls.mappers;

import com.infoshareacademy.niewiem.domain.Hall;
import com.infoshareacademy.niewiem.halls.dao.HallDao;
import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class HallDTOMapper {
    private static final Logger LOG = LoggerFactory.getLogger(HallDTOMapper.class);

    @Inject
    private HallDao hallDao;

    public HallDTO convertHallToDTO(Hall h){
        HallDTO dto = new HallDTO();
        dto.setId(h.getId());
        dto.setName(h.getName());
        dto.setImageURL(h.getImageURL());

        return dto;
    }

    public HallDTO getHallDTOById(Integer hid){
        Hall hall = hallDao.findById(hid);
        return convertHallToDTO(hall);
    }

}
