import exception.BookNotAvailableException;
import model.Book;
import model.LibraryMember;

import java.util.*;

public class LibraryManagementSystem {

    private ArrayList<Book> books;
    private LinkedList<LibraryMember> waitlist;
    private Vector<LibraryMember> members;
    private Stack<Book> returnedBooks;
    private HashSet<LibraryMember> uniqueMembers;
    private LinkedHashSet<Book> checkedOutBooks;
    private TreeSet<String> sortedBookTitles;
    private HashMap<Integer, Book> bookDetails;
    private LinkedHashMap<Integer, Book> bookCheckouts;
    private TreeMap<Integer, Book> sortedBooksByID;

    public LibraryManagementSystem() {
        books = new ArrayList<>();
        waitlist = new LinkedList<>();
        members = new Vector<>();
        returnedBooks = new Stack<>();
        uniqueMembers = new HashSet<>();
        checkedOutBooks = new LinkedHashSet<>();
        sortedBookTitles = new TreeSet<>();
        bookDetails = new HashMap<>();
        bookCheckouts = new LinkedHashMap<>();
        sortedBooksByID = new TreeMap<>();
    }

    public void addBook(int id, String title) {
        Book book = new Book(id, title);
        books.add(book);
        bookDetails.put(id, book);
        sortedBooksByID.put(id, book);
        sortedBookTitles.add(title);
    }

    public void addMember(int memberId, String name) {
        LibraryMember member = new LibraryMember(memberId, name);
        members.add(member);
        uniqueMembers.add(member);
    }

    public void checkoutBook(int memberId, int bookId) throws BookNotAvailableException {
        Book book = bookDetails.get(bookId);
        if (book == null || !book.isAvailable()) {
            throw new BookNotAvailableException("Book is not available for checkout.");
        }

        LibraryMember member = null;
        for (LibraryMember m : members) {
            if (m.getMemberId() == memberId) {
                member = m;
                break;
            }
        }

        if (member != null) {
            book.setAvailable(false);
            checkedOutBooks.add(book);
            bookCheckouts.put(bookId, book);
        } else {
            System.out.println("Member not found.");
        }
    }

    public void returnBook(int bookId) {
        Book book = bookDetails.get(bookId);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            returnedBooks.push(book);
            checkedOutBooks.remove(book);
            bookCheckouts.remove(bookId);
        } else {
            System.out.println("Book not found or already returned.");
        }
    }

    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();

        // Adding books to the library
        library.addBook(1, "Java Programming");
        library.addBook(2, "Data Structures and Algorithms");
        library.addBook(3, "Introduction to Algorithms");

         // Adding members to the library
        library.addMember(101, "Nitish singh");
        library.addMember(102, "Mehual");


        try {
            library.checkoutBook(101, 1);
            library.checkoutBook(102, 1);

        } catch (BookNotAvailableException e) {
            System.out.println(e.getMessage());
        }

    }
}
