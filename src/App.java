import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

import controleur.Controleur;

public class App extends Application
{
	// Attributs d'instances
	private Stage primaryStage;

	// Méthode permettant de lancer l'application
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			// Frame Accueil
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Accueil.fxml"));
			AnchorPane root = loader.load();

			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("ASTRE");
			Scene scene = new Scene(root, 800, 500);

			this.primaryStage.setResizable(false);

			/*--Positionnement--*/
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	@FXML
	private void chargement(ActionEvent event)
	{
		Controleur.ouvrirConnection();
		try
		{
			this.primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Affichage.fxml"));
			AnchorPane root = loader.load();

			Scene scene = new Scene(root, 800, 500);
			this.primaryStage.setScene(scene);
			this.primaryStage.setTitle("ASTRE");
			this.primaryStage.show();
			
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.close();
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	/*--------*/
	/*--MAIN--*/
	/*--------*/
	public static void main(String[] args) { launch(args); }
}