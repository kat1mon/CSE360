import java.util.ArrayList;

public class Seller extends User{
	private ArrayList<Book> activeListings;
	public Seller(User u) {
		super(u.getEmail(), null, null);
		activeListings = u.getListings();
	}
	
	public void addBook(Book b) {
		//this.sys.add(b);
	}
	
	public void removeBook(Book b) {
		activeListings.remove(b);
		//this.sys.remove(b);
	}
	
	public void update(User u) {
		u.setListings(activeListings);
	}
}
