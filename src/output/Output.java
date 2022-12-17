package output;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.elements.Movie;
import database.elements.User;
import run.program.ProgramInfo;

import java.util.ArrayList;

public class Output {

    public enum TYPE {
        ERRROR,
        NO_ERRROR
    }

    /**
     * Adds a movie array to the output.
     * @param moviesArray the ArrayNode where we output the movies array.
     * @param movies the array to be written in output.
     */
    public static void addMovies(final ArrayNode moviesArray, final ArrayList<Movie> movies) {
        if (movies == null) {
            return;
        }

        for (Movie movie : movies) {
            ObjectNode newMovie = moviesArray.objectNode();
            newMovie.put("name", movie.getName());
            newMovie.put("year", movie.getYear());
            newMovie.put("duration", movie.getDuration());
            newMovie.putPOJO("genres", movie.getGenres());
            newMovie.putPOJO("actors", movie.getActors());
            newMovie.putPOJO("countriesBanned", movie.getCountriesBanned());
            newMovie.put("numLikes", movie.getNumLikes());
            newMovie.put("rating", movie.getRating());
            newMovie.put("numRatings", movie.getNumRatings());
            moviesArray.add(newMovie);
        }

    }

    /**
     * Outputs a outputs an error.
     * @param currentUser The current user.
     * @param programInfo The ProgramInfo object that stores the current user,
     *                    the current page and the movie on seeDetails page.
     * @param output The output ArrayNode where the output is written.
     */
    public static void outputError(final ProgramInfo programInfo, final ArrayNode output,
                                                                    final User currentUser) {
        ArrayList<Movie> movieBackup = currentUser.getMoviesDisplayed();

        currentUser.setMoviesDisplayed(null);
        programInfo.getCurrentUser().setUserNullOutput(true);
        Output.display(currentUser, TYPE.ERRROR, output);
        programInfo.getCurrentUser().setUserNullOutput(false);
        currentUser.setMoviesDisplayed(movieBackup);

    }

    /**
     * Puts in the output the standardized message when a successful action is completed.
     * @param currentUser The current user.
     * @param outputType The type of the output.
     * @param output The output arrayNode.
     */
    public static void display(User currentUser, final TYPE outputType,
                                                            final ArrayNode output) {
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
            ArrayNode currentMoviesList = commandOutput.putArray("currentMoviesList");
            addMovies(currentMoviesList, currentUser.getMoviesDisplayed());
        } else {
            commandOutput.putArray("currentMoviesList");
        }
        //set currUser to null for change page error where even though we have current user
        //we have to set it to null
        if (currentUser != null && currentUser.isUserNullOutput()) {
            currentUser = null;
        }

        if (currentUser != null) {
            ObjectNode user = commandOutput.putObject("currentUser");

            ObjectNode credentials = user.putObject("credentials");
            credentials.put("name", currentUser.getCredentials().getName());
            credentials.put("password", currentUser.getCredentials().getPassword());
            credentials.put("accountType", currentUser.getCredentials().getAccountType());
            credentials.put("country", currentUser.getCredentials().getCountry());
            credentials.put("balance", currentUser.getCredentials().getBalance());

            user.put("tokensCount", currentUser.getTokensCount());
            user.put("numFreePremiumMovies", currentUser.getNumFreePremiumMovies());

            ArrayNode purchasedMovies = user.putArray("purchasedMovies");
            addMovies(purchasedMovies, currentUser.getPurchasedMovies());

            ArrayNode watchedMovies = user.putArray("watchedMovies");
            addMovies(watchedMovies, currentUser.getWatchedMovies());

            ArrayNode likedMovies = user.putArray("likedMovies");
            addMovies(likedMovies, currentUser.getLikedMovies());

            ArrayNode ratedMovies = user.putArray("ratedMovies");
            addMovies(ratedMovies, currentUser.getRatedMovies());

        } else {
            commandOutput.putPOJO("currentUser", currentUser);
        }

        output.add(commandOutput);
    }



}
