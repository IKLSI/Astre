import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

public class EssaiController implements Initializable
{
	@FXML
	private TableView<ObservableList<String>> tableView;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private TextField txtFld;

	@FXML
	private ObservableList<ObservableList<String>> data;

	public void recuperationInformations()
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/PAC", "postgres", "Kyliann.0815");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Produit;");

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

			for (int i = 0; i < columnCount; i++)
			{
				TableColumn<ObservableList<String>, String> column = new TableColumn<>(columns.get(i));
				final int colIndex = i;
				column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(colIndex)));
				tableView.getColumns().add(column);
			}

			tableView.setItems(data);

			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) { e.printStackTrace(); }
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) { recuperationInformations(); }

	@FXML
	private void ajouter(ActionEvent event)
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/PAC", "postgres", "Kyliann.0815");
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO Produit VALUES (" + txtFld.getText() + ");");
			statement.close();
			connection.close();
			tableView.getColumns().clear();
			recuperationInformations();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void supprimer(ActionEvent event)
	{
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			try {
				Class.forName("org.postgresql.Driver");
				Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/PAC", "postgres", "Kyliann.0815");
				Statement statement = connection.createStatement();
				statement.executeUpdate("DELETE FROM Produit WHERE np = " + tableView.getItems().get(selectedIndex).get(0) + ";");
				statement.close();
				connection.close();
				tableView.getColumns().clear();
				recuperationInformations();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}