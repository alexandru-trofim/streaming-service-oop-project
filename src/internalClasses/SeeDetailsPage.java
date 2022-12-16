package internalClasses;

import com.fasterxml.jackson.databind.node.ArrayNode;
import databaseElements.Movie;
import databaseElements.User;
import output.Output;
import runProgram.ProgramInfo;

import java.util.ArrayList;
import java.util.Arrays;

public class SeeDetailsPage implements Page{





    public void purchase(Action currentAction, ProgramInfo programInfo, ArrayNode output)  {
        User currentUser = programInfo.getCurrentUser();
        if (currentUser.getPurchasedMovies().contains(programInfo.getMovieDetails())) {
            System.out.println(programInfo.getMovieDetails().getName() + " IS ALREADY PURCHASED\n");

            Output.outputError(programInfo, output, currentUser);
            return;
        }

        //decrease number of free premium movies or tokens
        if (currentUser.getCredentials().getAccountType().equals("premium")) {
            if(currentUser.getNumFreePremiumMovies() > 0 ) {
                currentUser.setNumFreePremiumMovies(currentUser.getNumFreePremiumMovies() - 1);
            } else if (currentUser.getTokensCount() > 1){
                currentUser.setTokensCount(currentUser.getTokensCount() - 2);
            } else {
                System.out.println("NOT ENOUGH RESOURCES TO BUY MOVIE");
                Output.outputError(programInfo, output, currentUser);
                return;
            }
        } else if (currentUser.getTokensCount() > 1){
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



    public void watch(Action currentAction, ProgramInfo programInfo, ArrayNode output)  {
        User currentUser = programInfo.getCurrentUser();
        if (currentUser.getWatchedMovies().contains(programInfo.getMovieDetails())) {
            System.out.println(programInfo.getMovieDetails().getName() + " IS ALREADY WATCHED\n");
            Output.outputError(programInfo, output, currentUser);
            return;
        }
        if (!currentUser.getPurchasedMovies().contains(programInfo.getMovieDetails())) {
            System.out.println(programInfo.getMovieDetails().getName() + " IS NOT PURCHASED!! CAN'T WATCH\n");
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

    public void like(Action currentAction, ProgramInfo programInfo, ArrayNode output)  {
        System.out.println("LIKE LIKE LIKE LIKE\n");
        User currentUser = programInfo.getCurrentUser();
        if (currentUser.getLikedMovies().contains(programInfo.getMovieDetails())) {
            System.out.println(programInfo.getMovieDetails().getName() + " IS ALREADY LIKED\n");
            Output.outputError(programInfo, output, currentUser);
            return;
        }
        if (!currentUser.getWatchedMovies().contains(programInfo.getMovieDetails())) {
            System.out.println(programInfo.getMovieDetails().getName() + " IS NOT WATCHED!! CAN'T LIKE\n");
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

    public void rate(Action currentAction, ProgramInfo programInfo, ArrayNode output) {
        User currentUser = programInfo.getCurrentUser();
        if (!currentUser.getWatchedMovies().contains(programInfo.getMovieDetails())) {
            System.out.println(programInfo.getMovieDetails().getName() + " IS NOT WATCHED!! CAN'T RATE\n");
            Output.outputError(programInfo, output, currentUser);
            return;
        }
        if (currentAction.getRate() < 0 || currentAction.getRate() > 5) {
            System.out.println("RATE OUT OF RANGE \n");
            Output.outputError(programInfo, output, currentUser);
            return;
        }
        if (currentUser.getRatedMovies().contains(programInfo.getMovieDetails())) {
            System.out.println(programInfo.getMovieDetails().getName() + " IS ALREADY RATED!! CAN'T RATE\n");
            Output.outputError(programInfo, output, currentUser);
            return;
        }
        programInfo.getMovieDetails()
                .getMovieRatings().add(currentAction.getRate());
        programInfo.getMovieDetails()
                .setNumRatings(programInfo.getMovieDetails().getNumRatings() + 1);

        Double ratingSum = (double) programInfo.getMovieDetails().getMovieRatings().stream()
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

    @Override
    public void executeAction(Action currentAction, ProgramInfo programInfo, ArrayNode output) {
        if (programInfo.getMovieDetails() == null) {
            System.out.println("MOVIE NOT FOUND");
            Output.outputError(programInfo, output, programInfo.getCurrentUser());
        }
        switch (currentAction.getFeature()) {
            case "purchase" -> {
                purchase(currentAction, programInfo, output);
            }
            case "watch" -> {
                watch(currentAction, programInfo, output);
            }
            case "like" -> {
                like(currentAction, programInfo, output);
            }
            case "rate" -> {
                rate(currentAction, programInfo, output);
            }
        }
    }
}
