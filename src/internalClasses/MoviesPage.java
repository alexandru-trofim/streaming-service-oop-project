package internalClasses;

import com.fasterxml.jackson.databind.node.ArrayNode;
import databaseElements.Database;
import databaseElements.Movie;
import databaseElements.User;
import output.Output;
import runProgram.ProgramInfo;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoviesPage implements Page{

    public void searchMovie(Action currentAction, User currentUser,ArrayNode output) {
        ArrayList<Movie> moviesDisplayedBackup = currentUser.getMoviesDisplayed();

        Movie movieSearched =  currentUser.getMoviesDisplayed().stream()
                .filter((movie) -> movie.getName().equals(currentAction.getStartsWith()))
                .findFirst().orElse(null);
        if (movieSearched == null) {
            //to display nothing
            currentUser.setMoviesDisplayed(null);
            Output.display(currentUser, Output.TYPE.NO_ERRROR, output);
            currentUser.setMoviesDisplayed(moviesDisplayedBackup);
            return;
        }
        ArrayList<Movie> justTheMovieFound = new ArrayList<>();
        justTheMovieFound.add(movieSearched);
        currentUser.setMoviesDisplayed(justTheMovieFound);
        //restore the moviesDisplayed
        currentUser.setMoviesDisplayed(moviesDisplayedBackup);

    }
    public void displayWhenSwitchPage(ProgramInfo programInfo, ArrayNode output) {
        Database database = Database.getInstance();
        User currentUser = programInfo.getCurrentUser();
        if (currentUser == null) {
            System.out.println("!!ERROR!! CURRENT USER IS NULL IN MOVIES");
            return;
        }
        //first we filter the movies collection by eliminatin the films banned for the user
        List<Movie> movieList = database.getMoviesCollection().stream()
                .filter((movie) -> movie.getCountriesBanned().stream()
                        .noneMatch((country) -> {
                            return country.equals(currentUser.getCredentials().getCountry());
                        })).toList();

        //put the filtred list in moviesDisplayed
        currentUser.setMoviesDisplayed(new ArrayList<>(movieList));

        //display the movies on display
        Output.display(programInfo.getCurrentUser(), Output.TYPE.NO_ERRROR, output);
    }

    public void filterMovies(ProgramInfo programInfo, Action currentAction, ArrayNode output) {

    }
    @Override
    public void executeAction(Action currentAction, ProgramInfo programInfo, ArrayNode output) {
        switch (currentAction.getFeature()) {
            case "search" -> searchMovie(currentAction, programInfo.getCurrentUser(), output);
        }

    }
}
