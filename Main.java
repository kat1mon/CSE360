import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Scanner;

import javafx.application.Application;
//import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	static BookSystem sys = new BookSystem();
	private Stage primaryStage;
	
	public void showBuyerView(User u) {
		new BuyerView(sys, this, u, primaryStage);
	}
	
	public void showSellerView(User u) {
		new SellerView(sys, this, u, primaryStage);
	}
	
	
	public void showLoginView() {
		primaryStage.setScene(new LoginView(sys, this).getScene());
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
		
		readFiles();
		
		User DEBUG = new User("beep", "boop", r);
		ArrayList<Book> l = new ArrayList<>();
		l.add(new Book("bip", "bop", "2000", "Math", "Like New", 12.05, 2, "DEBUG"));
		l.add(new Book("bip", "bop", "2000", "Math", "Like New", 12.05, 2, "DEBUG"));
		DEBUG.setListings(l);
		sys.getPublishedBooks().add(new Book("bip", "bop", "2000", "Math", "Like New", 12.05, 2, "DEBUG"));
		sys.getPublishedBooks().add(new Book("boop", "bippy", "2000", "Math", "Like New", 12.05, 2, "DEBUG"));
		sys.addUser(DEBUG);
		launch(args);
		updateFiles();
	}
	
	//FIXME
	private static void readFiles() {
		try {
			Scanner usrScanner = new Scanner(new File("PhaseIII/Files/users.txt"));
			while(usrScanner.hasNextLine()) {
				String email = usrScanner.nextLine();
				String pW;
				ArrayList<Book> books = new ArrayList<Book>();
				Cart crt = new Cart();
				ArrayList<Order> orders = new ArrayList<Order>();
				Scanner individual = new Scanner(new File("PhaseIII/Files/" + email + "/info.txt"));
				pW = individual.nextLine();

				while(individual.nextLine() != "END OF LISTINGS") {
					String title = individual.nextLine();
					String author = individual.nextLine();
					String date = individual.nextLine();
					String cat = individual.nextLine();
					String con = individual.nextLine();
					double price = Double.parseDouble(individual.nextLine());
					int quan = Integer.parseInt(individual.nextLine());
					
					Book b = new Book(title, author, date, cat, con, price, quan, email);
					books.add(b);
					sys.addBook(b);
				}
				
				while(individual.nextLine() != "END OF CART") {
					int num = Integer.parseInt(individual.nextLine());
					String date = individual.nextLine();
					while(individual.nextLine() != "}") {
						
					}
				}
				
				User usr = new User();
			}
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}

 	private static void updateFiles() {
		try {
			File userFile = new File("PhaseIII/Files/users.txt");
			userFile.getParentFile().mkdirs();
			FileWriter users = new FileWriter(userFile);
			
			if(!sys.getUsers().isEmpty()) {
				for(Entry<String, User> u : sys.getUsers().entrySet()) {
					User usr = u.getValue();
					users.write(usr.getEmail() + "\n");
				}
				users.close();
				for(Entry<String, User> u : sys.getUsers().entrySet()) {
					User user = u.getValue();
					String email = user.getEmail();
					
					File indiFile = new File("PhaseIII/Files/" + email + "/info.txt");
					indiFile.getParentFile().mkdirs();
					FileWriter indi = new FileWriter(indiFile);
					
					indi.write(user.getPassword() + "\n");
					//indi.write(user.getRoles() + "\n");
					if(!user.getListings().isEmpty()) {
						for(Book b : user.getListings()) {
							indi.write("{\n");
							indi.write(b.getTitle() + "\n");
							indi.write(b.getAuthor() + "\n");
							indi.write(b.getDate() + "\n");
							indi.write(b.getCategory()  + "\n");
							indi.write(b.getCondition() +  "\n");
							indi.write(b.getPrice() + "\n");
							indi.write(b.getQuantity() + "\n");
							indi.write("}\n");
						}
					}
					indi.write("END OF LISTINGS\n");
					if(!user.getCart().books.isEmpty()) {
						for(Book b : user.getCart().getBooks()) {
							indi.write("{\n");
							indi.write(b.getTitle() + "\n");
							indi.write(b.getAuthor() + "\n");
							indi.write(b.getDate() + "\n");
							indi.write(b.getCategory()  + "\n");
							indi.write(b.getCondition() +  "\n");
							indi.write(b.getPrice() + "\n");
							indi.write(b.getQuantity() + "\n");
							indi.write(b.getSeller() + "\n");
							indi.write("}\n");
						}
					}
					indi.write("END OF CART\n");
					indi.close();
				}
				
			}
			
			File pubsFile = new File("PhaseIII/Files/pubs.txt");
			pubsFile.getParentFile().mkdirs();
			FileWriter pubs = new FileWriter(pubsFile);
			if(!sys.getPublishedBooks().isEmpty()) {
				for(Book b : sys.getPublishedBooks()) {
					pubs.write("{\n");
					pubs.write(b.getTitle() + "\n");
					pubs.write(b.getAuthor() + "\n");
					pubs.write(b.getDate() + "\n");
					pubs.write(b.getCategory()  + "\n");
					pubs.write(b.getCondition() +  "\n");
					pubs.write(b.getPrice() + "\n");
					pubs.write(b.getQuantity() + "\n");
					pubs.write(b.getSeller() + "\n");
					pubs.write("}\n");
				}
			}
			pubs.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		
	}
}
