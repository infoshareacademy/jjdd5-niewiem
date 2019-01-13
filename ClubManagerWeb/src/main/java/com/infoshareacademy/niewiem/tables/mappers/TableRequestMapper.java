package com.infoshareacademy.niewiem.tables.mappers;

import com.infoshareacademy.niewiem.domain.Hall;
import com.infoshareacademy.niewiem.domain.Table;
import com.infoshareacademy.niewiem.enums.TableType;
import com.infoshareacademy.niewiem.halls.dao.HallDao;
import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.reservations.mappers.ReservationRequestMapper;
import com.infoshareacademy.niewiem.tables.validators.TableValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Stateless
public class TableRequestMapper {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationRequestMapper.class);

    @Inject
    private TableValidator tableValidator;

    @Inject
    private HallDao hallDao;

    public Table getTableWithoutId(HttpServletRequest req, List<String> errors, HallDTO activeHall) {
        Table table = new Table();

        String typeParam = req.getParameter("type");
        String nameParam = req.getParameter("name");

        Hall hall = hallDao.findById(activeHall.getId());

        if (!tableValidator.validateTypeParam(typeParam, errors) ||
                tableValidator.validateStringIsEmpty(nameParam, "Table's name", errors)) {
            return null;
        }
        TableType type = TableType.valueOf(typeParam.toUpperCase());

        table.setName(nameParam);
        table.setType(type);
        table.setHall(hall);

        return table;
    }
}
