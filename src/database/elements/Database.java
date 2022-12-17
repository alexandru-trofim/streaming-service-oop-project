package database.elements;

import fileio.Input;
import fileio.MovieInput;
import fileio.UserInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public final class Database {

    private static Database database;

    private @Getter @Setter ArrayList<Movie> moviesCollection;
    private @Getter @Setter ArrayList<User> usersCollection;

    private Database() {
       moviesCollection = new ArrayList<>();
       usersCollection = new ArrayList<>();
    }

    /**
     *
     * @return returns the signle instance of the Database class because
     * it is a Singleton class.
     */
    public static Database getInstance() {
        if (database == null) {
            database = new Database();
        }

        return database;
    }

    /**
     * Adds a user to the database and takes as input a UserInput object.
     * @param userInput the user information.
     */
    public void addUserFromInput(final UserInput userInput) {
        if (usersCollection == null) {
            System.out.println("!!ERROR!! USER COLLECTION IS NULL" + "\n");
            return;
        }
        User newUser = new User(userInput);
        usersCollection.add(newUser);
    }

    /**
     * Adds a user to the database.
     * @param newUser The new user.
     */
    public void addUser(final User newUser) {
        if (usersCollection == null) {
            System.out.println("!!ERROR!! USER COLLECTION IS NULL" + "\n");
            return;
        }
        usersCollection.add(newUser);
    }

    /**
     * Adds a movie to the database and takes as input a MovieInput object.
     * @param movieInput The movie information.
     */
    public void addMovieFromInput(final MovieInput movieInput) {
        if (moviesCollection == null) {
            System.out.println("!!ERROR!! MOVIES COLLECTION IS NULL" + "\n");
            return;
        }
        Movie newMovie = new Movie(movieInput);
        moviesCollection.add(newMovie);
    }

    /**
     * Takes the input data of the program and adds all the users and all the
     * movies into the database.
     * @param inputData the input data of the program.
     */
    public void load(final Input inputData) {
        //take all the info from the input put in the database and action array

        inputData.getUsers().forEach((userInput) -> database.addUserFromInput(userInput));
        inputData.getMovies().forEach((movieInput) -> database.addMovieFromInput(movieInput));
        //fac la fel pentru actions
    }

    /**
     * Erases the database.
     */
    public void empty() {
        database = null;
        moviesCollection = null;
        usersCollection = null;
    }

    /**
     * Finds a user in the database by the username and the password.
     * @param username
     * @param password
     * @return returns the found user, otherwise returns null
     */
    public User findUser(final String username, final String password) {
        return usersCollection.stream()
        .filter(user -> username.equals(user.getCredentials().getName())
                && password.equals(user.getCredentials().getPassword()))
                .findFirst().orElse(null);
    }

}
