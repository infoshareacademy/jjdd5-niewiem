public class ConsoleInterface {
    private ConsoleReader cr;
    private Hall hall;

    ConsoleInterface(){
        this.cr = new ConsoleReader();
        this.hall = new Hall();
    }

    public void bootup() {
        hall.bootup("Green Club");
        mainMenu();
    }

    private void printMainMenu(){
        System.out.println(
                "Hall: " + hall.getName() + "\n" +
                "1. Start game\n" +
                "2. Stop game\n" +
                "3. Add reservation\n" +
                "4. Remove reservation\n" +
                "5. Add table\n" +
                "6. Remove table\n" +
                "7. Switch club\n" +
                "0. Exit programme\n"
        );
    }

    private void mainMenu(){
        printMainMenu();


    }

    // ask questions:
    // what do you want to do?
    // Switch -  start game, stop game, add reservation, rm reservation, add table, rm table
    // Functionality - login as admin allows you to add, rm tables
    //              user only starts tables and makes reservations
}
