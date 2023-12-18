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

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import metier.*;

public class PrevisionnelController implements Initializable
{
	@FXML
	public static AnchorPane panelCentre;

	@FXML
	private MenuButton menuButton;

	@FXML
	private TextField nbGrpTDS1 = new TextField();

	@FXML
	private TextField nbGrpTPS1 = new TextField();

	@FXML
	private TextField nbEtdS1 = new TextField();

	@FXML
	private TextField nbSemainesS1 = new TextField();

	@FXML
	private TextField nbGrpTDS2 = new TextField();

	@FXML
	private TextField nbGrpTPS2 = new TextField();

	@FXML
	private TextField nbEtdS2 = new TextField();

	@FXML
	private TextField nbSemainesS2 = new TextField();

	@FXML
	private TextField nbGrpTPS3 = new TextField();

	@FXML
	private TextField nbGrpTDS3 = new TextField();

	@FXML
	private TextField nbEtdS3 = new TextField();

	@FXML
	private TextField nbSemainesS3 = new TextField();

	@FXML
	private TextField nbGrpTPS4 = new TextField();

	@FXML
	private TextField nbGrpTDS4 = new TextField();

	@FXML
	private TextField nbEtdS4 = new TextField();

	@FXML
	private TextField nbSemainesS4 = new TextField();

	@FXML
	private TextField nbGrpTPS5 = new TextField();

	@FXML
	private TextField nbGrpTDS5 = new TextField();

	@FXML
	private TextField nbEtdS5 = new TextField();

	@FXML
	private TextField nbSemainesS5 = new TextField();

	@FXML
	private TextField nbGrpTPS6 = new TextField();

	@FXML
	private TextField nbGrpTDS6 = new TextField();

	@FXML
	private TextField nbEtdS6 = new TextField();

	@FXML
	private TextField nbSemainesS6 = new TextField();

	@FXML
	private String intitule = "S1";

	@FXML
	private TableView tableView;

	@FXML
	private TableView tableViewS2 = new TableView();

	@FXML
	private TableView tableViewS3 = new TableView();

	@FXML
	private TableView tableViewS4 = new TableView();

	@FXML
	private TableView tableViewS5 = new TableView();

	@FXML
	private TableView tableViewS6 = new TableView();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) { chargementBtn(); }

	private void chargementBtn()
	{
		ArrayList<TypeModule> lst = Controleur.getNomCategorieModules();

		for (TypeModule typeModule : lst)
		{
			String nomBouton = typeModule.getNomTypMod();
			MenuItem nouveauItem = new MenuItem(nomBouton);
			nouveauItem.setOnAction(e -> {
				switch (nomBouton)
				{
					case "Ressources" -> afficheRessource();
					case "SAE"-> System.out.println("SAE");
					case "Stage" -> System.out.println("STAGE");
					case "PPP" -> System.out.println("PPP");
				}
			});
			nouveauItem.setStyle("-fx-text-fill : #000000");
			menuButton.getItems().add(nouveauItem);
			menuButton.setPrefWidth(85);
		}
	}

	@FXML
	private void initialisationInformations()
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/lk210125","lk210125","Kyliann.0Bado");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT nbGrpTD, nbGrpTP, nbEtd, nbSemaines FROM Semestre WHERE codSem = '" + this.intitule + "';");

			while (rs.next())
			{
				int nbGrpTD = rs.getInt("nbGrpTD");
				int nbGrpTP = rs.getInt("nbGrpTP");
				int nbEtd = rs.getInt("nbEtd");
				int nbSemaine = rs.getInt("nbSemaines");

				if (this.intitule.equals("S1"))
				{
					nbGrpTDS1.setText(String.valueOf(nbGrpTD));
					nbGrpTPS1.setText(String.valueOf(nbGrpTP));
					nbEtdS1.setText(String.valueOf(nbEtd));
					nbSemainesS1.setText(String.valueOf(nbSemaine));
				}
				else if (this.intitule.equals("S2"))
				{
					nbGrpTDS2.setText(String.valueOf(nbGrpTD));
					nbGrpTPS2.setText(String.valueOf(nbGrpTP));
					nbEtdS2.setText(String.valueOf(nbEtd));
					nbSemainesS2.setText(String.valueOf(nbSemaine));
				}
				else if (this.intitule.equals("S3"))
				{
					nbGrpTDS3.setText(String.valueOf(nbGrpTD));
					nbGrpTPS3.setText(String.valueOf(nbGrpTP));
					nbEtdS3.setText(String.valueOf(nbEtd));
					nbSemainesS3.setText(String.valueOf(nbSemaine));
				}
				else if (this.intitule.equals("S4"))
				{
					nbGrpTDS4.setText(String.valueOf(nbGrpTD));
					nbGrpTPS4.setText(String.valueOf(nbGrpTP));
					nbEtdS4.setText(String.valueOf(nbEtd));
					nbSemainesS4.setText(String.valueOf(nbSemaine));
				}
				else if (this.intitule.equals("S5"))
				{
					nbGrpTDS5.setText(String.valueOf(nbGrpTD));
					nbGrpTPS5.setText(String.valueOf(nbGrpTP));
					nbEtdS5.setText(String.valueOf(nbEtd));
					nbSemainesS5.setText(String.valueOf(nbSemaine));
				}
				else if (this.intitule.equals("S6"))
				{
					nbGrpTDS6.setText(String.valueOf(nbGrpTD));
					nbGrpTPS6.setText(String.valueOf(nbGrpTP));
					nbEtdS6.setText(String.valueOf(nbEtd));
					nbSemainesS6.setText(String.valueOf(nbSemaine));
				}
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
	}

	@FXML
	private void remplirTableau()
	{
		tableView.getColumns().clear();
		tableView.getItems().clear();

		tableViewS2.getColumns().clear();
		tableViewS2.getItems().clear();

		tableViewS3.getColumns().clear();
		tableViewS3.getItems().clear();

		tableViewS4.getColumns().clear();
		tableViewS4.getItems().clear();

		tableViewS5.getColumns().clear();
		tableViewS5.getItems().clear();

		tableViewS6.getColumns().clear();
		tableViewS6.getItems().clear();
		ObservableList<Modules> listeModules = FXCollections.observableArrayList();

		// Stocker une arraylist dans une observablelist
		ArrayList<Modules> lst = Controleur.getListModule(this.intitule);
		
		for (Modules module : lst)
		{
			String codMod  = module.getCodMod();
			String libLong = module.getLibLong();
			String hAP     = module.getHAP();
			Boolean valid   = module.getValid();

			Modules module2 = new Modules(codMod, libLong, hAP, valid);
			listeModules.add(module2);
		}

		TableColumn<Modules, String> codModCol = new TableColumn<>("Code Module");
		codModCol.setCellValueFactory(new PropertyValueFactory<>("codMod"));

		TableColumn<Modules, String> libLongCol = new TableColumn<>("Libellé Long");
		libLongCol.setCellValueFactory(new PropertyValueFactory<>("libLong"));

		TableColumn<Modules, String> hAPCol = new TableColumn<>("Heure Affectée / Heure Prévue");
		hAPCol.setCellValueFactory(new PropertyValueFactory<>("HAP"));

		TableColumn<Modules, String> validCol = new TableColumn<>("Valid");
		validCol.setCellValueFactory(new PropertyValueFactory<>("valid"));

		if (this.intitule.equals("S1"))
		{
			tableView.getColumns().addAll(codModCol, libLongCol, hAPCol, validCol);
			tableView.setItems(listeModules);
		}
		else if (this.intitule.equals("S2"))
		{
			tableViewS2.getColumns().addAll(codModCol, libLongCol, hAPCol, validCol);
			tableViewS2.setItems(listeModules);
		}
		else if (this.intitule.equals("S3"))
		{
			tableViewS3.getColumns().addAll(codModCol, libLongCol, hAPCol, validCol);
			tableViewS3.setItems(listeModules);
		}
		else if (this.intitule.equals("S4"))
		{
			tableViewS4.getColumns().addAll(codModCol, libLongCol, hAPCol, validCol);
			tableViewS4.setItems(listeModules);
		}
		else if (this.intitule.equals("S5"))
		{
			tableViewS5.getColumns().addAll(codModCol, libLongCol, hAPCol, validCol);
			tableViewS5.setItems(listeModules);
		}
		else if (this.intitule.equals("S6"))
		{
			tableViewS6.getColumns().addAll(codModCol, libLongCol, hAPCol, validCol);
			tableViewS6.setItems(listeModules);
		}
	}

	@FXML
	private void semestreChange(Event event)
	{
		Tab tb = (Tab) event.getSource();
		this.intitule = tb.getText();
		initialisationInformations();
		remplirTableau();
	}

	@FXML
	private void modification(ActionEvent event)
	{
		TextField textField   = (TextField) event.getSource();
		String    textFieldId = textField.getId();
		textFieldId = textFieldId.substring(0, textFieldId.length() - 2);

		Controleur.updateSem(textFieldId, this.intitule, Integer.parseInt(textField.getText()));
	}

	@FXML
	private void afficheRessource() {
		RessourceControleur.intitule = this.intitule;
		new Ressource(PrevisionnelController.panelCentre);
	}
}