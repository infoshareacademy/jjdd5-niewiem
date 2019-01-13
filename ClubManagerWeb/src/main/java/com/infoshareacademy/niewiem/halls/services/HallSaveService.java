package com.infoshareacademy.niewiem.halls.services;

import com.infoshareacademy.niewiem.domain.Hall;
import com.infoshareacademy.niewiem.halls.dao.HallDao;
import com.infoshareacademy.niewiem.halls.mappers.HallMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Stateless
public class HallSaveService {
    private static final Logger LOG = LoggerFactory.getLogger(HallSaveService.class);

    @Inject
    private HallDao hallDao;

    @Inject
    private HallMapper hallMapper;

    public void saveWithoutValidation(Hall hall) {
        hallDao.save(hall);
    }

    public boolean createNewHall(HttpServletRequest req, List<String> errors) {
        Hall hall = hallMapper.getHallWithoutId(req, errors);
        return hallDao.save(hall) > -1;
    }
}