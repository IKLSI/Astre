import javafx.fxml.*;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;

public class Previsionnel
{
	public Previsionnel(AnchorPane panelCentre)
	{
		try
		{
			panelCentre.getChildren().clear();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("interface/Previsionnel.fxml"));
			AnchorPane loadedPane = loader.load();

			panelCentre.getChildren().setAll(loadedPane.getChildren());
			PrevisionnelController.panelCentre = panelCentre;
		}
		catch (Exception e) { e.printStackTrace(); }
	}
}