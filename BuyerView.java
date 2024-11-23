package application;

//import java.awt.print.Book;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BuyerView extends Application
{
	private static final double TAX_RATE = 0.081;		// constant tax rate of 8.1%
	private double cartTax = 0.00;
	private double cartTotal = 0.00;
	private double cartTotalWithTax = 0.00;
	
	// declares variables to be used in other methods
	private List<Book> books = new ArrayList<>();
	private ListView<String> cartListView;
	private VBox bookList = new VBox(10);		// will layout a dynamic list of books
	private ComboBox<String> categoryCombo;
	private ComboBox<String> conditionCombo;
	private Set<String> addedToCart = new HashSet<>();		// this well allow to monitor which books have been added to cart
	private Label taxLabel = new Label();
	private Label totalLabel = new Label();
	
	@Override
	public void start(Stage primaryStage)
	{
		testSampleBooks();
		
		// Title text at the top
		Text title = new Text("Book Nook");
		title.setFont(Font.font("Lucida Fax", FontWeight.BOLD, 30));
		title.setFill(Color.web("#FFD700"));
		
		
		// account button and logout button at the top
		Button accountButton = new Button("Your Account");
		accountButton.setStyle("-fx-background-color: #9a9a9a; -fx-text-fill: white;");
		accountButton.setFont(Font.font("arial", FontWeight.BOLD, 16));
		accountButton.setPrefWidth(150);
		
		Button logoutButton = new Button("Logout");
		logoutButton.setStyle("-fx-background-color: #9a9a9a; -fx-text-fill: white;");
		logoutButton.setFont(Font.font("arial", FontWeight.BOLD, 16));
		logoutButton.setPrefWidth(150);
		logoutButton.setOnAction(e -> 
		{			
			primaryStage.close();		// closes the current stage
			new LoginView().start(new Stage());		// opens the login view
		});
		
		
		// spacer between title and buttons in header
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);		// allows for the spacer to maintain space after altering window size.

		
		
		// Drop-down menu for category selection
		categoryCombo = new ComboBox<>();
		categoryCombo.getItems().addAll("All Categories", "Natural Science", "Math", "English Language", "Computer", "Other");
		categoryCombo.setValue("All Categories");		
		categoryCombo.setStyle(
				"-fx-background-color: white;" +
				"-fx-border-color: #F9C43A;" +
				"-fx-border-radius: 20;" +
				"-fx-background-radius: 20;" +
				"-fx-padding: 5 10;" +
				"-fx-font-size: 14px;"
		);
		categoryCombo.setOnAction(e -> updateBookList());
		
		// Drop-down menu for condition selection
		conditionCombo = new ComboBox<>();
		conditionCombo.getItems().addAll("All Conditions", "Like New", "Moderately Used", "Heavily Used");
		conditionCombo.setValue("All Conditions");
		//modeCombo.setStyle("-fx-background-radius: 15; -fx-padding: 5 10 5 10;");
		conditionCombo.setStyle(
				"-fx-background-color: white;" +
				"-fx-border-color: #F9C43A;" +
				"-fx-border-radius: 20;" +
				"-fx-background-radius: 20;" +
				"-fx-padding: 5 10;" +
				"-fx-font-size: 14px;"
		);
		conditionCombo.setOnAction(e -> updateBookList());
		
		// Shopping cart elements
		Label cartLabel = new Label("Your Cart:");
		cartLabel.setFont(Font.font("arial", FontWeight.BOLD, 20));
		taxLabel = new Label(String.format("Tax (8.1%%):\t\t\t$%.2f", cartTax));
		taxLabel.setFont(Font.font("arial", FontWeight.BOLD, 16));
		totalLabel = new Label(String.format("Total:\t\t\t\t$%.2f", cartTotalWithTax));
		totalLabel.setFont(Font.font("arial", FontWeight.BOLD, 16));
		
		cartListView = new ListView<>();
		cartListView.setPrefHeight(450);
		cartListView.setFocusTraversable(false);		// should allow for mouse hover to scroll
		
		Button clearCartButton = new Button("Clear Cart");
		clearCartButton.setStyle("-fx-background-color: #9a9a9a; -fx-text-fill: white;");
		clearCartButton.setFont(Font.font("arial", FontWeight.BOLD, 14));
		clearCartButton.setPrefWidth(100);
		clearCartButton.setOnAction(e -> 
		{
			cartListView.getItems().clear();		// clears cart
			cartTotal = 0.00;
			cartTax = 0.00;
			cartTotalWithTax = 0.00;
			
			updateCartCosts();			// updates cart costs back to zero
			addedToCart.clear();		// re-enables "add to cart" buttons
			updateBookList();
		});
		
		Button purchaseButton = new Button("Purchase");
		purchaseButton.setStyle("-fx-background-color: #8C1D40; -fx-text-fill: white;");
		purchaseButton.setFont(Font.font("arial", FontWeight.BOLD, 16));
		purchaseButton.setPrefWidth(150);

		
		
		// layouts
		// logo and button layout
		HBox topHeaderLayout = new HBox(10);
		topHeaderLayout.getChildren().addAll(title, spacer, accountButton, logoutButton);
		topHeaderLayout.setPadding(new Insets(10));
		
		
		// filter options layout
		HBox filterOptionsLayout = new HBox(10);
		filterOptionsLayout.getChildren().addAll(categoryCombo, conditionCombo);
		
		
		// book list scroll layout
		ScrollPane bookScrollPane = new ScrollPane();
		bookScrollPane.setFitToWidth(true);
		bookScrollPane.setPrefHeight(650);
		bookScrollPane.setStyle("-fx-background-color: white; -fx-border-color: white; -fx-border-width: 1px");
		bookScrollPane.setFocusTraversable(false);		// allow for mouse hover to scroll
		updateBookList();
		bookScrollPane.setContent(bookList);
		
		
		// layout for filter options and scroll book list
		VBox centerSectionLayout = new VBox(10);
		centerSectionLayout.setPadding(new Insets(10));
		centerSectionLayout.setStyle("-fx-background-color: white; -fx-border-color: #F9C43A; -fx-border-radius: 20; -fx-background-radius: 20; -fx-border-width: 1px");
		centerSectionLayout.getChildren().addAll(filterOptionsLayout, bookScrollPane);
		
		// cart button layout
		VBox cartButtonLayout = new VBox(10);
		cartButtonLayout.setPadding(new Insets(10));
		cartButtonLayout.getChildren().addAll(clearCartButton, purchaseButton);
		cartButtonLayout.setAlignment(Pos.CENTER);
		
		// layout for cart
		VBox cartLayout = new VBox(10);
		cartLayout.setPadding(new Insets(10));
		cartLayout.setStyle("-fx-background-color: white; -fx-border-color: #F9C43A; -fx-border-radius: 20; -fx-background-radius: 20; -fx-border-width: 1px");
		cartLayout.getChildren().addAll(cartLabel, cartListView, taxLabel, totalLabel, cartButtonLayout);
		
		
		
		// root layout
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: #8C1D40;");
		root.setPadding(new Insets(10));
		root.setTop(topHeaderLayout);
		root.setCenter(centerSectionLayout);
		root.setRight(cartLayout);
		
		// scene and stage
		Scene scene = new Scene(root, 1366, 768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Book Nook Purchasing");
		primaryStage.setMinWidth(670);
		primaryStage.setMinHeight(630);
		primaryStage.setMaxWidth(1366);
		primaryStage.setMaxHeight(768);
		primaryStage.show();
	}
	
	
	
	
	// Other Methods
	// updates viewable books
	private void updateBookList()
	{
		bookList.getChildren().clear();
		
		// gets drop down filter options
		String selectedCategory = categoryCombo.getValue();
		String selectedCondition = conditionCombo.getValue();
		
		for (Book book : books)
		{
			if (!selectedCategory.equals("All Categories") && !book.getCategory().equals(selectedCategory))
			{
				continue;		// skip book
			}
			
			if (!selectedCondition.equals("All Conditions") && !book.getCondition().equals(selectedCondition))
			{
				continue;		// skip book
			}
			
			if (book.getQuantity() > 0)
			{
				bookList.getChildren().add(createBookItem(book));
			}
		}
	}
	
	private void updateCartCosts()
	{
		cartTax = cartTotal * TAX_RATE;
		cartTotalWithTax = cartTotal + cartTax;
		
		// update tax and total labels of cart
		taxLabel.setText(String.format("Tax (8.1%%):\t\t\t$%.2f", cartTax));
		totalLabel.setText(String.format("Total:\t\t\t\t$%.2f", cartTotalWithTax));
	}
	
	// creates books
	private HBox createBookItem(Book book)
	{	
		// elements of the individual book info list		
		Label titleLabel = new Label(book.getTitle());
		titleLabel.setFont(Font.font("arial", FontWeight.BOLD, 20));
		
		Label infoLabel = new Label(String.format("Author: %s\nPublished: %s\nCategory: %s\nCondition: %s\nSeller: %s\nQuantity: %d", 
				book.getAuthor(), book.getDate(), book.getCategory(), book.getCondition(), book.getSeller(), book.getQuantity()));
		infoLabel.setFont(Font.font("arial", 14));
		
		Label priceLabel = new Label("$" + String.format("%.2f", book.getPrice()));
		priceLabel.setFont(Font.font("arial", FontWeight.BOLD, 20));
		
		// layout of individual book info list
		VBox bookInfo = new VBox(10);
		bookInfo.getChildren().addAll(titleLabel, infoLabel, priceLabel);
		bookInfo.setAlignment(Pos.CENTER);
		
		
		Button addToCartButton = new Button("Add To Cart");
		addToCartButton.setStyle("-fx-background-color: #8C1D40; -fx-text-fill: white;");
		addToCartButton.setFont(Font.font("arial", FontWeight.BOLD, 16));
		addToCartButton.setPrefWidth(150);
		addToCartButton.setOnAction(e -> 
		{
			cartListView.getItems().add(String.format("%-30s$%.2f", book.getTitle(), book.getPrice()));		//adds book info to cart
			
			addedToCart.add(book.getTitle());
			
			//book.changeQuantity(book.getQuantity() - 1);		// updates book quantity   ***NOTE: likely better after hitting the purchase button
			
			cartTotal += book.getPrice();
			
			updateCartCosts();
			updateBookList();
		});
		
		// disables "add to cart" button if book is already in cart
		if (addedToCart.contains(book.getTitle()))
		{
			addToCartButton.setDisable(true);
		}
		
		
		// layout of book item
		VBox bookItem = new VBox(10);
		bookItem.setPadding(new Insets(5));
		bookItem.getChildren().addAll(bookInfo, addToCartButton);
		bookItem.setPrefWidth(400);
		bookItem.setStyle("-fx-border-color: gray; -fx-border-width: 1px");
		bookItem.setAlignment(Pos.CENTER);
		
		HBox bookList = new HBox(10);
		bookList.setPadding(new Insets(5));
		bookList.getChildren().addAll(bookItem);
		bookList.setAlignment(Pos.CENTER);
		
		return bookList;
	}
	
	// sample test of books to add
	private void testSampleBooks()
	{
		books.add(new Book("Natural Science Book", "John Doe", "2000", "Natural Science", "Like New", 55, 2, "Jane Doe"));
		books.add(new Book("Math Book", "John Doe", "2000", "Math", "Heavily Used", 15, 1, "Jane Doe"));
		books.add(new Book("English Language Book", "John Doe", "2000", "English Language", "Moderately Used", 20, 1, "Jane Doe"));
		books.add(new Book("The Arts Book", "John Doe", "2000", "Other", "Like New", 30, 1, "Jane Doe"));
	}
	
	// main
	public static void main(String[] args)
	{
		launch(args);
	}
}//end