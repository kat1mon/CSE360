import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	static BookSystem sys = new BookSystem();
	private Stage primaryStage;
	
	public void showSellerView(User u) {
		primaryStage.setScene((new SellerView(sys, this, u).getScene()));
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Arizona State University BookNook");
		this.primaryStage.setScene(new LoginView(sys, this).getScene());
		this.primaryStage.show();
	}
	
	public static void main(String[] args) {
		boolean[] r = {true, true};
		User DEBUG = new User("beep", "boop", r);
		ArrayList<Book> l = new ArrayList<>();
		l.add(new Book("bip", "bop", "2000", "Math", "Like New", 12.05, 2, "DEBUG"));
		l.add(new Book("bip", "bop", "2000", "Math", "Like New", 12.05, 2, "DEBUG"));
		l.add(new Book("bip", "bop", "2000", "Math", "Like New", 12.05, 2, "DEBUG"));
		l.add(new Book("bip", "bop", "2000", "Math", "Like New", 12.05, 2, "DEBUG"));
		l.add(new Book("bip", "bop", "2000", "Math", "Like New", 12.05, 2, "DEBUG"));
		l.add(new Book("bip", "bop", "2000", "Math", "Like New", 12.05, 2, "DEBUG"));
		l.add(new Book("bip", "bop", "2000", "Math", "Like New", 12.05, 2, "DEBUG"));
		DEBUG.setListings(l);
		sys.addUser(DEBUG);
		launch(args);
	}
}
