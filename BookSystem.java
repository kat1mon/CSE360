import java.util.HashMap;
import java.util.ArrayList;

public class BookSystem {
	private HashMap<String, User> usersList;
	private ArrayList<Book> publishedBooks;
	private double taxPercent = 20;
	
	public BookSystem() {
		usersList = new HashMap<String, User>(1000000);
		publishedBooks = new ArrayList<Book>(1000000);
		//use file to load saved users
		//use file to load published books
		//use file to load tax percent
	}
	
	public User getUser(String email) {
		if(usersList.containsKey(email)) {
			return usersList.get(email);
		}
		return null;
	}
	
	public void addUser(User u) {
		usersList.put(u.getEmail(), u);
	}
	
	public ArrayList<Book> getPublishedBooks(){
		return this.publishedBooks;
	}
	
	public void addBook(Book b) {
		publishedBooks.add(b);
	}
	
	public double getTaxPercent() {
		return this.taxPercent;
	}
	
}
