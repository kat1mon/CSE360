import java.util.ArrayList;

public class Buyer extends User{
	private ArrayList<Book> selectedList;
	
	public Buyer(User u) {
		super(u.getEmail(), null, null);
		this.selectedList = new ArrayList<Book>();
	}
	
	public ArrayList<Book> getSelected(){
		return this.selectedList;
	}
	
	public void filterSelection(String param) {
		//this will be for front end
	}
	
	public void AddToCart(Book b) {
		this.getCart().add(b);
	}
	
	public void purchaseCart() {
		Order newOrder = new Order();
		this.getOrders().add(newOrder);
		this.selectedList.clear();
		this.getCart().empty();
	}
	
	public void removeFromCart(Book b) {
		this.getCart().remove(b);
	}
	
	public void clearCart() {
		this.getCart().empty();
	}
	
	public void updateUser(User u) {
		u.setCart(this.getCart());
	}
	
}
