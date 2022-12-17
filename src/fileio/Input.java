package fileio;


import java.util.ArrayList;

public class Input {
    private ArrayList<UserInput> users;
    private ArrayList<MovieInput> movies;
    private ArrayList<ActionInput> actions;

    public Input() {

    }

    /**
     *
     * @return return the ArrayList of UserInput from Input
     */
    public ArrayList<UserInput> getUsers() {
        return users;
    }

    /**
     * Sets the ArrayList of UserInput in Input
     * @param users the array to be set
     */
    public void setUsers(final ArrayList<UserInput> users) {
        this.users = users;
    }

    /**
     *
     * @return return the ArrayList of Movie Input from Input
     */
    public ArrayList<MovieInput> getMovies() {
        return movies;
    }

    /**
     * Sets the ArrayList of MovieInput in Input
     * @param movies the array to be set
     */
    public void setMovies(final ArrayList<MovieInput> movies) {
        this.movies = movies;
    }

    /**
     *
     * @return return the ArrayList of ActionInput from Input
     */
    public ArrayList<ActionInput> getActions() {
        return actions;
    }

    /**
     * Sets the ArrayList of ActionInput in Input
     * @param actions the array to be set
     */
    public void setActions(final ArrayList<ActionInput> actions) {
        this.actions = actions;
    }
}
