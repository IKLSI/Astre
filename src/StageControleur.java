import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import metier.*;
import controleur.Controleur;

public class StageControleur implements Initializable
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
	public TextField code = new TextField();

	@FXML
	public TextField libCourt = new TextField();

	@FXML
	public TextField libLong = new TextField();

	@FXML
	public TextField nbHPnREH = new TextField();

	@FXML
	public TextField nbHPnTut = new TextField();

	@FXML
	public TextField sommeHPnStage = new TextField();

	@FXML
	public TextField nbHREH = new TextField();

	@FXML
	public TextField nbHTut = new TextField();

	@FXML
	public TextField sommeTotPromoEqtd = new TextField();

	@FXML
	public TextField nbHAffecteREH = new TextField();

	@FXML
	public TextField nbHAffecteHT = new TextField();

	@FXML
	public TextField sommeTotAffectEqtd = new TextField();

	public static String codes;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) { affichageDefaut(); }

	@FXML
	public void affichageDefaut( )
	{
		this.semestre.setText(StageControleur.intitule);
		chargerRessource(new ActionEvent());
	}

	@FXML
	public void annuler(ActionEvent event)
	{
		new Previsionnel(PrevisionnelController.panelCentre);
	}

	@FXML
	public void chargerRessource(ActionEvent event)
	{
		code.setText(StageControleur.codes);
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
			ResultSet resultSet = Controleur.getAffectation(codes);

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

			// Remplit la table avec les données de la liste

			TableColumn<Affectation, String> nomCol = new TableColumn<>("Intervenant");
			nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

			TableColumn<Affectation, String> typeCol = new TableColumn<>("Type");
			typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

			TableColumn<Affectation, Integer> nbHCol = new TableColumn<>("Nb h");
			nbHCol.setCellValueFactory(new PropertyValueFactory<>("nbH"));

			TableColumn<Affectation, Integer> totalEqTdCol = new TableColumn<>("Total eqtd");
			totalEqTdCol.setCellValueFactory(new PropertyValueFactory<>("totalEqTd"));

			TableColumn<Affectation, Integer> comCol = new TableColumn<>("commentaire");
			comCol.setCellValueFactory(new PropertyValueFactory<>("commentaire"));

			tableView.getColumns().addAll(nomCol, typeCol, nbHCol, totalEqTdCol, comCol);
			tableView.setItems(listeAffectation);

			HashMap<String, String> map = Controleur.getPreviModule(codes);

			HashMap<String,TextField> lstButton = new HashMap<String,TextField>()
			{{
				put("nbetd",nbEtd);
				put("nbgrptp",nbTP);
				put("nbgrptd",nbTD);
				put("libcourt",libCourt);
				put("liblong",libLong);
				put("nbhpnreh", nbHPnREH);
				put("nbhpntut", nbHPnTut);
				put("sommehpnstage", sommeHPnStage);
				put("nbhreh", nbHREH);
				put("nbhtut", nbHTut);
				put("sommetotpromoeqtd", sommeTotPromoEqtd);
				put("nbhaffectereh", nbHAffecteREH);
				put("nbhaffecteht", nbHAffecteHT );
				put("sommetotaffecteqtd", sommeTotAffectEqtd);
			}};

			for (String key : map.keySet())
			{
				if(lstButton.containsKey(key))
					lstButton.get(key).setText(map.get(key));
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
	}
}