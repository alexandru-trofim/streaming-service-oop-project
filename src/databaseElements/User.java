package databaseElements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.CredentialsInput;
import fileio.UserInput;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {

    private @Getter @Setter Credentials credentials;
    private @Getter @Setter int tokensCount;
    private @Getter @Setter int numFreePremiumMovies;
    private @Getter @Setter ArrayList<Movie> purchasedMovies;
    private @Getter @Setter ArrayList<Movie> watchedMovies;
    private @Getter @Setter ArrayList<Movie> likedMovies;
    private @Getter @Setter ArrayList<Movie> ratedMovies;
    @JsonIgnore
    private @Getter @Setter ArrayList<Movie> moviesDisplayed;
    @JsonIgnore
    private @Getter @Setter boolean userNullOutput;





    public User(UserInput userInput) {
        credentials = new Credentials(userInput.getCredentials());
        tokensCount = 0;
        numFreePremiumMovies = 15;
        purchasedMovies = new ArrayList<>();
        watchedMovies = new ArrayList<>();
        likedMovies = new ArrayList<>();
        ratedMovies = new ArrayList<>();
    }

    public User(CredentialsInput credentialsInput) {
        credentials = new Credentials(credentialsInput);
        tokensCount = 0;
        numFreePremiumMovies = 15;
        purchasedMovies = new ArrayList<>();
        watchedMovies = new ArrayList<>();
        likedMovies = new ArrayList<>();
        ratedMovies = new ArrayList<>();
    }

}
