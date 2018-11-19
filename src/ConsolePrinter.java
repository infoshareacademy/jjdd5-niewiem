public class ConsolePrinter {
    // print me the world, like you print your French girls
    // print all tables every second
    // shows options to choose from ConsoleInterface

    public void printTable(){
        System.out.println("+-------+");
        System.out.println("|  P01  |");
        System.out.println("|       |");
        System.out.println("|1:30:02|");
        System.out.println("|       |");
        System.out.println("|       |");
        System.out.println("+-------+");

    }
    public void printTables(){
        System.out.println("+-------+ +-------+ +-------+ +-------+");
        System.out.println("|  P01  | |  P02  | |  P03  | |  P04  |");
        System.out.println("|       | |       | |       | |       |");
        System.out.println("|1:30:02| |       | | 30:00 | |2:18:13|");
        System.out.println("|       | |       | |       | |       |");
        System.out.println("|       | |       | |       | |       |");
        System.out.println("+-------+ +-------+ +-------+ +-------+");
    }

    public void printMessage(String title, String hallName){
        System.out.println(
                       "======================\n" +
                        "Hall: " + hallName.toUpperCase() + "\n" +
                        "======================\n" +
                        "1. Choose table\n" +
                        "2. Add reservation\n" +
                        "3. Cancel reservation\n" +
                        "4. Tables queue\n" +
                        "5. Admin panel\n" +
                        "0. Exit application\n"
        );
    }
}
