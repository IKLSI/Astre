import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class Previsionnel{

	public Previsionnel(AnchorPane panelCentre){
		try
		{
			panelCentre.getChildren().clear();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Previsionnel.fxml"));
			AnchorPane loadedPanel = loader.load();

			panelCentre.getChildren().setAll(loadedPanel.getChildren());
		}
		catch (Exception e){e.printStackTrace();}

	}
}