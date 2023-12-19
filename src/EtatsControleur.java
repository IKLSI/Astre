import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class EtatsControleur implements Initializable{

	@FXML
	private AnchorPane panelEtats;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.afficheExport();
	}

	@FXML
	private void afficheExportation(ActionEvent event){
		this.afficheExport();
	}
	@FXML
	private void afficheAnnees(ActionEvent event){
		try {
			panelEtats.getChildren().clear();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("interface/Annees.fxml"));
			AnchorPane loadedPane = loader.load();

			panelEtats.getChildren().setAll(loadedPane.getChildren());
		} catch (Exception e) {
		}
	}

	private void afficheExport(){
		try
		{
			panelEtats.getChildren().clear();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("interface/Exportation.fxml"));
			AnchorPane loadedPane = loader.load();

			panelEtats.getChildren().setAll(loadedPane.getChildren());
		}
		catch (Exception e) { e.printStackTrace(); }
	}

}