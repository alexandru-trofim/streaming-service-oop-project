package internalClasses;

import com.fasterxml.jackson.databind.node.ArrayNode;
import databaseElements.Database;
import databaseElements.Movie;
import databaseElements.User;
import fileio.FiltersInput;
import output.Output;
import runProgram.ProgramInfo;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MoviesPage implements Page{

    public void searchMovie(Action currentAction, User currentUser,ArrayNode output) {
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
        if (currentUser.getMoviesDisplayed() == null) {
            System.out.println("MOVIES DISPLAYED IS NULL\n");
        }
        //display the movies on display
        Output.display(programInfo.getCurrentUser(), Output.TYPE.NO_ERRROR, output);
    }

    public boolean checkActorPresent(Movie movie, ArrayList<String> actors) {
        for(String movieActor : movie.getActors()) {
            for(String filterActor : actors) {
                if (movieActor.equals(filterActor)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkGenresPresent(Movie movie, ArrayList<String> genres) {
        for(String movieGenre : movie.getGenres()) {
            for(String filterGenre: genres) {
                if (movieGenre.equals(filterGenre)) {
                    return true;
                }
            }
        }
        return false;
    }
    public List<Movie> filterActors(List<Movie> newMovieDisplayed, Action currentAction,
                                                                    ProgramInfo programInfo) {
        List<Movie> filteredMovies = new ArrayList<>();
        if (currentAction.getFilters().getContains().getActors() == null) {
            return newMovieDisplayed;
        }
        ArrayList<String> filterActors = currentAction.getFilters().getContains().getActors();
        //iterate through every movie
        for(Movie movie : newMovieDisplayed) {
           if (checkActorPresent(movie, filterActors)) {
               filteredMovies.add(movie);
           }
        }
       /* for(String actorWeNeed: currentAction.getFilters().getContains().getActors()) {
               newMovieDisplayed = newMovieDisplayed.stream()
                       .filter((movie) -> movie.getActors().stream()
                               .anyMatch((movieActor) ->{
                                   return movieActor.equals(actorWeNeed);
                               })).toList();

        } */

        return filteredMovies;
    }

    public List<Movie> filterGenres(List<Movie> newMovieDisplayed, Action currentAction,
                                                                    ProgramInfo programInfo) {

        List<Movie> filteredMovies = new ArrayList<>();
        if (currentAction.getFilters().getContains().getGenre() == null) {
            return newMovieDisplayed;
        }
        ArrayList<String> filterGenres = currentAction.getFilters().getContains().getGenre();
        //iterate through every movie
        for(Movie movie : newMovieDisplayed) {
            if (checkGenresPresent(movie, filterGenres)) {
                filteredMovies.add(movie);
            }
        }
        return filteredMovies;
    }

    public void filterMovies(ProgramInfo programInfo, Action currentAction, ArrayNode output) {
       //when we filter the movies we should erase all the filters and apply on all movies
        Database database = Database.getInstance();
        User currentUser = programInfo.getCurrentUser();

        //first we filter the movies collection by eliminatin the films banned for the user
        List<Movie> movieList = database.getMoviesCollection().stream()
                .filter((movie) -> movie.getCountriesBanned().stream()
                        .noneMatch((country) -> {
                            return country.equals(currentUser.getCredentials().getCountry());
                        })).toList();

        //put the filtred list in moviesDisplayed
        currentUser.setMoviesDisplayed(new ArrayList<>(movieList));


        FiltersInput filters = currentAction.getFilters();
        List<Movie> sortedMovies = null;
        List<Movie> moviesDisplayed = programInfo.getCurrentUser().getMoviesDisplayed();

        //first of all we filter our movies
        if (filters.getContains() != null && moviesDisplayed != null) {
            moviesDisplayed = filterActors(moviesDisplayed, currentAction, programInfo);
            System.out.println("AFTER FILTER WE HAVE " + moviesDisplayed.size() + " MOVIES\n");
            moviesDisplayed = filterGenres(moviesDisplayed, currentAction, programInfo);
            System.out.println("AFTER FILTER WE HAVE " + moviesDisplayed.size() + " MOVIES\n");
        }

        //here we sort the movies we check all possible casses and sort the list with streams
        if(filters.getSort() != null) {
            //here we certinly have sort
            if(filters.getSort().getRating() != null
            && filters.getSort().getDuration() == null) {
                //the case when we have just sort by rating
                if (filters.getSort().getRating().equals("decreasing")) {

                    sortedMovies = moviesDisplayed.stream()
                            .sorted(Comparator.comparing(Movie::getRating).reversed()).toList();

                } else if (filters.getSort().getRating().equals("increasing")) {
                    sortedMovies = moviesDisplayed.stream()
                            .sorted(Comparator.comparing(Movie::getRating)).toList();
                }
            } else if(filters.getSort().getRating() == null
                    && filters.getSort().getDuration() != null) {
                //the case when we have just sort by duration
                if (filters.getSort().getDuration().equals("decreasing")) {

                    sortedMovies = moviesDisplayed.stream()
                            .sorted(Comparator.comparing(Movie::getDuration).reversed()).toList();

                } else if (filters.getSort().getDuration().equals("increasing")) {
                    sortedMovies = moviesDisplayed.stream()
                            .sorted(Comparator.comparing(Movie::getDuration)).toList();
                }
            } else if(filters.getSort().getRating() != null
                && filters.getSort().getDuration() != null) {
                //here we have another four cases
                // rating decreasing; duration decreasing
                if (filters.getSort().getRating().equals("decreasing")
                && filters.getSort().getDuration().equals("decreasing")) {

                    sortedMovies = moviesDisplayed.stream()
                            .sorted(Comparator.comparing(Movie::getRating).reversed()
                                    .thenComparing(Movie::getDuration).reversed()).toList();
                }else if (filters.getSort().getRating().equals("increasing")
                        && filters.getSort().getDuration().equals("increasing")) {

                    sortedMovies = moviesDisplayed.stream()
                            .sorted(Comparator.comparing(Movie::getRating)
                            .thenComparing(Movie::getDuration)).toList();
                } else if (filters.getSort().getRating().equals("decreasing")
                    && filters.getSort().getDuration().equals("increasing")) {

                    sortedMovies = moviesDisplayed.stream()
                        .sorted(Comparator.comparing(Movie::getRating).reversed()
                                .thenComparing(Movie::getDuration)).toList();
                } else if (filters.getSort().getRating().equals("increasing")
                    && filters.getSort().getDuration().equals("decreasing")) {

                    sortedMovies = moviesDisplayed.stream()
                        .sorted(Comparator.comparing(Movie::getDuration).reversed()
                                .thenComparing(Movie::getRating)).toList();
                   /* moviesDisplayed.sort(new Comparator<Movie>() {
                        @Override
                        public int compare(Movie o1, Movie o2) {

                            if (o2.getDuration() != o1.getDuration()) {
                                return  Integer.compare(o2.getDuration(), o1.getDuration());
                            }

                            if (o2.getRating() != o1.getRating()) {
                                return Double.compare(o1.getRating(), o2.getRating());
                            }

                            return 0;
                        }
                    });
                    sortedMovies = moviesDisplayed;*/
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
    @Override
    public void executeAction(Action currentAction, ProgramInfo programInfo, ArrayNode output) {
        switch (currentAction.getFeature()) {
            case "search" -> searchMovie(currentAction, programInfo.getCurrentUser(), output);
            case "filter" -> filterMovies(programInfo, currentAction, output);
            default -> {
                Output.outputError(programInfo, output, programInfo.getCurrentUser());
                System.out.println("ERROR WRONG COMMAND\n");
            }
        }

    }
}
