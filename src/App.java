import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;

public class App extends Application
{
	/*Attributs d'instances*/
	private Stage primaryStage;	//Frame

	/*Méthode de lancement de la Frame*/
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			/*--Création--*/

			// Frame Accueil
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Accueil.fxml"));
			AnchorPane root = loader.load();

			//frame
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("ASTRE");
			Scene scene = new Scene(root, 800, 500);

			/*--Positionnement--*/

			this.primaryStage.setScene(scene);
			this.primaryStage.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@FXML
	private void chargement(ActionEvent event)
	{
		try
		{
			this.primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Affichage.fxml"));
			AnchorPane root = loader.load();
			AppControleur controller = loader.getController();

			Scene scene = new Scene(root, 800, 500);
			this.primaryStage.setScene(scene);
			this.primaryStage.setTitle("ASTRE");
			this.primaryStage.show();
			
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/*--------*/
	/*--MAIN--*/
	/*--------*/
	
	public static void main(String[] args) { launch(args); }
}