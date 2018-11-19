public class ConsoleInterface {
    private ConsoleReader cr;
    private ConsolePrinter cp;
    private Hall hall;

    ConsoleInterface(){
        this.cr = new ConsoleReader();
        this.hall = new Hall();
        this.cp = new ConsolePrinter();
    }

    public void bootup() {
        hall.bootup("Green Club");
        cp.printTables();
        cp.printMessage("MainMenu", hall.getName());
    }


    // ask questions:
    // what do you want to do?
    // Switch -  start game, stop game, add reservation, rm reservation, add table, rm table
    // Functionality - login as admin allows you to add, rm tables
    //              user only starts tables and makes reservations
}
