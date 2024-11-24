
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

	public BuyerView(BookSystem s, Main a, User u, Stage primary) {
		this.sys = s;
		this.app = a;
		this.usr = u;
		this.primaryStage = primary;
		this.scene = setupScene();
		primaryStage.setScene(scene);
	}
	
	private void showAccountView() {
    	new AccountView(sys, app, usr, primaryStage, "Buyer");
    }
	
	private HBox createTopBar() {
		Text title = new Text("Book Nook");
		title.setFont(Font.font("Lucida Fax", FontWeight.BOLD, 30));
		title.setFill(Color.web("#FFD700"));
		
		Button accountButton = new Button("Your Account");
		accountButton.setStyle("-fx-background-color: #9a9a9a; -fx-text-fill: white;");
		accountButton.setFont(Font.font("arial", FontWeight.BOLD, 16));
		accountButton.setPrefWidth(150);
		accountButton.setOnAction(e -> showAccountView());
		
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
				updateBookList(categoryCombo.getSelectionModel().getSelectedItem(), conditionCombo.getSelectionModel().getSelectedItem());
			}
		};
		
		EventHandler<ActionEvent> filterCat = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				updateBookList(categoryCombo.getSelectionModel().getSelectedItem(), conditionCombo.getSelectionModel().getSelectedItem());
			}
		};
		
		categoryCombo.setOnAction(filterCat);
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
		updateCartCosts();
		ScrollPane s = (ScrollPane) this.scene.lookup("#crt");
		s.setContent(cartSetup());
		
				FlowPane bookList = (FlowPane) this.scene.lookup("#bookList");	
				
				for (javafx.scene.Node node : bookList.getChildren()) {
					if (node instanceof VBox) {
						VBox bookItem = (VBox) node;
						
						for (javafx.scene.Node child : bookItem.getChildren()) {
							if (child instanceof Button) {
								Button addToCartButton = (Button) child;
								addToCartButton.setDisable(false);		
							}
						}
					}
				}
	}
	
	private void removeItem(Book b) {
		Stage confirmation = new Stage();
		VBox pane = new VBox(20);
		pane.setPrefSize(400, 400);
		pane.setAlignment(Pos.CENTER);
        pane.setStyle("-fx-border-color: #8C1D40;");
		
		Label removeListing = new Label("Remove Listing");
		removeListing.setFont(Font.font("arial", FontWeight.BOLD, 20));
        pane.getChildren().add(removeListing);
        
        Label Second_row = new Label("Are you sure you want to remove\nthe item from the cart?");
        pane.getChildren().add(Second_row);
        
        Pane pane2 = new Pane();
        pane2.setMaxHeight(200);
        pane2.setMaxWidth(175);
        pane2.setStyle("-fx-border-color: black");
        pane.getChildren().add(pane2);
        
        Label book_name_text = new Label(b.getTitle());
        book_name_text.setLayoutX(10);
        book_name_text.setLayoutY(10);
        pane2.getChildren().add(book_name_text);
        
        Label author_text = new Label(String.format("Author: %s", b.getAuthor()));
        author_text.setLayoutX(15);
        author_text.setLayoutY(25);
        pane2.getChildren().add(author_text);
        
        Label year_text = new Label(String.format("Published: %s", b.getDate()));
        year_text.setLayoutX(15);
        year_text.setLayoutY(40);
        pane2.getChildren().add(year_text);
        
        Label category_text = new Label(String.format("Published: %s", b.getCategory()));
        category_text.setLayoutX(15);
        category_text.setLayoutY(55);
        pane2.getChildren().add(category_text);
        
        Label condition_text = new Label(String.format("Condition: %s", b.getCondition()));
        condition_text.setLayoutX(15);
        condition_text.setLayoutY(70);
        pane2.getChildren().add(condition_text);
        
        Label price_text = new Label(String.format("Price: $ %.2f", b.getPrice()));
        price_text.setLayoutX(15);
        price_text.setLayoutY(85);
        pane2.getChildren().add(price_text);
        
        Label quantity_text = new Label(String.format("Quantity: %d", b.getQuantity()));
        quantity_text.setLayoutX(15);
        quantity_text.setLayoutY(100);
        pane2.getChildren().add(quantity_text);
        
        HBox btn = new HBox(60);
        btn.setPadding(new Insets(0, 0, 0, 60));
        Button yes_btn = new Button("Yes");
        yes_btn.setMinWidth(100);
        yes_btn.setStyle("-fx-font-weight: bold; -fx-background-color: #8C1D40; -fx-text-fill: #FFD700");
        btn.getChildren().add(yes_btn);
        
        Button no_btn = new Button("No");
        no_btn.setStyle("-fx-font-weight: bold; -fx-background-color: #8C1D40; -fx-text-fill: #FFD700");
        no_btn.setMinWidth(100);
        btn.getChildren().add(no_btn);
        pane.getChildren().add(btn);
        
        EventHandler<ActionEvent> confirm = new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		usr.getCart().price -= b.getPrice();
        		usr.getCart().remove_Book(b);	
        		updateCartCosts();
        		ScrollPane s = (ScrollPane) scene.lookup("#crt");
        		s.setContent(cartSetup());
        		confirmation.close();
        		
        		FlowPane bookList = (FlowPane) scene.lookup("#bookList");
				for (javafx.scene.Node node : bookList.getChildren()) {
					if (node instanceof VBox) {
						VBox bookItem = (VBox) node;

						for (javafx.scene.Node child : bookItem.getChildren()) {
							if (child instanceof Button) {
								Button addToCartButton = (Button) child;		
								addToCartButton.setDisable(false);	
							}
						}
					}
				}
        	}
        };
        
        yes_btn.setOnAction(confirm);
        
        no_btn.setOnAction(e -> {
        	confirmation.close();
        });
        
        Scene confirmationScene = new Scene(pane, 400, 400);
        confirmation.setScene(confirmationScene);
        confirmation.show();
	}
	
	private GridPane cartSetup() {
		GridPane cartListView = new GridPane();
		cartListView.setHgap(10);
		cartListView.setVgap(5);
		
		int i = 0;
		for(Book b : usr.getCart().books) {
			if(sys.getPublishedBooks().contains(b)) {
				Button x = new Button("X");
				x.setMaxSize(2, 2);
				cartListView.add(x, 0, i);
				EventHandler<ActionEvent> rmv = new EventHandler<ActionEvent>() {
					public void handle(ActionEvent arg0) {
						removeItem(b);
					}
				};
				x.setOnAction(rmv);
				
				Label l = new Label(b.getTitle());
				l.setFont(Font.font("Arial", FontWeight.BOLD, 16));
				cartListView.add(l, 1, i);
				Label p = new Label("$" + String.format("%.2f", b.getPrice()));
				l.setFont(Font.font("Arial", 16));
				cartListView.add(p, 2, i);
				i++;
			}
		}
		cartListView.setPrefHeight(450);
		cartListView.setFocusTraversable(false);
		updateCartCosts();
		
		return cartListView;
	}
	
	private VBox createCartList() {
		Label cartLabel = new Label("Your Cart:");
		cartLabel.setFont(Font.font("arial", FontWeight.BOLD, 20));
		tx = new Label(String.format("Tax (8.1%%):\t\t\t$%.2f", usr.getCart().getTax()));
		tx.setFont(Font.font("arial", FontWeight.BOLD, 16));
		to = new Label(String.format("Total:\t\t\t\t$%.2f", usr.getCart().getTotal()));
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
				if(!usr.getCart().books.isEmpty()) {
				Stage clearConfirm = new Stage();
				VBox v = new VBox(10);
				v.setAlignment(Pos.CENTER);
				
				Label clearTitle = new Label("Clear Cart");
				clearTitle.setFont(new Font("Arial", 36));
				v.getChildren().add(clearTitle);
				
				Label ques = new Label("Are you sure you want to clear the cart?");
				v.getChildren().add(ques);
				
				HBox btn = new HBox(60);
		        btn.setPadding(new Insets(0, 0, 0, 60));
		        Button submitButton = new Button("Confirm");
		        submitButton.setMinWidth(100);
		        submitButton.setStyle("-fx-font-weight: bold; -fx-background-color: #8C1D40; -fx-text-fill: #FFD700");
		        Button cancelButton = new Button("Cancel");
		        cancelButton.setMinWidth(100);
		        cancelButton.setStyle("-fx-font-weight: bold; -fx-background-color: #8C1D40; -fx-text-fill: #FFD700");
		        
				
		        EventHandler<ActionEvent> submit = new EventHandler<ActionEvent>() {
					public void handle(ActionEvent arg0) {
						clearCart();
						clearConfirm.close();
					}
		        };
		        submitButton.setOnAction(submit);
		        cancelButton.setOnAction(event -> clearConfirm.close());
		        btn.getChildren().add(submitButton);
		        btn.getChildren().add(cancelButton);
		        
		        v.getChildren().add(btn);
		        Scene clearScene = new Scene(v, 400, 400);
		        clearConfirm.setScene(clearScene);
		        clearConfirm.show();
				}
			}
		};
		
		clearCartButton.setOnAction(clear);
		
		Button purchaseButton = new Button("Purchase");
		purchaseButton.setStyle("-fx-background-color: #8C1D40; -fx-text-fill: white;");
		purchaseButton.setFont(Font.font("arial", FontWeight.BOLD, 16));
		purchaseButton.setPrefWidth(150);	
		purchaseButton.setOnAction(e -> {
			if(!usr.getCart().getBooks().isEmpty()) {
				showPurchaseConfirmationPopup();
			}
		});
		
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
		
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: #8C1D40;");
		root.setPadding(new Insets(10));
		root.setTop(top);
		root.setCenter(centerSectionLayout);
		root.setRight(cartList);
		
		return new Scene(root, 800, 700);
	}
	
	private void updateBookList(String cat, String con)
	{
		FlowPane booklist = (FlowPane) this.scene.lookup("#bookList");
		booklist.getChildren().clear();
		booklist.setPadding(new Insets(5));
		booklist.setPrefWrapLength(600);
		
		for(Book b : sys.getPublishedBooks()) {
			if((b.getCategory().equals(cat) || cat.equals("All Categories")) 
					&& 
				(b.getCondition().equals(con) || con.equals("All Conditions")
			)) {
				booklist.getChildren().add(createBookItem(b));
			}
		}
	}
	
	private void updateCartCosts()
	{
		to.setText(String.format("Total:\t\t\t\t$%.2f", usr.getCart().getTotal()));
		tx.setText(String.format("Tax (8.1%%):\t\t\t$%.2f", usr.getCart().getTax()));
		
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
	
	private void showPurchaseConfirmationPopup() {
	    // Create the confirmation dialog
	    Stage popup = new Stage();
	    popup.setTitle("Purchase Confirmation");
	    VBox v = new VBox(20);
	    v.setPrefSize(400, 400);
	    v.setStyle("-fx-border-color: #8C1D40;");
	    v.setAlignment(Pos.CENTER);
	    
	    Label top = new Label("Confirm Purchase");
	    top.setFont(Font.font("arial", FontWeight.BOLD, 20));
	    v.getChildren().add(top);
	    
	    // Set up the message text
	    Label confirmationMessage = new Label("Are you sure you want to complete the purchase?");
	    v.getChildren().add(confirmationMessage);
	    
	    Label confirmationMessage1 = new Label("By selecting \"Yes\", you agree to the terms and will be\nbilled for the total below.");
	    v.getChildren().add(confirmationMessage1);
	    
	    Label confirmationMessage2 = new Label("Selecting \"No\" will close the popup and\nbring you back to the listings.");
	    v.getChildren().add(confirmationMessage2);
	    
	    Label total = new Label(String.format("GRAND TOTAL:\t$%.2f", usr.getCart().getTotal()) );
	    total.setFont(Font.font("Arial", FontWeight.BOLD, 18));
	    v.getChildren().add(total);
	    
	    // Create the Yes and No buttons
	    HBox btn = new HBox(60);
	    btn.setPadding(new Insets(0, 0, 0, 60));
	    
	    Button yesButton = new Button("Yes");
	    yesButton.setStyle("-fx-font-weight: bold; -fx-background-color: #8C1D40; -fx-text-fill: #FFD700");
	    yesButton.setMinWidth(100);
	    yesButton.setOnAction(e -> {
	        // Confirm the purchase
	        completePurchase();
	        popup.close(); // Close the popup
	    });
	    btn.getChildren().add(yesButton);
	    
	    Button noButton = new Button("No");
	    noButton.setStyle("-fx-font-weight: bold; -fx-background-color: #8C1D40; -fx-text-fill: #FFD700");
	    noButton.setMinWidth(100);
	    noButton.setOnAction(e -> {
	        // Cancel the purchase
	        popup.close(); // Close the popup
	    });
	    btn.getChildren().add(noButton);

	    v.getChildren().add(btn);
	    
	    // Set up the scene and stage for the popup
	    Scene popupScene = new Scene(v, 400, 400);
	    popup.setScene(popupScene);
	    popup.show();
	}
	
	private void completePurchase() {
	    for (Book b : usr.getCart().getBooks()) {
	        b.changeQuantity(b.getQuantity() - 1); // Reduce quantity
	        if (b.getQuantity() <= 0) {
	            sys.getPublishedBooks().remove(b); // Remove book if out of stock
	        }
	    }
	    usr.purchaseCart();
	    usr.setCart(new Cart());       // Clear the cart after purchase
	    updateCartCosts();             // Update the cart costs (now zero)
	    ScrollPane cartScrollPane = (ScrollPane) this.scene.lookup("#crt");
	    cartScrollPane.setContent(cartSetup()); // Refresh cart UI

	    FlowPane bookList = (FlowPane) this.scene.lookup("#bookList");
	    bookList.getChildren().clear(); // Clear and refresh the book list
	    for (Book b : sys.getPublishedBooks()) {
	        bookList.getChildren().add(createBookItem(b));
	    }
	}

}