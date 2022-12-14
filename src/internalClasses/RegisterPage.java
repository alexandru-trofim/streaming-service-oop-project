package internalClasses;

import com.fasterxml.jackson.databind.node.ArrayNode;
import databaseElements.Database;
import databaseElements.User;
import output.Output;
import runProgram.Program;
import runProgram.ProgramInfo;

public class RegisterPage implements Page{
    @Override
    public void executeAction(Action currentAction, ProgramInfo programInfo, ArrayNode output) {
        Database database = Database.getInstance();
        User currentUser = programInfo.getCurrentUser();

        User userFound = database.findUser(currentAction.getCredentials().getName(),
                currentAction.getCredentials().getPassword());

        if (userFound != null) {
            System.out.println("!!ERROR!! REGISTER FAILED! ALREADY EXISTING USER\n");
            Output.display(currentUser, Output.TYPE.ERRROR, output);
            programInfo.setCurrentPage(PageSwitcher.CURRENT_PAGE.HOME_NO_AUTH);
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
        programInfo.setCurrentPage(PageSwitcher.CURRENT_PAGE.HOME_AUTH);
        if (programInfo.getCurrentUser() == null) {
            System.out.println("CURRENT USER IS NULL AT END OF REGISTER\n");
        }
    }
}
