package action.classes.pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import database.elements.Database;
import database.elements.User;
import action.classes.Action;
import action.classes.PageSwitcher;
import output.Output;
import run.program.ProgramInfo;

public class RegisterPage implements Page {
    /**
     * Has just the register action. It creates a user with credentials from
     * currentAction object and add it to the database. It checks if the user
     * already exists and throws an error if so.
     * @param currentAction The current action.
     * @param programInfo The ProgramInfo object that stores the current user,
     *                    the current page and the movie on seeDetails page.
     * @param output The output ArrayNode where the output is written.
     */
    @Override
    public void executeAction(final Action currentAction, final ProgramInfo programInfo,
                              final ArrayNode output) {
        Database database = Database.getInstance();
        User currentUser = programInfo.getCurrentUser();

        User userFound = database.findUser(currentAction.getCredentials().getName(),
                currentAction.getCredentials().getPassword());

        if (userFound != null) {
            System.out.println("!!ERROR!! REGISTER FAILED! ALREADY EXISTING USER\n");
            Output.outputError(programInfo, output, programInfo.getCurrentUser());
            programInfo.setCurrentPage(PageSwitcher.CURRENTPAGE.HOME_NO_AUTH);
            return;
        }

        User newUser = new User(currentAction.getCredentials());
        //add new user to the database
        database.addUser(newUser);
        //set the current user to be the new user
        programInfo.setCurrentUser(newUser);
        //display output message
        Output.display(programInfo.getCurrentUser(), Output.TYPE.NO_ERRROR, output);
        //switch page after completing action
        programInfo.setCurrentPage(PageSwitcher.CURRENTPAGE.HOME_AUTH);

    }
}
