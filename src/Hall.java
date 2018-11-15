import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Hall {
    private String name;
    private List<Table> tableList;
    private Map<Table, Boolean> activeTables; // shows list of tables,

    // log - id, table, timeStamp, timeSpan

    private boolean loadHall(){
        // load tables
        // load file with currently relevant time logs (like what tables are currently in use)
        return false;
    }


    private boolean startGame(Table table, LocalDateTime startingTime, int timeSpan){
        // first check if active
        // than check if reserved in the timeSpan
        // if table not on a list - return false
        return false;
    }

    private boolean reserveTable(Table table, LocalDateTime startingTime, int timeSpan){
        // check if one of currently active table is not in conflict
        // check if the table is taken at that time - read reservations from a file,
        //      filter reservations to only possible by only possible conflicts
        return false;
    }

    private boolean loadTables(){
        // check if tableLIst is empty (if not => ??)
        // loads tables from a file
        // use addTable
        return false;
    }

    private boolean addTable(Table table){
        // adds table to a list
        // saves it to a file in a currently open hall
        // check if table doesn't currently exist
        return false;
    }

    private boolean rmTable(Table table){
        // rm table
        // check if is on the list
        // check if is active, if active => ??
        //                          - rm with no other action
        //                          - don't remove, exception
        //                          - rm and cut timeSpan in log? (timeSpan = currentTime - startingTime)
        return false;
    }

    private void activeTablesHandler(){
        // if new log, add a table to active map
    }

    // some getter that gives printer data on what to print
    // can't be getTable
}
