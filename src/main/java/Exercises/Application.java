package Exercises;

import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
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
                //System.out.println("********************************LIST********************************");
                //archive.forEach(System.out::println);
                System.out.println("********************************ARCHIVE********************************");
                System.out.println("write 1 to add an element, 2 to remove an element with ISBN, 3 to search an element with ISBN");
                System.out.println(" 4 to search an element with date of publish, 5 to search an element with author");
                System.out.println(" 6 to save the archive, 7 to load the archive from an external file, 0 to exit");
                selector = Integer.parseInt(input.nextLine());
                if (selector > 7) throw new Exception();
                switch (selector) {
                    case 1: {
                        try {
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
                        }} catch (Exception ex) {
                            System.err.println("generic error during element creation");
                        }
                        break;
                    }
                    case 2: {
                        try {
                            System.out.println("write the ISBN to delete");
                            int isbn = Integer.parseInt(input.nextLine());
                            archive.removeIf(readable -> readable.getISBN() == isbn);
                        } catch (NoSuchElementException ex) {
                            System.err.println("cannot find the element to remove");
                        } catch (Exception ex) {
                            System.err.println("generic error during element removal");
                        }
                        break;
                    }
                    case 3: {
                        try {
                        System.out.println("write the ISBN to search");
                        int isbn = Integer.parseInt(input.nextLine());
                            Readable searchresult = archive.stream().filter(readable -> readable.getISBN() == isbn).findFirst().get();
                            System.out.println(searchresult);
                        } catch (NoSuchElementException ex) {
                            System.err.println("no element found");
                        } catch (Exception ex) {
                            System.err.println("generic error during search by ISBN");
                        }
                        break;
                    }
                    case 4: {
                        try {
                        System.out.println("write the date of publish to search in the format 'yyyy-MM-dd' ");
                        LocalDate searchdate = LocalDate.parse(input.nextLine());
                            List<Readable> searchresult = archive.stream().filter(readable -> readable.getPublished().equals(searchdate)).findAny().stream().toList();
                            searchresult.forEach(System.out::println);
                        } catch (NoSuchElementException ex) {
                            System.err.println("no book found");
                        } catch (Exception ex) {
                            System.err.println("generic error during search by localDate");
                        }
                        break;
                    }
                    case 5: {
                        try {
                        System.out.println("write the author to search");
                        String author = input.nextLine();
                            List<Readable> searchresult = archive.stream().filter(readable -> readable instanceof Book).filter(book -> ((Book) book).getAuthor().toLowerCase().trim().equals(author.toLowerCase().trim())).findAny().stream().toList();
                            System.out.println(searchresult);
                        } catch (NoSuchElementException ex) {
                            System.err.println("no magazine found");
                        } catch (Exception ex) {
                            System.err.println("generic error during search by author");
                        }
                        break;
                    }
                    case 6: {
                        try {
                            File file = new File("archive.txt");
                            String toWrite = "";
                            for (Readable readable : archive) {
                                if (readable instanceof Book) {
                                    toWrite += readable.getISBN() + "@" + readable.getTitle() + "@" + readable.getPublished() + "@" + readable.getPages() + "@" + ((Book) readable).getAuthor() + "@" + ((Book) readable).getGenre() + "#";
                                } else if (readable instanceof Magazine) {
                                    toWrite += readable.getISBN() + "@" + readable.getTitle() + "@" + readable.getPublished() + "@" + readable.getPages() + "@" + ((Magazine) readable).getPeriod() + "#";
                                }
                                FileUtils.writeStringToFile(file, toWrite, "UTF-8");
                            }
                        } catch (IOException ex) {
                            System.err.println("failed to write");
                        } catch (Exception ex) {
                            System.err.println("generic error during writing");
                        }
                    }
                    case 7: {
                        try {
                            File file = new File("archive.txt");
                            String fileString = FileUtils.readFileToString(file, "UTF-8");
                            List<String> splitted = Arrays.asList(fileString.split("#"));
                            List<Readable> loadedlist = splitted.stream().map(string -> {
                                String[] obj = string.split("@");
                                if (Arrays.asList(obj).size() == 6) {
                                    return new Book(Integer.parseInt(obj[0]), obj[1], LocalDate.parse(obj[2]), Integer.parseInt(obj[3]), obj[4], obj[5]);
                                } else {
                                    return new Magazine(Integer.parseInt(obj[0]), obj[1], LocalDate.parse(obj[2]), Integer.parseInt(obj[3]), Period.WEEKLY);
                                }
                            }).toList();
                            loadedlist.forEach(System.out::println);
                        } catch (IOException ex) {
                            System.err.println("failed to load");
                        } catch (Exception ex) {
                            System.err.println("generic error during loading");
                        }
                    }
                }
            } catch (Exception ex) {
                System.err.println("Generic, not defined error");
            }
        }
    }
}
