package fileio;


public class SortInput {
    private String rating;
    private String duration;

    public SortInput() {
    }

    /**
     *
     * @return returns the rating of SortInput Object
     */
    public String getRating() {
        return rating;
    }

    /**
     * Sets the rating of SortInput Object
     * @param rating the rating to be set
     */
    public void setRating(final String rating) {
        this.rating = rating;
    }

    /**
     *
     * @return returns the duration of SortInput Object
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Sets the duration of SortInput Object
     * @param duration the duration to be set
     */
    public void setDuration(final String duration) {
        this.duration = duration;
    }
}
