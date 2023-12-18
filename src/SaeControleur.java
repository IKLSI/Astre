import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import metier.*;
import metier.Affectation;

public class SaeControleur implements Initializable
{
	public static String intitule;

	@FXML
	public TextField semestre = new TextField();

	@FXML
	public TableView tableView = new TableView<>();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) { affichageDefaut(); }

	@FXML
	public void affichageDefaut( )
	{
		this.semestre.setText(RessourceControleur.intitule);
		remplirTableau();
	}

	@FXML
	public void annuler(ActionEvent event)
	{
		new Previsionnel(PrevisionnelController.panelCentre);
	}

	@FXML
	private void remplirTableau()
	{
		tableView.getColumns().clear();
		tableView.getItems().clear();

		ObservableList<Affectation> listeAffectation = FXCollections.observableArrayList();

		
		ArrayList<Affectation> lst = new ArrayList<Affectation>();

		try
		{
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/lk210125","lk210125","Kyliann.0Bado");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM affectation_final WHERE codmod='S1.1'");

			while (resultSet.next())
			{
				String nom = resultSet.getString("nom");
				String type = resultSet.getString("nomcatheure");
				int nbH = resultSet.getInt("nbH");
				int totalEqTd = resultSet.getInt("tot eqtd");

				//lst.add(new Affectation(nom, type, nbH, totalEqTd));
			}

			for (Affectation affectation : lst)
			{
				String nom = affectation.getNom();
				String type = affectation.getType();
				int nbSem = affectation.getNbSem();
				int nbGp = affectation.getNbGp();
				int totalEqTd = affectation.getTotalEqTd();

				listeAffectation.add(new Affectation(nom, type, nbSem, nbGp, totalEqTd));
			}

			// Remplit la table avec les données de la liste

			TableColumn<Affectation, String> nomCol = new TableColumn<>("Intervenant");
			nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

			TableColumn<Affectation, String> typeCol = new TableColumn<>("Type");
			typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

			TableColumn<Affectation, Integer> nbSemCol = new TableColumn<>("Nb sem");
			nbSemCol.setCellValueFactory(new PropertyValueFactory<>("nbSem"));

			TableColumn<Affectation, Integer> nbGpCol = new TableColumn<>("Nb Gp / nb h");
			nbGpCol.setCellValueFactory(new PropertyValueFactory<>("nbGp"));

			TableColumn<Affectation, Integer> totalEqTdCol = new TableColumn<>("Total eqtd");
			totalEqTdCol.setCellValueFactory(new PropertyValueFactory<>("totalEqTd"));

			tableView.getColumns().addAll(nomCol, typeCol, nbSemCol, nbGpCol, totalEqTdCol);
			tableView.setItems(listeAffectation);
		}
		catch (SQLException e) { e.printStackTrace(); }
	}
}