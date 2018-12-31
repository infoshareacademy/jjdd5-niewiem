package com.infoshareacademy.niewiem.tables.publishers;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.tables.dto.TableDTO;
import com.infoshareacademy.niewiem.tables.services.TableQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Stateless
public class TablesListPublisher {
    private static final Logger LOG = LoggerFactory.getLogger(TablesListPublisher.class);

    @Inject
    private TableQueryService tableQueryService;

    public void publishTablesInHall(Map<String, Object> model, HallDTO hallDTO){
        List<TableDTO> tables = tableQueryService.findAllByHall(hallDTO);
        model.put("tables", tables);
    }
}