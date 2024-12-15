package model;

public class Upcoming {
    private int id;
    private String movieName;
    private String trailerLink;
    private String movieImage;

    public Upcoming(int id, String movieName,  String trailerLink, String movieImage) {
        this.id = id;
        this.movieName = movieName;
        this.movieImage = movieImage;
        this.trailerLink = trailerLink;
        
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }
}
