package runProgram;

import com.fasterxml.jackson.databind.node.ArrayNode;
import databaseElements.Database;
import databaseElements.Movie;
import databaseElements.User;
import factoryStrategy.PageFactoryStrategy;
import fileio.Input;
import internalClasses.Action;
import internalClasses.PageSwitcher;
import lombok.Getter;
import lombok.Setter;
import output.Output;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class Program {

    private @Getter @Setter ProgramInfo programInfo;
    public Program() {
    }

    public void run(final Input inputData, ArrayNode output) {
        PageFactoryStrategy pageFactoryStrategy = new PageFactoryStrategy();
        ArrayList<Action> actions = new ArrayList<>();
        PageSwitcher pageSwitcher = new PageSwitcher();
        Database database = Database.getInstance();
        programInfo = new ProgramInfo(PageSwitcher.CURRENT_PAGE.HOME_NO_AUTH);

        database.load(inputData);

        //load actions in action list
        inputData.getActions()
                .forEach((actionInput) -> actions.add(new Action(actionInput)));

        for(Action currentAction: actions) {
            //here we probably will use our factory/strategy
            if (currentAction.getType().equals("change page")) {
                System.out.println("ENTERED CHANGE PAGE FROM " + programInfo.getCurrentPage() + " " + currentAction.getPage() + "\n");
                //switch page
                if (programInfo.getCurrentUser() == null) {
                    System.out.println("CURRENT USER IS NULL \n");
                }
                pageSwitcher.switchPage(currentAction, programInfo, output);

            } else if(currentAction.getType().equals("on page")){
                System.out.println("ENTERED ON PAGE "+ currentAction.getFeature() + " ON PAGE " + programInfo.getCurrentPage() + "\n");
                //here we'll do the strategy
                pageFactoryStrategy.doStrategy(currentAction, programInfo, output);
                if (programInfo.getCurrentUser() == null) {
                    System.out.println("CURRENT USER IS NULL \n");
                }
            }
        }

        database.empty();
        programInfo.setCurrentPage(PageSwitcher.CURRENT_PAGE.HOME_NO_AUTH);

    }
}
