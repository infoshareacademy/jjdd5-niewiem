package com.infoshareacademy.niewiem;

import com.infoshareacademy.niewiem.factories.Halls;
import com.infoshareacademy.niewiem.factories.Reservations;
import com.infoshareacademy.niewiem.factories.Tables;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


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
        hallMenuChoice(cr.readInt());
    }

    private void getHallMenuValue() {
        printWrongValueMessage();
        hallMenuChoice(cr.readInt());
    }

    private void hallMenuChoice(int choice) {
        switch (choice) {
            case 0:
                System.out.println(GOODBYE_MESSAGE);
                System.exit(0);
                break;
            case 1:
                System.out.println(FUNCTIONALITY_UNAVAILABLE);
                break;
            case 2:
                this.hall = Halls.create(readHallName());
                mainMenu();
                break;
            case 88224646:
                devPanelMenu();
                mainMenu();
                break;
            default:
                getHallMenuValue();
                break;
        }
    }

    private String readHallName() {
        System.out.println("Enter hall's name: ");
        return cr.readString();
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
        mainMenuChoice(cr.readInt());
    }

    private void getMainMenuValue() {
        printWrongValueMessage();
        mainMenuChoice(cr.readInt());
    }

    private void mainMenuChoice(int choice) {
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
                getMainMenuValue();
                break;
        }
    }

    private void printTables() {
        cp.printTables(hall.getAllTablesAndRemainingTimes());
    }

    /**
     * Choose tables
     *************************************************************************************************/

    private void startOrStopGame() {
        chooseTable();

        //todo: isAvailable() - check if table has a currently started game

        System.out.println("Enter time span in minutes:");
        int timeSpan = cr.readInt();

//        Reservations.create(tableChoice, timeSpan);

        mainMenu();
    }

    private void chooseTable() {
        System.out.println("Choose table ID:");
        int tableChoice = cr.readInt(); // todo: check what name table is given when created
        // todo: Tables.get()
    }

    /**
     * Add reservations
     **********************************************************************************************/

    private void addReservationMenu() {
        Table tableToBeReserved = null;
        LocalDateTime startDateTime = null;
        Integer timeSpan = 0;
        String customer = "";
        insertDataForReservation(tableToBeReserved, startDateTime, timeSpan, customer);

        Reservations.create(this.hall, tableToBeReserved, startDateTime, timeSpan, customer);

        mainMenu();
    }

    private void insertDataForReservation(Table table, LocalDateTime startDateTime, Integer timeSpan, String customer) {
        //todo: show available tables
        System.out.println("Enter table number:");
        int tableNumber = cr.readInt();

        System.out.println("Choose a date within a week of today (YYYY-MM-DD):");
        LocalDate startDate = getStartDate();
        System.out.println("Choose time between 12:00 and 23:00 (HH:MM):");
        LocalTime startTime = getStartTime(startDate);


        System.out.println("Enter time span in minutes:");
        timeSpan = getTimeSpan();
    }

    private LocalDate getStartDate() {
        LocalDate input = cr.readDate();
        if (input.isAfter(LocalDate.now()) || input.equals(LocalDate.now())) {
            return input;
        }
        printWrongValueMessage();
        return getStartDate();
    }

    private LocalTime getStartTime(LocalDate startDate) {
        LocalTime input = cr.readTime();
        if (startDate.isEqual(LocalDate.now())
                && (input.equals(LocalTime.now()) || input.isBefore(LocalTime.now()))
        ) {
            printWrongValueMessage();
            return getStartTime(startDate);
        }
        return input;
    }

    private Integer getTimeSpan() {
        int input = cr.readInt();
        if (input <= 0) {
            printWrongValueMessage();
            return getTimeSpan();
        }
        return input;
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
        adminPanelChoice(cr.readInt());
    }

    private void getAdminPanelValue() {
        printWrongValueMessage();
        adminPanelChoice(cr.readInt());
    }

    private void adminPanelChoice(int choice) {
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
                getAdminPanelValue();
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

    private void printWrongValueMessage() {
        System.out.println("Enter correct value: ");
    }
}