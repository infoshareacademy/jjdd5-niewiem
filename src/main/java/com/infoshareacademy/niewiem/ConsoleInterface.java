package com.infoshareacademy.niewiem;

import com.infoshareacademy.niewiem.factories.Halls;
import com.infoshareacademy.niewiem.factories.Reservations;
import com.infoshareacademy.niewiem.factories.Tables;

import java.time.LocalDateTime;


public class ConsoleInterface {
    private static final String FUNCTIONALITY_UNAVAILABLE = "I'm sorry Dave, I'm afraid I can't do that.";
    private static final String GOODBYE_MESSAGE = "Bye, bye!";

    private ConsoleReader cr;
    private ConsolePrinter cp;
    private Hall hall;

    /**
     * Constructor
     ***************************************************************************************************/

    ConsoleInterface() {
        this.cr = new ConsoleReader();
        this.cp = new ConsolePrinter();
    }

    /**
     * Boot Up
     *******************************************************************************************************/

    public void bootUp() {
        hallMenu();
    }

    /**
     * Hall Menu
     *****************************************************************************************************/

    private void printHallMenu() {
        System.out.println("" +
                "1. Load existing hall\n" +
                "2. Create new hall\n" +
                "0. Exit application");
    }

    private void hallMenu() {
        printHallMenu();
        int choice = cr.enterInt();
        switch (choice) {
            case 0:
                System.out.println(GOODBYE_MESSAGE);
                System.exit(0);
                break;
            case 1:
                System.out.println(FUNCTIONALITY_UNAVAILABLE);
                break;
            case 2:
                this.hall = Halls.create(enterHallName());
                mainMenu();
                break;
            case 88224646:
                devPanelMenu();
                mainMenu();
                break;
            default:
                hallMenu();
                break;
        }
    }

    private String enterHallName() {
        System.out.println("Enter hall's name: ");
        return cr.enterString();
    }

    /**
     * Main Menu
     *****************************************************************************************************/

    private void printMainMenu() {
        System.out.println("" +
                "======================\n" +
                "Hall: " + hall.getName() + "\n" +
                "======================\n" +
                "1. Start/stop game\n" +
                "2. Add reservation\n" +
                "3. Cancel reservation\n" +
                "4. Tables queue\n" +
                "5. Admin panel\n" +
                "0. Exit application\n"
        );
    }

    private void mainMenu() {
        printTables();
        printMainMenu();
        int choice = cr.enterInt();
        switch (choice) {
            case 0:
                System.out.println("Bye, bye!");
                System.exit(0);
                break;
            case 1:
                startOrStopGame();
                break;
            case 2:
                addReservationMenu();
                break;
            case 3:
                cancelReservationMenu();
                break;
            case 4:
                tablesQueueMenu();
                break;
            case 5:
                adminPanelMenu();
                break;
            case 88224646:
                devPanelMenu();
                break;
            default:
                mainMenu();
                break;
        }
    }

    private void printTables() {
        cp.printTables(hall.getAllTablesAndRemainingTimes());
    }

    /**
     * Start Stop Game
     *************************************************************************************************/

    private void startOrStopGame() {
        Table table = chooseTable();
        if (Reservations.tableIsActive(this.hall, table)) {
            stopGameMenu(table);
        } else {
            startGameMenu(table);
        }
        mainMenu();
    }

    private void startGameMenu(Table table) {
        LocalDateTime startTime = LocalDateTime.now();
        int timeSpan = enterTimeSpan();
        if (!Reservations.create(hall, table, startTime, timeSpan, "")) {
            System.out.println("Table is already taken, sorry.");
        }
    }

    private void stopGameMenu(Table table) {
        System.out.println("" +
                "Do you want to stop the game on this table?\n" +
                "1. Yes\n" +
                "2. No");
        int choice = cr.enterInt();
        switch (choice) {
            case 1:
                Reservations.stop(this.hall, table);
                mainMenu();
                break;
            default:
                mainMenu();
                break;
        }
        mainMenu();
    }

    private int enterTimeSpan() {
        System.out.println("Enter time span in minutes:");
        return cr.enterInt();
    }

    private Table chooseTable() {
        while (true) {
            System.out.println("Choose table ID:");
            int tableChoice = cr.enterInt();
            Table table = Tables.getTableByID(hall, tableChoice);
            if (table != null) {
                return table;
            }
            System.out.println("Table with that ID doesn't exist");
        }
    }

    /**
     * Add reservations
     **********************************************************************************************/

    private void addReservationMenu() {
        while (true) {
            Table table = chooseTable();
            LocalDateTime startDateTime = LocalDateTime.now().plusMinutes(1); //todo: enter valid time
            Integer timeSpan = enterTimeSpan();
            String customer = enterCustomerInformation();

            if (Reservations.create(this.hall, table, startDateTime, timeSpan, customer)) {
                break;
            }
            System.out.println("Table is already taken at that time, try again.");
        }
        mainMenu();
    }

    private String enterCustomerInformation() {
        System.out.println("Enter customer information:");
        return cr.enterString();
    }


    /**
     * Cancel Reservation
     ********************************************************************************************/

    private void cancelReservationMenu() {
        System.out.println("Functionality unavailable");
        mainMenu();
    }

    /**
     * Tables Queue
     **************************************************************************************************/

    private void tablesQueueMenu() {
        System.out.println("Functionality unavailable");
        mainMenu();
    }

    /**
     * Admin Panel
     ***************************************************************************************************/

    private void printAdminPanelMenu() {
        System.out.println("" +
                "ADMIN PANEL\n" +
                "1. Add table\n" +
                "2. Freeze table\n" +
                "3. Show frozen tables\n" +
                "4. Unfreeze table\n" +
                "5. Delete table\n" +
                "0. Get back to App Menu");
    }

    private void adminPanelMenu() {
        printAdminPanelMenu();
        int choice = cr.enterInt();
        switch (choice) {
            case 1:
                addTableMenu();
                break;
            case 2:
                printFunctionalityUnavailable();
//                freezeTableMenu();
                break;
            case 3:
                printFunctionalityUnavailable();
//                showFrozenTablesMenu();
                break;
            case 4:
                printFunctionalityUnavailable();
//                unfreezeTableMenu();
                break;
            case 5:
                printFunctionalityUnavailable();
//                deleteTableMenu();
                break;
            default:
                mainMenu();
                break;
        }
    }

    /**
     * Add Table
     *****************************************************************************************************/

    private void addTableMenu() {
        // todo: add choice of TableType and name
        String tableName = chooseTableName();
        TableType tableType = chooseTableType();

        Tables.create(this.hall, tableName, tableType);
        mainMenu();
    }

    private String chooseTableName() {
        Integer nextAvailableTableId = Tables.getNextAvailableId(hall);
        return giveNameBasedOnId(nextAvailableTableId, TableType.POOL);
    }

    private TableType chooseTableType() {
        return TableType.POOL;
    }


    /**
     * Dev Panel
     *****************************************************************************************************/

    private void printDevPanelMenu() {
        System.out.println("" +
                "1. Create DEMO CLUB, 10 tables, no reservations\n" +
                "2. Create DEMO CLUB, 10 tables, all running, no reservations\n" +
                "3. Create DEMO CLUB, 10 tables, 5 running, 5 reserved (in 10m, 30m, 1h, 2h, 5h");
    }

    private void devPanelMenu() {
        printDevPanelMenu();
        int choice = cr.enterInt();
        switch (choice) {
            case 1:
                demoClubTenTables();
                mainMenu();
                break;
            case 2:
                demoClubTenTablesAllBooked();
                mainMenu();
                break;
            default:
                hallMenu();
                break;
        }
        mainMenu();
    }


    private void demoClubTenTables() {
        createDemoHall();
        addTenTables();
    }

    private void demoClubTenTablesAllBooked() {
        createDemoHall();
        addTenTables();
        startAllTenTables();
    }

    private void createDemoHall() {
        this.hall = Halls.create("DEMO CLUB"); //todo: change to load when dataProvider delivers
    }

    private void addTenTables() {
        for (int i = 0; i <= 9; i++) {
            Integer tableID = Tables.getNextAvailableId(hall);
            TableType type = TableType.POOL;
            String tableName = giveNameBasedOnId(tableID, type);
            Tables.load(this.hall, type, tableID, tableName);
        }
    }

    private void startAllTenTables() {
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 1), LocalDateTime.now().minusMinutes(30), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 2), LocalDateTime.now().minusMinutes(10), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 3), LocalDateTime.now().minusMinutes(15), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 4), LocalDateTime.now().minusMinutes(20), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 5), LocalDateTime.now().minusMinutes(10), 120, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 6), LocalDateTime.now().minusMinutes(18), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 7), LocalDateTime.now().minusMinutes(10), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 8), LocalDateTime.now().minusMinutes(0), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 9), LocalDateTime.now().minusMinutes(14), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 10), LocalDateTime.now().minusMinutes(59), 60, "");

    }

    /**
     * Shared functions
     **********************************************************************************************/

    private void printFunctionalityUnavailable() {
        System.out.println(FUNCTIONALITY_UNAVAILABLE);
    }

    private String giveNameBasedOnId(Integer tableID, TableType type) {
        String typeToString = "P";
        String idToString = String.format("%02d", tableID);
        return typeToString + idToString;
    }
}