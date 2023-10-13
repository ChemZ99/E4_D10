package Exercises;

import java.time.LocalDate;

public class Magazine extends Readable {

    private Period period;

    public Magazine(int ISBN, String title, LocalDate published, int pages, Period period) {
        super(ISBN, title, published, pages);
        this.period = period;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
