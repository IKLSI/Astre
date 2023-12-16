import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.Action;

import javafx.beans.property.SimpleStringProperty;
import java.sql.SQLException;

public class AppControleur implements Initializable
{

	@FXML
	private AnchorPane panelCentre;

	@Override
	public void initialize(URL location, ResourceBundle resources) {affichageDefaut();}

	// Test exemple

	@FXML
	private void majCentre(ActionEvent event)
	{
		try { panelCentre.getChildren().clear(); }
		catch (Exception e) { e.printStackTrace(); }
	}

	/* Méthodes changement du Panel centre*/

	private void affichageDefaut(){ new Intervenants(this.panelCentre); }

	@FXML
	private void afficheParametres(ActionEvent event) { new Parametrage(this.panelCentre); }

	@FXML
	private void affichePrevisionnel(ActionEvent event){ new Previsionnel(this.panelCentre); }
	
	@FXML
	private void afficheIntervenants(ActionEvent event){ new Intervenants(this.panelCentre); }

	@FXML
	private void afficheEtats(ActionEvent event)
	{
		try { panelCentre.getChildren().clear(); } 
		catch (Exception e) { e.printStackTrace(); }
	}
}