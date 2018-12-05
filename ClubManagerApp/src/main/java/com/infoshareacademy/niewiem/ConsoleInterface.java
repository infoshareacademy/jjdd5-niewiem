package com.infoshareacademy.niewiem;

import com.infoshareacademy.niewiem.factories.Halls;
import com.infoshareacademy.niewiem.factories.Reservations;
import com.infoshareacademy.niewiem.factories.Tables;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


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
        hallMenuChoice(cr.enterInt());
    }

    private void getHallMenuValue() {
        printWrongValueMessage();
        hallMenuChoice(cr.enterInt());
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
                this.hall = Halls.create(enterHallName());
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
                "3. Show reservations and their options\n" +
                "4. Admin panel\n" +
                "0. Exit application\n"
        );
    }

    private void mainMenu() {
        printTables();
        printMainMenu();
        int choice = cr.enterInt();
        mainMenuChoice(choice);
    }

    private void getMainMenuValue() {
        printWrongValueMessage();
        mainMenuChoice(cr.enterInt());
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
                reservationsMenu();
                break;
            case 4:
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
        cp.printTables(Reservations.getAllTablesAndRemainingTimes(this.hall));
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
        if (choice == 1) {
            Reservations.stop(this.hall, table);
        }
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

        Table table = chooseTable();
        LocalDateTime startDateTime = LocalDateTime.now().plusMinutes(1); //todo: enter valid time
        Integer timeSpan = enterTimeSpan();
        String customer = enterCustomerInformation();

        if (!Reservations.create(this.hall, table, startDateTime, timeSpan, customer)) {
            System.out.println("Table is already taken at that time, sorry.");
        }
        mainMenu();
    }

    private void insertDataForReservation(Table table, LocalDateTime startDateTime, Integer timeSpan, String customer) {
        //todo: show available tables
        System.out.println("Enter table number:");
        int tableNumber = cr.enterInt();
    }

    private String enterCustomerInformation() {
        System.out.println("Enter customer information:");
        return cr.enterString();
    }


    /**
     * Reservations options
     ********************************************************************************************/

    private void printOptionsForReservations() {
        System.out.println("" +
                "=======================================\n" +
                "1. Add new reservation\n" +
                "2. Cancel reservation from the list\n" +
                "3. Show fastest available tables\n" +
                "4. Show all reservations\n" +
                "5. Show only upcoming reservations\n" +
                "6. Show reservations for only one table\n" +
                "7. Show history\n" +
                "8. Show history for specific table\n" +
                "0. Exit to main menu");
        System.out.println("Enter time span in minutes:");
        int timeSpan = getTimeSpan();
    }

    private LocalDate getStartDate() {
        LocalDate input = cr.enterDate();
        if (input.isAfter(LocalDate.now()) || input.equals(LocalDate.now())) {
            return input;
        }
        printWrongValueMessage();
        return getStartDate();
    }

    private LocalTime getStartTime(LocalDate startDate) {
        LocalTime input = cr.enterTime();
        if (startDate.isEqual(LocalDate.now())
                && (input.equals(LocalTime.now()) || input.isBefore(LocalTime.now()))
        ) {
            printWrongValueMessage();
            return getStartTime(startDate);
        }
        return input;
    }

    private Integer getTimeSpan() {
        int input = cr.enterInt();
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
    }

    private void reservationsSwitch(List<Reservation> reservations) {
        int choice = cr.enterInt();
        switch (choice) {
            case 1:
                addReservationMenu();
                break;
            case 2:
                cancelReservation(reservations);
                break;
            case 3:
                showFastestAvailableTables();
                break;
            case 4:
                reservationsMenu();
                break;
            case 5:
                showOnlyUpcomingReservations();
                break;
            case 6:
                showReservationsForSpecificTable();
                break;
            case 7:
                showPastReservations();
                break;
            case 8:
                showPastReservationsForSpecificTable();
                break;
            default:
                break;
        }
        mainMenu();
    }

    private void reservationsMenu() {
        List<Reservation> reservations = cp.printListOfReservationsSortedByTableName(this.hall);
        printOptionsForReservations();
        reservationsSwitch(reservations);
    }

    private void showPastReservations() {
        cp.printPastReservations(hall);
        System.out.println("Press enter to continue...");
        cr.enterString();
        reservationsMenu();
    }

    private void showPastReservationsForSpecificTable() {
        Table table = chooseTable();
        cp.showPastReservationsForSpecificTable(hall, table);
        System.out.println("Press enter to continue...");
        cr.enterString();
        reservationsMenu();
    }


    private void showReservationsForSpecificTable() {
        Table table = chooseTable();
        List<Reservation> reservations = cp.printReservationForSpecificTable(hall, table);
        printOptionsForReservations();
        reservationsSwitch(reservations);
    }

    private void showOnlyUpcomingReservations() {
        List<Reservation> reservations = cp.showOnlyUpcomingReservations(this.hall);
        printOptionsForReservations();
        reservationsSwitch(reservations);
    }

    private void showFastestAvailableTables() {
        cp.printFastestAvailableTables(hall);
        System.out.println("Press enter to continue...");
        cr.enterString();
        reservationsMenu();
    }

    private void cancelReservation(List<Reservation> reservations) {
        System.out.println("Choose number by the reservation you want to cancel:");
        Integer choice = cr.enterInt();
        Reservation reservation = reservations.get(choice - 1);
        if (reservation.isInProgress()) {
            Reservations.stop(hall, reservation);
        } else {
            Reservations.cancel(hall, reservation);
        }
    }

    /**
     * Admin Panel
     ***************************************************************************************************/

    private void printAdminPanelMenu() {
        System.out.println("" +
                "ADMIN PANEL\n" +
                "1. Add table\n" +
                "2. Remove table\n" +
                "0. Get back to App Menu");
    }

    private void adminPanelMenu() {
        printAdminPanelMenu();
        adminPanelChoice(cr.enterInt());
    }

    private void getAdminPanelValue() {
        printWrongValueMessage();
        adminPanelChoice(cr.enterInt());
    }

    private void adminPanelChoice(int choice) {
        switch (choice) {
            case 1:
                addTableMenu();
                break;
            case 2:
                removeTableMenu();
                adminPanelMenu();
                break;
            case 0:
                mainMenu();
            default:
                getAdminPanelValue();
                break;
        }
    }

    /**
     * Remove Table
     *****************************************************************************************************/

    private void removeTableMenu() {
        Table table = chooseTable();
        Tables.remove(hall, table);
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

    private void add7running9withReservationsAndHistory1Free() {
        // Seven running tables
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 1), LocalDateTime.now().minusMinutes(30), 60, "");
        //move this
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 1), LocalDateTime.now().plusMinutes(95), 60, "");

        Reservations.load(this.hall, Tables.getTableByID(this.hall, 2), LocalDateTime.now().minusMinutes(10), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 3), LocalDateTime.now().minusMinutes(15), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 4), LocalDateTime.now().minusMinutes(20), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 5), LocalDateTime.now().minusMinutes(10), 120, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 6), LocalDateTime.now().minusMinutes(18), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 7), LocalDateTime.now().minusMinutes(10), 60, "");
        // History
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 1), LocalDateTime.now().minusMinutes(100), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 1), LocalDateTime.now().minusMinutes(170), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 1), LocalDateTime.now().minusMinutes(240), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 1), LocalDateTime.now().minusMinutes(350), 60, "");

        Reservations.load(this.hall, Tables.getTableByID(this.hall, 2), LocalDateTime.now().minusMinutes(80), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 2), LocalDateTime.now().minusMinutes(210), 60, "");

        Reservations.load(this.hall, Tables.getTableByID(this.hall, 3), LocalDateTime.now().minusMinutes(150), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 3), LocalDateTime.now().minusMinutes(230), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 3), LocalDateTime.now().minusMinutes(300), 60, "");

        Reservations.load(this.hall, Tables.getTableByID(this.hall, 5), LocalDateTime.now().minusMinutes(135), 120, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 5), LocalDateTime.now().minusMinutes(260), 120, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 5), LocalDateTime.now().minusMinutes(385), 120, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 5), LocalDateTime.now().minusMinutes(510), 120, "");

        Reservations.load(this.hall, Tables.getTableByID(this.hall, 6), LocalDateTime.now().minusMinutes(80), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 6), LocalDateTime.now().minusMinutes(142), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 6), LocalDateTime.now().minusMinutes(204), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 6), LocalDateTime.now().minusMinutes(266), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 6), LocalDateTime.now().minusMinutes(337), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 6), LocalDateTime.now().minusMinutes(399), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 6), LocalDateTime.now().minusMinutes(461), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 6), LocalDateTime.now().minusMinutes(522), 60, "");

        Reservations.load(this.hall, Tables.getTableByID(this.hall, 7), LocalDateTime.now().minusMinutes(85), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 7), LocalDateTime.now().minusMinutes(310), 60, "");

        Reservations.load(this.hall, Tables.getTableByID(this.hall, 8), LocalDateTime.now().minusMinutes(80), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 8), LocalDateTime.now().minusMinutes(180), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 8), LocalDateTime.now().minusMinutes(280), 60, "");

        Reservations.load(this.hall, Tables.getTableByID(this.hall, 9), LocalDateTime.now().minusMinutes(94), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 9), LocalDateTime.now().minusMinutes(194), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 9), LocalDateTime.now().minusMinutes(255), 60, "");
        // Reservations

        Reservations.load(this.hall, Tables.getTableByID(this.hall, 1), LocalDateTime.now().plusMinutes(160), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 1), LocalDateTime.now().plusMinutes(230), 60, "");

        Reservations.load(this.hall, Tables.getTableByID(this.hall, 2), LocalDateTime.now().plusMinutes(60), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 2), LocalDateTime.now().plusMinutes(125), 60, "");

        Reservations.load(this.hall, Tables.getTableByID(this.hall, 3), LocalDateTime.now().plusMinutes(50), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 3), LocalDateTime.now().plusMinutes(120), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 3), LocalDateTime.now().plusMinutes(190), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 3), LocalDateTime.now().plusMinutes(255), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 3), LocalDateTime.now().plusMinutes(320), 60, "");

        Reservations.load(this.hall, Tables.getTableByID(this.hall, 4), LocalDateTime.now().plusMinutes(50), 60, "");

        Reservations.load(this.hall, Tables.getTableByID(this.hall, 5), LocalDateTime.now().plusMinutes(120), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 5), LocalDateTime.now().plusMinutes(190), 120, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 5), LocalDateTime.now().plusMinutes(320), 60, "");

        Reservations.load(this.hall, Tables.getTableByID(this.hall, 6), LocalDateTime.now().plusMinutes(50), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 6), LocalDateTime.now().plusMinutes(115), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 6), LocalDateTime.now().plusMinutes(178), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 6), LocalDateTime.now().plusMinutes(242), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 6), LocalDateTime.now().plusMinutes(310), 60, "");

        Reservations.load(this.hall, Tables.getTableByID(this.hall, 7), LocalDateTime.now().plusMinutes(200), 60, "");

        Reservations.load(this.hall, Tables.getTableByID(this.hall, 8), LocalDateTime.now().plusMinutes(10), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 8), LocalDateTime.now().plusMinutes(200), 60, "");

        Reservations.load(this.hall, Tables.getTableByID(this.hall, 9), LocalDateTime.now().plusMinutes(200), 60, "");
    }

    private void startOddTables() {
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 1), LocalDateTime.now().minusMinutes(10), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 3), LocalDateTime.now().minusMinutes(15), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 5), LocalDateTime.now().minusMinutes(20), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 7), LocalDateTime.now().minusMinutes(10), 120, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 9), LocalDateTime.now().minusMinutes(18), 60, "");
    }

    private void reserveEvenTables() {
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 2), LocalDateTime.now().plusMinutes(10), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 4), LocalDateTime.now().plusMinutes(30), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 6), LocalDateTime.now().plusMinutes(60), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 8), LocalDateTime.now().plusMinutes(120), 60, "");
        Reservations.load(this.hall, Tables.getTableByID(this.hall, 10), LocalDateTime.now().plusMinutes(360), 60, "");
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