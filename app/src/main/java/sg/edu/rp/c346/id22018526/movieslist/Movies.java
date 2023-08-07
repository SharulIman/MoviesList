package sg.edu.rp.c346.id22018526.movieslist;

import java.io.Serializable;

public class Movies implements Serializable {

    private int id;
    private String title;
    private String genre;
    private int year;
    private String rating;

    public Movies(int id, String title, String genre, int year, String rating) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
    }

    public int get_id() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public String getRating() {
        return rating;
    }

    public void set_id(int newid) {
        newid = id;
    }

    public void setTitle(String newtitle) {
        title = newtitle;
    }

    public void setGenre(String newgenre) {
        genre = newgenre;
    }

    public void setYear(int newyear) {
        year = newyear;
    }

    public void setRating(String newrating) {
        rating = newrating;
    }
}