package Exercises;

import java.time.LocalDate;

public class Book extends Readable{
   private String author;
   private String genre;

    public Book(int ISBN, String title, LocalDate published, int pages, String author, String genre) {
        super(ISBN, title, published, pages);
        this.author = author;
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                "} " + super.toString();
    }
}
