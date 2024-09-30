import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Period;


class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isBorrowed;

    public Book(String title,String author,String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isBorrowed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void borrow() {
        isBorrowed = true;
    }

    public void returnBook() {
        isBorrowed = false;
    }
}

class User {
    private String name;
    private int age;

    public User(String name) {
        this.name = name;
        
    }

    public String getName() {
        return name;
    }
}

class BorrowedBook {
    private User user;
    private Book book;

    public BorrowedBook(User user, Book book) {
        this.user = user;
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }
}

class Library {
    private List<Book> books;
    private List<User> users;
    private List<BorrowedBook> borrowedBooks; 

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        borrowedBooks = new ArrayList<>(); 
        // Initialize the new list
    }

    public void addBook(String title, String author, String isbn) {
        books.add(new Book(title, author, isbn));
    }

    public void addUser(String name) {
        users.add(new User(name));
    }

    public void borrowBook(String userName, String title) {
        // Check if user exists
        boolean userExists = users.stream().anyMatch(user -> user.getName().equalsIgnoreCase(userName));
        if (!userExists) {
            System.out.println("User does not exist.");
            return;
        }

        // to check if book is available
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && !book.isBorrowed()) {
                book.borrow();
                borrowedBooks.add(new BorrowedBook(users.stream().filter(user -> user.getName().equalsIgnoreCase(userName)).findFirst().get(), book));
                

                LocalDate nowD=LocalDate.now();
                LocalDate borrowDate =LocalDate.of(2024,9,26);
                



                System.out.println(userName + " has borrowed: " + title +" "+ borrowDate );

                return;
            }
        }
        System.out.println("Book is not available.");
    }

    public void returnBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && book.isBorrowed()) {
                book.returnBook();
                borrowedBooks.removeIf(borrowedBook -> borrowedBook.getBook().getTitle().equalsIgnoreCase(title)); // Remove from borrowed list
                System.out.println("You have returned: " + title);
                return;
            }
        }
        System.out.println("Book not found or was not borrowed.");
    }

    

    public void printAvailableBooks() {
        System.out.println("Available Books:");
        for (Book book : books) {
            if (!book.isBorrowed()) {
                System.out.println("Title: " + book.getTitle() + " Author: " + book.getAuthor() + " ISBN: " + book.getIsbn());
                
            }
        }
    }

    public void printBorrowedBooks() {
        System.out.println("Borrowed Books:");
        for (BorrowedBook borrowedBook : borrowedBooks) {
            System.out.println(borrowedBook.getUser().getName() + " has borrowed: " + borrowedBook.getBook().getTitle());
            LocalDate nowD=LocalDate.now();
            LocalDate borrowDate =LocalDate.of(2024,9,26);
            Period dueDate = Period.between(nowD,borrowDate);

            System.out.println(dueDate);
        }
    }

    public void printUsers() {
        System.out.println("Registered Users:");
        for (User user : users) {
            System.out.println(user.getName());
        }
    }

  
}

public class LibraryManagement {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        
        library.addBook("Harry Potter","J.K Rowling","12345");
        library.addBook("Lord of the rings","J.R.R Tolkien","67890");
        library.addUser("Alice");
        library.addUser("Bob");

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Add User");
            System.out.println("3. List of users");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Print Available Books");
            System.out.println("7. Print Borrowed Books");
            System.out.println("8. Search book tittle");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1:   
                System.out.print("Enter book title: ");
                String bookTitle = scanner.nextLine();
        
                System.out.print("Enter author: ");
                String author = scanner.nextLine();
        
                System.out.print("Enter ISBN: ");
                String isbn = scanner.nextLine();
        
                library.addBook(bookTitle, author, isbn);
        
                System.out.println("Book added successfully!");
                    break;
                case 2:
                    System.out.print("Enter user name: ");
                    String userName = scanner.nextLine();
                    library.addUser(userName);
                    break;

                case 3:
                    library.printUsers(); 
                    break;
                

                case 4:
                System.out.print("Enter your name: ");
                String borrowerName = scanner.nextLine();
                System.out.print("Enter book title to borrow: ");
                String borrowTitle = scanner.nextLine();
                library.borrowBook(borrowerName, borrowTitle);
                break;
                case 5:
                    System.out.print("Enter book title to return: ");
                    String returnTitle = scanner.nextLine();
                    library.returnBook(returnTitle);
                    break;
                case 6:
                    library.printAvailableBooks();
                    break;
                case 7:
                    library.printBorrowedBooks();
                    break;

                case 8:
                    library.printBorrowedBooks();
                    break;

                case 0:
                    System.out.println("Thank You");
                    scanner.close();
                    return;
                default:
                    System.out.println("Please choose between the choices.");
            }
        }
    }
}
