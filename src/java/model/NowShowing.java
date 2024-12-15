package model;

public class NowShowing {
    private int id;
    private String movieName;
    private String movieImage;
    private String trailer; // New field for the trailer URL

    // Constructor
    public NowShowing(int id, String movieName, String movieImage, String trailer) {
        this.id = id;
        this.movieName = movieName;
        this.movieImage = movieImage;
        this.trailer = trailer;
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

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
}
