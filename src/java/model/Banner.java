package model;

public class Banner {
    private int id;
    private String trailer;
    private String bannerImage;
    private String movieName; // New field for movie name

    // Constructor to initialize the Banner object
    public Banner(int id, String trailer, String bannerImage, String movieName) {
        this.id = id;
        this.trailer = trailer;
        this.bannerImage = bannerImage;
        this.movieName = movieName;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}
