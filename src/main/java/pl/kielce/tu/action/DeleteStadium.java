package pl.kielce.tu.action;

import pl.kielce.tu.model.Stadium;
import pl.kielce.tu.repository.StadiumRepository;

/**
 * @author ciepluchs
 */
public class DeleteStadium implements Action {

    private static final String DISPLAY_NAME = "Delete";

    private StadiumRepository repository;
    private Stadium travelOffer;

    public DeleteStadium(StadiumRepository repository, Stadium stadium) {
        this.repository = repository;
        this.travelOffer = stadium;
    }

    @Override
    public void execute() {
        repository.delete(travelOffer.getId());
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

}
