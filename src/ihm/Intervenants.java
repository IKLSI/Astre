package ihm;

import javafx.collections.*;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.layout.AnchorPane;
import javafx.beans.property.SimpleStringProperty;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;

import controleur.Controleur;
import metier.Intervenant;

public class Intervenants
{
	@FXML private ObservableList<ObservableList<String>> data;
	@FXML private TableView<ObservableList<String>> tableView;
	@FXML private ScrollPane scrollPane;
	@FXML private AnchorPane panelCentre;
	private Label lblErreur;

	private HashMap<Integer, Integer> idIntervenant = new HashMap<Integer, Integer>();

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
				columns.add(metaData.getColumnName(i));

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
			lbl.setStyle("-fx-font-size: 20px;");

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

			AnchorPane.setTopAnchor(bouton, 610.0);
			AnchorPane.setLeftAnchor(bouton, 20.0);

			AnchorPane.setTopAnchor(bouton2, 610.0);
			AnchorPane.setLeftAnchor(bouton2, 110.0);

			AnchorPane.setTopAnchor(boutonE, 660.0);
			AnchorPane.setLeftAnchor(boutonE, 20.0);

			AnchorPane.setTopAnchor(buttonA, 660.0);
			AnchorPane.setLeftAnchor(buttonA, 110.0);

			AnchorPane.setTopAnchor(lbl, 20.0);
			AnchorPane.setLeftAnchor(lbl, 20.0);
			tableView = new TableView<>();
			AnchorPane.setTopAnchor(tableView, 80.0);
			AnchorPane.setLeftAnchor(tableView, 20.0);
			tableView.setPrefSize(970, 500);
			tableView.setStyle("-fx-background-color: rgba(150, 81, 180, 0.0); -fx-text-fill: #333333; -fx-font-weight: bold; -fx-border-color: rgba(150, 81, 180, 0.0);");

			for (int i = 0; i < columnCount; i++)
			{
				TableColumn<ObservableList<String>, String> column = new TableColumn<>(columns.get(i));
				final int colIndex = i;
				column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(colIndex)));

				if (i < 5 && i >= 2)
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

				column.setStyle("-fx-alignment: CENTER;");
				tableView.getColumns().add(column);
			}

			tableView.setRowFactory(tv -> {
				TableRow<ObservableList<String>> row = new TableRow<>();
				row.setStyle("\t-fx-padding: 2px; -fx-background-radius: 7px; -fx-background-color: #dadada; -fx-background-insets: 5px;");

				return row;
			});

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
			this.panelCentre = panelCentre;
		}
		catch (Exception e)	{ e.printStackTrace(); }
	}

	@FXML
	public void ajouter(ActionEvent event, AnchorPane panelCentre) throws Exception
	{
		//Déchargement du panel

		panelCentre.getChildren().clear();

		/*------------*/
		/*--Creation--*/
		/*------------*/

		TextField nom = new TextField();
		nom.setPromptText("Entrez un nom");
		nom.setPrefWidth(200);

		TextField prenom = new TextField();
		prenom.setPromptText("Entrez un prénom");
		prenom.setPrefWidth(200);

		ComboBox<String> nomCat = new ComboBox<String>();
		nomCat.getItems().addAll(Controleur.getNomCatInter());
		nomCat.setPrefWidth(200);

		TextField hserv = new TextField();
		hserv.setPromptText("Entrez une heure minimale");
		hserv.setPrefWidth(200);

		TextField maxheure = new TextField();
		maxheure.setPromptText("Entrez une heure maximale");
		maxheure.setPrefWidth(200);

		TextField annee = new TextField();
		annee.setPromptText("Entrez une année");
		annee.setPrefWidth(200);

		// Titre
		Label lbl = new Label("Ajouter un intervenant :");
		lbl.setStyle("-fx-font-weight: bold");

		// Label
		Label lblNom = new Label("Nom :");
		Label lblPrenom = new Label("Prénom :");
		Label lblNomCat = new Label("Nom catégorie :");
		Label lblHserv = new Label("Heures de service :");
		Label lblMaxheure = new Label("Max heures :");
		Label lblAnnee  = new Label("Année :");
		this.lblErreur = new Label("");
		this.lblErreur.setStyle("-fx-text-fill: red;");

		// Boutons
		Button bouton = new Button("Ajouter");
		bouton.setStyle("-fx-background-color: #7F23A7; -fx-text-fill: white;");

		Button buttonA = new Button("Annuler");
		buttonA.setStyle("-fx-background-color: #7F23A7; -fx-text-fill: white;");

		/*--------------*/
		/*--Activation--*/
		/*--------------*/

		//Bouton Ajouter

		bouton.setOnMouseEntered(e -> bouton.setStyle("-fx-background-color: #D09AE8; -fx-text-fill: white;"));
		bouton.setOnMouseExited(e -> bouton.setStyle("-fx-background-color: #7F23A7; -fx-text-fill: white;"));
		bouton.setOnAction((ActionEvent event2) -> {
			try
			{
				nomCat.getValue().equals(null);

				int numCat = Controleur.getCodCatInter(nomCat.getValue());
				int hmin = Controleur.gethMin(numCat);
				int hmax = Controleur.gethMax(numCat);

				if(regString(nom.getText(),true) && regString(prenom.getText(),true) &&
				regInt(hserv.getText(),">",hmin) && regInt(hserv.getText(),"<=",Integer.parseInt(maxheure.getText())) &&
				regInt(maxheure.getText(),"<",hmax) && regInt(maxheure.getText(),">=",Integer.parseInt(hserv.getText())) &&
				regInt(annee.getText(),"=",Controleur.anneeActuelle))
				{
					Intervenant intervenant = new Intervenant(nom.getText(), prenom.getText(), numCat, Integer.parseInt(hserv.getText()), Integer.parseInt(maxheure.getText()), Integer.parseInt(annee.getText()));
					Controleur.insertIntervenant(intervenant);

					panelCentre.getChildren().clear();
					new Intervenants(panelCentre);
				}

			}
			catch (Exception e) { Intervenants.notifications("Aucune catégorie sélectionnée"); }
		});

		//Bouton Annuler

		buttonA.setOnMouseEntered(e -> buttonA.setStyle("-fx-background-color: #D09AE8; -fx-text-fill: white;"));
		buttonA.setOnMouseExited(e -> buttonA.setStyle("-fx-background-color: #7F23A7; -fx-text-fill: white;"));
		buttonA.setOnAction((ActionEvent event2) -> {
			try
			{
				new Intervenants(panelCentre);
				this.idIntervenant.clear();
			}
			catch (Exception e)	{ e.printStackTrace(); }
		});

		/*------------------*/
		/*--Positionnement--*/
		/*------------------*/

		// TextFields

		AnchorPane.setTopAnchor (nom, 80.0);
		AnchorPane.setLeftAnchor(nom, 140.0);
		panelCentre.getChildren ().add(nom);

		AnchorPane.setTopAnchor (prenom, 120.0);
		AnchorPane.setLeftAnchor(prenom, 140.0);
		panelCentre.getChildren ().add(prenom);

		AnchorPane.setTopAnchor (nomCat, 160.0);
		AnchorPane.setLeftAnchor(nomCat, 140.0);
		panelCentre.getChildren ().add(nomCat);

		AnchorPane.setTopAnchor (hserv, 200.0);
		AnchorPane.setLeftAnchor(hserv, 140.0);
		panelCentre.getChildren ().add(hserv);

		AnchorPane.setTopAnchor (maxheure, 240.0);
		AnchorPane.setLeftAnchor(maxheure, 140.0);
		panelCentre.getChildren ().add(maxheure);

		AnchorPane.setTopAnchor (annee, 280.0);
		AnchorPane.setLeftAnchor(annee, 140.0);
		panelCentre.getChildren().add(annee);

		//Titre
		AnchorPane.setTopAnchor (lbl, 20.0);
		AnchorPane.setLeftAnchor(lbl, 20.0);
		panelCentre.getChildren ().add(lbl);

		//Label
		AnchorPane.setTopAnchor (lblNom, 80.0);
		AnchorPane.setLeftAnchor(lblNom, 20.0);
		panelCentre.getChildren ().add(lblNom);

		AnchorPane.setTopAnchor (lblPrenom, 120.0);
		AnchorPane.setLeftAnchor(lblPrenom, 20.0);
		panelCentre.getChildren ().add(lblPrenom);

		AnchorPane.setTopAnchor (lblNomCat, 160.0);
		AnchorPane.setLeftAnchor(lblNomCat, 20.0);
		panelCentre.getChildren ().add(lblNomCat);

		AnchorPane.setTopAnchor (lblHserv, 200.0);
		AnchorPane.setLeftAnchor(lblHserv, 20.0);
		panelCentre.getChildren ().add(lblHserv);

		AnchorPane.setTopAnchor (lblMaxheure, 240.0);
		AnchorPane.setLeftAnchor(lblMaxheure, 20.0);
		panelCentre.getChildren ().add(lblMaxheure);

		AnchorPane.setTopAnchor (lblAnnee, 280.0);
		AnchorPane.setLeftAnchor(lblAnnee, 20.0);
		panelCentre.getChildren ().add(lblAnnee);

		AnchorPane.setTopAnchor (this.lblErreur, 180.0);
		AnchorPane.setLeftAnchor(this.lblErreur, 420.0);
		panelCentre.getChildren ().add(this.lblErreur);

		//Boutons
		AnchorPane.setTopAnchor (bouton, 340.0);
		AnchorPane.setLeftAnchor(bouton, 20.0);
		panelCentre.getChildren ().add(bouton);

		AnchorPane.setTopAnchor (buttonA, 340.0);
		AnchorPane.setLeftAnchor(buttonA, 80.0);
		panelCentre.getChildren ().add(buttonA);

	}

	private boolean regString(String messageTester,boolean message)
	{
		if(!message)
			this.lblErreur = new Label("");

		String regex = "^[a-zA-Z]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(messageTester);

		if(matcher.matches())
		{
			this.lblErreur.setText("");
			return true;
		}

		if(message)
			this.lblErreur.setText(messageTester + " n'est pas valide");

		return false;
	}

	private boolean regInt(String nbTester, String contrainte,int borne)
	{
		if(this.isStringNumeric(nbTester))
		{
			int val = Integer.parseInt(nbTester);

			if(contrainte.equals("<"))
				if(val < borne) return true;
			if(contrainte.equals("<="))
				if(val <= borne) return true;
			if(contrainte.equals(">"))
				if(val > borne) return true;
			if(contrainte.equals(">="))
				if(val >= borne) return true;
			if(contrainte.equals("="))
				if(val == borne) return true;
		}

		this.lblErreur.setText(nbTester + " doit être " + contrainte + " " + borne);
		return false;
	}

	private boolean isStringNumeric(String str)
	{
		for (char c : str.toCharArray())
		{
			if (!Character.isDigit(c))
				return false;
		}

		return true;
	}

	@FXML
	private void supprimer(ActionEvent event)
	{
		try
		{
			int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
			this.idIntervenant.put(Integer.valueOf(tableView.getItems().get(selectedIndex).get(1)), Integer.valueOf(tableView.getItems().get(selectedIndex).get(0)));

			if (selectedIndex >= 0)
				tableView.getItems().remove(selectedIndex);

		}
		catch (Exception e) { Intervenants.notifications("Impossible de supprimer cet intervenant"); }
	}

	@FXML
	private void enregistrer(ActionEvent event)
	{
		for (int i = 0; i < this.idIntervenant.size(); i++)
		{
			try
			{
				for (Integer k : this.idIntervenant.keySet())
					Controleur.supprInter(k, this.idIntervenant.get(k));
			}
			catch (Exception e)	{ e.printStackTrace(); }
		}
	}

	@FXML
	private void modifier(CellEditEvent<ObservableList<String>, String> event) throws SQLException
	{
		ObservableList<String> row = event.getRowValue();
		String newValue = event.getNewValue();
		int index = event.getTablePosition().getColumn();
		int code = Integer.parseInt(row.get(1));

		row.set(index, newValue);

		if(regString(row.get(3),false) && regString(row.get(4),false))
		{
			Intervenant intervenant = new Intervenant(code, row.get(3), row.get(4), Integer.parseInt(row.get(5)), Integer.parseInt(row.get(6)), Integer.parseInt(row.get(0)));

			if (Controleur.getNomCatInter().contains(row.get(2)))
				Controleur.updateInter(intervenant, Controleur.getCodCatInter(row.get(2)));
			else
				Intervenants.notifications("La catégorie " + row.get(2) + " n'existe pas");
		}
		else
			Intervenants.notifications("Un ou des caractères incorrects");

		new Intervenants(this.panelCentre);
	}

	public static void notifications(String message)
	{
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}