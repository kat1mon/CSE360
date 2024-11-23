
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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


public class BuyerView{

	private Main app;
	BookSystem sys;
	User usr;
	private Stage primaryStage;
	private Scene scene;
	private Label tx;
	private Label to;
	
	// declares variables to be used in other methods

	public BuyerView(BookSystem s, Main a, User u, Stage primary) {
		this.sys = s;
		this.app = a;
		this.usr = u;
		this.primaryStage = primary;
		this.scene = setupScene();
		primaryStage.setScene(scene);
	}
	
	private HBox createTopBar() {
		Text title = new Text("Book Nook");
		title.setFont(Font.font("Lucida Fax", FontWeight.BOLD, 30));
		title.setFill(Color.web("#FFD700"));
		
		Button accountButton = new Button("Your Account");
		accountButton.setStyle("-fx-background-color: #9a9a9a; -fx-text-fill: white;");
		accountButton.setFont(Font.font("arial", FontWeight.BOLD, 16));
		accountButton.setPrefWidth(150);
		
		Button logoutButton = new Button("Logout");
		logoutButton.setStyle("-fx-background-color: #9a9a9a; -fx-text-fill: white;");
		logoutButton.setFont(Font.font("arial", FontWeight.BOLD, 16));
		logoutButton.setPrefWidth(150);
		logoutButton.setOnAction(e -> app.showLoginView());
		
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		
		HBox topHeaderLayout = new HBox(10);
		topHeaderLayout.getChildren().addAll(title, spacer, accountButton, logoutButton);
		topHeaderLayout.setPadding(new Insets(10));
		
		return topHeaderLayout;
	}
	
	private HBox createFilterBar() {
		ComboBox<String> categoryCombo = new ComboBox<>();
		categoryCombo.getItems().addAll("All Categories", "Math", "English", "Natural Science", "Computer", "Other");
		categoryCombo.setValue("All Categories");		
		categoryCombo.setStyle(
				"-fx-background-color: white;" +
				"-fx-border-color: #F9C43A;" +
				"-fx-border-radius: 20;" +
				"-fx-background-radius: 20;" +
				"-fx-padding: 5 10;" +
				"-fx-font-size: 14px;"
		);
		EventHandler<ActionEvent> filterCat = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				updateBookList(categoryCombo.getSelectionModel().getSelectedItem(), "cat");
			}
		};
		categoryCombo.setOnAction(filterCat);
		
		ComboBox<String> conditionCombo = new ComboBox<>();
		conditionCombo.getItems().addAll("All Conditions", "Like New", "Moderately Used", "Heavily Used");
		conditionCombo.setValue("All Conditions");
		conditionCombo.setStyle(
				"-fx-background-color: white;" +
				"-fx-border-color: #F9C43A;" +
				"-fx-border-radius: 20;" +
				"-fx-background-radius: 20;" +
				"-fx-padding: 5 10;" +
				"-fx-font-size: 14px;"
		);
		EventHandler<ActionEvent> filterCon = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				updateBookList(conditionCombo.getSelectionModel().getSelectedItem(), "con");
			}
		};
		conditionCombo.setOnAction(filterCon);
		
		HBox filterOptionsLayout = new HBox(10);
		filterOptionsLayout.getChildren().addAll(categoryCombo, conditionCombo);
		return filterOptionsLayout;
		
	}
	
	private ScrollPane createBookList() {
		ScrollPane bookScrollPane = new ScrollPane();
		FlowPane list = new FlowPane();
		list.setId("bookList");
		list.setPadding(new Insets(5));
		list.setPrefWrapLength(600);
		
		for(Book b : sys.getPublishedBooks()) {
			list.getChildren().add(createBookItem(b));
		}
		
		bookScrollPane.setFitToWidth(true);
		bookScrollPane.setPrefHeight(650);
		bookScrollPane.setStyle("-fx-background-color: white; -fx-border-color: white; -fx-border-width: 1px");
		bookScrollPane.setFocusTraversable(false);		// allow for mouse hover to scroll
		bookScrollPane.setContent(list);
		return bookScrollPane;
	}
	
	private void clearCart() {
		this.usr.setCart(new Cart());
		ScrollPane s = (ScrollPane) this.scene.lookup("#crt");
		s.setContent(cartSetup());
		
		// JUST ADDED
		FlowPane bookList = (FlowPane) this.scene.lookup("#bookList");		// located bookList FlowPane
		
		// iterate through all children in bookList
		for (javafx.scene.Node node : bookList.getChildren()) {
			// grab each child
			if (node instanceof VBox) {
				VBox bookItem = (VBox) node;
				
				// find "Add to Cart" button in each child
				for (javafx.scene.Node child : bookItem.getChildren()) {
					// look for add to cart button
					if (child instanceof Button) {
						Button addToCartButton = (Button) child;		// grab "Add to Cart" button
						addToCartButton.setDisable(false);				// re-enable 
					}
				}
			}
		}
	}
	
	private GridPane cartSetup() {
		GridPane cartListView = new GridPane();
		cartListView.setHgap(10);
		cartListView.setVgap(5);
		
		int i = 0;
		for(Book b : usr.getCart().books) {
			Button x = new Button("X");
			x.setMaxSize(2, 2);
			cartListView.add(x, 0, i);
			Label l = new Label(b.getTitle());
			l.setFont(Font.font("Arial", FontWeight.BOLD, 16));
			cartListView.add(l, 1, i);
			Label p = new Label("$" + String.format("%.2f", b.getPrice()));
			l.setFont(Font.font("Arial", 16));
			cartListView.add(p, 2, i);
			i++;
		}
		cartListView.setPrefHeight(450);
		cartListView.setFocusTraversable(false);
		
		return cartListView;
	}
	
	private VBox createCartList() {
		Label cartLabel = new Label("Your Cart:");
		cartLabel.setFont(Font.font("arial", FontWeight.BOLD, 20));
		tx = new Label(String.format("Tax (8.1%%):\t\t\t$%.2f", usr.getCart().getTax()));
		tx.setFont(Font.font("arial", FontWeight.BOLD, 16));
		//taxLabel.setId("tax");
		to = new Label(String.format("Total:\t\t\t\t$%.2f", usr.getCart().getTotal()));
		//taxLabel.setId("total");
		to.setFont(Font.font("arial", FontWeight.BOLD, 16));
		
		ScrollPane scrl = new ScrollPane();
		scrl.setId("crt");
		VBox.setVgrow(scrl, Priority.ALWAYS);
        
        scrl.setVmax(400);
        scrl.setPrefSize(250, 400);;
        scrl.setContent(cartSetup());
		
		Button clearCartButton = new Button("Clear Cart");
		clearCartButton.setStyle("-fx-background-color: #9a9a9a; -fx-text-fill: white;");
		clearCartButton.setFont(Font.font("arial", FontWeight.BOLD, 14));
		clearCartButton.setPrefWidth(100);
		
		EventHandler<ActionEvent> clear = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				clearCart();
			}
		};
		
		clearCartButton.setOnAction(clear);
		
		Button purchaseButton = new Button("Purchase");
		purchaseButton.setStyle("-fx-background-color: #8C1D40; -fx-text-fill: white;");
		purchaseButton.setFont(Font.font("arial", FontWeight.BOLD, 16));
		purchaseButton.setPrefWidth(150);
		
		VBox cartButtonLayout = new VBox(10);
		cartButtonLayout.setPadding(new Insets(10));
		cartButtonLayout.getChildren().addAll(clearCartButton, purchaseButton);
		cartButtonLayout.setAlignment(Pos.CENTER);
		
		VBox cartLayout = new VBox(10);
		cartLayout.setPadding(new Insets(10));
		cartLayout.setStyle("-fx-background-color: white; -fx-border-color: #F9C43A; -fx-border-radius: 20; -fx-background-radius: 20; -fx-border-width: 1px");
		cartLayout.getChildren().addAll(cartLabel, scrl, tx, to, cartButtonLayout);
		return cartLayout;
	}
	
	private Scene setupScene() {
		HBox top = createTopBar();
		HBox filterBar = createFilterBar();
		ScrollPane bookList = createBookList();
		VBox cartList = createCartList();
		
		VBox centerSectionLayout = new VBox(10);
		centerSectionLayout.setPadding(new Insets(10));
		centerSectionLayout.setStyle("-fx-background-color: white; -fx-border-color: #F9C43A; -fx-border-radius: 20; -fx-background-radius: 20; -fx-border-width: 1px");
		centerSectionLayout.getChildren().addAll(filterBar, bookList);
		
		
		
		// root layout
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: #8C1D40;");
		root.setPadding(new Insets(10));
		root.setTop(top);
		root.setCenter(centerSectionLayout);
		root.setRight(cartList);
		
		// scene and stage
		return new Scene(root, 800, 700);
	}
	
	private void updateBookList(String val, String fil)
	{
		FlowPane booklist = (FlowPane) this.scene.lookup("#bookList");
		booklist.getChildren().clear();
		booklist.setPadding(new Insets(5));
		booklist.setPrefWrapLength(600);
		
		if(fil == "cat") {
			for(Book b : sys.getPublishedBooks()) {
				if(b.getCategory() == val) {
					booklist.getChildren().add(createBookItem(b));
				}
			}
		}else if(fil == "con") {
			for(Book b : sys.getPublishedBooks()) {
				if(b.getCondition() == val) {
					booklist.getChildren().add(createBookItem(b));
				}
			}
		}
	}
	
	private void updateCartCosts()
	{
//		cartTax = cartTotal * TAX_RATE;
//		cartTotalWithTax = cartTotal + cartTax;
		
		/*tx = (Label) this.scene.lookup("#tax");
		to = (Label) this.scene.lookup("#total");*/
		
		tx.setText(String.format("Tax (8.1%%):\t\t\t$%.2f", usr.getCart().getTax()));
		to.setText(String.format("Total:\t\t\t\t$%.2f", usr.getCart().getTotal()));
		
	}
	
	private void addItemToCart(Book b) {
		usr.getCart().getBooks().add(b);
		usr.getCart().price += b.getPrice();
		updateCartCosts();
		ScrollPane scrl = (ScrollPane) this.scene.lookup("#crt");
		scrl.setContent(cartSetup());
	}
	
	private VBox createBookItem(Book book)
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
		
		// Add to cart button
		Button addToCartButton = new Button("Add To Cart");
		addToCartButton.setStyle("-fx-background-color: #8C1D40; -fx-text-fill: white;");
		addToCartButton.setFont(Font.font("arial", FontWeight.BOLD, 16));
		addToCartButton.setPrefWidth(150);
		
		EventHandler<ActionEvent> addToCart = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				addItemToCart(book);
				addToCartButton.setDisable(true);
			}
		};
		
		if(usr.getCart().getBooks().contains(book)) {
			addToCartButton.setDisable(true);
		}else {
			addToCartButton.setDisable(false);
		}
		
		addToCartButton.setOnAction(addToCart);
		
		// layout of book item
		VBox bookItem = new VBox(10);
		bookItem.setPadding(new Insets(5));
		bookItem.getChildren().addAll(bookInfo, addToCartButton);
		bookItem.setPrefWidth(200);
		bookItem.setStyle("-fx-border-color: gray; -fx-border-width: 1px");
		bookItem.setAlignment(Pos.CENTER);
		
		return bookItem;
	}

}