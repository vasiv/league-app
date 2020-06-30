package pl.kielce.tu.util;

import pl.kielce.tu.action.Action;

import java.util.List;

/**
 * @author ciepluchs
 */
public abstract class ActionUtil {

    private static final String BACK_SYMBOL = "<-";
    private static final String BACK = "back";

    private ActionUtil() {
    }

    public static boolean isBackOptionSelected(String selectedOption) {
        return BACK_SYMBOL.equals(selectedOption) || BACK.equals(selectedOption);
    }

    public static Action getSelectedAction(List<Action> actions, String selectedOption) {
        return actions.get(Integer.parseInt(selectedOption) - 1);
    }
}
