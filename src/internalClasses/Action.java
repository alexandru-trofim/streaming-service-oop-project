package internalClasses;

import fileio.ActionInput;
import fileio.CredentialsInput;
import fileio.FiltersInput;
import lombok.Getter;
import lombok.Setter;

public class Action {
    private @Getter @Setter String type;
    private @Getter @Setter String page;
    private @Getter @Setter String movie;
    private @Getter @Setter String feature;
    private @Getter @Setter CredentialsInput credentials;
    private @Getter @Setter FiltersInput filters;
    private @Getter @Setter int count;
    private @Getter @Setter String startsWith;
    private @Getter @Setter int rate;

    public Action(ActionInput actionInput) {
         type = actionInput.getType();
         page = actionInput.getPage();
         movie = actionInput.getMovie();
         feature = actionInput.getFeature();
         credentials = actionInput.getCredentials();
         filters = actionInput.getFilters();
         count = actionInput.getCount();
         startsWith = actionInput.getStartsWith();
         rate = actionInput.getRate();
    }
}
