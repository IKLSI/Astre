package ihm;

import java.net.URL;
import java.util.*;
import javafx.fxml.*;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;

import controleur.Controleur;

public class Annees implements Initializable
{
	@FXML private ComboBox<String> lstAnnee;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		ArrayList<String> lst = Controleur.getAnnee();
		this.lstAnnee.getItems().addAll(FXCollections.observableArrayList(lst));
	}

	@FXML
	private void dupliquerAnnee(ActionEvent event)
	{
		try
		{
			this.lstAnnee.getValue().equals(null);

			Controleur.clonage(Integer.parseInt(lstAnnee.getValue()),Controleur.anneeActuelle + 1);
			Intervenants.notifications("Année dupliquée");
		}
		catch (Exception e) { Intervenants.notifications("Aucune année sélectionnée"); }

		ArrayList<String> lst = Controleur.getAnnee();
		this.lstAnnee.getItems().clear();
		this.lstAnnee.getItems().addAll(FXCollections.observableArrayList(lst));
	}

	@FXML
	private void supprimerAnnee(ActionEvent event) {
		try
		{
			this.lstAnnee.getValue().equals(null);

			Controleur.supprimerAnnee(Integer.parseInt(lstAnnee.getValue()));
			Intervenants.notifications("Année supprimé");
		}
		catch (Exception e) { Intervenants.notifications("Aucune année sélectionnée"); }

		ArrayList<String> lst = Controleur.getAnnee();
		this.lstAnnee.getItems().clear();
		this.lstAnnee.getItems().addAll(FXCollections.observableArrayList(lst));
	}

	@FXML
	private void valider(ActionEvent event)
	{
		try
		{
			this.lstAnnee.getValue().equals(null);

			Controleur.anneeActuelle = Integer.parseInt(lstAnnee.getValue());
			Intervenants.notifications("Vous avez choisi l'année " + lstAnnee.getValue());
		}
		catch (Exception e) { Intervenants.notifications("Aucune année sélectionnée"); }
	}
}