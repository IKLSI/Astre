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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import java.util.HashMap;

import metier.*;

public class RessourceControleur implements Initializable
{
	
	public static String intitule;

	@FXML
	public TextField semestre = new TextField();

	@FXML
	public TableView tableView = new TableView<>();

	@FXML
	public TextField nbEtd = new TextField();

	@FXML
	public TextField nbTP = new TextField();

	@FXML
	public TextField nbTD = new TextField();

	@FXML
	public static TextField code = new TextField();

	public String codes;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) { affichageDefaut(); }

	@FXML
	public void affichageDefaut( )
	{
		this.semestre.setText(RessourceControleur.intitule);
	}

	@FXML
	public void annuler(ActionEvent event)
	{
		new Previsionnel(PrevisionnelController.panelCentre);
	}

	@FXML
	public void chargerRessource(ActionEvent event)
	{
		codes = code.getText();
		remplirTableau();
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
			ResultSet resultSet = statement.executeQuery("SELECT * FROM affectation_final WHERE codmod='" + codes + "'");

			while (resultSet.next())
			{
				String nom = resultSet.getString("nom");
				String type = resultSet.getString("nomcatheure");
				int nbSem = resultSet.getInt("nbsem");
				int nbGrp = resultSet.getInt("nbgrp");
				int totalEqTd = resultSet.getInt("tot eqtd");
				String codMod = resultSet.getString("codMod");
				int codInter = resultSet.getInt("codInter");
				int codCatHeure = resultSet.getInt("codCatHeure");
				String commentaire = resultSet.getString("commentaire");
				int nbH = resultSet.getInt("nbH");
				
				lst.add(new Affectation(codMod, codInter, codCatHeure, commentaire, nom, type, nbSem, nbGrp, totalEqTd, nbH));
			}

			for (Affectation affectation : lst)
			{
				String nom = affectation.getNom();
				String type = affectation.getType();
				int nbSem = affectation.getNbSem();
				int nbGrp = affectation.getNbGrp();
				int totalEqTd = affectation.getTotalEqTd();
				String codMod = affectation.getCodMod();
				int codInter = affectation.getCodInter();
				int codCatHeure = affectation.getCodCatHeure();
				String commentaire = affectation.getCommentaire();
				int nbH = affectation.getNbH();

				listeAffectation.add(new Affectation(codMod, codInter, codCatHeure, commentaire, nom, type, nbSem, nbGrp, totalEqTd, nbH));
			}

			TableColumn<Affectation, String> nomCol = new TableColumn<>("Intervenant");
			nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

			TableColumn<Affectation, String> typeCol = new TableColumn<>("Type");
			typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

			TableColumn<Affectation, Integer> nbSemCol = new TableColumn<>("Nb sem");
			nbSemCol.setCellValueFactory(new PropertyValueFactory<>("nbSem"));

			TableColumn<Affectation, Integer> nbGpCol = new TableColumn<>("Nb Gp / nb h");
			nbGpCol.setCellValueFactory(new PropertyValueFactory<>("nbGrp"));

			TableColumn<Affectation, Integer> totalEqTdCol = new TableColumn<>("Total eqtd");
			totalEqTdCol.setCellValueFactory(new PropertyValueFactory<>("totalEqTd"));

			TableColumn<Affectation, Integer> commentaire = new TableColumn<>("Commentaire");
			commentaire.setCellValueFactory(new PropertyValueFactory<>("commentaire"));

			tableView.getColumns().addAll(nomCol, typeCol, nbSemCol, nbGpCol, totalEqTdCol, commentaire);
			tableView.setItems(listeAffectation);

			ArrayList<Semestre> lstSem = Controleur.getSemestre(intitule);

			for (Semestre sem : lstSem)
			{
				this.nbEtd.setText(String.valueOf(sem.getNbEtd()));
				this.nbTP.setText(String.valueOf(sem.getNbGrpTP()));
				this.nbTD.setText(String.valueOf(sem.getNbGrpTD()));
			}

			HashMap<String, String> map = Controleur.getPreviModuleRessource(codes);

			for (String key : map.keySet())
			{
				if (key.equals("nbEtd"))
				{
					this.nbEtd.setText(map.get(key));
				}
				else if (key.equals("nbGrpTP"))
				{
					this.nbTP.setText(map.get(key));
				}
				else if (key.equals("nbGrpTD"))
				{
					this.nbTD.setText(map.get(key));
				}
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
	}

	// methode accessible depuis RessourceController permettant de set le textfield de code

	public static void setCode(String newCode)
	{
		code.setText(newCode);
	}
}