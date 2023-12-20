package ihm;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;


public class Annees implements Initializable{

	@FXML
	private ComboBox<String> lstAnnee;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	private void dupliquerAnnee(ActionEvent event){
		System.out.println("dupliquer");
	}
		@FXML
	private void supprimerAnnee(ActionEvent event){
		System.out.println("supprimer");
	}
	@FXML
	private void valider(ActionEvent event){
		System.out.println("valider");
	}
}