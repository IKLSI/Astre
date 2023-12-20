package ihm;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

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
		System.out.println("dupliquer");
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