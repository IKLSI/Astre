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
		Controleur.clonage(2023,2022); // A changé
	}

	@FXML
	private void supprimerAnnee(ActionEvent event)
	{
		System.out.println("supprimer");
	}

	@FXML
	private void valider(ActionEvent event)
	{
		System.out.println("valider");
	}
}