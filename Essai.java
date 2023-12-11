import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Essai extends Application
{
	public static void main(String[] args) { launch(args); }

	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Affichage.fxml"));
			AnchorPane root = loader.load();
			EssaiController controller = loader.getController();

			Scene scene = new Scene(root, 600, 400);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Gestion de stock");
			primaryStage.show();
		} catch (Exception e) { e.printStackTrace(); }
	}
}