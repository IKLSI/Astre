import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class ParametrageControleur implements Initializable{

	@FXML
	private ObservableList<ObservableList<String>> data;
	@FXML
	private TableView<ObservableList<String>> tableView;

	@Override
	public void initialize(URL location, ResourceBundle resources){ categorieIntervenants();}


	public void categorieIntervenants(){
		tableView.getColumns().clear();
		tableView.getItems().clear();
		try{
			ResultSet rs = Controleur.getCategorieInter();
			data = FXCollections.observableArrayList();

			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			ObservableList<String> columns = FXCollections.observableArrayList();
			for (int i = 1; i <= columnCount; i++)
			{
				columns.add(metaData.getColumnName(i));
			}
			data.add(columns);
			while (rs.next())
			{
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= columnCount; i++)
				{
					if (rs.getObject(i) != null)
					{
						row.add(rs.getObject(i).toString());
					}
					else
						row.add("null");
				}
				data.add(row);
			}

			for (int i = 0; i < columnCount; i++)
			{
				TableColumn<ObservableList<String>, String> column = new TableColumn<>(columns.get(i));
				final int colIndex = i;
				column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(colIndex)));
				column.setGraphic(null);
				tableView.getColumns().add(column);
			}

			tableView.setItems(data);
		}catch (Exception e) {e.printStackTrace();}
	}

	public void categorieHeure(){

	}

	@FXML
	private void btnIntervenant(ActionEvent e){
		categorieIntervenants();
	}

	@FXML
	private void btnHeure(ActionEvent e){
		categorieHeure();
	}
}
