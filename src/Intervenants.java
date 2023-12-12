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
import javafx.scene.control.Label;
import javafx.scene.control.Button;

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
			ResultSet resultSet = statement.executeQuery("SELECT * FROM intervenant_final;");

			data = FXCollections.observableArrayList();

			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			ObservableList<String> columns = FXCollections.observableArrayList();

			for (int i = 1; i <= columnCount; i++)
			{
				columns.add(metaData.getColumnName(i));
			}

			data.add(columns);

			while (resultSet.next())
			{
				ObservableList<String> row = FXCollections.observableArrayList();

				for (int i = 1; i <= columnCount; i++)
				{
					if (resultSet.getObject(i) != null)
					{
						row.add(resultSet.getObject(i).toString());
					}
					else
						row.add("null");
				}

				data.add(row);
			}

			Label lbl = new Label("Liste des intervenants :");
			lbl.setStyle("-fx-font-weight: bold");

			Button bouton = new Button("Ajouter");
			bouton.setOnAction((ActionEvent event) -> {
				try
				{
					ajouter(event, panelCentre);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			});

			Button bouton2 = new Button("Supprimer");
			bouton2.setOnAction((ActionEvent event) -> {
				try
				{
					supprimer(event);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			});

			AnchorPane.setTopAnchor(bouton, 400.0);
			AnchorPane.setLeftAnchor(bouton, 20.0);

			AnchorPane.setTopAnchor(bouton2, 400.0);
			AnchorPane.setLeftAnchor(bouton2, 80.0);

			AnchorPane.setTopAnchor(lbl, 20.0);
			AnchorPane.setLeftAnchor(lbl, 20.0);
			tableView = new TableView<>();
			AnchorPane.setTopAnchor(tableView, 80.0);
			AnchorPane.setLeftAnchor(tableView, 20.0);
			tableView.setPrefSize(630, 300);


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
			panelCentre.getChildren().add(lbl);
			panelCentre.getChildren().add(bouton);
			panelCentre.getChildren().add(bouton2);

			resultSet.close();
			statement.close();
			connection.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@FXML
	private void supprimer(ActionEvent event)
	{
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
	
		if (selectedIndex >= 0)
		{
			try
			{
				Class.forName("org.postgresql.Driver");
				Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/lk210125","lk210125","Kyliann.0Bado");
				Statement statement = connection.createStatement();

				ResultSet resultSet = statement.executeQuery("SELECT codInter FROM Intervenant WHERE nom = '" + tableView.getItems().get(selectedIndex).get(1) + "';");
	
				if (resultSet.next())
				{
					String codInter = resultSet.getString(1);
					statement.executeUpdate("DELETE FROM Intervenant WHERE codInter = " + codInter + ";");
					resultSet.close();
				}
				else
				{
					System.out.println("Erreur lors de la suppression");
				}

				statement.close();
				connection.close();
				tableView.getColumns().clear();
				tableView.getItems().clear();
				new Intervenants((AnchorPane) tableView.getParent());
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void ajouter(ActionEvent event, AnchorPane panelCentre) throws Exception
	{
		try
		{
			ObservableList<String> newRow = FXCollections.observableArrayList();
	
			for (int i = 0; i < 12; i++)
			{
				TextField textField = new TextField();
				newRow.add(textField.getText());
				panelCentre.getChildren().add(textField);
				AnchorPane.setTopAnchor(textField, 80.0 + data.size() * 30.0);
				AnchorPane.setLeftAnchor(textField, 20.0 + i * 65.0);
			}
	
			tableView.setEditable(true);
			data.add(newRow);
			
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/lk210125","lk210125","Kyliann.0Bado");
			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery("SELECT MAX(codInter) FROM Intervenant;");
			resultSet.next();
			int codInter = resultSet.getInt(1) + 1;
			resultSet.close();

			statement.executeUpdate("INSERT INTO Intervenant_final VALUES (" + codInter + ", '" + Integer.parseInt(newRow.get(1)) + "', '" + newRow.get(2) + "', '" + newRow.get(3) + "', '" + newRow.get(4) + "', '" + newRow.get(5) + "', '" + newRow.get(6) + "', '" + newRow.get(7) + "', '" + newRow.get(8) + "', '" + newRow.get(9) + "', '" + newRow.get(10) + "', '" + newRow.get(11) + "');");
			statement.close();
			connection.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}