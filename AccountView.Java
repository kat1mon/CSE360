import java.util.ArrayList;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AccountView{
    private Main app;
	BookSystem sys;
	User usr;
	private ToggleGroup g;
	
    private Scene scene;
	public AccountView(BookSystem s, Main a, User u) {
    	this.app = a;
    	this.sys = s;
    	this.usr = u;
    	scene = setupScene();
    }
    
    private VBox scrlSetup() {
    	VBox list = new VBox(20);
        
    	g = new ToggleGroup();
    	
        for(Book b : usr.getListings()) {
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
        
        return list;
    }
    
    private Pane createOuter() {
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
        Button back_btn = new Button("Home");
        back_btn.setPrefWidth(100); 
        back_btn.setPrefHeight(20);
        back_btn.setLayoutX(500);
        back_btn.setLayoutY(10);
        pane.getChildren().add(back_btn);
        
        //Logout Button
        Button log_out_Button = new Button("Log Out");
        log_out_Button.setPrefWidth(100); 
        log_out_Button.setPrefHeight(20);
        log_out_Button.setLayoutX(650);
        log_out_Button.setLayoutY(10);
        pane.getChildren().add(log_out_Button);
        //log_out_Button.setOnAction(event -> app.showLoginView());
        
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

        String username = User.getEmail();
        Label greeting = new Label("Welcome " + username);
        greeting.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        overall.getChildren().add(greeting);

        Button change_Pswd_Button = new Button("Change Password");
        withdraw_Button.setPrefWidth(200);
		withdraw_Button.setPrefHeight(50);
        change_Pswd_Button.setOnAction(e -> changingPassword());

        Button withdraw_Button = new Button("Withdraw Funds");
        withdraw_Button.setPrefWidth(200);
		withdraw_Button.setPrefHeight(50);
        //withdraw_Button.setOnAction(e -> withdrawFunds());
    	
    	VBox recentOrders = cartListView();
    	recentOrders.setLayoutX(35);
    	recentOrders.setLayoutY(10);
    	overall.getChildren().add(recentOrders);
    	
    	VBox yourListings = createYourListingsView();
    	yourListings.setLayoutX(415);
    	yourListings.setLayoutY(10);
    	overall.getChildren().add(yourListings);
    	
    	return overall;
		
    }
    
    private VBox cartListView(Book book) {
    	VBox result = new VBox(25);
    	result.setAlignment(Pos.CENTER);
    	
    	Label cart_list = new Label("Recent Orders");
        cart_list.setFont(new Font("Arial", 36)); 
        cart_list.setPadding(new Insets(0, 0, 30, 0));
        result.getChildren().add(cart_list);
    	
    	// VBox order = new VBox();
    	// order.setHgap(25);
    	// order.setVgap(25);
    	// order.setPrefWidth(400);
    	// order.setPadding(new Insets(0, 0, 34, 0));
    	
        	//Booking Listing part

            result.getChildren().add(new Text("Order #" + book.));
            result.getChildren().add(new Text("Date Ordered: " + ));
            result.getChildren().add(new Text(book.getTitle() + book.getAuthor()));
            result.getChildren().add(new Text("Tax: " + ));
            result.getChildren().add(new Text("Total: " + book.getPrice()));
    	
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
		delete_Btn.setPrefWidth(200);
		delete_Btn.setPrefHeight(50);
		result.getChildren().add(delete_Btn);
		delete_Btn.setOnAction(event -> {
			Book b = (Book) g.getSelectedToggle().getUserData();
			
			deleted_window(b);
		});
		
		return result;
    }

    private void changingPassword(){
        Label changeLabel = new Label("Change Label");
        changeLabel.setFont(new Font("Arial", 36)); 
        
        ButtonType submitButton = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField oldPassword = new PasswordField();
        PasswordField newPassword = new PasswordField();
        PasswordField retypePassword = new PasswordField();
        
        grid.add(new Label("Old Password:"), 0, 0);
        grid.add(oldPassword, 1, 0);
        grid.add(new Label("New Password:"), 0, 1);
        grid.add(newPassword, 1, 1);
        grid.add(new Label("Retype Password:"), 0, 2);
        grid.add(retypePassword, 1, 2);

        dialog.getDialogPane().setContent(grid);
    }
    
    private Scene setupScene() {
    	Pane outer = createOuter();
        
    	return (new Scene(outer, 800, 700));
    }
            
    // Method to return the Scene
    public Scene getScene() {
    	return setupScene();
    }
}
