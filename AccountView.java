
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
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

public class AccountView{
    private Main app;
	BookSystem sys;
	User usr;
	Stage primaryStage;
	private String from;
	
    private Scene scene;
	public AccountView(BookSystem s, Main a, User u, Stage p, String f) {
    	this.app = a;
    	this.sys = s;
    	this.usr = u;
    	this.from = f;
    	scene = setupScene();
    	primaryStage = p;
    	primaryStage.setScene(scene);
    }
    
    private VBox scrlSetup() {
    	VBox list = new VBox(20);
    	
        for(Book b : usr.getListings()) {
        	Label l = new Label(
        			b.getTitle() + "\n" +
        			"Author: " + b.getAuthor() + "\n" +
        			"Published: " + b.getDate() + "\n" +
        			"Category: " + b.getCategory() + "\n" +
        			"Condition: " + b.getCondition() + "\n" +
        			"Selling: " + b.getQuantity() + "\n"
        	);
        	l.setAlignment(Pos.CENTER);
        	l.setFont(Font.font("Arial", 18));
        	l.setPadding(new Insets(15));
        	list.getChildren().add(l);
        }
        
        return list;
    }
    
    private void goBack() {
    	if(from == "Seller") {
    		new SellerView(sys, app, usr, primaryStage);
    	}else if(from == "Buyer") {
    		new BuyerView(sys, app, usr, primaryStage);
    	}
    }
    
    private void logOut() {
    	primaryStage.setScene(new LoginView(sys, app).layout);
    }
    
    private Pane createOuter() {
    	// First pane
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: darkred;");
        
        // Book Nook label
        Text title = new Text("Book Nook");
		title.setFont(Font.font("Lucida Fax", FontWeight.BOLD, 36));
		title.setFill(Color.web("#FFD700"));
        title.setLayoutX(50); 
        title.setLayoutY(60); 
        pane.getChildren().add(title);
        
        //Your Account Button
        Button back_btn = new Button("Home");
        back_btn.setPrefWidth(150); 
        back_btn.setPrefHeight(40);
        back_btn.setLayoutX(350);
        back_btn.setLayoutY(30);
        back_btn.setStyle("-fx-background-color: #9a9a9a; -fx-text-fill: white;");
        back_btn.setFont(Font.font("arial", FontWeight.BOLD, 16));
        back_btn.setOnAction(event -> goBack());
        pane.getChildren().add(back_btn);
        
        //Logout Button
        Button log_out_Button = new Button("Log Out");
        log_out_Button.setStyle("-fx-background-color: #9a9a9a; -fx-text-fill: white;");
        log_out_Button.setPrefWidth(150); 
        log_out_Button.setPrefHeight(40);
        log_out_Button.setLayoutX(550);
        log_out_Button.setLayoutY(30);
        log_out_Button.setFont(Font.font("arial", FontWeight.BOLD, 16));
        log_out_Button.setOnAction(event -> logOut());
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

        String username = usr.getEmail();
        Label greeting = new Label("     Welcome " + username);
        greeting.setStyle("-fx-text-fill: #8C1D40;-fx-background-color: #FFD700; -fx-font-size: 30px; -fx-font-weight: bold;");
        //greeting.setLayoutY(20);
        greeting.setMinWidth(700);
        greeting.setMinHeight(50);
        
        overall.getChildren().add(greeting);

        Button change_Pswd_Button = new Button("Change Password");
        change_Pswd_Button.setLayoutX(35);
        change_Pswd_Button.setLayoutY(75);
        change_Pswd_Button.setPrefWidth(200);
        change_Pswd_Button.setPrefHeight(50);
        change_Pswd_Button.setOnAction(e -> changingPassword());
        overall.getChildren().add(change_Pswd_Button);

        Button withdraw_Button = new Button("Withdraw Funds");
        withdraw_Button.setLayoutX(415);
        withdraw_Button.setLayoutY(75);
        withdraw_Button.setPrefWidth(200);
		withdraw_Button.setPrefHeight(50);
		overall.getChildren().add(withdraw_Button);
		
        //withdraw_Button.setOnAction(e -> withdrawFunds());
		
		VBox yourOrders = createRecentOrders();
		yourOrders.setLayoutX(35);
    	yourOrders.setLayoutY(150);
    	overall.getChildren().add(yourOrders);
    	
    	VBox yourListings = createYourListingsView();
    	yourListings.setLayoutX(415);
    	yourListings.setLayoutY(150);
    	overall.getChildren().add(yourListings);
    	
    	return overall;
		
    }
    
    private VBox orderSetup() {
    	VBox list = new VBox(20);
        
        for(Order o : usr.getOrders()) {
        	Label l = new Label(
        			o.getNumber() + "\n" +
        			"Date: " + o.getOrderDate() + "\n" +
        			"Cost: " + o.getTotalCost() + "\n" +
        			"Tax: " + o.getTax() + "\n" +
        			"Titles: " + o.getTitles()
        	);
        	l.setAlignment(Pos.CENTER);
        	l.setFont(Font.font("Arial", 18));
        	l.setPadding(new Insets(15));
        	list.getChildren().add(l);
        }
        
        return list;
    }
    
    private VBox createRecentOrders() {
    	VBox result = new VBox(25);
    	result.setAlignment(Pos.CENTER);
    	
    	Label orderList = new Label("Recent Orders");
    	orderList.setFont(new Font("Arial", 36));
    	result.getChildren().add(orderList);
    	
    	ScrollPane scrl = new ScrollPane();
    	VBox.setVgrow(scrl, Priority.ALWAYS);
    	//scrl.setVmax(400);
        scrl.setPrefSize(250, 300);;
    	scrl.setContent(orderSetup());
    	result.getChildren().add(scrl);
    	
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
        
        //scrl.setVmax(400);
        scrl.setPrefSize(250, 300);;
        scrl.setContent(scrlSetup());
        scrl.setId("scroll");

        result.getChildren().add(scrl);
		
		return result;
    }

    private void changingPassword(){
    	Stage changePassword = new Stage();
    	
    	VBox v = new VBox(10);
    	v.setAlignment(Pos.CENTER);
    	
        Label changeLabel = new Label("Change Password");
        changeLabel.setFont(new Font("Arial", 36)); 
        v.getChildren().add(changeLabel);
        
        HBox btn = new HBox(5);
        btn.setPadding(new Insets(0, 0, 0, 100));
        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> changePassword.close());
        //dialog.getDialogPane().getButtonTypes().addAll(submitButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(0, 0, 0, 60));
        grid.setHgap(10);
        grid.setVgap(10);

        PasswordField oldPassword = new PasswordField();
        PasswordField newPassword = new PasswordField();
        PasswordField retypePassword = new PasswordField();
        
        grid.add(new Label("Old Password:"), 0, 0);
        grid.add(oldPassword, 1, 0);
        grid.add(new Label("New Password:"), 0, 1);
        grid.add(newPassword, 1, 1);
        grid.add(new Label("Retype Password:"), 0, 2);
        grid.add(retypePassword, 1, 2);
        
        EventHandler<ActionEvent> submit = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR!");
				if(usr.getPassword() == oldPassword.getText()) {
					if(newPassword.getText() == retypePassword.getText()) {
						usr.changePassword(retypePassword.getText());
						changePassword.close();
					}else {
					    alert.setContentText("New password and retype don't match!");
					    alert.showAndWait();
					}
				}else {
					alert.setContentText("Old password incorrect");
					alert.showAndWait();
				}
			}
        };
        
        submitButton.setOnAction(submit);
        btn.getChildren().add(submitButton);
        btn.getChildren().add(cancelButton);
        v.getChildren().add(grid);
        v.getChildren().add(btn);
        Scene passwordScene = new Scene(v, 400, 400);
        changePassword.setScene(passwordScene);
        changePassword.show();

        //dialog.getDialogPane().setContent(grid);
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
