

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SellerView {
	private Main app;
	BookSystem sys;
	User usr;
	private ToggleGroup g;
	private Stage primaryStage;
	
    private Scene scene; // Store the Scene object

    
    public SellerView(BookSystem s, Main a, User u, Stage primary) {
    	this.app = a;
    	this.sys = s;
    	this.usr = u;
    	this.primaryStage = primary;
    	scene = setupScene();
    	primaryStage.setScene(scene);
    }
    
    private void showAccountView() {
    	new AccountView(sys, app, usr, primaryStage, "Seller");
    }
    
    private VBox scrlSetup() {
    	VBox list = new VBox(20);
        
    	g = new ToggleGroup();
    	
        for(Book b : sys.getPublishedBooks()) {
        	if(b.getSeller().equals(usr.getEmail())) {
        	RadioButton n = new RadioButton(
        			b.getTitle() + "\n" +
        			"Author: " + b.getAuthor() + "\n" +
        			"Published: " + b.getDate() + "\n" +
        			"Category: " + b.getCategory() + "\n" +
        			"Condition: " + b.getCondition() + "\n" +
        			"Selling: " + b.getQuantity() + "\n"
        	);
        	n.setUserData(b);
        	n.setToggleGroup(g);
        	list.getChildren().add(n);
        	}
        }
        
        return list;
    }
    
    private Pane createOuter() {
    	// First pane
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #8C1D40;");
        
        // Book Nook label
        Text title = new Text("Book Nook");
		title.setFont(Font.font("Lucida Fax", FontWeight.BOLD, 36));
		title.setFill(Color.web("#FFD700"));
        title.setLayoutX(50); 
        title.setLayoutY(60); 
        pane.getChildren().add(title);
        
        //Your Account Button
        Button your_account_btn = new Button("Your Account");
        your_account_btn.setPrefWidth(150); 
        your_account_btn.setPrefHeight(40);
        your_account_btn.setLayoutX(350);
        your_account_btn.setLayoutY(30);
        your_account_btn.setStyle("-fx-background-color: #9a9a9a; -fx-text-fill: white;");
		your_account_btn.setFont(Font.font("arial", FontWeight.BOLD, 16));
		your_account_btn.setOnAction(event -> showAccountView());
        pane.getChildren().add(your_account_btn);
        
        
        //Logout Button
        Button log_out_Button = new Button("Logout");
        log_out_Button.setStyle("-fx-background-color: #9a9a9a; -fx-text-fill: white;");
        log_out_Button.setPrefWidth(150); 
        log_out_Button.setPrefHeight(40);
        log_out_Button.setLayoutX(550);
        log_out_Button.setLayoutY(30);
        log_out_Button.setFont(Font.font("arial", FontWeight.BOLD, 16));
        pane.getChildren().add(log_out_Button);
        log_out_Button.setOnAction(event -> app.showLoginView());
        
        Pane inner = createInner();
        inner.setLayoutX(50);
        inner.setLayoutY(100);
        
        pane.getChildren().add(inner);
        
        return pane;
        
    }
    
    private Pane createInner() {
    	Pane overall = new Pane();
    	overall.setPrefSize(700, 550);
    	overall.setStyle("-fx-background-color: white");
    	
    	VBox createList = createListingView();
    	createList.setLayoutX(35);
    	createList.setLayoutY(10);
    	overall.getChildren().add(createList);
    	
    	VBox yourListings = createYourListingsView();
    	yourListings.setLayoutX(415);
    	yourListings.setLayoutY(10);
    	overall.getChildren().add(yourListings);
    	
    	return overall;
		
    }
    
    private VBox createListingView() {
    	VBox result = new VBox(25);
    	result.setAlignment(Pos.CENTER);
    	
    	Label create_listing = new Label("Create Listing");
        create_listing.setFont(new Font("Arial", 36)); 
        create_listing.setPadding(new Insets(0, 0, 30, 0));
        result.getChildren().add(create_listing);
    	
    	GridPane form = new GridPane();
    	form.setHgap(25);
    	form.setVgap(25);
    	form.setPrefWidth(400);
    	form.setPadding(new Insets(0, 0, 34, 0));
    	
        	//Booking Listing part
    		Label Book_name = new Label("Name:");
    		Book_name.setFont(new Font("Arial", 16));
    		form.add(Book_name, 0, 0);
    		
    		TextField Book_name_textfield = new TextField();
    		Book_name_textfield.setPrefWidth(200);
    		Book_name_textfield.setId("bookField");
    		form.add(Book_name_textfield, 1, 0);
    		
    		Label author_name = new Label("Author:");
    		author_name.setFont(new Font("Arial", 16));
    		form.add(author_name, 0, 1);
    		
    		TextField author_name_textField = new TextField();
    		author_name_textField.setPrefWidth(200);
    		author_name_textField.setId("authorField");
    		form.add(author_name_textField, 1, 1);
    		
    		Label publication_date = new Label("Publication Date:");
    		publication_date.setFont(new Font("Arial", 16));
    		form.add(publication_date, 0, 2);
    		
    		TextField publication_date_textField = new TextField();
    		publication_date_textField.setPrefWidth(200);
    		publication_date_textField.setId("pubField");
    		form.add(publication_date_textField, 1, 2);
    		
    		Label category = new Label("Category:");
    		category.setFont(new Font("Arial", 16));
    		form.add(category, 0, 3);
    		
    		ComboBox<String> category_groupOption = new ComboBox<>();
    		category_groupOption.getItems().addAll("Math", "English", "Natural Science", "Computer", "Other");
    		category_groupOption.setPrefWidth(200);
    		category_groupOption.setId("catField");
    		form.add(category_groupOption, 1, 3);
    		
    		Label condition = new Label("Condition:");
    		condition.setFont(new Font("Arial", 16));
    		form.add(condition, 0, 4);
    		
    		ComboBox<String> condition_groupOption = new ComboBox<>();
    		condition_groupOption.getItems().addAll("Like New", "Moderately Used", "Heavily Used");
    		condition_groupOption.setPrefWidth(200);
    		condition_groupOption.setId("conField");
    		form.add(condition_groupOption, 1, 4);
   
    		Label quantity = new Label("Quantity:");
    		quantity.setFont(new Font("Arial", 16));
    		form.add(quantity, 0, 5);
    		
    		TextField quantity_textField = new TextField();
    		quantity_textField.setPrefWidth(200);
    		quantity_textField.setId("quanField");
    		form.add(quantity_textField, 1, 5);
    		
    		Label price = new Label("Price:");
    		price.setFont(new Font("Arial", 16));
    		form.add(price, 0, 6);
    		
    		TextField price_textField = new TextField();
    		price_textField.setPrefWidth(200);
    		price_textField.setId("priceField");
    		form.add(price_textField, 1, 6);
    		
    		result.getChildren().add(form);
    		
    		 // Add the Publish button
    		Button publish_Btn = new Button("Publish");
    		publish_Btn.setStyle("-fx-background-color: #8C1D40; -fx-text-fill: white;");
    		publish_Btn.setFont(Font.font("arial", FontWeight.BOLD, 16));
    		publish_Btn.setPrefWidth(200);
    		publish_Btn.setPrefHeight(50);
    		result.getChildren().add(publish_Btn);
    		publish_Btn.setOnAction(event -> {
    			Book b = new Book(Book_name_textfield.getText(), 
    					author_name_textField.getText(), 
    					publication_date_textField.getText(), 
    					category_groupOption.getSelectionModel().getSelectedItem(), 
    					condition_groupOption.getSelectionModel().getSelectedItem(),
    					Double.parseDouble(price_textField.getText()),
    					Integer.parseInt((quantity_textField.getText())),
    					usr.getEmail());
    			
    			confirm_user_window(b);
    		});
    	
    	return result;
        
        
    }
    
    private VBox createYourListingsView() {
    	VBox result = new VBox(20);
    	result.prefWidth(300);
    	result.setAlignment(Pos.CENTER);
    	
    	Label your_listing = new Label("Your Listings");
        your_listing.setFont(new Font("Arial", 36)); 
        result.getChildren().add(your_listing);
        
        ScrollPane scrl = new ScrollPane();
        VBox.setVgrow(scrl, Priority.ALWAYS);
        
        scrl.setVmax(400);
        scrl.setPrefSize(250, 400);;
        scrl.setContent(scrlSetup());
        scrl.setId("scroll");

        result.getChildren().add(scrl);
        
        Button delete_Btn = new Button("Delete");
        delete_Btn.setStyle("-fx-background-color: #8C1D40; -fx-text-fill: white;");
        delete_Btn.setFont(Font.font("arial", FontWeight.BOLD, 16));
		delete_Btn.setPrefWidth(200);
		delete_Btn.setPrefHeight(50);
		result.getChildren().add(delete_Btn);
		delete_Btn.setOnAction(event -> {
			Book b = (Book) g.getSelectedToggle().getUserData();
			
			deleted_window(b);
		});
		
		return result;
    }
    
    private Scene setupScene() {
    	Pane outer = createOuter();
        
    	return (new Scene(outer, 800, 700));
    }
    
   private void confirm_user_window(Book b) {
        Stage confirmationStage = new Stage();
        confirmationStage.setTitle("Confirm Publishing");
        
        VBox pane = new VBox(20);
        pane.setPrefSize(400, 400);
        pane.setAlignment(Pos.CENTER);
        pane.setStyle("-fx-border-color: #8C1D40;");

        Label Publish_Listing = new Label("Publish Listing");
        Publish_Listing.setFont(Font.font("arial", FontWeight.BOLD, 20));
        pane.getChildren().add(Publish_Listing);
        
        Label Second_row = new Label("Are you sure you want to publish the following listing?\n"
        		+ "Note: If you publish improper listings, you may loose\n"
        		+ "access to selling books on BookNook");
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
        
        
        Label Fifth_row = new Label("By clicking yes, you agree that 20% of your profit will go\n"
        		+ "to the system administration");
        pane.getChildren().add(Fifth_row);
        
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
        

        Scene confirmationScene = new Scene(pane, 400, 400);
        confirmationStage.setScene(confirmationScene);
        confirmationStage.show();
        
        
        EventHandler<ActionEvent> confirm = new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		confirmListing(b);	
        		confirmationStage.close();
        	}
        };
        
        yes_btn.setOnAction(confirm);
        
        no_btn.setOnAction(e -> {
        	confirmationStage.close();
        });
        
    }

   private void confirmListing(Book b) {
	   sys.addBook(b);
	   ArrayList<Book> l = usr.getListings();
   	   l.add(b);
   	   usr.setListings(l);
   	   
   	   TextField title = (TextField) this.scene.lookup("#bookField");
   	   title.clear();
    
   	   TextField author = (TextField) this.scene.lookup("#authorField");
   	   author.clear();

   	   TextField pub = (TextField) this.scene.lookup("#pubField");
   	   pub.clear();
    
   	   @SuppressWarnings("unchecked")
   	   ComboBox<String> cat = (ComboBox<String>) this.scene.lookup("#catField");
   	   cat.getSelectionModel().clearSelection();
    
   	   @SuppressWarnings("unchecked")
   	   ComboBox<String> con = (ComboBox<String>) this.scene.lookup("#conField");
   	   con.getSelectionModel().clearSelection();
    
   	   TextField quan = (TextField) this.scene.lookup("#quanField");
   	   quan.clear();
    
   	   TextField price = (TextField) this.scene.lookup("#priceField");
   	   price.clear();
   	   
   	   ScrollPane s = (ScrollPane) this.scene.lookup("#scroll");
   	   s.setContent(scrlSetup());
   	   
   	   primaryStage.setScene(scene);
   }
  
   private void deleted_window(Book selection) {
            Stage deletionStage = new Stage();
            deletionStage.setTitle("Delete Listing");
            
            VBox pane = new VBox(20);
            pane.setPrefSize(400, 400);
            pane.setAlignment(Pos.CENTER);
            pane.setStyle("-fx-border-color: #8C1D40;");

            Label removeListing = new Label("Remove Listing");
            removeListing.setFont(Font.font("arial", FontWeight.BOLD, 20));
            pane.getChildren().add(removeListing);

            Label promptText = new Label("Are you sure you want to remove the following listing?");
            pane.getChildren().add(promptText);

            Pane detailsPane = new Pane();
            detailsPane.setMaxHeight(200);
            detailsPane.setMaxWidth(175);
            detailsPane.setStyle("-fx-border-color: black");
            pane.getChildren().add(detailsPane);

            Label bookNameText = new Label(selection.getTitle());
            bookNameText.setLayoutX(10);
            bookNameText.setLayoutY(10);
            detailsPane.getChildren().add(bookNameText);

            Label authorText = new Label(String.format("Author: %s", selection.getAuthor()));
            authorText.setLayoutX(15);
            authorText.setLayoutY(25);
            detailsPane.getChildren().add(authorText);

            Label yearText = new Label(String.format("Published: %s", selection.getDate()));
            yearText.setLayoutX(15);
            yearText.setLayoutY(40);
            detailsPane.getChildren().add(yearText);

            Label categoryText = new Label(String.format("Category: %s", selection.getCategory()));
            categoryText.setLayoutX(15);
            categoryText.setLayoutY(55);
            detailsPane.getChildren().add(categoryText);

            Label conditionText = new Label(String.format("Condition: %s", selection.getCondition()));
            conditionText.setLayoutX(15);
            conditionText.setLayoutY(70);
            detailsPane.getChildren().add(conditionText);

            Label priceText = new Label(String.format("Price: $ %.2f", selection.getPrice()));
            priceText.setLayoutX(15);
            priceText.setLayoutY(85);
            detailsPane.getChildren().add(priceText);

            Label quantityText = new Label(String.format("Quantity: %d", selection.getQuantity()));
            quantityText.setLayoutX(15);
            quantityText.setLayoutY(100);
            detailsPane.getChildren().add(quantityText);
            
            HBox btn = new HBox(60);
            btn.setPadding(new Insets(0, 0, 0, 70));
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
            		removeBook(selection);
            		deletionStage.close();
            	}
            };
            
            yes_btn.setOnAction(confirm);
            
            no_btn.setOnAction(e -> {
            	deletionStage.close();
            });



            Scene deletionScene = new Scene(pane, 400, 400);
            deletionStage.setScene(deletionScene);
            deletionStage.show();
   }
   
   private void removeBook(Book selection) {
	   usr.getListings().remove(selection);
		sys.getPublishedBooks().remove(selection);
		
		ScrollPane s = (ScrollPane) this.scene.lookup("#scroll");
		s.setContent(scrlSetup());
		primaryStage.setScene(scene);
   }
            
    // Method to return the Scene
    public Scene getScene() {
    	return setupScene();
    }
}