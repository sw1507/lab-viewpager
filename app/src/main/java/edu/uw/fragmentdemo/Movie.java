package edu.uw.fragmentdemo;

/**
 * A class that represents information about a movie.
 */
public class Movie {
    public String title;
    public String year;
    public String description;
    public String url;

    public Movie(String title, String year, String description, String url) {
        this.title = title;
        this.year = year.substring(0,4);
        this.description = description;
        this.url = url;
    }

    //default constructor; empty movie
    public Movie(){}

    public String toString() { return this.title + " (" + this.year +")"; }
}