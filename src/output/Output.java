package output;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import databaseElements.Database;
import databaseElements.Movie;
import databaseElements.User;
import runProgram.ProgramInfo;

import java.util.ArrayList;

public class Output {

    public enum TYPE {
        ERRROR,
        NO_ERRROR
    }

    public static void addMovies(ArrayNode moviesArray, ArrayList<Movie> movies) {
        if (movies == null) {
            return;
        }

        for(Movie movie : movies) {
            if (movie.getName().equals("Titanic")) {
                System.out.println(movie.getName() + "LIKE " + movie.getNumLikes() + "\n");
            }
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

    public static void outputError(ProgramInfo programInfo, ArrayNode output, User currentUser) {
        ArrayList<Movie> movieBackup = currentUser.getMoviesDisplayed();

        if (currentUser != null) {
            currentUser.setMoviesDisplayed(null);
            programInfo.getCurrentUser().setUserNullOutput(true);
            Output.display(currentUser, TYPE.ERRROR, output);
            programInfo.getCurrentUser().setUserNullOutput(false);
            currentUser.setMoviesDisplayed(movieBackup);

        } else {
            Output.display(currentUser, TYPE.ERRROR, output);
        }

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
            //System.out.println("size la database " + Database.getInstance().getMoviesCollection().size() + " sile la current users " + currentUser.getMoviesDisplayed().size() + "\n" );
            commandOutput.putPOJO("currentMoviesList", currentUser.getMoviesDisplayed());
            ArrayNode currentMoviesList= commandOutput.putArray("currentMoviesList");
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
//            user.putPOJO("purchasedMovies", currentUser.getPurchasedMovies());
//            user.putPOJO("watchedMovies", currentUser.getWatchedMovies());
//            user.putPOJO("likedMovies", currentUser.getLikedMovies());
//            user.putPOJO("ratedMovies", currentUser.getRatedMovies());
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
        if (currentUser != null) {
            System.out.println("ON LOGIN USER HAS " + currentUser.getCredentials().getAccountType() + "  " +currentUser.getTokensCount() +  "\n");
        }
    }



}
