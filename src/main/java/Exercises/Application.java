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
            return new Book(rndm.nextInt(0,9999),faker.book().title(), LocalDate.now().minusMonths(rndm.nextLong(0,1000)), rndm.nextInt(0,1000),faker.book().author(),faker.book().genre());

        };
        Supplier<Magazine> magazineSupplier = () -> {
            Faker faker = new Faker(Locale.ITALY);
            Random rndm = new Random();
            return new Magazine(rndm.nextInt(0,9999),faker.book().title(),LocalDate.now().minusMonths(rndm.nextLong(0,1000)),rndm.nextInt(0,1000),Period.WEEKLY );
        };
        List<Book> bookList = new ArrayList<>();
        for (int i = 0; i < 50; i++){
            bookList.add(bookSupplier.get());
        }
        List<Magazine> magazineList = new ArrayList<>();
        for (int i = 0; i < 50; i++){
            magazineList.add(magazineSupplier.get());
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        formatter = formatter.withLocale(Locale.ITALY);
        Scanner input = new Scanner(System.in);
        int selector = 9;
        while (selector != 0) {
            try {
        System.out.println("********************************ARCHIVE********************************");
        System.out.println("write 1 to add an element, 2 to remove an element with ISBN, 3 to search an element with ISBN");
        System.out.println(" 4 to search an element with date of publish, 5 to search an element with author");
        System.out.println(" 6 to save the archive, 7 to load the archive from an external file, 0 to exit");
        selector = Integer.parseInt(input.nextLine());
        if (selector > 7) throw new Exception();
        switch (selector){
            case 1: {
                System.out.println("write 1 to add a book, 2 to add a magazine");
                int discrim = Integer.parseInt(input.nextLine());
                System.out.println("write the ISBN");
                int ISBN = Integer.parseInt(input.nextLine());
                System.out.println("write the title");
                String title = input.nextLine();
                System.out.println("write the publish date in the format 'yyyy-MMM-dd' ");
                LocalDate date = LocalDate.parse(input.nextLine(), formatter);
                System.out.println("write the number of pages");
                int pages = Integer.parseInt(input.nextLine());
                if (discrim == 1) {
                    System.out.println("write the author");
                    String author = input.nextLine();
                    System.out.println("write the genre");
                    String genre = input.nextLine();
                    Book newbook = new Book(ISBN,title,date,pages,author,genre);
                    bookList.add(newbook);
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
                    Magazine newMag = new Magazine(ISBN,title,date,pages,tempP);
                    magazineList.add(newMag);
                }
            }
            case 2: {
                System.out.println("write the ISBN to delete");
                int isbn = Integer.parseInt(input.nextLine());
                bookList.removeIf(book -> book.getISBN() == isbn);
                magazineList.removeIf(magazine -> magazine.getISBN() == isbn);
            }
            case 3: {
                System.out.println("write the ISBN to search");
                int isbn = Integer.parseInt(input.nextLine());
                try {
                    Book searchresult = bookList.stream().filter(book -> book.getISBN() == isbn).findFirst().get().;
                    System.out.println(searchresult);
                } catch (NoSuchElementException ex) {
                    System.err.println("no book found");
                }
                try {
                    Magazine searchresult2 = magazineList.stream().filter(magazine -> magazine.getISBN() == isbn).findFirst().get();
                    System.out.println(searchresult2);
                } catch (NoSuchElementException ex){
                    System.err.println("no magazine found");
                }

            }
        }
    }catch (Exception ex) {
                System.err.println("Generic, not defined error");
            }
        }
    }
}
