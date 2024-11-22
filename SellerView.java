
import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;


public class SellerView {
	private Main app;
	BookSystem sys;
    private Scene scene; // Store the Scene object
    
    private ArrayList<Book> book_arr; 
    private ObservableList<String> listings;
    
    private TextField Book_name_textfield;
    private TextField author_name_textField;
    private TextField publication_date_textField;
    private TextField price_textField;
    private TextField quantity_textField;
    private ComboBox<String> category_groupOption;
    private ComboBox<String> condition_groupOption;

    
    public SellerView(BookSystem s, Main a) {
    	this.app = a;
    	this.sys = s;
    	scene = setupScene();
    	
    	listings = FXCollections.observableArrayList();
        book_arr = new ArrayList<>();
    	
        
        
        
        //Create a listing
        Label create_listing = new Label("Create a Listing");
        create_listing.setFont(new Font("Arial", 35)); 
        create_listing.setLayoutX(10);
        create_listing.setLayoutY(10);
        pane2.getChildren().add(create_listing);
        
        Label your_listing = new Label("Your Listings");
        your_listing.setFont(new Font("Arial", 35)); 
        your_listing.setLayoutX(500);
        your_listing.setLayoutY(10);
        pane2.getChildren().add(your_listing);
        
        
        //Booking Listing part
    
		Label Book_name = new Label("Name");
		Book_name.setFont(new Font("Arial", 16));
		Book_name.setLayoutX(20);
		Book_name.setLayoutY(60);
		pane2.getChildren().add(Book_name);
		
		Book_name_textfield = new TextField();
		Book_name_textfield.setLayoutX(150);
		Book_name_textfield.setLayoutY(60);
		Book_name_textfield.setPrefWidth(200);
		pane2.getChildren().add(Book_name_textfield);
		
		Label author_name = new Label("Author");
		author_name.setFont(new Font("Arial", 16));
		author_name.setLayoutX(20);
		author_name.setLayoutY(100);
		pane2.getChildren().add(author_name);
		
		author_name_textField = new TextField();
		author_name_textField.setLayoutX(150);
		author_name_textField.setLayoutY(100);
		author_name_textField.setPrefWidth(200);
		pane2.getChildren().add(author_name_textField);
		
		Label publication_date = new Label("Publication Date");
		publication_date.setFont(new Font("Arial", 16));
		publication_date.setLayoutX(20);
		publication_date.setLayoutY(140);
		pane2.getChildren().add(publication_date);
		
		publication_date_textField = new TextField();
		publication_date_textField .setLayoutX(150);
		publication_date_textField .setLayoutY(140);
		publication_date_textField .setPrefWidth(200);
		pane2.getChildren().add(publication_date_textField);
		
		Label category = new Label("Category");
		category.setFont(new Font("Arial", 16));
		category.setLayoutX(20);
		category.setLayoutY(180);
		pane2.getChildren().add(category );
		
		category_groupOption = new ComboBox<>();
		category_groupOption.getItems().addAll("Fiction", "Non-Fiction", "Science", "Biography", "Other");
		category_groupOption.setLayoutX(150);
		category_groupOption.setLayoutY(180);
		category_groupOption.setPrefWidth(200);
		pane2.getChildren().add(category_groupOption);
		
		Label condition = new Label("Condition");
		condition.setFont(new Font("Arial", 16));
		condition.setLayoutX(20);
		condition.setLayoutY(220);
		pane2.getChildren().add(condition);
		
		condition_groupOption = new ComboBox<>();
		condition_groupOption.getItems().addAll("New", "Used", "Bad");
		condition_groupOption.setLayoutX(150);
		condition_groupOption.setLayoutY(220);
		condition_groupOption.setPrefWidth(200);
		pane2.getChildren().add(condition_groupOption);
		
		Label quantity = new Label("Quantity");
		quantity.setFont(new Font("Arial", 16));
		quantity.setLayoutX(20);
		quantity.setLayoutY(260);
		pane2.getChildren().add(quantity);
		
		quantity_textField = new TextField();
		quantity_textField.setLayoutX(150);
		quantity_textField.setLayoutY(260);
		quantity_textField.setPrefWidth(200);
		pane2.getChildren().add(quantity_textField);
		
		Label price = new Label("Price");
		price.setFont(new Font("Arial", 16));
		price.setLayoutX(20);
		price.setLayoutY(300);
		pane2.getChildren().add(price);
		
		price_textField = new TextField();
		price_textField.setLayoutX(150);
		price_textField.setLayoutY(300);
		price_textField.setPrefWidth(200);
	    pane2.getChildren().add(price_textField);
		
		 // Add the Publish button
		Button publish_Btn = new Button("Publish");
		publish_Btn.setLayoutX(150);
		publish_Btn.setLayoutY(350);
		publish_Btn.setPrefWidth(100);
		pane2.getChildren().add(publish_Btn);
		
		//Add the delete button 
		Button delete_Btn = new Button("Delete");
		delete_Btn.setLayoutX(500);
		delete_Btn.setLayoutY(420);
		delete_Btn.setPrefWidth(100);
		pane2.getChildren().add(delete_Btn);
		

	
		ListView<String> listingsListView = new ListView<>(listings);
		listingsListView.setLayoutX(420);
		listingsListView.setLayoutY(60);
		listingsListView.setPrefWidth(350);
		listingsListView.setPrefHeight(350);
		pane2.getChildren().add(listingsListView);


		publish_Btn.setOnAction(e -> {
			 // Get book details
            String BookName = Book_name_textfield.getText();
            String AuthorName = author_name_textField.getText();
            int PublicationDate = Integer.parseInt(publication_date_textField.getText());
            String Category = category_groupOption.getValue();
            String Condition = condition_groupOption.getValue();
            int Quantity = Integer.parseInt(quantity_textField.getText());
            int Price = Integer.parseInt(price_textField.getText());

            confirm_user_window(BookName, AuthorName, PublicationDate, Category, Condition, Quantity, Price);

        });
		
		delete_Btn.setOnAction(e -> {
	        Book deletedBook = null;
		    String selectedListing = listingsListView.getSelectionModel().getSelectedItem();
		    if (selectedListing != null) {
		        for (Book search_book : book_arr) {
		            String listingFormat = String.format(
		                "%s\nAuthor: %s\nPublished: %d\nCategory: %s\nCondition: %s\nSelling: %d\n",
		                search_book.getBookName(),
		                search_book.getAuthorName(),
		                search_book.getPublicationDate(),
		                search_book.getCategory(),
		                search_book.getCondition(),
		                search_book.getQuantity()
		            );
		            if (listingFormat.equals(selectedListing)) {
		                deletedBook = search_book;
		                break; //Book Found
		            }
		        }
		        if (deletedBook != null) {
		            deleted_window(
		                deletedBook.getBookName(), deletedBook.getAuthorName(), deletedBook.getPublicationDate(), deletedBook.getCategory(), 
		                deletedBook.getCondition(), deletedBook.getQuantity(), deletedBook.getPrice()   
		            );
		        }
		    }
		});



        this.scene = new Scene(pane, 900, 550);
    }
    
    private Scene setupScene() {
    	// First pane
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: darkred;");
        
     // Book Nook label
        Label bookNook = new Label("Book Nook");
        bookNook.setStyle("-fx-background-color: Yellow");
        bookNook.setFont(new Font("Arial", 24)); 
        bookNook.setLayoutX(10); 
        bookNook.setLayoutY(10); 
        pane.getChildren().add(bookNook);
        
      //Your Account Button
        Button your_account_btn = new Button("Your Account");
        your_account_btn.setPrefWidth(100); 
        your_account_btn.setPrefHeight(20);
        your_account_btn.setLayoutX(650);
        your_account_btn.setLayoutY(10);
        pane.getChildren().add(your_account_btn);
        
      //Logout Button
        Button log_out_Button = new Button("Log Out");
        log_out_Button.setPrefWidth(100); 
        log_out_Button.setPrefHeight(20);
        log_out_Button.setLayoutX(790);
        log_out_Button.setLayoutY(10);
        pane.getChildren().add(log_out_Button);
    }
    
    private void confirm_user_window(String bookName, String authorName, int publication_year, String category, String condition, int quantity, double price) {
        Stage confirmationStage = new Stage();
        Pane pane = new Pane();
        pane.setPrefSize(400, 400);

        Label Publish_Listing = new Label("Publish Listing");
        Publish_Listing.setFont(new Font("Arial", 20)); 
        Publish_Listing.setLayoutX(140);
        Publish_Listing.setLayoutY(10);
        pane.getChildren().add(Publish_Listing);
        
        Label Second_row = new Label("Are you sure you want to publish the following listing?");
        Second_row.setLayoutX(60);
        Second_row.setLayoutY(40);
        pane.getChildren().add(Second_row);
        
        Label Third_row = new Label("Note: If you publish improper listings, you may loose");
        Third_row.setLayoutX(60);
        Third_row.setLayoutY(60);
        pane.getChildren().add(Third_row);
        
        Label Fourth_row = new Label("access to selling books on BookNook");
        Fourth_row.setLayoutX(80);
        Fourth_row.setLayoutY(80);
        pane.getChildren().add(Fourth_row);
        
        
        Pane pane2 = new Pane();
        pane2.setPrefSize(200, 200);
        pane2.setLayoutX(50);
        pane2.setLayoutY(100);
        pane.getChildren().add(pane2);
        
        Label book_name_text = new Label(bookName);
        book_name_text.setLayoutX(70);
        book_name_text.setLayoutY(10);
        pane2.getChildren().add(book_name_text);
        
        Label author_text = new Label(String.format("Author: %s", authorName));
        author_text.setLayoutX(80);
        author_text.setLayoutY(25);
        pane2.getChildren().add(author_text);
        
        Label year_text = new Label(String.format("Published: %d", publication_year));
        year_text.setLayoutX(80);
        year_text.setLayoutY(40);
        pane2.getChildren().add(year_text);
        
        Label category_text = new Label(String.format("Published: %s", category));
        category_text.setLayoutX(80);
        category_text.setLayoutY(55);
        pane2.getChildren().add(category_text);
        
        Label condition_text = new Label(String.format("Condition: %s", condition));
        condition_text.setLayoutX(80);
        condition_text.setLayoutY(70);
        pane2.getChildren().add(condition_text);
        
        Label price_text = new Label(String.format("Price: $ %.2f", price));
        price_text.setLayoutX(80);
        price_text.setLayoutY(85);
        pane2.getChildren().add(price_text);
        
        Label quantity_text = new Label(String.format("Quantity: %d", quantity));
        quantity_text.setLayoutX(80);
        quantity_text.setLayoutY(100);
        pane2.getChildren().add(quantity_text);
        
        
        Label Fifth_row = new Label("By Clicking yes, you agree that 20% of your profit will go");
        Fifth_row.setLayoutX(60);
        Fifth_row.setLayoutY(240);
        pane.getChildren().add(Fifth_row);
        
        Label Sixth_row = new Label("to the system administration");
        Sixth_row.setLayoutX(100);
        Sixth_row.setLayoutY(260);
        pane.getChildren().add(Sixth_row);
     
        Button yes_btn = new Button("Yes");
        yes_btn.setLayoutX(60);
        yes_btn.setLayoutY(300);
        pane.getChildren().add(yes_btn);
        
        Button no_btn = new Button("No");
        no_btn.setLayoutX(300);
        no_btn.setLayoutY(300);
        pane.getChildren().add(no_btn);
        

        Scene confirmationScene = new Scene(pane, 400, 400);
        confirmationStage.setScene(confirmationScene);
        confirmationStage.show();
        
        yes_btn.setOnAction(e -> {
        	String listing = String.format(
                    "%s\nAuthor: %s\nPublished: %d\nCategory: %s\nCondition: %s\nSelling: %d\n",
                    bookName, authorName, publication_year, category, condition, quantity, price);
        	listings.add(listing);
        	
        	Book new_book = new Book(bookName, authorName, publication_year, category, condition, quantity, price);
            book_arr.add(new_book);   
            
            Book_name_textfield.clear();
            author_name_textField.clear();
            publication_date_textField.clear();
            quantity_textField.clear();
            price_textField.clear();
            category_groupOption.getSelectionModel().clearSelection();
            condition_groupOption.getSelectionModel().clearSelection();
            	
            
            confirmationStage.close();
            
        });
        
        no_btn.setOnAction(e -> {
        	confirmationStage.close();
        });
        
    }
        
        private void deleted_window(String bookName, String authorName, int publication_year, String category, String condition, int quantity, double price) {
            Stage deletionStage = new Stage();
            Pane pane = new Pane();
            pane.setPrefSize(400, 400);

            Label removeListing = new Label("Remove Listing");
            removeListing.setFont(new Font("Arial", 20));
            removeListing.setLayoutX(140);
            removeListing.setLayoutY(10);
            pane.getChildren().add(removeListing);

            Label promptText = new Label("Are you sure you want to remove the following listing?");
            promptText.setLayoutX(60);
            promptText.setLayoutY(40);
            pane.getChildren().add(promptText);

            Pane detailsPane = new Pane();
            detailsPane.setPrefSize(200, 200);
            detailsPane.setLayoutX(50);
            detailsPane.setLayoutY(100);
            pane.getChildren().add(detailsPane);

            Label bookNameText = new Label(bookName);
            bookNameText.setLayoutX(70);
            bookNameText.setLayoutY(10);
            detailsPane.getChildren().add(bookNameText);

            Label authorText = new Label(String.format("Author: %s", authorName));
            authorText.setLayoutX(80);
            authorText.setLayoutY(25);
            detailsPane.getChildren().add(authorText);

            Label yearText = new Label(String.format("Published: %d", publication_year));
            yearText.setLayoutX(80);
            yearText.setLayoutY(40);
            detailsPane.getChildren().add(yearText);

            Label categoryText = new Label(String.format("Category: %s", category));
            categoryText.setLayoutX(80);
            categoryText.setLayoutY(55);
            detailsPane.getChildren().add(categoryText);

            Label conditionText = new Label(String.format("Condition: %s", condition));
            conditionText.setLayoutX(80);
            conditionText.setLayoutY(70);
            detailsPane.getChildren().add(conditionText);

            Label priceText = new Label(String.format("Price: $ %.2f", price));
            priceText.setLayoutX(80);
            priceText.setLayoutY(85);
            detailsPane.getChildren().add(priceText);

            Label quantityText = new Label(String.format("Quantity: %d", quantity));
            quantityText.setLayoutX(80);
            quantityText.setLayoutY(100);
            detailsPane.getChildren().add(quantityText);

            Button yesButton = new Button("Yes");
            yesButton.setLayoutX(60);
            yesButton.setLayoutY(300);
            pane.getChildren().add(yesButton);

            Button noButton = new Button("No");
            noButton.setLayoutX(300);
            noButton.setLayoutY(300);
            pane.getChildren().add(noButton);

            Scene deletionScene = new Scene(pane, 400, 400);
            deletionStage.setScene(deletionScene);
            deletionStage.show();

            yesButton.setOnAction(e -> {
                String deleted_book = String.format(
                    "%s\nAuthor: %s\nPublished: %d\nCategory: %s\nCondition: %s\nSelling: %d\n",
                    bookName, authorName, publication_year, category, condition, quantity, price
                );
                listings.remove(deleted_book);

                Book deletedBook = new Book(bookName, authorName, publication_year, category, condition, quantity, price);
                if (deletedBook != null) {
                    book_arr.remove(deletedBook);
                }
                deletionStage.close();
            });

            noButton.setOnAction(e -> {
                deletionStage.close();
            });
            
        }


    // Method to return the Scene
    public Scene getScene() {
        return scene;
    }
}
