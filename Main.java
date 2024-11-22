import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	Scene currScene;
	static BookSystem sys = new BookSystem();
	
	public void showSellerView() {
		currScene = (new SellerView(sys, this).getScene());
	}
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Arizona State University BookNook");
		currScene = (new LoginView(sys, this)).getScene();
		primaryStage.setScene(currScene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		boolean[] r = {true, true};
		User DEBUG = new User("beep", "boop", r);
		sys.addUser(DEBUG);
		launch(args);
	}
}
