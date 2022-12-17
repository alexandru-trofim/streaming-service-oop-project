package fileio;


import java.util.ArrayList;

public class ContainsInput {
    private ArrayList<String> actors;
    private ArrayList<String> genre;

    public ContainsInput() {
    }

    /**
     *
     * @return returns the ArrayList of actors from ContainsInput
     */
    public ArrayList<String> getActors() {
        return actors;
    }

    /**
     * Sets the ArrayList of actors in ContainsInput
     * @param actors array to be set
     */
    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    /**
     *
     * @return returns the ArrayList of genres from ContainsInput
     */
    public ArrayList<String> getGenres() {
        return genre;
    }

    /**
     * Sets the ArrayList of genre in ContainsInput
     * @param genre array to be set
     */
    public void setGenre(final ArrayList<String> genre) {
        this.genre = genre;
    }
}
