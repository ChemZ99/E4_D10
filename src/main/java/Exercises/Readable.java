package Exercises;

import java.time.LocalDate;

public abstract class Readable {
    private int ISBN;
    private String title;
    private LocalDate published;
    private int pages;

    public Readable(int ISBN, String title, LocalDate published, int pages) {
        this.ISBN = ISBN;
        this.title = title;
        this.published = published;
        this.pages = pages;
    }

    public int getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getPublished() {
        return published;
    }

    public int getPages() {
        return pages;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublished(LocalDate published) {
        this.published = published;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "Readable{" +
                "ISBN=" + ISBN +
                ", title='" + title + '\'' +
                ", published=" + published +
                ", pages=" + pages +
                '}';
    }
}
