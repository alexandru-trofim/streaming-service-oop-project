package internalClasses;

import com.fasterxml.jackson.databind.node.ArrayNode;
import databaseElements.User;
import output.Output;
import runProgram.Program;
import runProgram.ProgramInfo;

public class PageSwitcher {
    public enum CURRENT_PAGE {
        HOME_AUTH,
        HOME_NO_AUTH,
        LOGIN,
        REGISTER,
        MOVIES,
        UPGRADES,
        LOGOUT,
        SEE_DETAILS
    }

    public PageSwitcher() {
    }

    public void switchPage(String newPage, ProgramInfo programInfo, ArrayNode output) {
        CURRENT_PAGE currentPage = programInfo.getCurrentPage();
        User currentUser = programInfo.getCurrentUser();

        if (currentPage == null) {
            System.out.println("ERROR!! CURRENT PAGE IS NULL \n");
            return;
        }

        if (newPage.equals("register")) {
            if (currentPage != CURRENT_PAGE.HOME_NO_AUTH) {
                System.out.println("!!ERROR!! SWITCHING PAGE FROM " + currentPage + " to " + newPage + "\n");
                Output.display(currentUser, Output.TYPE.ERRROR, output);
                //returns user to no auth page
                //programInfo.setCurrentPage(CURRENT_PAGE.HOME_NO_AUTH);
                return;
            }
            programInfo.setCurrentPage(CURRENT_PAGE.REGISTER);

        }else if (newPage.equals("login")) {
            if (currentPage != CURRENT_PAGE.HOME_NO_AUTH) {
                System.out.println("!!ERROR!! SWITCHING PAGE FROM " + currentPage + " to " + newPage + "\n");
                //nu modificam current user pur si simplu spunem la display sa il puna null
                //programInfo.setCurrentUser(null);
                programInfo.getCurrentUser().setUserNullOutput(true);
                Output.display(programInfo.getCurrentUser(), Output.TYPE.ERRROR, output);
                //returns user to no auth page
                //programInfo.setCurrentPage(CURRENT_PAGE.HOME_NO_AUTH);
                programInfo.getCurrentUser().setUserNullOutput(false);
                return;
            }
            programInfo.setCurrentPage(CURRENT_PAGE.LOGIN);

        } else if (newPage.equals("logout")) {
            if (currentPage != CURRENT_PAGE.HOME_AUTH
             && currentPage != CURRENT_PAGE.MOVIES
             && currentPage != CURRENT_PAGE.UPGRADES) {

                System.out.println("!!ERROR!! SWITCHING PAGE FROM" + currentPage + " to " + newPage + "\n");
                Output.display(currentUser, Output.TYPE.ERRROR, output);
                return;
            }
            programInfo.setCurrentPage(CURRENT_PAGE.HOME_NO_AUTH);
            //set current user to null
            programInfo.setCurrentUser(null);

        } else if (newPage.equals("movies")) {
            if (currentPage != CURRENT_PAGE.HOME_AUTH
                    && currentPage != CURRENT_PAGE.SEE_DETAILS
                    && currentPage != CURRENT_PAGE.UPGRADES) {

                System.out.println("!!ERROR!! SWITCHING PAGE FROM" + currentPage + " to " + newPage + "\n");
                Output.display(currentUser, Output.TYPE.ERRROR, output);
                return;
            }
            //when we switch to movies we have to display all movies available to the user
            MoviesPage moviesPage = new MoviesPage();
            moviesPage.displayWhenSwitchPage(programInfo, output);

            programInfo.setCurrentPage(CURRENT_PAGE.MOVIES);
        }
    }
}
