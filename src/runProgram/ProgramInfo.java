package runProgram;

import databaseElements.User;
import internalClasses.PageSwitcher;
import lombok.Getter;
import lombok.Setter;

public class ProgramInfo {
    private @Getter @Setter PageSwitcher.CURRENT_PAGE currentPage;
    private @Getter @Setter User currentUser;

    public ProgramInfo(PageSwitcher.CURRENT_PAGE currentPage) {
        this.currentPage = currentPage;
        this.currentUser = null;
    }
}
