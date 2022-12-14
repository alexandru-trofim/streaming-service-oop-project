package internalClasses;

import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.node.ArrayNode;
import databaseElements.Database;
import databaseElements.User;
import output.Output;
import runProgram.Program;
import runProgram.ProgramInfo;

public class LoginPage implements Page{

    public LoginPage() {
    }

    @Override
    public void executeAction(Action currentAction, ProgramInfo programInfo, ArrayNode output) {
        Database database = Database.getInstance();
        User currentUser = programInfo.getCurrentUser();
        //verificam if user with given credential exists then setting up the  current user
        //facem o functie pentru database care face find user si intoarce user sau null
        User userFound = database.findUser(currentAction.getCredentials().getName(),
                                           currentAction.getCredentials().getPassword());

        if (userFound == null) {
            System.out.println("!!ERROR!! USER NOT FOUND OR WRONG PASS \n");
            Output.display(currentUser, Output.TYPE.ERRROR, output);
            programInfo.setCurrentPage(PageSwitcher.CURRENT_PAGE.HOME_NO_AUTH);
            return;
        }

        //set the current user to the user found
        programInfo.setCurrentUser(userFound);

        //change page to home auth
        programInfo.setCurrentPage(PageSwitcher.CURRENT_PAGE.HOME_AUTH);

        //make sure user is displayed
        programInfo.getCurrentUser().setUserNullOutput(false);
        Output.display(programInfo.getCurrentUser(), Output.TYPE.NO_ERRROR, output);
    }

}
