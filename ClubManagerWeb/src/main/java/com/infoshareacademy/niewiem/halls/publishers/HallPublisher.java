package com.infoshareacademy.niewiem.halls.publishers;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.halls.services.HallQueryService;
import com.infoshareacademy.niewiem.halls.validators.HallValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Stateless
public class HallPublisher {
    private static final Logger LOG = LoggerFactory.getLogger(HallPublisher.class);

    @Inject
    private HallValidator hallValidator;

    @Inject
    private HallQueryService hallQueryService;

    public void publishRequestedHall(Map<String, Object> model, List<String> errors, String hidParam) {
        if(hallValidator.validateHidParam(hidParam, errors)){
            Integer hid = Integer.parseInt(hidParam);
            HallDTO hallDTO = hallQueryService.findById(hid);
            model.put("hall", hallDTO);
        }
    }
}
