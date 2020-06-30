package pl.kielce.tu.action;

import pl.kielce.tu.model.Stadium;
import pl.kielce.tu.repository.StadiumRepository;
import pl.kielce.tu.util.ViewUtil;

import java.util.List;

/**
 * @author ciepluchs
 */
public class ShowAllStadiums implements Action {

    private static final String HEADER = "################################################ STADIUMS ###############################################";
    private static final String DISPLAY_NAME = "Show all stadiums";

    private StadiumRepository repository;

    public ShowAllStadiums(StadiumRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        while (true) {
            List<Stadium> stadiums = repository.findAll();
            String tableWithStadiums = ViewUtil.getTable(stadiums);
            ViewUtil.cls();
            System.out.println(HEADER);
            System.out.println(tableWithStadiums);
            System.out.println("Type number of stadium to see details:");
            String selectedOption = ViewUtil.getSelectedOption();
            ViewUtil.cls();
            if (ViewUtil.isBackOptionSelected(selectedOption)) {
                return;
            } else {
                new ShowStadium(repository, stadiums.get(Integer.parseInt(selectedOption) - 1)).execute();
            }
        }
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

}
