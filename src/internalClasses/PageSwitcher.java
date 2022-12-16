package internalClasses;

import com.fasterxml.jackson.databind.node.ArrayNode;
import databaseElements.Movie;
import databaseElements.User;
import output.Output;
import runProgram.Program;
import runProgram.ProgramInfo;

import java.util.ArrayList;
import java.util.Arrays;

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

    public void switchPage(Action currentAction, ProgramInfo programInfo, ArrayNode output) {
        CURRENT_PAGE currentPage = programInfo.getCurrentPage();
        User currentUser = programInfo.getCurrentUser();
        String newPage = currentAction.getPage();

        if (currentPage == null) {
            System.out.println("ERROR!! CURRENT PAGE IS NULL \n");
            return;
        }

        if (newPage.equals("register")) {
            if (currentPage != CURRENT_PAGE.HOME_NO_AUTH) {
                System.out.println("!!ERROR!! SWITCHING PAGE FROM " + currentPage + " to " + newPage + "\n");
                Output.outputError(programInfo, output, currentUser);
                return;
            }
            programInfo.setCurrentPage(CURRENT_PAGE.REGISTER);

        } else if (newPage.equals("login")) {
            if (currentPage != CURRENT_PAGE.HOME_NO_AUTH) {
                System.out.println("!!ERROR!! SWITCHING PAGE FROM " + currentPage + " to " + newPage + "\n");
                Output.outputError(programInfo, output, currentUser);
                return;
            }
            programInfo.setCurrentPage(CURRENT_PAGE.LOGIN);

        } else if (newPage.equals("logout")) {
            if (currentPage == CURRENT_PAGE.HOME_NO_AUTH) {

                System.out.println("!!ERROR!! SWITCHING PAGE FROM" + currentPage + " to " + newPage + "\n");
                Output.display(currentUser, Output.TYPE.ERRROR, output);
                return;
            }
            currentUser.setMoviesDisplayed(null);
            programInfo.setCurrentPage(CURRENT_PAGE.HOME_NO_AUTH);
            //set current user to null
            programInfo.setCurrentUser(null);

        } else if (newPage.equals("movies")) {
            if (currentPage != CURRENT_PAGE.HOME_AUTH
                    && currentPage != CURRENT_PAGE.SEE_DETAILS
                    && currentPage != CURRENT_PAGE.UPGRADES
                    && currentPage != CURRENT_PAGE.MOVIES) {

                System.out.println("!!ERROR!! SWITCHING PAGE FROM" + currentPage + " to " + newPage + "\n");
                Output.display(currentUser, Output.TYPE.ERRROR, output);
                return;
            }
            //when we switch to movies we have to display all movies available to the user
            MoviesPage moviesPage = new MoviesPage();
            moviesPage.displayWhenSwitchPage(programInfo, output);

            programInfo.setCurrentPage(CURRENT_PAGE.MOVIES);

        } else if (newPage.equals("see details")) {
            if (currentPage != CURRENT_PAGE.MOVIES) {

                System.out.println("!!ERROR!! SWITCHING PAGE FROM" + currentPage + " to " + newPage + "\n");
                programInfo.setMovieDetails(null);
                Output.display(currentUser, Output.TYPE.ERRROR, output);
                return;
            }
            //here we should change the page and try to find the movie
            //if we don't have the movie we throw an error
            //TODO
            Movie movieToSee = null;
            if (programInfo.getCurrentUser().getMoviesDisplayed() != null) {
                movieToSee = programInfo.getCurrentUser().getMoviesDisplayed()
                        .stream()
                        .filter((movie) -> movie.getName().equals(currentAction.getMovie()))
                        .findFirst().orElse(null);
            }

            if (movieToSee == null) {
                System.out.println("ERROR!!! MOVIE NOT FOUND SEE DETAILS CRASHED\n");
                programInfo.setMovieDetails(null);
                //programInfo.setCurrentPage(CURRENT_PAGE.SEE_DETAILS);
                Output.outputError(programInfo, output, currentUser);
                return;
            }

            //now we change the page and set up the Movie
            programInfo.setCurrentPage(CURRENT_PAGE.SEE_DETAILS);
            programInfo.setMovieDetails(movieToSee);

            ArrayList<Movie> movieBackup = currentUser.getMoviesDisplayed();

            currentUser.setMoviesDisplayed(new ArrayList<>(Arrays.asList(movieToSee)));
            Output.display(currentUser, Output.TYPE.NO_ERRROR, output);
            currentUser.setMoviesDisplayed(movieBackup);

        } else if (newPage.equals("upgrades")) {
            System.out.println("SWITCH PAGE TO UPGRADES !!!!!!!!!!\n");
            if (currentPage != CURRENT_PAGE.SEE_DETAILS
                    && currentPage != CURRENT_PAGE.UPGRADES
                    && currentPage != CURRENT_PAGE.MOVIES
                    && currentPage != CURRENT_PAGE.HOME_AUTH) {
                System.out.println("!!ERROR!! SWITCHING PAGE FROM" + currentPage + " to " + newPage + "\n");
                Output.outputError(programInfo, output, currentUser);
                return;
            }
            programInfo.setCurrentPage(CURRENT_PAGE.UPGRADES);


        }
    }
}
