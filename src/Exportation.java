import metier.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Exportation implements Initializable{

	@FXML
	private AnchorPane panelSaisie;
	@FXML
	private RadioButton btnCSV;

	@FXML
	private Label lblErreur;

	private TextField nomInter;
	private TextField prenomInter;
	private ComboBox<String> lst;

	private boolean exportValid = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initialisationInter();
	}

	@FXML
	public void afficheIntervenants(ActionEvent event){
		initialisationInter();
	}
	@FXML
	public void afficheModule(ActionEvent event){
		initialisationModSem("module");
	}
	@FXML
	public void afficheSemestre(ActionEvent event){
		initialisationModSem("semestre");
	}

	@FXML
	public void verification(ActionEvent event){
		if(panelSaisie.getChildren().contains(this.nomInter)){
			if(Controleur.intervenantExist(this.nomInter.getText(),this.prenomInter.getText())){
				this.lblErreur.setText("");
				exportValid = true;
			}
			else{
				this.lblErreur.setText("nom ou prénom incorrect");
				exportValid = false;
			}
		}
		else{
			this.lblErreur.setText("");
			exportValid = true;
		}
	}
	@FXML
	public void exportation(ActionEvent event){
		if(exportValid){
			this.lblErreur.setText("Exportation");
			exportValid = true;
		}
		else{
			this.lblErreur.setText("Impossible d'export pas valider");
		}
	}


	public void initialisationInter(){
		try {
			this.btnCSV.setDisable(false);
			this.panelSaisie.getChildren().remove(this.lst);
		} catch (Exception e) {}

		this.nomInter = new TextField();
		this.prenomInter = new TextField();
		nomInter.setPrefWidth(200);
		nomInter.setPrefHeight(28);
		prenomInter.setPrefWidth(200);
		prenomInter.setPrefHeight(28);
		AnchorPane.setTopAnchor(nomInter, 30.0);
		AnchorPane.setLeftAnchor(nomInter, 20.0);
		AnchorPane.setTopAnchor(prenomInter, 62.0);
		AnchorPane.setLeftAnchor(prenomInter, 20.0);
		this.nomInter.setPromptText("Entrez le nom de l'intervenant");
		this.prenomInter.setPromptText("Entrez le prénom de l'intervenant");
		panelSaisie.getChildren().add(nomInter);
		panelSaisie.getChildren().add(prenomInter);
	}

	public void initialisationModSem(String type){
		this.lst = new ComboBox<String>();
		ArrayList<String> lstElement = new ArrayList<String>();

		//on efface le textfield
		try {
			this.btnCSV.setDisable(true);
			this.btnCSV.setSelected(false);
			this.panelSaisie.getChildren().remove(this.nomInter);
			this.panelSaisie.getChildren().remove(this.prenomInter);
		} catch (Exception e) {}

		if(type.equals("module"))
			lstElement = Controleur.getNomModule();
		else
			lstElement = Controleur.getNomSemestre();


		this.lst.getItems().addAll(FXCollections.observableArrayList(lstElement));

		this.lst.setPrefWidth(200);
		this.lst.setPrefHeight(28);
		AnchorPane.setTopAnchor(this.lst, 30.0);
		AnchorPane.setLeftAnchor(this.lst, 20.0);
		panelSaisie.getChildren().add(this.lst);
	}

}
