package pl.kielce.tu.action;

import pl.kielce.tu.model.Stadium;
import pl.kielce.tu.repository.StadiumRepository;
import pl.kielce.tu.util.ActionUtil;
import pl.kielce.tu.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ciepluchs
 */
public class ShowStadium implements Action {

    private static final String HEADER = "################################################ STADIUM ################################################";
    private static final String DISPLAY_NAME = "Show stadium";

    private List<Action> subActions = new ArrayList<>();
    private Stadium stadium;

    public ShowStadium(StadiumRepository repository, Stadium stadium) {
        this.stadium = stadium;
        subActions.add(new ModifyStadium(repository, stadium));
        subActions.add(new DeleteStadium(repository, stadium));
    }

    @Override
    public void execute() {
        System.out.println(HEADER);
        System.out.println(ViewUtil.getDetailedView(stadium));
        while (true) {
            ViewUtil.displaySubMenu(subActions);
            String selectedOption = ViewUtil.getSelectedOption();
            ViewUtil.cls();
            if (ActionUtil.isBackOptionSelected(selectedOption)) {
                return;
            } else {
                Action selectedAction = ActionUtil.getSelectedAction(subActions, selectedOption);
                selectedAction.execute();
                return;
            }
        }
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

}
