package factory.strategy;

import com.fasterxml.jackson.databind.node.ArrayNode;
import action.classes.Action;
import action.classes.pages.HomeAuthPage;
import action.classes.pages.LoginPage;
import action.classes.pages.MoviesPage;
import action.classes.pages.Page;
import action.classes.pages.RegisterPage;
import action.classes.pages.SeeDetailsPage;
import action.classes.pages.UpgradesPage;
import run.program.ProgramInfo;

public class PageFactoryStrategy {
    private Page currentPage;

    public PageFactoryStrategy() {
    }

    /**
     * Implements the factory. Generates a new page depending on page type
     * given in programInfo.
     * @param programInfo The ProgramInfo object that stores the current user,
     *                    the current page and the movie on seeDetails page.
     * @return
     */
    private Page getPageStrategy(final ProgramInfo programInfo) {
        Page nullPage = null;
        switch (programInfo.getCurrentPage()) {
            case LOGIN -> {
                return new LoginPage();
            }
            case REGISTER -> {
                return new RegisterPage();
            } case MOVIES -> {
                return new MoviesPage();
            } case HOME_AUTH -> {
                return new HomeAuthPage();
            } case UPGRADES -> {
                return new UpgradesPage();
            } case SEE_DETAILS -> {
                return new SeeDetailsPage();
            }
            default -> {
                return null;
            }

        }
    }

    /**
     * Implements the strategy part by calling executeAction which processes
     * the action name and executes the corresponding algorithm.
     * @param currentAction The current action.
     * @param programInfo The ProgramInfo object that stores the current user,
     *                    the current page and the movie on seeDetails page.
     * @param output The output ArrayNode where the output is written.
     */
    public void doStrategy(final Action currentAction, final ProgramInfo programInfo,
                                                                final ArrayNode output) {
        currentPage = getPageStrategy(programInfo);

        if (currentPage == null) {
            System.out.println("ERROR!! CURRENT PAGE IS NULL CAN'T EXECUTE ACTIONS\n");
            return;
        }
        currentPage.executeAction(currentAction, programInfo, output);

    }

}
