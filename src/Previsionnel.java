import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Previsionnel
{
	@FXML
	private MenuButton menuButton;

	public Previsionnel(AnchorPane panelCentre)
	{
		try
		{
			panelCentre.getChildren().clear();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("Previsionnel.fxml"));
			AnchorPane loadedPane = loader.load();

			panelCentre.getChildren().setAll(loadedPane.getChildren());
			PrevisionnelController.panelCentre = panelCentre;
		}
		catch (Exception e) { e.printStackTrace(); }
	}
}