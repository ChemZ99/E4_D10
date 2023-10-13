package Exercises;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Supplier;

public class Application {


    public static void main(String[] args) {
        Supplier<Book> bookSupplier = () -> {
            Faker faker = new Faker(Locale.ITALY);
            Random rndm = new Random();
            return new Book(rndm.nextInt(0, 9999), faker.book().title(), LocalDate.now().minusMonths(rndm.nextLong(0, 1000)), rndm.nextInt(0, 1000), faker.book().author(), faker.book().genre());

        };
        Supplier<Magazine> magazineSupplier = () -> {
            Faker faker = new Faker(Locale.ITALY);
            Random rndm = new Random();
            return new Magazine(rndm.nextInt(0, 9999), faker.book().title(), LocalDate.now().minusMonths(rndm.nextLong(0, 1000)), rndm.nextInt(0, 1000), Period.WEEKLY);
        };
        List<Readable> archive = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            archive.add(bookSupplier.get());
        }
        for (int i = 0; i < 20; i++) {
            archive.add(magazineSupplier.get());
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.ITALY);
        Scanner input = new Scanner(System.in);
        int selector = 9;
        while (selector != 0) {
            try {
                System.out.println("********************************LIST********************************");
                archive.forEach(System.out::println);
                System.out.println("********************************ARCHIVE********************************");
                System.out.println("write 1 to add an element, 2 to remove an element with ISBN, 3 to search an element with ISBN");
                System.out.println(" 4 to search an element with date of publish, 5 to search an element with author");
                System.out.println(" 6 to save the archive, 7 to load the archive from an external file, 0 to exit");
                selector = Integer.parseInt(input.nextLine());
                if (selector > 7) throw new Exception();
                switch (selector) {
                    case 1: {
                        System.out.println("write 1 to add a book, 2 to add a magazine");
                        int discrim = Integer.parseInt(input.nextLine());
                        System.out.println("write the ISBN");
                        int ISBN = Integer.parseInt(input.nextLine());
                        System.out.println("write the title");
                        String title = input.nextLine();
                        System.out.println("write the publish date in the format 'yyyy-MM-dd' ");
                        LocalDate date = LocalDate.parse(input.nextLine(), formatter);
                        System.out.println("write the number of pages");
                        int pages = Integer.parseInt(input.nextLine());
                        if (discrim == 1) {
                            System.out.println("write the author");
                            String author = input.nextLine();
                            System.out.println("write the genre");
                            String genre = input.nextLine();
                            Book newbook = new Book(ISBN, title, date, pages, author, genre);
                            archive.add(newbook);
                        } else if (discrim == 2) {
                            System.out.println("specify if the magazine is weekly,monthly or semestral");
                            String check = input.nextLine().toLowerCase().trim();
                            Period tempP = null;
                            if (check.equals("weekly")) {
                                tempP = Period.WEEKLY;
                            } else if (check.equals("monthly")) {
                                tempP = Period.MONTHLY;
                            } else if (check.equals("semestral")) {
                                tempP = Period.SEMESTRAL;
                            } else System.out.println("comando non riconosciuto");
                            Magazine newMag = new Magazine(ISBN, title, date, pages, tempP);
                            archive.add(newMag);
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("write the ISBN to delete");
                        int isbn = Integer.parseInt(input.nextLine());
                        archive.removeIf(readable -> readable.getISBN() == isbn);
                        break;
                    }
                    case 3: {
                        System.out.println("write the ISBN to search");
                        int isbn = Integer.parseInt(input.nextLine());
                        try {
                            Readable searchresult = archive.stream().filter(readable -> readable.getISBN() == isbn).findFirst().get();
                            System.out.println(searchresult);
                        } catch (NoSuchElementException ex) {
                            System.err.println("no book found");
                        }
                        break;
                    }
                    case 4: {
                        System.out.println("write the date of publish to search in the format 'yyyy-MM-dd' ");
                        LocalDate searchdate = LocalDate.parse(input.nextLine());
                        try {
                            List<Readable> searchresult = archive.stream().filter(readable -> readable.getPublished() == searchdate).findAny().stream().toList();
                            searchresult.forEach(System.out::println);
                        } catch (NoSuchElementException ex) {
                            System.err.println("no book found");
                        }
                        break;
                    }
                    case 5: {
                        System.out.println("write the author to search");
                        String author = input.nextLine();
                        try {
                            List<Readable> searchresult = archive.stream().filter(readable -> readable instanceof Book).filter(book -> ((Book) book).getAuthor().equals(author)).findAny().stream().toList();
                            System.out.println(searchresult);
                        } catch (NoSuchElementException ex) {
                            System.err.println("no magazine found");
                        }
                        break;
                    }
                }
            } catch (Exception ex) {
                System.err.println("Generic, not defined error");
            }
        }
    }
}
