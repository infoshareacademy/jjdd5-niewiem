package com.infoshareacademy.niewiem.tables.validators;

import com.infoshareacademy.niewiem.enums.TableType;
import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.shared.validators.GenericValidator;
import com.infoshareacademy.niewiem.tables.services.TableQueryService;
import org.apache.commons.lang3.StringUtils;
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

    public boolean validateTypeParam(String typeParam, List<String> errors) {
        if (StringUtils.isEmpty(typeParam)) {
            LOG.info("No table type parameter in request.");
            return false;
        }

        return validateTableTypeExists(typeParam, errors);
    }

    public boolean validateTidParam(String tidParam, List<String> errors, HallDTO hallDTO) {
        if (StringUtils.isEmpty(tidParam)) {
            LOG.info("No table id parameter in request.");
            return false;
        }
        if (validateIsNotNumeric(tidParam, "table ID", errors)) {
            return false;
        }

        Integer tid = Integer.parseInt(tidParam);
        if (validateTableIdDoesNotExists(tid, errors)) {
            return false;
        }
        if (validateTableIdDoesNotExistInActiveHallId(tid, hallDTO.getId(), errors)) {
            return false;
        }

        return true;
    }

    private boolean validateTableIdExists(Integer tid, List<String> errors) {
        if (tableQueryService.doesExist(tid)) {
            LOG.debug("Table ID was found in database. ({})", tid);
            return true;
        }
        LOG.warn("Table ID not found in database. ({})", tid);
        errors.add("Requested table was not found in database.");
        return false;
    }

    private boolean validateTableIdDoesNotExists(Integer tid, List<String> errors) {
        return !validateTableIdExists(tid, errors);
    }

    private boolean validateTableIdExistsInActiveHallId(Integer tid, Integer activeHid, List<String> errors) {
        Integer tableHid = tableQueryService.findById(tid).getHall().getId();
        if (tableHid.equals(activeHid)) {
            LOG.debug("Table's hall matches active hall. Table hid: {}, Active hid: {}", tableHid, activeHid);
            return true;
        }
        LOG.warn("Table's hall does not matches active hall. Table hid: {}, Active hid: {}", tableHid, activeHid);
        errors.add("Requested table was not found in the active hall.");
        return false;
    }

    private boolean validateTableIdDoesNotExistInActiveHallId(Integer tid, Integer activeHid, List<String> errors) {
        return !validateTableIdExistsInActiveHallId(tid, activeHid, errors);
    }

    private boolean validateTableTypeExists(String typeParam, List<String> errors) {
        for (TableType t : TableType.values()) {
            if (t.name().equalsIgnoreCase(typeParam)) {
                LOG.debug("Table type exists. ({})", typeParam);
                return true;
            }
        }
        LOG.warn("Requested table type does not exist. ({})", typeParam);
        errors.add("Requested table type does not exist.");
        return false;
    }
}