import java.util.ArrayList;
//import *.Cart;
//import *.Book;
//import *.Order;

public class User {
	private String asuEmail;
	private String password;
	private boolean[] roles;
	private ArrayList<Book> userListings;
	private Cart userCart;
	private boolean loggedIn;
	private ArrayList<Order> recentOrders;
	static BookSystem sys;
	
	public User(String e, String p, boolean[] r) {
		this.asuEmail = e;
		this.password = p;
		this.roles = r;
		
		this.userListings = new ArrayList<Book>();
		this.userCart = new Cart();
		this.loggedIn = false;
		this.recentOrders = new ArrayList<Order>();
		this.sys = s;
	}
	
	public void setListings(ArrayList<Book> l) {
		this.userListings = l;
	}
	
	public String getEmail() {
		return this.asuEmail;
	}
	
	public void changeLog() {
		if(this.loggedIn == true) {
			this.loggedIn = false;
		}else {
			this.loggedIn = true;
		}
	}
	
	public void changePassword(String p) {
		this.password = p;
	}
	
	public void changeRoles(boolean[] r) {
		this.roles = r;
	}
	
	public boolean checkRole(int i) {
		if(this.roles[i]) {
			return true;
		}
		return false;
	}
	
	public boolean checkPassword(String p) {
		if(p.equals(this.password)) {
			return true;
		}
		return false;
	}
	
	public Cart getCart() {
		return this.userCart;
	}
	
	public void setCart(Cart c) {
		this.userCart = c;
	}
	
	public ArrayList<Order> getOrders(){
		return this.recentOrders;
	}
	
	public ArrayList<Book> getListings(){
		return this.userListings;
	}
	
}