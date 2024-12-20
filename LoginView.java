
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.ButtonType;

public class LoginView {
	private Main app;
	BookSystem sys;
	Scene layout;
	
	
	public LoginView(BookSystem s, Main a) {
		this.sys = s;
		this.app = a;
		
		layout = setupScene();
		
	}
	
	private Scene setupScene() {
		//Text title = new Text("Book Nook");
		Text title = new Text("Book Nook");
		title.setFont(Font.font("Lucida Fax", FontWeight.BOLD, 42));
		title.setFill(Color.web("#FFD700"));
		// Drop-down menu for User-Type/mode Selection
		ComboBox<String> modeCombo = new ComboBox<>();
		modeCombo.getItems().addAll("Buying", "Selling");
		modeCombo.setValue("Buying");
		modeCombo.setStyle(
				"-fx-background-color: white;" +
				"-fx-border-color: #F9C43A;" +
				"-fx-border-radius: 20;" +
				"-fx-background-radius: 20;" +
				"-fx-padding: 5 10;" +
				"-fx-font-size: 14px;"
		);
		
		// User Login Form (ULF) - Sign In label
		Label signInLabel = new Label("Sign in");
		signInLabel.setFont(Font.font("arial", FontWeight.BOLD, 30));
		signInLabel.setTextFill(Color.DARKSLATEGRAY);
		
		// ULF - directions label
		Label guideLabel = new Label("Log in by entering your ASU ID");
		guideLabel.setFont(Font.font("arial", 14));
		
		// ULF - text field for user ID
		TextField idField = new TextField();
		idField.setPromptText("ASU ID");
		idField.setFont(Font.font("arial", 15));
		idField.setStyle(
			"-fx-border-color: #822433;" +
			"-fx-border-radius: 3;" +
			"-fx-background-radius: 3;"
		);
		
		// ULF - password field for user password (like text field, but masks input)
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Password");
		passwordField.setFont(Font.font("arial", 15));
		passwordField.setStyle(
				"-fx-border-color: #822433;" +
				"-fx-border-radius: 3;" +
				"-fx-background-radius: 3;"
		);
		
		// ULF - button to login
		Button loginButton = new Button("Log in");
		loginButton.setStyle("-fx-background-color: #8C1D40; -fx-text-fill: white;");
		loginButton.setFont(Font.font("arial", FontWeight.BOLD, 16));
		loginButton.setPrefWidth(150);
		loginButton.setDisable(true);		// disables ability to click button
		
		// Layout for title and mode selection drop-down
		VBox titleLayout = new VBox(60, title, modeCombo);
				titleLayout.setAlignment(Pos.TOP_CENTER);
				
				// Layout for "Sign in" label (ULF)
				HBox formSignInLayout = new HBox(40, signInLabel);
				formSignInLayout.setAlignment(Pos.TOP_LEFT);
				formSignInLayout.setPadding(new Insets(20));
				
				// Layout for user input guide (ULF)
				HBox formGuideLayout = new HBox(0, guideLabel);
				formGuideLayout.setAlignment(Pos.CENTER_LEFT);
				formGuideLayout.setPadding(new Insets(20));
				
				// Layout for ID and password fields (ULF)
				VBox formFieldLayout = new VBox(20, idField, passwordField);
				formFieldLayout.setAlignment(Pos.CENTER);
				formFieldLayout.setPadding(new Insets(-10, 20, 40, 20));
				
				// Layout for Login button (ULF)
				HBox formLoginLayout = new HBox(40, loginButton);
				formLoginLayout.setAlignment(Pos.CENTER);
				formLoginLayout.setPadding(new Insets(20));
				
				// Layout ULF components
				VBox formLayout = new VBox(10, formSignInLayout, formGuideLayout, formFieldLayout, formLoginLayout);
				formLayout.setPadding(new Insets(0, 20, 0, 20));
				
				// pane for ULF components; creates the rounded-corner, white backdrop
				StackPane formPane = new StackPane(formLayout);
				formPane.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
				formPane.setPadding(new Insets(20));
				formPane.setPrefHeight(375);
				formPane.setMaxSize(350, 375);
				
				// Main layout
				VBox mainLayout = new VBox(20, titleLayout, formPane);
				mainLayout.setAlignment(Pos.CENTER);
				mainLayout.setStyle("-fx-background-color: #8C1D40;");
				mainLayout.setPadding(new Insets(50));
				
				loginButton.disableProperty().bind(
						Bindings.createBooleanBinding(
								() -> idField.getText().isEmpty() || passwordField.getText().isEmpty(),
								idField.textProperty(),
								passwordField.textProperty()
						)
				);
				
				EventHandler<ActionEvent> login = new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						String selectedMode = modeCombo.getValue();  // Get the selected mode
						String inputEmail = idField.getText();
						String pW = passwordField.getText();
						User selected = sys.getUser(inputEmail);
						
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("ERROR!");
						
						alert.setHeaderText(null);		// clear default header text
						alert.setGraphic(null);			// clear default icon
						
						DialogPane dialogPane = alert.getDialogPane();
						dialogPane.setStyle(
								"-fx-background-color: white;" +
								"-fx-border-color: #8C1D40;" +
								"-fx-border-radius: 10;" +
								"-fx-background-radius: 10;"
						);
						dialogPane.lookup(".content.label").setStyle(
								"-fx-font-size: 16px;" +
								"-fx-text-fill: #822433;" +
								"-fx-alignment: center;"
						);
						dialogPane.lookupButton(ButtonType.OK).setStyle(
								"-fx-background-color: #8C1D40;" +
								"-fx-text-fill: white;" +
								"-fx-font-weight: bold;"
						);
										
						
						if(selected == null) {
							alert.setContentText("USER NOT FOUND");
							alert.showAndWait();
						}else if(!selected.checkPassword(pW)) {
							alert.setContentText("INCORRECT PASSWORD");
							alert.showAndWait();
						}else {
							if(selectedMode == "Buying") {
								redirectScreen("Buying", selected);
							}else if(selectedMode == "Selling") {
								redirectScreen("Selling", selected);
							}else {
								System.out.println("Error: Invalid Mode Selection.");
							}
						}
					}
				};
				
				loginButton.setOnAction(login);
			return new Scene(mainLayout, 800, 700);
				
	}
	
	private Scene redirectScreen(String screen, User u) {
		if(screen == "Selling") {
			app.showSellerView(u);
		}else if(screen == "Buying") {
			app.showBuyerView(u);
		}
		return null;
	}
	
	public Scene getScene() {
		return this.layout;
	}

}