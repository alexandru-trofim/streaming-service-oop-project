package action.classes.pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import database.elements.Database;
import database.elements.Movie;
import database.elements.User;
import fileio.FiltersInput;
import action.classes.Action;
import output.Output;
import run.program.ProgramInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MoviesPage implements Page {

    /**
     * Erares previous filters by taking all the movies from the database and eliminating
     * all the banned movies for the currentUser
     * @param programInfo The ProgramInfo object that stores the current user,
     *                    the current page and the movie on seeDetails page.
     * @return returns the default ArrayList of movies shown to the currentUser
     */
    public ArrayList<Movie> erasePreviousFilters(final ProgramInfo programInfo) {
        Database database = Database.getInstance();
        User currentUser = programInfo.getCurrentUser();

        //we filter the movies collection by eliminating the films banned for the user
        List<Movie> movieList = database.getMoviesCollection().stream()
                .filter((movie) -> movie.getCountriesBanned().stream()
                        .noneMatch((country) -> {
                            return country.equals(currentUser.getCredentials().getCountry());
                        })).toList();

        return  new ArrayList<>(movieList);
    }

    /**
     * Puts all the movies that the user can see in the MoviesDisplayed array
     * and puts them into the output
     * @param programInfo The ProgramInfo object that stores the current user,
     *                    the current page and the movie on seeDetails page.
     * @param output The output ArrayNode where the output is written.
     */
    public void displayWhenSwitchPage(final ProgramInfo programInfo, final ArrayNode output) {
        User currentUser = programInfo.getCurrentUser();
        //put in the MoviesDisplayed the movie list without banned films
        currentUser.setMoviesDisplayed(erasePreviousFilters(programInfo));
        //display the movies on display
        Output.display(programInfo.getCurrentUser(), Output.TYPE.NO_ERRROR, output);
    }

    /**
     * Filters the current movies list by the actors that are given as input
     * in currentAction object
     *
     * @param movieDisplayed the movie list of the movies displayed to the current user.
     * @param currentAction the current action.
     * @return returns the new filtred movie list.
     */
    public List<Movie> filterActors(final List<Movie> movieDisplayed,
                                                final Action currentAction) {

        List<Movie> filteredMovies = new ArrayList<>();

        if (currentAction.getFilters().getContains().getActors() == null) {
            return movieDisplayed;
        }

        for (String actorWeNeed: currentAction.getFilters().getContains().getActors()) {
               filteredMovies = movieDisplayed.stream()
                       .filter((movie) -> movie.getActors().stream()
                               .anyMatch((movieActor) -> {
                                   return movieActor.equals(actorWeNeed);
                               })).toList();
        }
        return filteredMovies;
    }

    /**
     * Filters the current movies list by the genres that are given as input
     * in currentAction object
     * @param movieDisplayed the movie list of the movies displayed to the current user.
     * @param currentAction the current action.
     * @return returns the new filtred movie list.
     */
    public List<Movie> filterGenres(final List<Movie> movieDisplayed,
                                    final Action currentAction, final ProgramInfo programInfo) {
        List<Movie> filteredMovies = new ArrayList<>();

        if (currentAction.getFilters().getContains().getGenres() == null) {
            return movieDisplayed;
        }

        for (String genreWeNeed: currentAction.getFilters().getContains().getGenres()) {
            filteredMovies = movieDisplayed.stream()
                    .filter((movie) -> movie.getGenres().stream()
                            .anyMatch((movieGenre) -> {
                                return movieGenre.equals(genreWeNeed);
                            })).toList();
        }
        return filteredMovies;
    }

    /**
     * Filters and sorts the movies from the movies displayed to the current user.
     * @param currentAction The current action.
     * @param programInfo The ProgramInfo object that stores the current user,
     *                    the current page and the movie on seeDetails page.
     * @param output The output ArrayNode where the output is written.
     */
    public void filterSortMovies(final ProgramInfo programInfo, final Action currentAction,
                                 final ArrayNode output) {
        User currentUser = programInfo.getCurrentUser();

        //when we filter the movie list we should erase previous filters
        currentUser.setMoviesDisplayed(erasePreviousFilters(programInfo));

        FiltersInput filters = currentAction.getFilters();
        List<Movie> sortedMovies = null;
        List<Movie> moviesDisplayed = programInfo.getCurrentUser().getMoviesDisplayed();

        //first of all we filter our movies
        if (filters.getContains() != null && moviesDisplayed != null) {
            moviesDisplayed = filterActors(moviesDisplayed, currentAction);
            moviesDisplayed = filterGenres(moviesDisplayed, currentAction, programInfo);
        }

        //here we sort the movies we check all possible casses and sort the list with streams
        if (filters.getSort() != null) {
            //here we certinly have sort
            if (filters.getSort().getRating() != null
            && filters.getSort().getDuration() == null) {
                //the case when we have just sort by rating
                if (filters.getSort().getRating().equals("decreasing")) {

                    sortedMovies = moviesDisplayed.stream()
                            .sorted(Comparator.comparing(Movie::getRating).reversed()).toList();

                } else if (filters.getSort().getRating().equals("increasing")) {

                    sortedMovies = moviesDisplayed.stream()
                            .sorted(Comparator.comparing(Movie::getRating)).toList();

                }
            } else if (filters.getSort().getRating() == null
                    && filters.getSort().getDuration() != null) {
                //the case when we have just sort by duration
                if (filters.getSort().getDuration().equals("decreasing")) {

                    sortedMovies = moviesDisplayed.stream()
                            .sorted(Comparator.comparing(Movie::getDuration).reversed()).toList();

                } else if (filters.getSort().getDuration().equals("increasing")) {

                    sortedMovies = moviesDisplayed.stream()
                            .sorted(Comparator.comparing(Movie::getDuration)).toList();

                }
            } else if (filters.getSort().getRating() != null
                && filters.getSort().getDuration() != null) {

                //here we have another four cases when both criteria exists
                if (filters.getSort().getRating().equals("decreasing")
                && filters.getSort().getDuration().equals("decreasing")) {

                    sortedMovies = moviesDisplayed.stream()
                            .sorted(Comparator.comparing(Movie::getDuration).reversed()
                                    .thenComparing(Movie::getRating).reversed()).toList();

                } else if (filters.getSort().getRating().equals("increasing")
                        && filters.getSort().getDuration().equals("increasing")) {

                    sortedMovies = moviesDisplayed.stream()
                            .sorted(Comparator.comparing(Movie::getDuration)
                            .thenComparing(Movie::getRating)).toList();

                } else if (filters.getSort().getRating().equals("decreasing")
                    && filters.getSort().getDuration().equals("increasing")) {

                    sortedMovies = moviesDisplayed.stream()
                        .sorted(Comparator.comparing(Movie::getDuration)
                                .thenComparing(Movie::getRating).reversed()).toList();

                } else if (filters.getSort().getRating().equals("increasing")
                    && filters.getSort().getDuration().equals("decreasing")) {

                    sortedMovies = moviesDisplayed.stream()
                        .sorted(Comparator.comparing(Movie::getDuration).reversed()
                                .thenComparing(Movie::getRating)).toList();
                }
            }
        }
        ArrayList<Movie> convertedSortedMovies = null;
        if (sortedMovies != null) {
            convertedSortedMovies = new ArrayList<>(sortedMovies);
        } else {
            convertedSortedMovies = new ArrayList<>(moviesDisplayed);
        }
        programInfo.getCurrentUser().setMoviesDisplayed(convertedSortedMovies);

        //display the movies on display
        Output.display(programInfo.getCurrentUser(), Output.TYPE.NO_ERRROR, output);
    }

    /**
     * Searches a movie in the movie list of the current user and outputs it if
     * the movie is present in the list.
     * @param currentAction The current action.
     * @param currentUser the current user.
     * @param output The output ArrayNode where the output is written.
     */
    public void searchMovie(final Action currentAction, final User currentUser,
                            final ArrayNode output) {
        ArrayList<Movie> moviesDisplayedBackup = currentUser.getMoviesDisplayed();

        Movie movieSearched =  currentUser.getMoviesDisplayed().stream()
                .filter((movie) -> movie.getName().startsWith(currentAction.getStartsWith()))
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
        Output.display(currentUser, Output.TYPE.NO_ERRROR, output);
        //restore the moviesDisplayed
        currentUser.setMoviesDisplayed(moviesDisplayedBackup);
    }

    /**
     * Executes the "search" or "filter" actions depending on the
     * currentAction. If the currentAction contains another action it throws
     * an error.
     * @param currentAction The current action.
     * @param programInfo The ProgramInfo object that stores the current user,
     *                    the current page and the movie on seeDetails page.
     * @param output The output ArrayNode where the output is written.
     */
    @Override
    public void executeAction(final Action currentAction, final ProgramInfo programInfo,
                                                                    final ArrayNode output) {
        switch (currentAction.getFeature()) {
            case "search" -> searchMovie(currentAction, programInfo.getCurrentUser(), output);
            case "filter" -> filterSortMovies(programInfo, currentAction, output);
            default -> {
                Output.outputError(programInfo, output, programInfo.getCurrentUser());
                System.out.println("ERROR WRONG COMMAND\n");
            }
        }

    }
}
