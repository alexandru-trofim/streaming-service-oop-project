package internal.classes;

import com.fasterxml.jackson.databind.node.ArrayNode;
import database.elements.Movie;
import database.elements.User;
import output.Output;
import run.program.ProgramInfo;

import java.util.ArrayList;
import java.util.Arrays;

public class PageSwitcher {
    public enum CURRENTPAGE {
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

    /**
     * It switches the page from the old page to the new page given in
     * the currentAction. It throws an error if the switch is not possible.
     * @param currentAction The current action.
     * @param programInfo The ProgramInfo object that stores the current user,
     *                    the current page and the movie on seeDetails page.
     * @param output The output ArrayNode where the output is written.
     */
    public void switchPage(final Action currentAction, final ProgramInfo programInfo,
                                                                    final ArrayNode output) {
        CURRENTPAGE currentPage = programInfo.getCurrentPage();
        User currentUser = programInfo.getCurrentUser();
        String newPage = currentAction.getPage();

        if (currentPage == null) {
            System.out.println("ERROR!! CURRENT PAGE IS NULL \n");
            return;
        }
        if (newPage.equals("register")) {
            if (currentPage != CURRENTPAGE.HOME_NO_AUTH) {
                System.out.println("!!ERROR!! SWITCHING PAGE FROM "
                                            + currentPage + " to " + newPage + "\n");
                Output.outputError(programInfo, output, currentUser);
                return;
            }
            programInfo.setCurrentPage(CURRENTPAGE.REGISTER);

        } else if (newPage.equals("login")) {
            if (currentPage != CURRENTPAGE.HOME_NO_AUTH) {
                System.out.println("!!ERROR!! SWITCHING PAGE FROM "
                                            + currentPage + " to " + newPage + "\n");
                Output.outputError(programInfo, output, currentUser);
                return;
            }
            programInfo.setCurrentPage(CURRENTPAGE.LOGIN);

        } else if (newPage.equals("logout")) {
            if (currentPage == CURRENTPAGE.HOME_NO_AUTH) {
                System.out.println("!!ERROR!! SWITCHING PAGE FROM"
                                            + currentPage + " to " + newPage + "\n");
                Output.display(currentUser, Output.TYPE.ERRROR, output);
                return;
            }
            currentUser.setMoviesDisplayed(null);
            programInfo.setCurrentPage(CURRENTPAGE.HOME_NO_AUTH);
            //set current user to null
            programInfo.setCurrentUser(null);

        } else if (newPage.equals("movies")) {
            if (currentPage != CURRENTPAGE.HOME_AUTH
                    && currentPage != CURRENTPAGE.SEE_DETAILS
                    && currentPage != CURRENTPAGE.UPGRADES
                    && currentPage != CURRENTPAGE.MOVIES) {

                System.out.println("!!ERROR!! SWITCHING PAGE FROM"
                                            + currentPage + " to " + newPage + "\n");
                Output.display(currentUser, Output.TYPE.ERRROR, output);
                return;
            }
            //when we switch to movies we have to display all movies available to the user
            MoviesPage moviesPage = new MoviesPage();
            moviesPage.displayWhenSwitchPage(programInfo, output);

            programInfo.setCurrentPage(CURRENTPAGE.MOVIES);

        } else if (newPage.equals("see details")) {
            if (currentPage != CURRENTPAGE.MOVIES) {
                System.out.println("!!ERROR!! SWITCHING PAGE FROM"
                                            + currentPage + " to " + newPage + "\n");
                programInfo.setMovieDetails(null);
                Output.display(currentUser, Output.TYPE.ERRROR, output);
                return;
            }
            //here we should change the page and try to find the movie
            //if we don't have the movie we throw an error
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
            programInfo.setCurrentPage(CURRENTPAGE.SEE_DETAILS);
            programInfo.setMovieDetails(movieToSee);

            ArrayList<Movie> movieBackup = currentUser.getMoviesDisplayed();

            currentUser.setMoviesDisplayed(new ArrayList<>(Arrays.asList(movieToSee)));
            Output.display(currentUser, Output.TYPE.NO_ERRROR, output);
            currentUser.setMoviesDisplayed(movieBackup);

        } else if (newPage.equals("upgrades")) {
            if (currentPage != CURRENTPAGE.SEE_DETAILS
                    && currentPage != CURRENTPAGE.UPGRADES
                    && currentPage != CURRENTPAGE.MOVIES
                    && currentPage != CURRENTPAGE.HOME_AUTH) {
                System.out.println("!!ERROR!! SWITCHING PAGE FROM"
                                            + currentPage + " to " + newPage + "\n");
                Output.outputError(programInfo, output, currentUser);
                return;
            }
            programInfo.setCurrentPage(CURRENTPAGE.UPGRADES);
        }
    }
}
