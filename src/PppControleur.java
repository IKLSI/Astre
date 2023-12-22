import javafx.fxml.*;
import javafx.scene.control.*;
import java.sql.*;
import java.util.*;
import javafx.collections.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;

import java.net.URL;

import metier.*;
import controleur.Controleur;

public class PppControleur implements Initializable
{
	public static String intitule;

	@FXML public TextField semestre           = new TextField();
	@FXML public TableView tableView          = new TableView<>();
	@FXML public TextField nbEtd              = new TextField();
	@FXML public TextField nbTP               = new TextField();
	@FXML public TextField nbTD               = new TextField();
	@FXML public TextField code               = new TextField();
	@FXML public TextField libCourt           = new TextField();
	@FXML public TextField libLong            = new TextField();
	@FXML public TextField nbHPnCM            = new TextField();
	@FXML public TextField nbHPnTD            = new TextField();
	@FXML public TextField nbHPnTP            = new TextField();
	@FXML public TextField sommePn            = new TextField();
	@FXML public TextField nbHParSemaineCM    = new TextField();
	@FXML public TextField nbHParSemaineTD    = new TextField();
	@FXML public TextField nbHParSemaineTP    = new TextField();
	@FXML public TextField hPonctuelle        = new TextField();
	@FXML public TextField sommeTotPromoEqtd  = new TextField();
	@FXML public TextField sommeTotAffectEqtd = new TextField();
	@FXML public TextField nbHAffecteHT       = new TextField();
	@FXML public TextField nbHPnTut           = new TextField();
	@FXML public TextField nbHTut             = new TextField();
	@FXML public TextField sommeHeurePnPPP    = new TextField();
	@FXML public TextField nbHAffecteCM       = new TextField();
	@FXML public TextField nbHAffecteTD       = new TextField();
	@FXML public TextField nbHAffecteTP       = new TextField();
	@FXML public TextField nbHAffecteHP       = new TextField();

	public static String codes;
	private HashMap<String, String> map;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) { affichageDefaut(); }

	@FXML
	public void affichageDefaut( )
	{
		this.semestre.setText(PppControleur.intitule);
		chargerRessource(new ActionEvent());
	}

	@FXML
	public void chargerRessource(ActionEvent event)
	{
		code.setText(PppControleur.codes);
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
				
				lst.add(new Affectation(codMod, codInter, codCatHeure, commentaire, nom, type, nbSem, nbGrp, totalEqTd, nbH, 2023));
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

				listeAffectation.add(new Affectation(codMod, codInter, codCatHeure, commentaire, nom, type, nbSem, nbGrp, totalEqTd, nbH, 2023));
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

			ArrayList<Semestre> lstSem = Controleur.getSemestre(intitule);

			this.map = Controleur.getPreviModule(codes);

			HashMap<String,TextField> lstButton = new HashMap<String,TextField>()
			{{
				put("nbetd", nbEtd);
				put("nbtp", nbTP);
				put("nbtd", nbTD);
				put("libcourt", libCourt);
				put("liblong", libLong);
				put("nbhpncm", nbHPnCM);
				put("nbhpntd", nbHPnTD);
				put("nbhpntp", nbHPnTP);
				put("sommepn", sommePn);
				put("nbhparsemainecm", nbHParSemaineCM);
				put("nbhparsemainetd", nbHParSemaineTD);
				put("nbhparsemainetp", nbHParSemaineTP);
				put("hponctuelle", hPonctuelle);
				put("sommeproeqtd", sommeTotPromoEqtd);
				put("sommepreaffecteqtd", sommeTotAffectEqtd);
				put("nbhaffecteht", nbHAffecteHT);
				put("nbhpnTut", nbHPnTut);
				put("nbhtut", nbHTut);
				put("sommeheurepnppp", sommeHeurePnPPP);
				put("nbhaffectecm", nbHAffecteCM);
				put("nbhaffectetd", nbHAffecteTD);
				put("nbhaffectetp", nbHAffecteTP);
				put("nbhaffectehp", nbHAffecteHP);
			}};

			for (String key : this.map.keySet())
			{
				if(lstButton.containsKey(key))
					lstButton.get(key).setText(this.map.get(key));
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
	}
}