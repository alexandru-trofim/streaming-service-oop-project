package run.program;

import database.elements.Movie;
import database.elements.User;
import internal.classes.PageSwitcher;
import lombok.Getter;
import lombok.Setter;

public class ProgramInfo {
    private @Getter @Setter PageSwitcher.CURRENTPAGE currentPage;
    private @Getter @Setter User currentUser;
    private @Getter @Setter Movie movieDetails;

    public ProgramInfo(final PageSwitcher.CURRENTPAGE currentPage) {
        this.currentPage = currentPage;
        this.currentUser = null;
        this.movieDetails = null;
    }
}
