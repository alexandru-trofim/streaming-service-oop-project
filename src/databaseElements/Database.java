package databaseElements;

import fileio.Input;
import fileio.MovieInput;
import fileio.UserInput;
import lombok.Getter;
import lombok.Setter;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class Database {

    private static Database database = null;

    private @Getter @Setter ArrayList<Movie> moviesCollection;
    private @Getter @Setter ArrayList<User> usersCollection;

    private Database() {
       moviesCollection = new ArrayList<>();
       usersCollection = new ArrayList<>();
    }

    public static Database getInstance () {
        if (database == null) {
            database = new Database();
        }

        return database;
    }

    public void addUserFromInput(UserInput userInput) {
        if (usersCollection == null) {
            System.out.println("!!ERROR!! USER COLLECTION IS NULL" + "\n");
            return;
        }
        User newUser = new User(userInput);
        usersCollection.add(newUser);
    }

    public void addUser(User newUser) {
        if (usersCollection == null) {
            System.out.println("!!ERROR!! USER COLLECTION IS NULL" + "\n");
            return;
        }
        usersCollection.add(newUser);
    }

    public void addMovieFromInput(MovieInput movieInput) {
        if (moviesCollection == null) {
            System.out.println("!!ERROR!! MOVIES COLLECTION IS NULL" + "\n");
            return;
        }
        Movie newMovie = new Movie(movieInput);
        moviesCollection.add(newMovie);
    }

    public void load(final Input inputData) {
        //take all the info from the input put in the database and action array

        inputData.getUsers().forEach((userInput) -> database.addUserFromInput(userInput));
        inputData.getMovies().forEach((movieInput) -> database.addMovieFromInput(movieInput));
        //fac la fel pentru actions
    }

    public void empty() {
        database = null;
        moviesCollection = null;
        usersCollection = null;
    }

    public User findUser(String username, String password) {
        return usersCollection.stream()
        .filter(user -> username.equals(user.getCredentials().getName())
                && password.equals(user.getCredentials().getPassword()))
                .findFirst().orElse(null);
    }

}
