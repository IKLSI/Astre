package ihm;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.layout.AnchorPane;

public class Etats implements Initializable
{
	@FXML private AnchorPane panelEtats;

	// Changement d'onglet

	@FXML private void afficheExportation(ActionEvent event){ this.changerOnglet("Exportation"); }
	@FXML private void afficheAnnees(ActionEvent event){ this.changerOnglet("Annees"); }

	// Onglet par défaut
	@Override public void initialize(URL location, ResourceBundle resources) { this.changerOnglet("Exportation"); }

	// Méthode pour changer les onglets
	private void changerOnglet(String nomOnglet)
	{
		try
		{
			panelEtats.getChildren().clear();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("../interface/" + nomOnglet +".fxml"));
			AnchorPane loadedPane = loader.load();

			panelEtats.getChildren().setAll(loadedPane.getChildren());
		}
		catch (Exception e) { e.printStackTrace(); }
	}
}