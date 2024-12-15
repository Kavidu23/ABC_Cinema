package model;

public class movie {
    private int id; // Primary key
    private String name; // Movie name
    private String image; // Path or URL of the movie image
    private String trailer; // Path or URL of the trailer
    private String bannerImage; // Path or URL of the movie banner image

    // Default constructor
    public movie() {
    }

    // Getters and Setters for all fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    // Override toString() (optional, for debugging purposes)//I used this code to debugging purposes.
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", trailer='" + trailer + '\'' +
                ", bannerImage='" + bannerImage + '\'' +
                '}';
    }
}