import javafx.fxml.*;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;

public class Ppp
{
	@FXML private MenuButton menuButton;

	@FXML private AnchorPane panelCentre;

	public Ppp(AnchorPane panelCentre)
	{
		this.panelCentre = panelCentre;
		try
		{
			panelCentre.getChildren().clear();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("PPP.fxml"));
			AnchorPane loadedPane = loader.load();

			panelCentre.getChildren().setAll(loadedPane.getChildren());
		}
		catch (Exception e) { e.printStackTrace(); }
	}
}