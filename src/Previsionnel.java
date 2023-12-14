import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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

	@FXML
	private MenuButton menuButton;

	public Previsionnel(AnchorPane panelCentre){
		try
		{
			panelCentre.getChildren().clear();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Previsionnel.fxml"));
			AnchorPane loadedPanel = loader.load();

			panelCentre.getChildren().setAll(loadedPanel.getChildren());
            System.out.println("previ");

		}
		catch (Exception e){e.printStackTrace();}

		try {
            Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/lk210125","lk210125","Kyliann.0Bado");
			Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nomTypMod FROM TypeModule;");

            while (rs.next()) {
                String nomBouton = rs.getString("nomTypMod");
                MenuItem nouveauItem = new MenuItem(nomBouton);
                nouveauItem.setOnAction(e -> {
                    System.out.println("Bouton " + nomBouton + " cliqué !");
                });
                menuButton.getItems().add(nouveauItem);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
    }
}