import core.data.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;

public class Movie {
    private String id;
    private String title;
    private String description;
    private int releaseYear;
    private String ageRating;
    private int runtime;
    private ArrayList<String> genres;
    private double imdbScore;
    private double tmdbScore;
    private double averageScore;

    public Movie(String id, String title, String description, int releaseYear, String ageRating, int runtime, ArrayList<String> genres, double imdbScore, double tmdbScore) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.ageRating = ageRating;
        this.runtime = runtime;
        this.genres = genres;
        this.imdbScore = imdbScore;
        this.tmdbScore = tmdbScore;
        this.averageScore = Math.floor(((imdbScore + tmdbScore) / 2) * 100) / 100;
    }

    //getters
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public int getReleaseYear() {
        return releaseYear;
    }
    public String getAgeRating() {
        return ageRating;
    }
    public int getRuntime() {
        return runtime;
    }
    public ArrayList<String> getGenres() {
        return genres;
    }
    public double getImdbScore() {
        return imdbScore;
    }
    public double getTmdbScore() {
        return tmdbScore;
    }
    public double getAverageScore() {
        return averageScore;
    }
    //methods
    public void print() {
        System.out.println("ID: " + id + " Title: " + title + " Description: " + description + " Release Year: " + releaseYear + " Age Rating: " + ageRating + " Runtime: " + runtime + " Genres: " + genres);
    }

    public static void main(String[] args) {

    }

}