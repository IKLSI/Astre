package ihm;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;

public class Chargement implements Initializable
{
	@FXML private ProgressBar bar;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{

	}

	public void charger()
	{
		this.bar.setProgress(100);
	}
}