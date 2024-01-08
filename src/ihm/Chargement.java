package ihm;

import javafx.fxml.*;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class Chargement implements Initializable
{
	@FXML private ProgressBar bar;
	public static double valBar = 0.7;

	@Override
	public void initialize(URL location, ResourceBundle resources) { this.bar.setProgress(Chargement.valBar); }

	public static void charger(double val) { valBar = val; }
}
