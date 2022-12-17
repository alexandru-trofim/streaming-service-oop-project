package action.classes.pages;

import action.classes.Action;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.elements.Movie;
import database.elements.User;
import output.Output;
import run.program.ProgramInfo;

import java.util.ArrayList;
import java.util.Arrays;

public class SeeDetailsPage implements Page {
    private final int maxRating = 5;

    /**
     * Purchases a movie for the current user. If the user has a premium account
     * it decreases the amount of free premium movies of the user. If the premium
     * user has no more free premium movies it decreases the amount of his tokens
     * amount by two. If a user has not a premium account it decreases his
     * tokens amount by two. If the user has not enough tokens the function throws
     * an error
     * @param currentAction The current action.
     * @param programInfo The ProgramInfo object that stores the current user,
     *                    the current page and the movie on seeDetails page.
     * @param output The output ArrayNode where the output is written.
     */
    public void purchase(final Action currentAction, final ProgramInfo programInfo,
                         final ArrayNode output)  {
        User currentUser = programInfo.getCurrentUser();

        if (currentUser.getPurchasedMovies().contains(programInfo.getMovieDetails())) {
            System.out.println(programInfo.getMovieDetails().getName() + " IS ALREADY PURCHASED\n");
            Output.outputError(programInfo, output, currentUser);
            return;
        }

        //decrease number of free premium movies or tokens
        if (currentUser.getCredentials().getAccountType().equals("premium")) {
            if (currentUser.getNumFreePremiumMovies() > 0) {
                currentUser.setNumFreePremiumMovies(currentUser.getNumFreePremiumMovies() - 1);
            } else if (currentUser.getTokensCount() > 1) {
                currentUser.setTokensCount(currentUser.getTokensCount() - 2);
            } else {
                System.out.println("NOT ENOUGH RESOURCES TO BUY MOVIE");
                Output.outputError(programInfo, output, currentUser);
                return;
            }
        } else if (currentUser.getTokensCount() > 1) {
           currentUser.setTokensCount(currentUser.getTokensCount() - 2);
        } else {
            System.out.println("NOT ENOUGH RESOURCES TO BUY MOVIE");
            Output.outputError(programInfo, output, currentUser);
            return;
        }
        currentUser.getPurchasedMovies().add(programInfo.getMovieDetails());

        ArrayList<Movie> movieBackup = currentUser.getMoviesDisplayed();

        currentUser.setMoviesDisplayed(
                new ArrayList<>(Arrays.asList(programInfo.getMovieDetails())));

        Output.display(currentUser, Output.TYPE.NO_ERRROR, output);
        currentUser.setMoviesDisplayed(movieBackup);
    }

    /**
     * It performs the action of watching a movie and adds it to the list of watched
     * movies of the current user. If the movie isn't purchased or is already watched it
     * throws an error.
     * @param currentAction The current action.
     * @param programInfo The ProgramInfo object that stores the current user,
     *                    the current page and the movie on seeDetails page.
     * @param output The output ArrayNode where the output is written.
     */
    public void watch(final Action currentAction, final ProgramInfo programInfo,
                                                            final ArrayNode output)  {
        User currentUser = programInfo.getCurrentUser();

        if (currentUser.getWatchedMovies().contains(programInfo.getMovieDetails())) {
            System.out.println(programInfo.getMovieDetails().getName()
                                                        + " IS ALREADY WATCHED\n");
            Output.outputError(programInfo, output, currentUser);
            return;
        }
        if (!currentUser.getPurchasedMovies().contains(programInfo.getMovieDetails())) {
            System.out.println(programInfo.getMovieDetails().getName()
                                                    + " IS NOT PURCHASED!! CAN'T WATCH\n");
            Output.outputError(programInfo, output, currentUser);
            return;
        }
        currentUser.getWatchedMovies().add(programInfo.getMovieDetails());

        ArrayList<Movie> movieBackup = currentUser.getMoviesDisplayed();

        currentUser.setMoviesDisplayed(
                new ArrayList<>(Arrays.asList(programInfo.getMovieDetails())));

        Output.display(currentUser, Output.TYPE.NO_ERRROR, output);
        currentUser.setMoviesDisplayed(movieBackup);

    }

    /**
     * It likes a movie that current user watched. Adds it to the like movies list of the
     * current user and increase the number of likes of a movie. If the movie is already
     * liked or not watched it throws an error.
     * @param currentAction The current action.
     * @param programInfo The ProgramInfo object that stores the current user,
     *                    the current page and the movie on seeDetails page.
     * @param output The output ArrayNode where the output is written.
     */
    public void like(final Action currentAction, final ProgramInfo programInfo,
                                                            final ArrayNode output)  {
        User currentUser = programInfo.getCurrentUser();

        if (currentUser.getLikedMovies().contains(programInfo.getMovieDetails())) {
            System.out.println(programInfo.getMovieDetails().getName() + " IS ALREADY LIKED\n");
            Output.outputError(programInfo, output, currentUser);
            return;
        }
        if (!currentUser.getWatchedMovies().contains(programInfo.getMovieDetails())) {
            System.out.println(programInfo.getMovieDetails().getName()
                                                        + "NOT WATCHED!! CAN'T LIKE\n");
            Output.outputError(programInfo, output, currentUser);
            return;
        }
        currentUser.getLikedMovies().add(programInfo.getMovieDetails());

        programInfo.getMovieDetails().setNumLikes(programInfo.getMovieDetails().getNumLikes() + 1);

        ArrayList<Movie> movieBackup = currentUser.getMoviesDisplayed();

        currentUser.setMoviesDisplayed(
                new ArrayList<>(Arrays.asList(programInfo.getMovieDetails())));

        Output.display(currentUser, Output.TYPE.NO_ERRROR, output);
        currentUser.setMoviesDisplayed(movieBackup);
    }

    /**
     * Ir rates a movie on the page see details. It adds the movie to the rated movies
     * of the current user, adds the given rating to the array of ratings of the movie
     * and updates the rating of the movie. In case the user had already rated the movie
     * or hadn't watched it the function will throw an error.
     * @param currentAction The current action.
     * @param programInfo The ProgramInfo object that stores the current user,
     *                    the current page and the movie on seeDetails page.
     * @param output The output ArrayNode where the output is written.
     */
    public void rate(final Action currentAction, final ProgramInfo programInfo,
                                                                final ArrayNode output) {
        User currentUser = programInfo.getCurrentUser();
        if (!currentUser.getWatchedMovies().contains(programInfo.getMovieDetails())) {
            System.out.println(programInfo.getMovieDetails().getName()
                                                + " IS NOT WATCHED!! CAN'T RATE\n");
            Output.outputError(programInfo, output, currentUser);
            return;
        }
        if (currentAction.getRate() < 0 || currentAction.getRate() > maxRating) {
            System.out.println("RATE OUT OF RANGE \n");
            Output.outputError(programInfo, output, currentUser);
            return;
        }
        if (currentUser.getRatedMovies().contains(programInfo.getMovieDetails())) {
            System.out.println(programInfo.getMovieDetails().getName()
                                                + " IS ALREADY RATED!! CAN'T RATE\n");
            Output.outputError(programInfo, output, currentUser);
            return;
        }
        programInfo.getMovieDetails()
                .getMovieRatings().add(currentAction.getRate());
        programInfo.getMovieDetails()
                .setNumRatings(programInfo.getMovieDetails().getNumRatings() + 1);

        double ratingSum = (double) programInfo.getMovieDetails().getMovieRatings().stream()
                .mapToInt(value -> value).sum();
        programInfo.getMovieDetails()
                .setRating(ratingSum / programInfo.getMovieDetails().getNumRatings());

        //add to already rated movies list
        programInfo.getCurrentUser().getRatedMovies().add(programInfo.getMovieDetails());

        //display output
        ArrayList<Movie> movieBackup = currentUser.getMoviesDisplayed();

        currentUser.setMoviesDisplayed(
                new ArrayList<>(Arrays.asList(programInfo.getMovieDetails())));

        Output.display(currentUser, Output.TYPE.NO_ERRROR, output);
        currentUser.setMoviesDisplayed(movieBackup);

    }

    /**
     * Executes the "purchase", "watch", "like", "rate" actions depending
     * on the currentAction. If the currentAction contains another action
     * it throws an error.
     * @param currentAction The current action.
     * @param programInfo The ProgramInfo object that stores the current user,
     *                    the current page and the movie on seeDetails page.
     * @param output The output ArrayNode where the output is written.
     */
    @Override
    public void executeAction(final Action currentAction, final ProgramInfo programInfo,
                                                                    final ArrayNode output) {
        if (programInfo.getMovieDetails() == null) {
            System.out.println("MOVIE NOT FOUND");
            Output.outputError(programInfo, output, programInfo.getCurrentUser());
        }
        switch (currentAction.getFeature()) {
            case "purchase" -> purchase(currentAction, programInfo, output);
            case "watch" -> watch(currentAction, programInfo, output);
            case "like" -> like(currentAction, programInfo, output);
            case "rate" -> rate(currentAction, programInfo, output);
            default -> {
                Output.outputError(programInfo, output, programInfo.getCurrentUser());
                System.out.println("ERROR WRONG COMMAND\n");
            }
        }
    }
}
