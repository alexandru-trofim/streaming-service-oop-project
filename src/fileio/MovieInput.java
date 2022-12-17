package fileio;


import java.util.ArrayList;

public class MovieInput {
    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;

    public MovieInput() {
    }

    /**
     *
     * @return returns the name of the MovieInput
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name in MovieInput
     * @param name the name to be set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     *
     * @return returns the year of the MovieInput
     */
    public int  getYear() {
        return year;
    }

    /**
     * Sets the year in MovieInput
     * @param year the year to be set
     */
    public void setYear(final int year) {
        this.year = year;
    }

    /**
     *
     * @return returns the duration of the MovieInput
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration in MovieInput
     * @param duration the duration to be set
     */
    public void setDuration(final int duration) {
        this.duration = duration;
    }

    /**
     *
     * @return returns the genres ArrayList of the MovieInput
     */
    public ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * Sets the genres Arraylist in MovieInput
     * @param genres genres to be set
     */
    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    /**
     *
     * @return returns the actors ArrayList of the MovieInput
     */
    public ArrayList<String> getActors() {
        return actors;
    }

    /**
     * Sets the actors Arraylist in MovieInput
     * @param actors actors to be set
     */
    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    /**
     *
     * @return returns the banned countries ArrayList of the MovieInput
     */
    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    /**
     * Sets the countriesBanned Arraylist in MovieInput
     * @param countriesBanned contriesBanned to be set
     */
    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }
}
