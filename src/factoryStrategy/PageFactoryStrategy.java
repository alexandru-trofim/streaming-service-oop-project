package factoryStrategy;

import com.fasterxml.jackson.databind.node.ArrayNode;
import databaseElements.User;
import internalClasses.*;
import runProgram.Program;
import runProgram.ProgramInfo;

public class PageFactoryStrategy {
    private Page currentPage;

    public PageFactoryStrategy() {
    }

    private Page getPageStrategy(ProgramInfo programInfo) {
        Page nullPage = null;
        switch (programInfo.getCurrentPage()) {
            case LOGIN -> {
                return new LoginPage();}
            case REGISTER -> {
                return new RegisterPage();
            } case MOVIES -> {
                return new MoviesPage();
            } case HOME_AUTH -> {
                return new HomeAuth();
            }
            default -> {
                return null;
            }

        }
    }

    public void doStrategy(Action currentAction, ProgramInfo programInfo, ArrayNode output) {
        currentPage = getPageStrategy(programInfo);

        if (currentPage == null) {
            System.out.println("ERROR!! CURRENT PAGE IS NULL CAN'T EXECUTE ACTIONS\n");
            return;
        }
        currentPage.executeAction(currentAction, programInfo, output);

    }

}
