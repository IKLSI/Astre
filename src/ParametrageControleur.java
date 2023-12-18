import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;
import java.util.ArrayList;

import metier.*;
public class ParametrageControleur implements Initializable
{
	@FXML
	private TableView tableView = new TableView<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) { categorieHeure(); }

	public void categorieIntervenants()
	{
		tableView.getColumns().clear();
		tableView.getItems().clear();

		ObservableList<CategorieIntervenant> data = FXCollections.observableArrayList();

		ArrayList<CategorieIntervenant> tab = new ArrayList<CategorieIntervenant>();

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
			{
				data.add(new CategorieIntervenant(c.getCodCatInter(), c.getNomCat(), c.getService(), c.getMaxHeure(), c.getRatioTPCatInterNum(), c.getRatioTPCatInterDen()));
			}

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
		catch (Exception e) { e.printStackTrace(); }
	}

	public void categorieHeure()
	{
		tableView.getColumns().clear();
		tableView.getItems().clear();

		ObservableList<CategorieHeure> data = FXCollections.observableArrayList();

		ArrayList<CategorieHeure> tab = Controleur.getCategorieHeure();

		for (CategorieHeure c : tab)
		{
			data.add(new CategorieHeure(c.getCodCatHeure(), c.getNomCatHeure(), c.getCoeffNum(), c.getCoeffDen()));
		}

		TableColumn<ObservableList<String>, String> codCatHeure = new TableColumn<>("Code");
		codCatHeure.setCellValueFactory(new PropertyValueFactory<>("codCatHeure"));

		TableColumn<ObservableList<String>, String> nomCatHeure = new TableColumn<>("Nom");
		nomCatHeure.setCellValueFactory(new PropertyValueFactory<>("nomCatHeure"));

		TableColumn<ObservableList<String>, String> coeffNum = new TableColumn<>("Coeff Num");
		coeffNum.setCellValueFactory(new PropertyValueFactory<>("coeffNum"));

		TableColumn<ObservableList<String>, String> coeffDen = new TableColumn<>("Coeff Den");
		coeffDen.setCellValueFactory(new PropertyValueFactory<>("coeffDen"));

		tableView.getColumns().addAll(codCatHeure, nomCatHeure, coeffNum, coeffDen);
		tableView.setItems(data);
	}

	@FXML
	private void btnIntervenant(ActionEvent e) { categorieIntervenants(); }

	@FXML
	private void btnHeure(ActionEvent e){ categorieHeure(); }
}