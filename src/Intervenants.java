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

public class Intervenants
{
	@FXML
	private ObservableList<ObservableList<String>> data;
	@FXML
	private TableView<ObservableList<String>> tableView;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private TextField txtFld;
	
	public Intervenants(AnchorPane panelCentre)
	{
		try
		{
			panelCentre.getChildren().clear();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/lk210125","lk210125","Kyliann.0Bado");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Intervenant;");

			data = FXCollections.observableArrayList();

			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			ObservableList<String> columns = FXCollections.observableArrayList();

			for (int i = 1; i <= columnCount; i++)
				columns.add(metaData.getColumnName(i));

			data.add(columns);

			while (resultSet.next())
			{
				ObservableList<String> row = FXCollections.observableArrayList();

				for (int i = 1; i <= columnCount; i++)
					row.add(resultSet.getString(i));

				data.add(row);
			}

			tableView = new TableView<>();

			for (int i = 0; i < columnCount; i++)
			{
				TableColumn<ObservableList<String>, String> column = new TableColumn<>(columns.get(i));
				final int colIndex = i;
				column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(colIndex)));
				column.setGraphic(null);
				tableView.getColumns().add(column);
			}

			tableView.setItems(data);
			panelCentre.getChildren().add(tableView);

			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}