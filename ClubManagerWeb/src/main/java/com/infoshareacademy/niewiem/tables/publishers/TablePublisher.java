package com.infoshareacademy.niewiem.tables.publishers;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.tables.dto.TableDTO;
import com.infoshareacademy.niewiem.tables.services.TableQueryService;
import com.infoshareacademy.niewiem.tables.validators.TableValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Stateless
public class TablePublisher extends TableGenericPublisher {
    private static final Logger LOG = LoggerFactory.getLogger(TablePublisher.class);

    @Inject
    private TableValidator tableValidator;

    @Inject
    private TableQueryService tableQueryService;

    public boolean publishRequestedTable(Map<String, Object> model, List<String> errors, String tidParam, HallDTO hallDTO) {
        if (tableValidator.validateTidParam(tidParam, errors, hallDTO)) {
            TableDTO tableDTO = tableQueryService.findById(Integer.parseInt(tidParam));
            model.put("table", tableDTO);
            return true;
        }
        return false;
    }

    public void publishIsActive(int tid, Map<String, Object> model) {
        if (tableQueryService.isActive(tid)) {
            LOG.info("Requested table with id: {} is active.", tid);
            model.put("isActive", true);
        } else {
            LOG.info("Requested table with id: {} is inactive.", tid);
            model.put("isActive", false);
        }
    }

    public void publishTid(Map<String, Object> model, List<String> errors, String tidParam, HallDTO activeHall) {
        if(tableValidator.validateTidParam(tidParam, errors, activeHall)){
            Integer tid = Integer.valueOf(tidParam);
            model.put("tid", tid);
        }
    }
}