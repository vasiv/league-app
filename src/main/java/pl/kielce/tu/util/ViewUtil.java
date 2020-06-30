package pl.kielce.tu.util;

import org.apache.commons.lang3.StringUtils;
import pl.kielce.tu.action.Action;
import pl.kielce.tu.action.AddStadium;
import pl.kielce.tu.action.FindStadium;
import pl.kielce.tu.action.ShowAllStadiums;
import pl.kielce.tu.model.Stadium;
import pl.kielce.tu.repository.StadiumRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author ciepluchs
 */
public abstract class ViewUtil {

    private static final String NEXT_LINE = "\n";
    private static final String SPACE_DELIMITER = " ";
    private static final int COLUMN_WIDTH = 32;
    private static final int NUMBER_OF_COLUMNS = 4;
    private static final String BACK_SYMBOL = "<-";
    private static final String BACK = "back";
    private static final String QUIT = "quit";

    private ViewUtil() {
    }

    public static void cls() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception E) {
            System.out.println(E);
        }
    }

    public static void displayMainMenu(StadiumRepository stadiumRepository) {
        List<Action> mainMenuActions = new ArrayList<>();
        mainMenuActions.add(new AddStadium(stadiumRepository));
        mainMenuActions.add(new ShowAllStadiums(stadiumRepository));
        mainMenuActions.add(new FindStadium(stadiumRepository));
        ViewUtil.cls();
        while (true) {
            System.out.println("################################################ STADIUMS ###############################################");
            int i = 0;
            for (Action action : mainMenuActions) {
                System.out.println(++i + ") " + action.getDisplayName());
            }
            System.out.println("What do you want to do: ");
            Scanner scanner = new Scanner(System.in);
            String operation = scanner.nextLine();
            if (QUIT.equals(operation)) {
                return;
            }
            int nextOperation = Integer.parseInt(operation) - 1;
            mainMenuActions.get(nextOperation).execute();
        }
    }

    public static void displaySubMenu(List<Action> availableActions) {
        System.out.println("What you want to do: ");
        int i = 0;
        for (Action action : availableActions) {
            System.out.println(++i + ") " + action.getDisplayName());
        }
        System.out.println("<- back");
    }

    public static boolean isBackOptionSelected(String selectedOption) {
        return BACK_SYMBOL.equals(selectedOption) || BACK.equals(selectedOption);
    }

    public static String getSelectedOption() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    public static String getTable(List<Stadium> stadiums) {
        String tableHeader = getTableHeader();
        String tableContent = getTableContent(stadiums);
        return tableHeader + "\n" + tableContent;
    }

    private static String getTableContent(List<Stadium> stadiums) {
        String tableContent = StringUtils.EMPTY;
        for (Stadium stadium : stadiums) {
            StringBuilder sb = new StringBuilder();
            String owner = stadium.getOwnerClub();
            String city = stadium.getCity();
            String capacity = stadium.getCapacity().toString();
            String isGrandstandCovered = "no";
            if (stadium.isGrandstandCovered()) {
                isGrandstandCovered = "yes";
            }
            sb.append(stadiums.indexOf(stadium) + 1);
            sb.append(SPACE_DELIMITER.repeat(9 - sb.length()));
            sb.append(owner).append(SPACE_DELIMITER.repeat((COLUMN_WIDTH + 9) - sb.length()));
            sb.append(city);
            sb.append(SPACE_DELIMITER.repeat((2 * COLUMN_WIDTH + 9) - sb.length()));
            sb.append(capacity);
            sb.append(SPACE_DELIMITER.repeat((3 * COLUMN_WIDTH + 9) - sb.length()));
            sb.append(isGrandstandCovered);
            sb.append(NEXT_LINE);
            tableContent += sb.toString();
        }
        return tableContent;
    }

    private static String getTableHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("--------|");
        sb.append("-------------------------------|".repeat(NUMBER_OF_COLUMNS) + "\n");
        sb.append("   No.  |");
        sb.append("              TEAM             |");
        sb.append("              CITY             |");
        sb.append("            CAPACITY           |");
        sb.append("       GRANDSTAND COVERED      |\n");
        sb.append("--------|");
        sb.append("-------------------------------|".repeat(NUMBER_OF_COLUMNS) + "\n");
        return sb.toString();
    }

    public static String getDetailedView(Stadium stadium) {
        StringBuilder sb = new StringBuilder();
        sb.append("Team               : ").append(stadium.getOwnerClub()).append(NEXT_LINE);
        sb.append("City               : ").append(stadium.getCity()).append(NEXT_LINE);
        sb.append("Capacity           : ").append(stadium.getCapacity()).append(NEXT_LINE);
        if (stadium.isGrandstandCovered()) {
            sb.append("Grandstand covered : yes").append(NEXT_LINE);
        } else {
            sb.append("Grandstand covered : no").append(NEXT_LINE);
        }
        return sb.toString();
    }
}
