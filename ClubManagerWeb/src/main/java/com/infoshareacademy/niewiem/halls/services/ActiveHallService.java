package com.infoshareacademy.niewiem.halls.services;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.halls.mappers.HallDTOMapper;
import com.infoshareacademy.niewiem.halls.validators.HallValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.List;

@Stateless
public class ActiveHallService {
    private static final Logger LOG = LoggerFactory.getLogger(ActiveHallService.class);
    public static final String ACTIVE_HALL_PARAM = "activeHall";

    @Inject
    private HallDTOMapper hallDTOMapper;

    @Inject
    private HallValidator hallValidator;

    public HallDTO getActive(HttpSession session) {
        return (HallDTO) session.getAttribute(ACTIVE_HALL_PARAM);
    }

    public boolean setActive(HttpSession session, String hidString, List<String> errors) {
        if(!hallValidator.validateHidParam(hidString, errors)){
            return false;
        }
        Integer hid = Integer.valueOf(hidString);
        HallDTO hallDTO = hallDTOMapper.getHallDTOById(hid);

        if (hallDTO == null) {
            LOG.warn("Failed to retrieve requested hall by id: {}", hid);
            return false;
        }

        session.setAttribute(ACTIVE_HALL_PARAM, hallDTO);
        return true;
    }

    public void setNull(HttpSession session){
        session.setAttribute(ACTIVE_HALL_PARAM, null);
    }
}