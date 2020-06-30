package pl.kielce.tu.action;

import org.apache.commons.lang3.StringUtils;
import pl.kielce.tu.model.Stadium;
import pl.kielce.tu.repository.StadiumRepository;
import pl.kielce.tu.util.ViewUtil;

import java.util.List;
import java.util.Scanner;

/**
 * @author ciepluchs
 */
public class FindStadium implements Action {

    private static final String HEADER = "############################################# FIND STADIUM ##############################################";
    private static final String DISPLAY_NAME = "Find stadium";

    private StadiumRepository repository;

    public FindStadium(StadiumRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        ViewUtil.cls();
        System.out.println(HEADER);
        List<Stadium> travelOffers = repository.find(getStadium());
        if (travelOffers.isEmpty()) {
            System.out.println("Nothing found...");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            ViewUtil.cls();
            return;
        }
        if (travelOffers.size() == 1) {
            new ShowStadium(repository, travelOffers.get(0)).execute();
        } else {
            System.out.println("-".repeat(105) + "\n");
            String tableWithBooks = ViewUtil.getTable(travelOffers);
            System.out.println(tableWithBooks);
            System.out.println("Type number of offer to see details:");
            String selectedOption = ViewUtil.getSelectedOption();
            ViewUtil.cls();
            if (!ViewUtil.isBackOptionSelected(selectedOption)) {
                new ShowStadium(repository, travelOffers.get(Integer.parseInt(selectedOption) - 1)).execute();
            }
        }
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    private Stadium getStadium() {
        Scanner input = new Scanner(System.in);
        Stadium stadium = new Stadium();
        System.out.println("Owner: (press ENTER to skip)");
        String owner = input.nextLine();
        if (StringUtils.isNotEmpty(owner)) {
            stadium.setOwnerClub(owner);
            System.out.println("\n");
        }

        System.out.println("City (press ENTER to skip)");
        String city = input.nextLine();
        if (StringUtils.isNotEmpty(city)) {
            stadium.setCity(city);
            System.out.println("\n");
        }


        System.out.println("Capacity: (press ENTER to skip)");
        String capacity = input.nextLine();
        if (StringUtils.isNotEmpty(capacity)) {
            stadium.setCapacity(Integer.valueOf(capacity));
            System.out.println("\n");
        }

        System.out.println("Covered: (press ENTER to skip)");
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
