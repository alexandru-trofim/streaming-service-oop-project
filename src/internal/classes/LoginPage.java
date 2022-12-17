package internal.classes;

import com.fasterxml.jackson.databind.node.ArrayNode;
import database.elements.Database;
import database.elements.User;
import output.Output;
import run.program.ProgramInfo;

public class LoginPage implements Page {

    public LoginPage() {
    }

    /**
     * Has just the login action. It logs in into an existing account
     * with credentials from currentAction object and add it to the database.
     * It checks if the user is valid and throws an error if so.
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

        //check if user with given credential exists then setting up the  current user
        User userFound = database.findUser(currentAction.getCredentials().getName(),
                                           currentAction.getCredentials().getPassword());
        if (userFound == null) {
            System.out.println("!!ERROR!! USER NOT FOUND OR WRONG PASS \n");
            Output.display(currentUser, Output.TYPE.ERRROR, output);
            programInfo.setCurrentPage(PageSwitcher.CURRENTPAGE.HOME_NO_AUTH);
            return;
        }

        //set the current user to the user found
        programInfo.setCurrentUser(userFound);

        //change page to home auth
        programInfo.setCurrentPage(PageSwitcher.CURRENTPAGE.HOME_AUTH);

        //make sure user is displayed
        programInfo.getCurrentUser().setUserNullOutput(false);
        Output.display(programInfo.getCurrentUser(), Output.TYPE.NO_ERRROR, output);
    }

}
