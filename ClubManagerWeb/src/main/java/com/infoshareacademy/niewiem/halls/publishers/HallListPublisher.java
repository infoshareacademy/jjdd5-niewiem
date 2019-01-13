package com.infoshareacademy.niewiem.halls.publishers;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.halls.services.HallQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Stateless
public class HallListPublisher {
    private static final Logger LOG = LoggerFactory.getLogger(HallListPublisher.class);

    @Inject
    private HallQueryService hallQueryService;

    public void publishHallList(Map<String, Object> model ) {
        List<HallDTO> halls = hallQueryService.findAll();
        model.put("halls", halls);
    }
}
