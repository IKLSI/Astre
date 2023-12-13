

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Parametrage{

	public Parametrage(AnchorPane panelCentre){
		try{
			panelCentre.getChildren().clear();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("Parametres.fxml"));
			AnchorPane loadedPane = loader.load();

			panelCentre.getChildren().setAll(loadedPane.getChildren());
		}
		catch (Exception e){e.printStackTrace();}
	}
}