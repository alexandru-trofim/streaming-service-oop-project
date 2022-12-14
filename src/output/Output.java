package output;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import databaseElements.Movie;
import databaseElements.User;

import java.util.ArrayList;

public class Output {

    public enum TYPE {
        ERRROR,
        NO_ERRROR
    }

    public static void display(User currentUser, TYPE outputType, ArrayNode output) {
        String error;
        ObjectNode commandOutput = output.objectNode();

        if (outputType == TYPE.ERRROR) {
            error = "Error";
        } else {
            error = null;
        }

        commandOutput.put("error", error);
        if (currentUser != null && currentUser.getMoviesDisplayed() != null) {
            commandOutput.putPOJO("currentMoviesList", currentUser.getMoviesDisplayed());
        } else {
            commandOutput.putArray("currentMoviesList");
        }
        //set currUser to null for change page error where even though we have current user
        //we have to set it to null
        if (currentUser != null && currentUser.isUserNullOutput()) {
            currentUser = null;
        }
        commandOutput.putPOJO("currentUser", currentUser);

        output.add(commandOutput);
    }

}
