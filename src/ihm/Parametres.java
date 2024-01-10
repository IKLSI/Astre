package ihm;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.sql.ResultSet;
import controleur.Controleur;

import metier.*;

public class Parametres implements Initializable
{
	//attributs d'instance
	@FXML private TableView tableView = new TableView<>();
	@FXML private Button btnAjouter;
	@FXML private Button btnSuppr;
	private boolean etat = false;
	private String nomCat;

	//charge par défaut le tableau de catégorie d'heure
	@Override public void initialize(URL location, ResourceBundle resources) { categorieHeure(); }

	//changement de tableau
	@FXML private void btnHeure(ActionEvent e){ categorieHeure(); }
	@FXML private void btnIntervenant(ActionEvent e) { categorieIntervenants(); }

	@FXML private void ajouter(ActionEvent e)
	{
		if (etat == false)
		{
			tableView.getItems().add(new CategorieIntervenant(0, "", 0, 0, 0, 0));
		}
		else
		{
			int dernier = tableView.getItems().size() - 1;
			CategorieIntervenant catInter = (CategorieIntervenant) tableView.getItems().get(dernier);
			Controleur.insertCategorieIntervenant(catInter);
		}

		etat = !etat;
	}

	@FXML private void supprimer(ActionEvent e)
	{
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
		CategorieIntervenant catInter = (CategorieIntervenant) tableView.getSelectionModel().getSelectedItem();
		Controleur.supprimerCategorieIntervenant(catInter.getNomCat());
		categorieIntervenants();
	}

	//remplissage du tableau de catégorie d'heure
	public void categorieHeure()
	{

		//vide le tableau
		tableView.getColumns().clear();
		tableView.getItems().clear();
		this.btnAjouter.setVisible(false);
		this.btnSuppr.setVisible(false);

		ArrayList<CategorieHeure> tab = Controleur.getCategorieHeure();	//stock toutes les catégories
		ObservableList<CategorieHeure> data = FXCollections.observableArrayList();	//stock toutes les cat de manière organiser pour les ajouter au tableview

		for (CategorieHeure c : tab)
			data.add(new CategorieHeure(c.getCodCatHeure(), c.getNomCatHeure(), c.getCoeffNum(), c.getCoeffDen()));

		TableColumn<ObservableList<String>, String> nomCatHeure = new TableColumn<>("Nom");
		nomCatHeure.setCellValueFactory(new PropertyValueFactory<>("nomCatHeure"));

		TableColumn<ObservableList<String>, String> coeffNum = new TableColumn<>("Coeff Num");
		coeffNum.setCellValueFactory(new PropertyValueFactory<>("coeffNum"));

		TableColumn<ObservableList<String>, String> coeffDen = new TableColumn<>("Coeff Den");
		coeffDen.setCellValueFactory(new PropertyValueFactory<>("coeffDen"));;

		//ajoute tout les titres des colonnes au tableView
		tableView.getColumns().addAll(nomCatHeure, coeffNum, coeffDen);

		//ajoute toutes les données dans le tableView
		tableView.setItems(data);
	}

	//remplissage du tableau de catégorie d'intervenants
	public void categorieIntervenants()
	{
		//vide le tableau
		tableView.getColumns().clear();
		tableView.getItems().clear();

		this.btnAjouter.setVisible(true);
		this.btnSuppr.setVisible(true);

		ObservableList<CategorieIntervenant> data = FXCollections.observableArrayList();//stock toutes les catégories
		ArrayList<CategorieIntervenant> tab = new ArrayList<CategorieIntervenant>();//stock toutes les cat de manière organiser pour les ajouter au tableview

		try
		{
			ResultSet rs = Controleur.getCategorieInter();

			while (rs.next())
			{
				int codCatInter = rs.getInt("codCatInter");
				String nomCat = rs.getString("nomCat");
				int service = rs.getInt("service");
				int maxHeure = rs.getInt("maxHeure");
				int ratioTPCatInterNum = rs.getInt("ratioTPCatInterNum");
				int ratioTPCatInterDen = rs.getInt("ratioTPCatInterDen");

				tab.add(new CategorieIntervenant(codCatInter, nomCat, service, maxHeure, ratioTPCatInterNum, ratioTPCatInterDen));
			}

			for (CategorieIntervenant c : tab)
				data.add(new CategorieIntervenant(c.getCodCatInter(), c.getNomCat(), c.getService(), c.getMaxHeure(), c.getRatioTPCatInterNum(), c.getRatioTPCatInterDen()));

			tableView.setEditable(true);
			tableView.getSelectionModel().setCellSelectionEnabled(true);
			tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

			TableColumn<CategorieIntervenant, String> nomCat = new TableColumn<>("Nom");
			nomCat.setCellValueFactory(new PropertyValueFactory<>("nomCat"));
			nomCat.setCellFactory(TextFieldTableCell.forTableColumn());
			nomCat.setOnEditCommit(e -> {
				modifier(new ActionEvent());
				e.getTableView().getItems().get(e.getTablePosition().getRow()).setNomCat(e.getNewValue());
				update(new ActionEvent(), this.nomCat);
			});

			TableColumn<CategorieIntervenant, Integer> service = new TableColumn<>("Service");
			service.setCellValueFactory(new PropertyValueFactory<>("service"));
			service.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			service.setOnEditCommit(e -> {
				modifier(new ActionEvent());
				e.getTableView().getItems().get(e.getTablePosition().getRow()).setService(e.getNewValue());
				update(new ActionEvent(), this.nomCat);
			});

			TableColumn<CategorieIntervenant, Integer> maxHeure = new TableColumn<>("Max Heure");
			maxHeure.setCellValueFactory(new PropertyValueFactory<>("maxHeure"));
			maxHeure.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			maxHeure.setOnEditCommit(e -> {
				modifier(new ActionEvent());
				e.getTableView().getItems().get(e.getTablePosition().getRow()).setMaxHeure(e.getNewValue());
				update(new ActionEvent(), this.nomCat);
			});

			TableColumn<CategorieIntervenant, Integer> ratioTPCatInterNum = new TableColumn<>("Ratio TP Num");
			ratioTPCatInterNum.setCellValueFactory(new PropertyValueFactory<>("ratioTPCatInterNum"));
			ratioTPCatInterNum.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			ratioTPCatInterNum.setOnEditCommit(e -> {
				modifier(new ActionEvent());
				e.getTableView().getItems().get(e.getTablePosition().getRow()).setRatioTPCatInterNum(e.getNewValue());
				update(new ActionEvent(), this.nomCat);
			});

			TableColumn<CategorieIntervenant, Integer> ratioTPCatInterDen = new TableColumn<>("Ratio TP Den");
			ratioTPCatInterDen.setCellValueFactory(new PropertyValueFactory<>("ratioTPCatInterDen"));
			ratioTPCatInterDen.setOnEditCommit(event -> event.getRowValue().setRatioTPCatInterDen(event.getNewValue()));
			ratioTPCatInterDen.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			ratioTPCatInterDen.setOnEditCommit(e -> {
				modifier(new ActionEvent());
				e.getTableView().getItems().get(e.getTablePosition().getRow()).setRatioTPCatInterDen(e.getNewValue());
				update(new ActionEvent(), this.nomCat);
			});

			tableView.getColumns().addAll(nomCat, service, maxHeure, ratioTPCatInterNum, ratioTPCatInterDen);
			tableView.setItems(data);
		}
		catch (Exception e) { Intervenants.notifications("Erreur lors de l'ajout"); } // Voir la requete bado
	}

	@FXML
	public void modifier(ActionEvent event)
	{
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
		CategorieIntervenant catInter = (CategorieIntervenant) tableView.getSelectionModel().getSelectedItem();
		this.nomCat = catInter.getNomCat();
	}

	@FXML
	private void update(ActionEvent event, String nomCat)
	{
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0)
		{
			CategorieIntervenant ancienneValeur = (CategorieIntervenant) tableView.getItems().get(selectedIndex);

			CategorieIntervenant nouvelleValeur = new CategorieIntervenant(
					ancienneValeur.getCodCatInter(),
					ancienneValeur.getNomCat(),
					ancienneValeur.getService(),
					ancienneValeur.getMaxHeure(),
					ancienneValeur.getRatioTPCatInterNum(),
					ancienneValeur.getRatioTPCatInterDen()
			);

			Controleur.updateCategorieIntervenant(nouvelleValeur, nomCat);
		}

		categorieIntervenants();
	}
}