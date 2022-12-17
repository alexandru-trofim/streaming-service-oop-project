package run.program;

import com.fasterxml.jackson.databind.node.ArrayNode;
import database.elements.Database;
import factory.strategy.PageFactoryStrategy;
import fileio.Input;
import action.classes.Action;
import action.classes.PageSwitcher;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Program {

    private @Getter @Setter ProgramInfo programInfo;
    public Program() {
    }

    /**
     * Takes as parameters the input data of the program and an output
     * arrayNode. It initializes the database and runs all the actions.
     * @param inputData The input data.
     * @param output The output arraynode.
     */
    public void run(final Input inputData, final ArrayNode output) {
        PageFactoryStrategy pageFactoryStrategy = new PageFactoryStrategy();
        ArrayList<Action> actions = new ArrayList<>();
        PageSwitcher pageSwitcher = new PageSwitcher();
        Database database = Database.getInstance();
        programInfo = new ProgramInfo(PageSwitcher.CURRENTPAGE.HOME_NO_AUTH);

        database.load(inputData);

        //load actions in action list
        inputData.getActions()
                .forEach((actionInput) -> actions.add(new Action(actionInput)));

        for (Action currentAction: actions) {
            /*
             * here we use our factoryStrategy setup for a very simple
             * and clean flow of the program.
             */
            if (currentAction.getType().equals("change page")) {
                //switch page
                if (programInfo.getCurrentUser() == null) {
                    System.out.println("CURRENT USER IS NULL \n");
                }
                pageSwitcher.switchPage(currentAction, programInfo, output);

            } else if (currentAction.getType().equals("on page")) {
                pageFactoryStrategy.doStrategy(currentAction, programInfo, output);
                if (programInfo.getCurrentUser() == null) {
                    System.out.println("CURRENT USER IS NULL \n");
                }
            }
        }
        database.empty();
        programInfo.setCurrentPage(PageSwitcher.CURRENTPAGE.HOME_NO_AUTH);
    }
}
