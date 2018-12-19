package com.infoshareacademy.niewiem.shared.sevlets;

import com.infoshareacademy.niewiem.pojo.Table;
import com.infoshareacademy.niewiem.tables.services.TableQueryService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.PrintWriter;

@Transactional
@WebServlet("test-query")
public class TestQueryServlet extends HttpServlet {

    @Inject
    private TableQueryService tableQueryService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        writer.println("Hello World!");

        Table table = tableQueryService.findById(1);
//        Reservation query = reservationQueryService.findActiveForTable(table);
        Boolean active = tableQueryService.isActive(table);

        writer.println("\nACTIVE====================");
        writer.println(table + "\n");
//        writer.println(query + "\n");
        writer.println(active + "\n");

        table = tableQueryService.findById(4);
//        query = reservationQueryService.findActiveForTable(table);
        active = tableQueryService.isActive(table);

        writer.println("\nINACTIVE====================");
        writer.println(table + "\n");
//        writer.println(query + "\n");
        writer.println(active + "\n");

    }
}
