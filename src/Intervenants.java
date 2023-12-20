import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.DefaultStringConverter;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;

import metier.Intervenant;

public class Intervenants
{
	@FXML
	private ObservableList<ObservableList<String>> data;
	@FXML
	private TableView<ObservableList<String>> tableView;
	@FXML
	private ScrollPane scrollPane;

	private ArrayList<Integer>     idIntervenant            = new ArrayList<Integer>();
	private ArrayList<Intervenant> intervenants             = new ArrayList<Intervenant>();
	private ArrayList<Intervenant> intervenantsModification = new ArrayList<Intervenant>();
	
	public Intervenants(AnchorPane panelCentre)
	{
		try	{ panelCentre.getChildren().clear(); }
		catch (Exception e)	{ e.printStackTrace(); }
		
		try
		{
			ResultSet resultSet = Controleur.getIntervenant_final();

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
						row.add(resultSet.getObject(i).toString());
					else
						row.add("null");
				}

				data.add(row);
			}

			Label lbl = new Label("Liste des intervenants :");
			lbl.setStyle("-fx-font-weight: bold");

			Button bouton = new Button("Ajouter");
			bouton.setStyle("-fx-background-color: #7F23A7; -fx-text-fill: white;");
			bouton.setOnMouseEntered(e -> bouton.setStyle("-fx-background-color: #D09AE8; -fx-text-fill: white;"));
			bouton.setOnMouseExited(e -> bouton.setStyle("-fx-background-color: #7F23A7; -fx-text-fill: white;"));
			bouton.setPrefSize(75, 20);
			bouton.setOnAction((ActionEvent event) -> {
				try
				{
					ajouter(event, panelCentre);
				}
				catch (Exception e) { e.printStackTrace(); }
			});

			Button bouton2 = new Button("Supprimer");
			bouton2.setStyle("-fx-background-color: #7F23A7; -fx-text-fill: white;");
			bouton2.setOnMouseEntered(e -> bouton2.setStyle("-fx-background-color: #D09AE8; -fx-text-fill: white;"));
			bouton2.setOnMouseExited(e -> bouton2.setStyle("-fx-background-color: #7F23A7; -fx-text-fill: white;"));
			bouton2.setPrefSize(75, 20);
			bouton2.setOnAction((ActionEvent event) -> {
				try
				{
					supprimer(event);
				}
				catch (Exception e)	{ e.printStackTrace(); }
			});

			Button boutonE = new Button("Enregistrer");
			boutonE.setStyle("-fx-background-color: #7F23A7; -fx-text-fill: white;");
			boutonE.setOnMouseEntered(e -> boutonE.setStyle("-fx-background-color: #D09AE8; -fx-text-fill: white;"));
			boutonE.setOnMouseExited(e -> boutonE.setStyle("-fx-background-color: #7F23A7; -fx-text-fill: white;"));
			boutonE.setPrefSize(75, 20);
			boutonE.setOnAction((ActionEvent event) -> {
				try
				{
					enregistrer(event);
				}
				catch (Exception e)	{ e.printStackTrace(); }
			});

			Button buttonA = new Button("Annuler");
			buttonA.setStyle("-fx-background-color: #7F23A7; -fx-text-fill: white;");
			buttonA.setOnMouseEntered(e -> buttonA.setStyle("-fx-background-color: #D09AE8; -fx-text-fill: white;"));
			buttonA.setOnMouseExited(e -> buttonA.setStyle("-fx-background-color: #7F23A7; -fx-text-fill: white;"));
			buttonA.setPrefSize(75, 20);
			buttonA.setOnAction((ActionEvent event) -> {
				try
				{
					new Intervenants(panelCentre);
				}
				catch (Exception e)	{ e.printStackTrace(); }
			});

			AnchorPane.setTopAnchor(bouton, 390.0);
			AnchorPane.setLeftAnchor(bouton, 20.0);

			AnchorPane.setTopAnchor(bouton2, 390.0);
			AnchorPane.setLeftAnchor(bouton2, 110.0);

			AnchorPane.setTopAnchor(boutonE, 440.0);
			AnchorPane.setLeftAnchor(boutonE, 20.0);

			AnchorPane.setTopAnchor(buttonA, 440.0);
			AnchorPane.setLeftAnchor(buttonA, 110.0);

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

				if (i < 5)
				{
					column.setEditable(true);
					column.setCellFactory(TextFieldTableCell.forTableColumn());
					column.setOnEditCommit((CellEditEvent<ObservableList<String>, String> event) -> {
						try
						{
							modifier(event);
						}
						catch (Exception e)	{ e.printStackTrace(); }
					});
				}

				tableView.getColumns().add(column);
			}

			tableView.setEditable(true);
			tableView.getSelectionModel().setCellSelectionEnabled(true);

			tableView.setItems(data);
			panelCentre.getChildren().add(tableView);
			panelCentre.getChildren().add(lbl);
			panelCentre.getChildren().add(bouton);
			panelCentre.getChildren().add(bouton2);
			panelCentre.getChildren().add(boutonE);
			panelCentre.getChildren().add(buttonA);

			resultSet.close();
		}
		catch (Exception e)	{ e.printStackTrace(); }
	}
	
	@FXML
	public void ajouter(ActionEvent event, AnchorPane panelCentre) throws Exception
	{
		TextField nom = new TextField();
		TextField prenom = new TextField();
		TextField nomCat = new TextField();
		TextField hserv = new TextField();
		TextField maxheure = new TextField();

		Label lbl = new Label("Ajouter un intervenant :");
		lbl.setStyle("-fx-font-weight: bold");

		Label lblNom = new Label("Nom :");
		Label lblPrenom = new Label("Prénom :");
		Label lblNomCat = new Label("Nom catégorie :");
		Label lblHserv = new Label("Heures de service :");
		Label lblMaxheure = new Label("Max heures :");

		Button bouton = new Button("Ajouter");
		bouton.setStyle("-fx-background-color: #7F23A7; -fx-text-fill: white;");
		bouton.setOnMouseEntered(e -> bouton.setStyle("-fx-background-color: #D09AE8; -fx-text-fill: white;"));
		bouton.setOnMouseExited(e -> bouton.setStyle("-fx-background-color: #7F23A7; -fx-text-fill: white;"));
		bouton.setOnAction((ActionEvent event2) -> {
			Intervenant intervenant = new Intervenant(nom.getText(), prenom.getText(), Integer.parseInt(nomCat.getText()), Integer.parseInt(hserv.getText()), Integer.parseInt(maxheure.getText()));
			this.intervenants.add(intervenant);
			panelCentre.getChildren().clear();
			Controleur.insertIntervenant(intervenant);
			new Intervenants(panelCentre);
		});

		Button buttonA = new Button("Annuler");
		buttonA.setStyle("-fx-background-color: #7F23A7; -fx-text-fill: white;");
		buttonA.setOnMouseEntered(e -> buttonA.setStyle("-fx-background-color: #D09AE8; -fx-text-fill: white;"));
		buttonA.setOnMouseExited(e -> buttonA.setStyle("-fx-background-color: #7F23A7; -fx-text-fill: white;"));	
		buttonA.setOnAction((ActionEvent event2) -> {
			try
			{
				new Intervenants(panelCentre);
			}
			catch (Exception e)	{ e.printStackTrace(); }
		});

		AnchorPane.setTopAnchor(lbl, 20.0);
		AnchorPane.setLeftAnchor(lbl, 20.0);

		AnchorPane.setTopAnchor(lblNom, 80.0);
		AnchorPane.setLeftAnchor(lblNom, 20.0);

		AnchorPane.setTopAnchor(lblPrenom, 120.0);
		AnchorPane.setLeftAnchor(lblPrenom, 20.0);

		AnchorPane.setTopAnchor(lblNomCat, 160.0);
		AnchorPane.setLeftAnchor(lblNomCat, 20.0);

		AnchorPane.setTopAnchor(lblHserv, 200.0);
		AnchorPane.setLeftAnchor(lblHserv, 20.0);

		AnchorPane.setTopAnchor(lblMaxheure, 240.0);
		AnchorPane.setLeftAnchor(lblMaxheure, 20.0);

		AnchorPane.setTopAnchor(nom, 80.0);
		AnchorPane.setLeftAnchor(nom, 140.0);

		AnchorPane.setTopAnchor(prenom, 120.0);
		AnchorPane.setLeftAnchor(prenom, 140.0);

		AnchorPane.setTopAnchor(nomCat, 160.0);
		AnchorPane.setLeftAnchor(nomCat, 140.0);

		AnchorPane.setTopAnchor(hserv, 200.0);
		AnchorPane.setLeftAnchor(hserv, 140.0);

		AnchorPane.setTopAnchor(maxheure, 240.0);
		AnchorPane.setLeftAnchor(maxheure, 140.0);

		AnchorPane.setTopAnchor(bouton, 300.0);
		AnchorPane.setLeftAnchor(bouton, 20.0);

		AnchorPane.setTopAnchor(buttonA, 300.0);
		AnchorPane.setLeftAnchor(buttonA, 80.0);

		panelCentre.getChildren().clear();
		panelCentre.getChildren().add(lbl);
		panelCentre.getChildren().add(lblNom);
		panelCentre.getChildren().add(lblPrenom);
		panelCentre.getChildren().add(lblNomCat);
		panelCentre.getChildren().add(lblHserv);
		panelCentre.getChildren().add(lblMaxheure);
		panelCentre.getChildren().add(nom);
		panelCentre.getChildren().add(prenom);
		panelCentre.getChildren().add(nomCat);
		panelCentre.getChildren().add(hserv);
		panelCentre.getChildren().add(maxheure);
		panelCentre.getChildren().add(bouton);
		panelCentre.getChildren().add(buttonA);
	}
	
	@FXML
	private void supprimer(ActionEvent event)
	{
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
		this.idIntervenant = Controleur.getCodInter(tableView.getItems().get(selectedIndex).get(1));
		this.idIntervenant.add(Integer.parseInt(tableView.getItems().get(selectedIndex).get(0)));

		if (selectedIndex >= 0)
			tableView.getItems().remove(selectedIndex);
	}

	@FXML
	private void enregistrer(ActionEvent event)
	{
		for (int i = 0; i < this.idIntervenant.size(); i++)
		{
			try
			{
				for (int j = 0; j < this.idIntervenant.size(); j++)
					Controleur.supprInter(this.idIntervenant.get(j));
				
				for (Intervenant intervenant : this.intervenants)
					Controleur.insertIntervenant(intervenant);
			}
			catch (Exception e)	{ e.printStackTrace(); }
		}
	}

	@FXML
	private void modifier(CellEditEvent<ObservableList<String>, String> event) throws SQLException
	{
		ObservableList<String> row = event.getRowValue();
		String newValue = event.getNewValue();
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
		int index = event.getTablePosition().getColumn();
		System.out.println(tableView.getItems().get(selectedIndex));
		int code = Integer.parseInt(row.get(0));

		row.set(index, newValue);

		Intervenant intervenant = new Intervenant(code, row.get(2), row.get(3), Integer.parseInt(row.get(4)), Integer.parseInt(row.get(5)));
		Controleur.updateInter(intervenant);
	}
}