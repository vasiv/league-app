package pl.kielce.tu.action;

import org.apache.commons.lang3.StringUtils;
import pl.kielce.tu.model.Stadium;
import pl.kielce.tu.repository.StadiumRepository;
import pl.kielce.tu.util.ViewUtil;

import java.util.Scanner;

/**
 * @author ciepluchs
 */
public class ModifyStadium implements Action {

    private static final String HEADER = "################################################ MODIFY  ################################################";
    private static final String DISPLAY_NAME = "Modify";

    private StadiumRepository repository;
    private Stadium stadium;

    public ModifyStadium(StadiumRepository repository, Stadium stadium) {
        this.repository = repository;
        this.stadium = stadium;
    }

    @Override
    public void execute() {
        System.out.println(HEADER);
        System.out.println(ViewUtil.getDetailedView(stadium));
        System.out.println("-".repeat(105) + "\n");
        Stadium modifiedStadium = getModifiedStadium(stadium);
        repository.createOrUpdate(modifiedStadium);
        ViewUtil.cls();
        System.out.println("Stadium modified!");
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    private Stadium getModifiedStadium(Stadium stadium) {
        Scanner input = new Scanner(System.in);

        System.out.println("What is new owner: (press ENTER to make no change)");
        String owner = input.nextLine();
        if (StringUtils.isNotEmpty(owner)) {
            stadium.setOwnerClub(owner);
            System.out.println("\n");
        }

        System.out.println("What is new city (press ENTER to make no change)");
        String city = input.nextLine();
        if (StringUtils.isNotEmpty(city)) {
            stadium.setCity(city);
            System.out.println("\n");
        }


        System.out.println("What is new capacity: (press ENTER to make no change)");
        String capacity = input.nextLine();
        if (StringUtils.isNotEmpty(capacity)) {
            stadium.setCapacity(Integer.valueOf(capacity));
            System.out.println("\n");
        }

        System.out.println("Is covered [y/s]: (press ENTER to make no change)");
        String isCovered = input.nextLine();
        if (StringUtils.isNotEmpty(isCovered)) {
            if (isCovered.equalsIgnoreCase("y")) {
                stadium.setGrandstandCovered(true);
            } else {
                stadium.setGrandstandCovered(false);
            }
            System.out.println("\n");
        }
        return stadium;
    }
}
