import java.util.HashMap;
import java.util.ArrayList;

public class BookSystem {
	private HashMap<Integer, User> usersList;
	private ArrayList<Book> publishedBooks;
	private double taxPercent = 20;
	
	public BookSystem() {
		usersList = new HashMap<Integer, User>(1000000);
		publishedBooks = new ArrayList<Book>(1000000);
		//use file to load saved users
		//use file to load published books
		//use file to load tax percent
	}
	
	public User getUser(int id) {
		if(usersList.containsKey(id)) {
			return usersList.get(id);
		}
		return null;
	}
	
	public void addUser(User u) {
		usersList.put(null, u);
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
