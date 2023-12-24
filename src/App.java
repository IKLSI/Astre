import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import controleur.Controleur;


public class App extends Application
{
	// Attributs d'instances

	private Stage frameAppli;

	// Méthode permettant de lancer l'application

	@Override
	public void start(Stage frameAppli)
	{
		try
		{
			// Frame Accueil
			FXMLLoader loader = new FXMLLoader(getClass().getResource("interface/Accueil.fxml"));
			AnchorPane panelAppli = loader.load();

			this.frameAppli = frameAppli;
			this.frameAppli.setTitle("ASTRE");
			Scene scene = new Scene(panelAppli, 1200, 750);

			this.frameAppli.setResizable(false);

			// Positionnement
			this.frameAppli.setScene(scene);
			this.frameAppli.show();
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	@FXML
	private void chargement(ActionEvent event)
	{
		try
		{
			this.frameAppli = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("interface/Chargement.fxml"));
			AnchorPane panelAppli = loader.load();

			Scene scene = new Scene(panelAppli, 1200, 750);
			this.frameAppli.setScene(scene);
			this.frameAppli.setTitle("ASTRE");
			this.frameAppli.show();

			// Thread qui fait le chargement de la DB
			Platform.runLater(() -> {
				Controleur.ouvrirConnection();
				Controleur.setAnneeActuelle();
				try
				{
					Thread.sleep(1000);
				}
				catch (Exception e) {}

				if(Controleur.connecter)
				{
					//Ouvre le menu
					Platform.runLater(() -> {
						try
						{
							FXMLLoader load = new FXMLLoader(getClass().getResource("interface/Menu.fxml"));
							AnchorPane panelApplis = load.load();
							scene.setRoot(panelApplis);
						}
						catch (Exception e) { e.printStackTrace(); }
					});
				}
				else
					this.afficherErreur("Connexion échouée", "La connexion à la base de données a échoué.");
			});

			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.close();

		}
		catch (Exception e) { e.printStackTrace(); }
	}

	public void afficherErreur(String titre, String message)
	{
		this.frameAppli.close();

 		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titre);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	/*--------*/
	/*--MAIN--*/
	/*--------*/
	public static void main(String[] args) { launch(args); }
}