package database.elements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.MovieInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Movie {

    private @Getter @Setter String name;
    private @Getter @Setter int year;
    private @Getter @Setter int duration;
    private @Getter @Setter ArrayList<String> genres;
    private @Getter @Setter ArrayList<String> actors;
    private @Getter @Setter ArrayList<String> countriesBanned;
    private @Getter @Setter int numLikes;
    private @Getter @Setter double rating;
    private @Getter @Setter int numRatings;
    @JsonIgnore
    private @Getter @Setter ArrayList<Integer> movieRatings;


    public Movie(final MovieInput movieInput) {
        name = movieInput.getName();
        year = movieInput.getYear();
        duration = movieInput.getDuration();

        genres = new ArrayList<>();
        genres.addAll(movieInput.getGenres());

        actors = new ArrayList<>();
        actors.addAll(movieInput.getActors());

        countriesBanned = new ArrayList<>();
        countriesBanned.addAll(movieInput.getCountriesBanned());

        movieRatings = new ArrayList<>();
    }
}
