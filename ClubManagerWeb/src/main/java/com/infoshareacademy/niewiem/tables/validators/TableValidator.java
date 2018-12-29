package com.infoshareacademy.niewiem.tables.validators;

import com.infoshareacademy.niewiem.shared.Validators.GenericValidator;
import com.infoshareacademy.niewiem.tables.services.TableQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TableValidator extends GenericValidator {
    private static final Logger LOG = LoggerFactory.getLogger(TableValidator.class);

    @Inject
    private TableQueryService tableQueryService;

    public boolean validateTableIdExists(Integer tid, List<String> errors){
        if(tableQueryService.doesExist(tid)){
            LOG.debug("Table ID was found in database. ({})", tid);
            return true;
        }
        LOG.warn("Table ID not found in database. ({})", tid);
        errors.add("Requested table was not found in database.");
        return false;
    }

    public boolean validateTableIdDoesNotExists(Integer tid, List<String> errors) {
        return !validateTableIdExists(tid, errors);
    }

    public boolean validateTableIdExistsInActiveHallId(Integer tid, Integer activeHid, List<String> errors) {
        Integer tableHid = tableQueryService.findById(tid).getHall().getId();
        if(tableHid.equals(activeHid)){
            LOG.debug("Table's hall matches active hall. Table hid: {}, Active hid: {}", tableHid, activeHid);
            return true;
        }
        LOG.warn("Table's hall does not matches active hall. Table hid: {}, Active hid: {}", tableHid, activeHid);
        errors.add("Requested table was not found in the active hall.");
        return false;
    }

    public boolean validateTableIdDoesNotExistInActiveHallId(Integer tid, Integer activeHid, List<String> errors){
        return !validateTableIdExistsInActiveHallId(tid, activeHid, errors);
    }
}
