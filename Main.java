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
		/*boolean[] r = {true, true};
		User DEBUG = new User("beep", "boop", r);
		ArrayList<Book> l = new ArrayList<>();
		l.add(new Book("bip", "bop", "2000", "Math", "Like New", 12.05, 2, "DEBUG"));
		l.add(new Book("bip", "bop", "2000", "Math", "Like New", 12.05, 2, "DEBUG"));
		DEBUG.setListings(l);
		sys.getPublishedBooks().add(new Book("bip", "bop", "2000", "Math", "Like New", 12.05, 2, "DEBUG"));
		sys.getPublishedBooks().add(new Book("boop", "bippy", "2000", "Math", "Like New", 12.05, 2, "DEBUG"));
		sys.addUser(DEBUG);*/
		readFiles();
		launch(args);
		updateFiles();
	}
	
	private static void readFiles() {
		try {
			boolean[] r = {true, true};
			Scanner usrScanner = new Scanner(new File("PhaseIII/Files/users.txt"));
			while(usrScanner.hasNextLine()) {
				String email = usrScanner.nextLine();
				String pW;
				ArrayList<Book> books = new ArrayList<>();
				Cart crt = new Cart();
				ArrayList<Order> orders = new ArrayList<>();
				Scanner individual = new Scanner(new File("PhaseIII/Files/" + email + "/info.txt"));
				
				// Read password
				pW = individual.nextLine();
				
				while (true) {
					String n = individual.nextLine();
					if(n.equals("END OF LISTINGS")) {
						break;
					}else {
						String title = n;
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
				}
				
				// Read Cart details
				crt.price = Double.parseDouble(individual.nextLine());
				crt.tax = Double.parseDouble(individual.nextLine());
				ArrayList<Book> cB = new ArrayList<>();
				while (true) {
					String n = individual.nextLine();
					if(n.equals("END OF CART")) {
						break;
					}else {
						String title = n;
						String author = individual.nextLine();
						String date = individual.nextLine();
						String cat = individual.nextLine();
						String con = individual.nextLine();
						double price = Double.parseDouble(individual.nextLine());
						int quan = Integer.parseInt(individual.nextLine());
						String seller = individual.nextLine();
						cB.add(new Book(title, author, date, cat, con, price, quan, seller));
					}
				}
				crt.books = cB;
				
				// Read Orders
				while (true) {
					String n = individual.nextLine();
					if(n.equals("END OF ORDERS")) {
						break;
					}else {
						int num = Integer.parseInt(n);
						String date = individual.nextLine();
						double to = Double.parseDouble(individual.nextLine());
						double ta = Double.parseDouble(individual.nextLine());
						ArrayList<String> titles = new ArrayList<>();
						while (individual.hasNextLine()) {
							String x = individual.nextLine();
							if (x.equals("}")) {
								break;
							} else {
								titles.add(x);
							}
						}
						orders.add(new Order(num, date, to, ta, titles, true));
					}		
				}
				individual.close();
				
				// Add user to the system
				User usr = new User(email, pW, r, books, crt, orders);
				sys.addUser(usr);
			}
			usrScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void updateFiles() {
	    try {
	        File userFile = new File("PhaseIII/Files/users.txt");
	        userFile.getParentFile().mkdirs();
	        FileWriter users = new FileWriter(userFile);
	        
	        // Write each user's email to users.txt
	        if (!sys.getUsers().isEmpty()) {
	            for (Entry<String, User> u : sys.getUsers().entrySet()) {
	                User usr = u.getValue();
	                users.write(usr.getEmail() + "\n");
	            }
	        }
	        users.close();
	        
	        // Write individual user info to their info.txt file
	        for (Entry<String, User> u : sys.getUsers().entrySet()) {
	            User user = u.getValue();
	            String email = user.getEmail();
	            
	            File indiFile = new File("PhaseIII/Files/" + email + "/info.txt");
	            indiFile.getParentFile().mkdirs();
	            FileWriter indi = new FileWriter(indiFile);
	            
	            // Write user password
	            indi.write(user.getPassword() + "\n");
	            
	            // Write user's listings (books)
	            if (!user.getListings().isEmpty()) {
	                for (Book b : user.getListings()) {
	                    indi.write(b.getTitle() + "\n");
	                    indi.write(b.getAuthor() + "\n");
	                    indi.write(b.getDate() + "\n");
	                    indi.write(b.getCategory() + "\n");
	                    indi.write(b.getCondition() + "\n");
	                    indi.write(b.getPrice() + "\n");
	                    indi.write(b.getQuantity() + "\n");
	                }
	            }
	            indi.write("END OF LISTINGS\n");

	            // Write cart details
	            Cart cart = user.getCart();
	            indi.write(cart.getTotal() + "\n");
	            indi.write(cart.getTax() + "\n");
	            if (!cart.getBooks().isEmpty()) {
	                for (Book b : cart.getBooks()) {
	                    indi.write(b.getTitle() + "\n");
	                    indi.write(b.getAuthor() + "\n");
	                    indi.write(b.getDate() + "\n");
	                    indi.write(b.getCategory() + "\n");
	                    indi.write(b.getCondition() + "\n");
	                    indi.write(b.getPrice() + "\n");
	                    indi.write(b.getQuantity() + "\n");
	                    indi.write(b.getSeller() + "\n");
	                }
	            }
	            indi.write("END OF CART\n");

	            // Write orders
	            if (!user.getOrders().isEmpty()) {
	                for (Order o : user.getOrders()) {
	                    indi.write(o.getNumber() + "\n");
	                    indi.write(o.getOrderDate() + "\n");
	                    indi.write(o.getTotalCost() + "\n");
	                    indi.write(o.getTax() + "\n");
	                    indi.write(o.getTitles());
	                    indi.write("}\n");
	                }
	            }
	            indi.write("END OF ORDERS\n");

	            indi.close();
	        }

	        // Write published books
	        File pubsFile = new File("PhaseIII/Files/pubs.txt");
	        pubsFile.getParentFile().mkdirs();
	        FileWriter pubs = new FileWriter(pubsFile);
	        if (!sys.getPublishedBooks().isEmpty()) {
	            for (Book b : sys.getPublishedBooks()) {
	                pubs.write("{\n");
	                pubs.write(b.getTitle() + "\n");
	                pubs.write(b.getAuthor() + "\n");
	                pubs.write(b.getDate() + "\n");
	                pubs.write(b.getCategory() + "\n");
	                pubs.write(b.getCondition() + "\n");
	                pubs.write(b.getPrice() + "\n");
	                pubs.write(b.getQuantity() + "\n");
	                pubs.write(b.getSeller() + "\n");
	                pubs.write("}\n");
	            }
	        }
	        pubs.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
