package ihm;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import java.net.URL;

import controleur.Controleur;
import metier.GenererHTML;

public class Exportation implements Initializable
{
	//Attributs d'instance

	@FXML private AnchorPane panelSaisie;
	@FXML private Label lblErreur;
	@FXML private RadioButton btnCSV;
	@FXML private RadioButton btnHTML;
	@FXML private RadioButton btnIntervenants;
	@FXML private RadioButton btnModule;

	private TextField nomInter;
	private TextField prenomInter;
	private ComboBox<String> lst;

	private boolean exportValid = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) { initialisationInter(); }

	@FXML public void afficheIntervenants(ActionEvent event){ initialisationInter(); }
	@FXML public void afficheModule(ActionEvent event)      { initialisationModule();}
	@FXML public void afficheCSV(ActionEvent event)         { initialisationInter(); }

	// Panel Droit pour exporter les Intervenants

	public void initialisationInter()
	{
		//Déchargement du Panel Droit

		try
		{
			this.btnCSV.setDisable(false);
			this.panelSaisie.getChildren().remove(this.lst);
		}
		catch (Exception e) { e.printStackTrace(); }

		//Création

		this.nomInter = new TextField();
		nomInter.setPrefWidth(200);
		nomInter.setPrefHeight(28);

		if(this.btnCSV.isSelected())
			this.nomInter.setPromptText("Entrez le nom du fichier");
		else
		{
			this.nomInter.setPromptText("Entrez le nom de l'intervenant");

			this.prenomInter = new TextField();
			prenomInter.setPrefWidth(200);
			prenomInter.setPrefHeight(28);
			this.prenomInter.setPromptText("Entrez le prénom de l'intervenant");
		}

		//Activation dans le fxml

		//Positionnement

		AnchorPane.setTopAnchor(nomInter, 30.0);
		AnchorPane.setLeftAnchor(nomInter, 20.0);
		panelSaisie.getChildren().add(nomInter);

		if(this.panelSaisie.getChildren().contains(this.prenomInter))
			this.panelSaisie.getChildren().remove(this.prenomInter);

		if(!this.btnCSV.isSelected())
		{
			AnchorPane.setTopAnchor(prenomInter, 62.0);
			AnchorPane.setLeftAnchor(prenomInter, 20.0);
			panelSaisie.getChildren().add(prenomInter);
		}
	}

	// Panel Droit pour exporter les Semestres ou les modules

	public void initialisationModule()
	{
		ArrayList<String> lstElement = new ArrayList<String>(); // Stock les modules

		//Déchargement du Panel Droit
		try
		{
			this.btnCSV.setDisable(true); // Permet d'exporter en csv
			this.btnCSV.setSelected(false);
			this.btnHTML.setSelected(true);
			// Retire les textfield
			this.panelSaisie.getChildren().remove(this.nomInter);
			this.panelSaisie.getChildren().remove(this.prenomInter);
		}
		catch (Exception e) {}

		lstElement = Controleur.getNomModule(); // Chargement des modules dans la liste

		//Création

		this.lst = new ComboBox<String>();
		this.lst.getItems().addAll(FXCollections.observableArrayList(lstElement)); // Place le contenu de la liste dans le cbBox
		this.lst.setPrefWidth(200);
		this.lst.setPrefHeight(28);

		//Activation dans le fxml

		//Positionnement

		AnchorPane.setTopAnchor(this.lst, 30.0);
		AnchorPane.setLeftAnchor(this.lst, 20.0);
		panelSaisie.getChildren().add(this.lst);
	}

	//Test si ce qu'on a choisi est bien exportable
	@FXML
	public void verification(ActionEvent event)
	{
		//on part par défaut à true
		this.lblErreur.setText("");
		exportValid = true;

		//vérfie si c'est un intervenant et non un module
		if(panelSaisie.getChildren().contains(this.nomInter))
		{
			//test si c'est le btn HTML qui est sélectionner
			if(!this.btnCSV.isSelected())
			{
				if(!Controleur.intervenantExist(this.nomInter.getText(),this.prenomInter.getText()))
				{
					this.lblErreur.setText("nom ou prénom incorrect");	//affiche une erreur
					exportValid = false;
				}
			}
		}
		else
		{
			try { if(lst.getValue().equals(null)); }
			catch (Exception e)
			{
				exportValid = false;
				this.lblErreur.setText("Aucun module sélectionné");	//affiche une erreur
			}
		}
	}

	// Exportation
	@FXML
	public void exportation(ActionEvent event)
	{
		//Vérifie qu'on a bien validé
	
		if(exportValid)
		{
			try
			{
				// Test si on doit générer un csv
				if(this.btnCSV.isSelected())
					GenererHTML.genererCSV(this.nomInter.getText());
				else
				{
					// Test si on doit générer un intervenant html
					if(btnIntervenants.isSelected())
						GenererHTML.GenererIntervenant(this.nomInter.getText(), Controleur.getCodInter(this.nomInter.getText()).get(0));

					// Test si on doit générer un module html
					if(btnModule.isSelected())
					{
						String tab[] = this.lst.getValue().split(" "); //récupère le code module
						GenererHTML.GenererModule(tab[0], tab[0]);
					}
				}

				this.lblErreur.setText("Exportation"); // Prévient qu'on a exporté
			} catch (Exception e){this.lblErreur.setText("Erreur d'exportation");}

			exportValid = false; // Remet l'exportation à faux une fois exporté
		}
		else
			this.lblErreur.setText("Vous n'avez pas valider");
	}
}
