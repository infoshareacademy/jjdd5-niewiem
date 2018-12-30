package com.infoshareacademy.niewiem.halls.services;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.halls.mappers.HallDTOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

@Stateless
public class ActiveHallService {
    private static final Logger LOG = LoggerFactory.getLogger(ActiveHallService.class);

    @Inject
    private HallDTOMapper hallDTOMapper;

    public HallDTO getActiveHall(HttpSession session) {
        return (HallDTO) session.getAttribute("activeHall");
    }

    public boolean setActive(HttpSession session, String hidString) {
        // todo: here be validation
        // todo: add errors like - could not find chosen hall etc.
        LOG.info("Requested hall id to set as an active hall: {}", hidString);
        Integer hid = Integer.valueOf(hidString);
        HallDTO hallDTO = hallDTOMapper.getHallDTOById(hid);

        if (hallDTO == null) {
            LOG.warn("Failed to retrieve requested hall by id: {}", hid);
            return false;
        }

        session.setAttribute("activeHall", hallDTO);
        return true;
    }

    public void setNull(HttpSession session){
        session.setAttribute("activeHall", null);
    }
}