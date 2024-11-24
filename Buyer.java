import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class Buyer extends User{
	private ArrayList<Book> selectedList;
	
	public Buyer(User u) {
		super(u.getEmail(), null, null);
		this.selectedList = new ArrayList<Book>();
	}
	
	public ArrayList<Book> getSelected(){
		return this.selectedList;
	}
	
	public void AddToCart(Book b) {
		this.getCart().addBook(b);
	}
	
	
	public void removeFromCart(Book b) {
		this.getCart().remove_Book(b);
	}
	
	public void clearCart() {
		this.getCart().clearCart();
	}
	
	public void updateUser(User u) {
		u.setCart(this.getCart());
	}
	
}
