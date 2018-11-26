package com.infoshareacademy.niewiem;

public class ConsoleInterface {
    private static final String FUNCTIONALITY_UNAVAILABLE = "I'm sorry Dave, I'm afraid I can't do that.";
    private static final String GOODBYE_MESSAGE = "Bye, bye!";

    private ConsoleReader cr;
    private ConsolePrinter cp;
    private Hall hall;

    /*********** CONSTRUCTOR *************************/

    ConsoleInterface() {
        this.cr = new ConsoleReader();
        this.cp = new ConsolePrinter();
    }

    /*********** BOOTUP *************************/

    public void bootup() {
        hallMenu();

    }

    /*********** HALL MENU *************************/

    private void printHallMenu(){
        System.out.println("" +
                "1. Load existing hall\n" +
                "2. Create new hall\n" +
                "0. Exit application");
    }

    private void hallMenu(){
        printHallMenu();
        int choice = cr.readInt();
        switch (choice) {
            case 0:
                System.out.println(GOODBYE_MESSAGE);
                System.exit(0);
                break;
            case 1:
                System.out.println(FUNCTIONALITY_UNAVAILABLE);
                break;
            case 2:
                this.hall = new Hall(readHallName());
                mainMenu();
                break;
            default:
                hallMenu();
                break;
        }
    }

    private String readHallName(){
        System.out.println("Insert hall's name: ");
        return cr.readString();
    }

    /*********** MAIN MENU *************************/

    private void printMainMenu() {
        System.out.println("" +
                "======================\n" +
                "Hall: " + hall.getName() + "\n" +
                "======================\n" +
                "1. Choose table\n" +
                "2. Add reservation\n" +
                "3. Cancel reservation\n" +
                "4. Tables queue\n" +
                "5. Admin panel\n" +
                "0. Exit application\n"
        );
    }

    private void mainMenu() {
        printMainMenu();
        int choice = cr.readInt();
        switch (choice) {
            case 0:
                System.out.println("Bye, bye!");
                System.exit(0);
                break;
            case 1:
                chooseTableMenu();
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

    /*********** CHOOSE TABLE *************************/

    private void chooseTableMenu() {
        System.out.println("Choose table:");
        int choice = cr.readInt();
        // check if table exists todo: needs a list of tables
        // check if table is inactive
        //      if active - ask to stop or move
        //      if inactive - ask for time-span
        mainMenu();
    }

    /*********** ADD RESERVATION *************************/

    private void addReservationMenu() {
        System.out.println("Functionality unavailable");
        mainMenu();
    }

    /*********** CANCEL RESERVATION *************************/

    private void cancelReservationMenu() {
        System.out.println("Functionality unavailable");
        mainMenu();
    }

    /*********** TABLES QUEUE - MENU *************************/

    private void tablesQueueMenu() {
        System.out.println("Functionality unavailable");
        mainMenu();
    }

    /*********** ADMIN PANEL - MENU *************************/

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
        int choice = cr.readInt();
//        switch (choice) {
//            case 1:
//                addTableMenu();
//                break;
//            case 2:
//                freezeTableMenu();
//                break;
//            case 3:
//                showFrozenTablesMenu();
//                break;
//            case 4:
//                unfreezeTableMenu();
//                break;
//            case 5:
//                deleteTableMenu();
//                break;
//            default:
//                mainMenu();
//                break;
//        }
    }

    /*********** DEV PANEL - MENU *************************/

    private void devPanelMenu() {
        System.out.println("Functionality unavailable");
        mainMenu();
    }

    // add X tables
}
