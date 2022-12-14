package fileio;

import lombok.Getter;
import lombok.Setter;

public class SortInput {
    private String rating;
    private String duration;

    public SortInput() {
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
