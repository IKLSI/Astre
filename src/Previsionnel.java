import javafx.fxml.*;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;

public class Previsionnel
{
	//@FXML private MenuButton menuButton;

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