package com.infoshareacademy.niewiem.halls.services;

import com.infoshareacademy.niewiem.domain.Hall;
import com.infoshareacademy.niewiem.halls.dao.HallDao;
import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.halls.mappers.HallDTOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class HallQueryService {
    private static final Logger LOG = LoggerFactory.getLogger(HallQueryService.class);

    @Inject
    private HallDao hallDao;

    @Inject
    private HallDTOMapper hallDTOMapper;

    public HallDTO findById(Integer id) {
        Hall hall = hallDao.findById(id);
        return hallDTOMapper.convertHallToDTO(hall);
    }

    public List<HallDTO> findAll() {
        List<Hall> halls = hallDao.findAll();
        return halls.stream()
                .map(h -> hallDTOMapper.convertHallToDTO(h))
                .collect(Collectors.toList());
    }

}
