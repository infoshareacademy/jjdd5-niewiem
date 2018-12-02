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
        LocalDateTime startTime = LocalDateTime.now();

        while (true) {
            Table table = chooseTable();
            int timeSpan = enterTimeSpan();
            if (Reservations.create(hall, table, startTime, timeSpan, "")) {
                break;
            }
            System.out.println("Table is already taken, try again.");
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

    private void devPanelMenu() {
        System.out.println("Adding new hall...");
        this.hall = Halls.create("DEMO CLUB"); // todo: new hall should not be saved to file!
        for (int i = 0; i < 9; i++) {
            System.out.printf("Adding table P%d...\n", (i + 1));
            Integer tableID = Tables.getNextAvailableId(hall);
            TableType type = TableType.POOL;
            String tableName = giveNameBasedOnId(tableID, type);
            Tables.load(hall, type, tableID, tableName); // todo: automatically add table
        }
        mainMenu();
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