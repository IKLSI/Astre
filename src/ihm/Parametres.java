package ihm;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import controleur.Controleur;

import metier.*;
public class Parametres implements Initializable
{
	//attributs d'instance
	@FXML private TableView tableView = new TableView<>();

	//charge par défaut le tableau de catégorie d'heure
	@Override public void initialize(URL location, ResourceBundle resources) { categorieHeure(); }

	//changement de tableau
	@FXML private void btnHeure(ActionEvent e){ categorieHeure(); }
	@FXML private void btnIntervenant(ActionEvent e) { categorieIntervenants(); }

	//remplissage du tableau de catégorie d'heure
	public void categorieHeure()
	{
		//vide le tableau
		tableView.getColumns().clear();
		tableView.getItems().clear();

		ArrayList<CategorieHeure> tab = Controleur.getCategorieHeure();	//stock toutes les catégories
		ObservableList<CategorieHeure> data = FXCollections.observableArrayList();	//stock toutes les cat de manière organiser pour les ajouter au tableview

		//organise les catégorie d'heure
		for (CategorieHeure c : tab)
			data.add(new CategorieHeure(c.getCodCatHeure(), c.getNomCatHeure(), c.getCoeffNum(), c.getCoeffDen()));
		
		//création de toutes les colonnes
		TableColumn<ObservableList<String>, String> codCatHeure = new TableColumn<>("Code");
		codCatHeure.setCellValueFactory(new PropertyValueFactory<>("codCatHeure"));

		TableColumn<ObservableList<String>, String> nomCatHeure = new TableColumn<>("Nom");
		nomCatHeure.setCellValueFactory(new PropertyValueFactory<>("nomCatHeure"));

		TableColumn<ObservableList<String>, String> coeffNum = new TableColumn<>("Coeff Num");
		coeffNum.setCellValueFactory(new PropertyValueFactory<>("coeffNum"));

		TableColumn<ObservableList<String>, String> coeffDen = new TableColumn<>("Coeff Den");
		coeffDen.setCellValueFactory(new PropertyValueFactory<>("coeffDen"));

		//ajoute tout les titres des colonnes au tableView
		tableView.getColumns().addAll(codCatHeure, nomCatHeure, coeffNum, coeffDen);

		//ajoute toutes les données dans le tableView
		tableView.setItems(data);
	}

	//remplissage du tableau de catégorie d'intervenants
	public void categorieIntervenants()
	{
		//vide le tableau
		tableView.getColumns().clear();
		tableView.getItems().clear();

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

			//organise les catégorie d'heure
			for (CategorieIntervenant c : tab)
				data.add(new CategorieIntervenant(c.getCodCatInter(), c.getNomCat(), c.getService(), c.getMaxHeure(), c.getRatioTPCatInterNum(), c.getRatioTPCatInterDen()));
			
			//création de toutes les colonnes
			TableColumn<ObservableList<String>, String> codCatInter = new TableColumn<>("Code");
			codCatInter.setCellValueFactory(new PropertyValueFactory<>("codCatInter"));

			TableColumn<ObservableList<String>, String> nomCat = new TableColumn<>("Nom");
			nomCat.setCellValueFactory(new PropertyValueFactory<>("nomCat"));

			TableColumn<ObservableList<String>, String> service = new TableColumn<>("Service");
			service.setCellValueFactory(new PropertyValueFactory<>("service"));

			TableColumn<ObservableList<String>, String> maxHeure = new TableColumn<>("Max Heure");
			maxHeure.setCellValueFactory(new PropertyValueFactory<>("maxHeure"));

			TableColumn<ObservableList<String>, String> ratioTPCatInterNum = new TableColumn<>("Ratio TP Num");
			ratioTPCatInterNum.setCellValueFactory(new PropertyValueFactory<>("ratioTPCatInterNum"));

			TableColumn<ObservableList<String>, String> ratioTPCatInterDen = new TableColumn<>("Ratio TP Den");
			ratioTPCatInterDen.setCellValueFactory(new PropertyValueFactory<>("ratioTPCatInterDen"));

			tableView.getColumns().addAll(codCatInter, nomCat, service, maxHeure, ratioTPCatInterNum, ratioTPCatInterDen);
			tableView.setItems(data);
		}
		catch (Exception e) {//ajoute toutes les données dans le tableView
			 e.printStackTrace(); }
	}

}