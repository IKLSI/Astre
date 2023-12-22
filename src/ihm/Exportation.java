package ihm;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import java.net.URL;

import controleur.Controleur;

public class Exportation implements Initializable
{
	//Attributs d'instance
	@FXML private AnchorPane panelSaisie;
	@FXML private RadioButton btnCSV;
	@FXML private Label lblErreur;

	private TextField nomInter;
	private TextField prenomInter;
	private ComboBox<String> lst;

	private boolean exportValid = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) { initialisationInter(); }

	@FXML public void afficheIntervenants(ActionEvent event){ initialisationInter(); }
	@FXML public void afficheModule(ActionEvent event)      { initialisationModSem("module"); }
	@FXML public void afficheSemestre(ActionEvent event)    { initialisationModSem("semestre"); }


	/*Panel Droit pour exporter les Intervenants*/
	public void initialisationInter()
	{
		//Déchargement du Panel Droit
		try
		{
			this.btnCSV.setDisable(false);	//permet d'exporter en csv
			this.panelSaisie.getChildren().remove(this.lst);	//on retire le comboBox pour mettre des fields
		}
		catch (Exception e) { e.printStackTrace(); }

		//Création
		this.nomInter = new TextField();
		nomInter.setPrefWidth(200);
		nomInter.setPrefHeight(28);
		this.nomInter.setPromptText("Entrez le nom de l'intervenant");

		this.prenomInter = new TextField();
		prenomInter.setPrefWidth(200);
		prenomInter.setPrefHeight(28);
		this.prenomInter.setPromptText("Entrez le prénom de l'intervenant");

		//Positionnement
		AnchorPane.setTopAnchor(nomInter, 30.0);
		AnchorPane.setLeftAnchor(nomInter, 20.0);

		AnchorPane.setTopAnchor(prenomInter, 62.0);
		AnchorPane.setLeftAnchor(prenomInter, 20.0);

		panelSaisie.getChildren().add(nomInter);
		panelSaisie.getChildren().add(prenomInter);
	}

	/*Panel Droit pour exporter les Semestres ou les modules*/
	public void initialisationModSem(String type)
	{
		ArrayList<String> lstElement = new ArrayList<String>(); //Stock les années ou les modules

		//Déchargement du Panel Droit
		try
		{
			this.btnCSV.setDisable(true);	//permet d'exporter en csv
			this.btnCSV.setSelected(false);
			//retire les textfield
			this.panelSaisie.getChildren().remove(this.nomInter);
			this.panelSaisie.getChildren().remove(this.prenomInter);
		}
		catch (Exception e) { e.printStackTrace(); }

		if(type.equals("module"))
			lstElement = Controleur.getNomModule();	//chargement des modules dans la liste
		else
			lstElement = Controleur.getNomSemestre();	//chargement des années dans la liste

		//Création
		this.lst = new ComboBox<String>();
		this.lst.getItems().addAll(FXCollections.observableArrayList(lstElement)); //place le contenu de la liste dans le cbBox
		this.lst.setPrefWidth(200);
		this.lst.setPrefHeight(28);

		//Positionnement
		AnchorPane.setTopAnchor(this.lst, 30.0);
		AnchorPane.setLeftAnchor(this.lst, 20.0);
		panelSaisie.getChildren().add(this.lst);
	}

	/*Test si ce qu'on a choisi est bien exportable*/
	@FXML
	public void verification(ActionEvent event)
	{
		if(panelSaisie.getChildren().contains(this.nomInter))
		{
			if(Controleur.intervenantExist(this.nomInter.getText(),this.prenomInter.getText()))
			{
				this.lblErreur.setText("");
				exportValid = true;
			}
			else
			{
				this.lblErreur.setText("nom ou prénom incorrect");
				exportValid = false;
			}
		}
		else
		{
			this.lblErreur.setText("");
			exportValid = true;
		}
	}

	/*--Exportation--*/
	@FXML
	public void exportation(ActionEvent event)
	{
		if(exportValid)
		{
			this.lblErreur.setText("Exportation");
			//GenererHTML.genererCSV("test");
			exportValid = true;
		}
		else
		{
			this.lblErreur.setText("Impossible d'export pas valider");
		}
	}
}
