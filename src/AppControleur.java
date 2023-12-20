import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AppControleur implements Initializable
{

	@FXML
	private AnchorPane panelCentre;

	@Override
	public void initialize(URL location, ResourceBundle resources) {affichageDefaut();}


	/* Méthodes changement du Panel centre*/
	private void affichageDefaut(){ new Intervenants(this.panelCentre); }

	@FXML
	private void afficheParametres(ActionEvent event) { this.chargerOnglet("interface/Parametres"); }
	@FXML
	public void affichePrevisionnel(ActionEvent event){ new Previsionnel(this.panelCentre); }
	@FXML
	private void afficheIntervenants(ActionEvent event){ new Intervenants(this.panelCentre); }
	@FXML
	private void afficheEtats(ActionEvent event){ this.chargerOnglet("interface/Etats"); }

	private void chargerOnglet(String nomOnglet)
	{
		try
		{
			panelCentre.getChildren().clear();

			FXMLLoader loader = new FXMLLoader(getClass().getResource( nomOnglet + ".fxml"));
			AnchorPane loadedPane = loader.load();

			panelCentre.getChildren().setAll(loadedPane.getChildren());
		}
		catch (Exception e) { e.printStackTrace(); }
	}
}