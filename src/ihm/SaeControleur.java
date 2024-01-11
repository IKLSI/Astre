package ihm;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.util.converter.*;
import javafx.scene.control.cell.*;
import javafx.event.ActionEvent;


import java.net.URL;
import java.sql.*;
import java.util.*;
import javafx.collections.*;

import metier.Affectation;
import metier.Modules;
import metier.Semestre;
import controleur.*;

public class SaeControleur implements Initializable
{
	public static String intitule;

	@FXML public TextField semestre   = new TextField();
	@FXML public TableView tableView  = new TableView<>();
	@FXML public TextField nbEtd      = new TextField();
	@FXML public TextField nbTP       = new TextField();
	@FXML public TextField nbTD       = new TextField();
	@FXML public TextField code       = new TextField();
	@FXML public TextField libCourt   = new TextField();
	@FXML public TextField libLong    = new TextField();
	@FXML public TextField nbHSaePN   = new TextField();
	@FXML public TextField nbHTutPN   = new TextField();
	@FXML public TextField sommePN    = new TextField();
	@FXML public TextField nbHSaeProm = new TextField();
	@FXML public TextField nbHTutProm = new TextField();
	@FXML public TextField sommeProm  = new TextField();
	@FXML public TextField nbHSaeAff  = new TextField();
	@FXML public TextField nbHTutAff  = new TextField();
	@FXML public TextField sommeAff   = new TextField();
	@FXML public TextField codMod     = new TextField();
	@FXML public CheckBox  valid      = new CheckBox();

	public static String codes;
	private HashMap<String, String> map;
	private boolean etat = false;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) { affichageDefaut(); }

	@FXML
	public void affichageDefaut( )
	{
		this.semestre.setText(SaeControleur.intitule);
		chargerRessource(new ActionEvent());
	}

	@FXML
	public void annuler(ActionEvent event) { new Previsionnel(PrevisionnelController.panelCentre); }

	@FXML
	public void chargerRessource(ActionEvent event)
	{
		code.setText(SaeControleur.codes);

		if (Controleur.getPreviModule(SaeControleur.codes) != null)
			remplirTableau();
		else
		{
			ArrayList<Semestre> lst = Controleur.getSemestre(SaeControleur.intitule);
			nbEtd.setText(String.valueOf(lst.get(0).getNbEtd()));
			nbTP.setText(String.valueOf(lst.get(0).getNbGrpTP()));
			nbTD.setText(String.valueOf(lst.get(0).getNbGrpTD()));
		}
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

				lst.add(new Affectation(codMod, codInter, codCatHeure, commentaire, nom, type, nbSem, nbGrp, totalEqTd, nbH, Controleur.anneeActuelle));
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

				listeAffectation.add(new Affectation(codMod, codInter, codCatHeure, commentaire, nom, type, nbSem, nbGrp, totalEqTd, nbH, Controleur.anneeActuelle));
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

			TableColumn<Affectation, String> commentaire = new TableColumn<>("Commentaire");
			commentaire.setCellValueFactory(new PropertyValueFactory<>("commentaire"));

			tableView.getColumns().addAll(nomCol, typeCol, nbSemCol, nbGpCol, totalEqTdCol, commentaire);
			tableView.setItems(listeAffectation);

			tableView.setEditable(true);
			nomCol.setCellFactory(TextFieldTableCell.forTableColumn());
			nomCol.setOnEditCommit(e -> {
				e.getTableView().getItems().get(e.getTablePosition().getRow()).setNom(e.getNewValue());
				modifier(new ActionEvent());
			});

			typeCol.setCellFactory(TextFieldTableCell.forTableColumn());
			typeCol.setOnEditCommit(e -> {
				e.getTableView().getItems().get(e.getTablePosition().getRow()).setType(e.getNewValue());
				modifier(new ActionEvent());
			});

			nbSemCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			nbSemCol.setOnEditCommit(e -> {
				modifier(new ActionEvent());
				e.getTableView().getItems().get(e.getTablePosition().getRow()).setNbSem(e.getNewValue());
			});

			nbGpCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			nbGpCol.setOnEditCommit(e -> {
				modifier(new ActionEvent());
				e.getTableView().getItems().get(e.getTablePosition().getRow()).setNbGrp(e.getNewValue());
			});

			totalEqTdCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			totalEqTdCol.setOnEditCommit(e -> {
				modifier(new ActionEvent());
				e.getTableView().getItems().get(e.getTablePosition().getRow()).setTotalEqTd(e.getNewValue());
			});

			commentaire.setCellFactory(TextFieldTableCell.forTableColumn());
			commentaire.setOnEditCommit(e -> {
				e.getTableView().getItems().get(e.getTablePosition().getRow()).setCommentaire(e.getNewValue());
				modifier(new ActionEvent());
			});

			this.map = Controleur.getPreviModule(codes);

			HashMap<String,TextField> lstButton = new HashMap<String,TextField>()
			{{
				put("nbetd",nbEtd);
				put("nbgrptp",nbTP);
				put("nbgrptd",nbTD);
				put("libcourt",libCourt);
				put("liblong",libLong);
				put("nbhpnsaeparsemestre", nbHSaePN);
				put("nbhpntutparsemestre", nbHTutPN);
				put("sommehpnsae", sommePN);
				put("nbhsaeparsemestre", nbHSaeProm);
				put("nbhtutparsemestre", nbHTutProm);
				put("sommetotpromoeqtd", sommeProm);
				put("nbhaffectesae", nbHSaeAff);
				put("nbhaffecteht", nbHTutAff);
				put("sommetotaffecteqtd", sommeAff);
			}};

			for (String key : map.keySet())
			{
				if(lstButton.containsKey(key))
					lstButton.get(key).setText(map.get(key));
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
	}

	@FXML
	public void valid(ActionEvent event) { Controleur.updateBool(("f").equals(this.map.get("valid")), code.getText()); }

	@FXML
	public void modifier(ActionEvent event)
	{
		Affectation affectation = (Affectation) tableView.getSelectionModel().getSelectedItem();
		int codInter = affectation.getCodInter();

		Controleur.updateAffectation(new Affectation(
			affectation.getCodMod(),
			codInter,
			Controleur.getCodCatInter(affectation.getType()),
			affectation.getCommentaire(),
			affectation.getNom(),
			affectation.getType(),
			affectation.getNbSem(),
			affectation.getNbGrp(),
			affectation.getTotalEqTd(),
			affectation.getNbH(),
			Controleur.anneeActuelle
		));
	}

	@FXML
	public void ajouter(ActionEvent event)
	{
		if (etat == false)
		{
			tableView.getItems().add(new Affectation(
				"",
				Integer.valueOf(0),
				Integer.valueOf(0),
				"",
				"",
				"",
				Integer.valueOf(0),
				Integer.valueOf(0),
				Integer.valueOf(0),
				Integer.valueOf(0),
				Controleur.anneeActuelle
			));
		}
		else
		{
			int dernier = tableView.getItems().size() - 1;
			Affectation affectation = (Affectation) tableView.getItems().get(dernier);

			Controleur.insertAffectationRessource(new Affectation(
				codes,
				Controleur.getCodInter(affectation.getNom()).get(0),
				Controleur.getCodCatInter(affectation.getType()),
				affectation.getCommentaire(),
				affectation.getNom(),
				affectation.getType(),
				affectation.getNbSem(),
				affectation.getNbGrp(),
				affectation.getTotalEqTd(),
				affectation.getNbH(),
				Controleur.anneeActuelle
			));
		}

		etat = !etat;
	}

	@FXML
	public void enregistrer (ActionEvent event)
	{
		try {
			HashMap<String, String> map = Controleur.getPreviModule(code.getText());

			Modules module = new Modules(
				code.getText(),
				semestre.getText(),
				Integer.valueOf(2),
				libLong.getText(),
				libCourt.getText(),
				valid.isSelected(),
				Integer.valueOf(0),
				Integer.valueOf(0),
				Integer.valueOf(0),
				Integer.valueOf(0),
				Integer.valueOf(0),
				Integer.valueOf(0),
				Integer.valueOf(0),
				Integer.valueOf(0),
				Integer.valueOf(0),
				Integer.valueOf(0),
				Integer.valueOf(nbHSaePN.getText()),
				Integer.valueOf(nbHTutPN.getText()),
				Integer.valueOf(nbHSaeProm.getText()),
				Integer.valueOf(nbHTutProm.getText()),
				Integer.valueOf(0),
				Integer.valueOf(0),
				Integer.valueOf(0),
				Integer.valueOf(0),
				Integer.valueOf(0),
				Controleur.anneeActuelle
			);

			if (map == null)
				Controleur.insertModSAE(module);
			else
				Controleur.updateMod(module, codMod.getText(), codes);

			new Previsionnel(PrevisionnelController.panelCentre);

		} catch (Exception e) {Intervenants.notifications("Un ou des champs incomplets ou erronés !");}
	}
}