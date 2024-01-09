package ihm;
import javafx.fxml.*;
import javafx.scene.layout.AnchorPane;

public class Ressource
{
	@FXML private AnchorPane panelCentre;

	public Ressource(AnchorPane panelCentre)
	{
		this.panelCentre = panelCentre;

		try
		{
			panelCentre.getChildren().clear();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("../interface/Ressource.fxml"));
			AnchorPane loadedPane = loader.load();

			panelCentre.getChildren().setAll(loadedPane.getChildren());
		}
		catch (Exception e) { e.printStackTrace(); }
	}
}