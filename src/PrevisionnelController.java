import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;
import java.util.ResourceBundle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrevisionnelController implements Initializable
{
	@FXML
	private MenuButton menuButton;

	@FXML
	private AnchorPane panelCentre;

	@FXML
	private TextField nbgpTPS1 = new TextField();

	@FXML
	private TextField nbgpTDS1 = new TextField();

	@FXML
	private TextField nbEtdS1 = new TextField();

	@FXML
	private TextField nbSemaineS1 = new TextField();

	@FXML
	private TextField nbgpTPS2 = new TextField();

	@FXML
	private TextField nbgpTDS2 = new TextField();

	@FXML
	private TextField nbEtdS2 = new TextField();

	@FXML
	private TextField nbSemaineS2 = new TextField();

	@FXML
	private TextField nbgpTPS3 = new TextField();

	@FXML
	private TextField nbgpTDS3 = new TextField();

	@FXML
	private TextField nbEtdS3 = new TextField();

	@FXML
	private TextField nbSemaineS3 = new TextField();

	@FXML
	private TextField nbgpTPS4 = new TextField();

	@FXML
	private TextField nbgpTDS4 = new TextField();

	@FXML
	private TextField nbEtdS4 = new TextField();

	@FXML
	private TextField nbSemaineS4 = new TextField();

	@FXML
	private TextField nbgpTPS5 = new TextField();

	@FXML
	private TextField nbgpTDS5 = new TextField();

	@FXML
	private TextField nbEtdS5 = new TextField();

	@FXML
	private TextField nbSemaineS5 = new TextField();

	@FXML
	private TextField nbgpTPS6 = new TextField();

	@FXML
	private TextField nbgpTDS6 = new TextField();

	@FXML
	private TextField nbEtdS6 = new TextField();

	@FXML
	private TextField nbSemaineS6 = new TextField();

	@FXML
	private String intitule = "S2";

	@FXML
	private TableView tableView;

	@FXML
	private TableView tableViewS2;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		chargementBtn();
	}

	private void chargementBtn()
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/lk210125","lk210125","Kyliann.0Bado");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT nomTypMod FROM TypeModule;");

			while (rs.next())
			{
				String nomBouton = rs.getString("nomTypMod");
				MenuItem nouveauItem = new MenuItem(nomBouton);
				nouveauItem.setOnAction(e -> { System.out.println("Bouton " + nomBouton + " cliqué !"); });
				nouveauItem.setStyle("-fx-text-fill : #000000");
				menuButton.getItems().add(nouveauItem);
				menuButton.setPrefWidth(85);
			}

			conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
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
					nbgpTDS1.setText(String.valueOf(nbGrpTD));
					nbgpTPS1.setText(String.valueOf(nbGrpTP));
					nbEtdS1.setText(String.valueOf(nbEtd));
					nbSemaineS1.setText(String.valueOf(nbSemaine));
				}
				else if (this.intitule.equals("S2"))
				{
					nbgpTDS2.setText(String.valueOf(nbGrpTD));
					nbgpTPS2.setText(String.valueOf(nbGrpTP));
					nbEtdS2.setText(String.valueOf(nbEtd));
					nbSemaineS2.setText(String.valueOf(nbSemaine));
				}
				else if (this.intitule.equals("S3"))
				{
					nbgpTDS3.setText(String.valueOf(nbGrpTD));
					nbgpTPS3.setText(String.valueOf(nbGrpTP));
					nbEtdS3.setText(String.valueOf(nbEtd));
					nbSemaineS3.setText(String.valueOf(nbSemaine));
				}
				else if (this.intitule.equals("S4"))
				{
					nbgpTDS4.setText(String.valueOf(nbGrpTD));
					nbgpTPS4.setText(String.valueOf(nbGrpTP));
					nbEtdS4.setText(String.valueOf(nbEtd));
					nbSemaineS4.setText(String.valueOf(nbSemaine));
				}
				else if (this.intitule.equals("S5"))
				{
					nbgpTDS5.setText(String.valueOf(nbGrpTD));
					nbgpTPS5.setText(String.valueOf(nbGrpTP));
					nbEtdS5.setText(String.valueOf(nbEtd));
					nbSemaineS5.setText(String.valueOf(nbSemaine));
				}
				else if (this.intitule.equals("S6"))
				{
					nbgpTDS6.setText(String.valueOf(nbGrpTD));
					nbgpTPS6.setText(String.valueOf(nbGrpTP));
					nbEtdS6.setText(String.valueOf(nbEtd));
					nbSemaineS6.setText(String.valueOf(nbSemaine));
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	@FXML
	private void remplirTableau()
	{
		try
		{
			tableView.getItems().clear();
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/lk210125", "lk210125", "Kyliann.0Bado");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM liste_module WHERE codSem = '" + this.intitule + "';");

			ObservableList<Modules> listeModules = FXCollections.observableArrayList();

			while (rs.next())
			{
				String codMod = rs.getString("codMod");
				String libLong = rs.getString("libLong");
				String hAP = rs.getString("heureAffect/heurePn");
				String valid = rs.getString("valid");

				Modules module = new Modules(codMod, libLong, hAP, valid);
				listeModules.add(module);
			}

			TableColumn<Module, String> codModCol = new TableColumn<>("Code Module");
			codModCol.setCellValueFactory(new PropertyValueFactory<>("codMod"));

			TableColumn<Module, String> libLongCol = new TableColumn<>("Libellé Long");
			libLongCol.setCellValueFactory(new PropertyValueFactory<>("libLong"));

			TableColumn<Module, String> hAPCol = new TableColumn<>("Heure Affectée / Heure Prévue");
			hAPCol.setCellValueFactory(new PropertyValueFactory<>("hAP"));

			TableColumn<Module, String> validCol = new TableColumn<>("Valid");
			validCol.setCellValueFactory(new PropertyValueFactory<>("valid"));

			if (this.intitule.equals("S1"))
			{
				tableView.getColumns().addAll(codModCol, libLongCol, hAPCol, validCol);
				tableView.setItems(listeModules);
			}
			else if (this.intitule.equals("S2"))
			{
				tableViewS2.getColumns().addAll(codModCol, libLongCol, validCol);
				tableViewS2.setItems(listeModules);
			}

			conn.close();
			stmt.close();
			rs.close();
		}
		catch (SQLException | ClassNotFoundException e)
		{
			e.printStackTrace();
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
}
