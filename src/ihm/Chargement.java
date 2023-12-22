package ihm;

import javafx.fxml.*;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class Chargement implements Initializable {

	@FXML
	private ProgressBar bar;
	private static double valBar = 0.5;

	@Override
	public void initialize(URL location, ResourceBundle resources) { this.bar.setProgress(Chargement.valBar); }

	public static void charger(double val) { valBar = val; }
}
