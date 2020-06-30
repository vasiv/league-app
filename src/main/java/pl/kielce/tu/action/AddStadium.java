package pl.kielce.tu.action;

import pl.kielce.tu.model.Stadium;
import pl.kielce.tu.repository.StadiumRepository;
import pl.kielce.tu.util.ViewUtil;

import java.util.Scanner;

/**
 * @author ciepluchs
 */
public class AddStadium implements Action {

    public static final String YES = "y";
    public static final String NO = "n";
    private static final String HEADER = "############################################## ADD STADIUM ##############################################";
    private static final String DISPLAY_NAME = "Add new stadium";
    private StadiumRepository repository;

    public AddStadium(StadiumRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        ViewUtil.cls();
        System.out.println(HEADER);
        Stadium stadiumToBeAdded = buildStadiumToBeCreated();
        repository.createOrUpdate(stadiumToBeAdded);
        ViewUtil.cls();
        System.out.println("Stadium added!");
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    private Stadium buildStadiumToBeCreated() {
        Scanner input = new Scanner(System.in);
        System.out.println("Owner club: ");
        String ownerClub = input.nextLine();
        System.out.println("City: ");
        String city = input.nextLine();
        System.out.println("What is capacity: ");
        int capacity = input.nextInt();
        System.out.println("Is grandstand covered: [y/n]");
        String isCoveredInput = input.next();
        if (YES.equalsIgnoreCase(isCoveredInput)) {
            return new Stadium(ownerClub, city, capacity, true);
        } else if (NO.equalsIgnoreCase(isCoveredInput)) {
            return new Stadium(ownerClub, city, capacity, false);
        }
        return null;
    }
}
