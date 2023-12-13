import metier.*;
import java.util.ArrayList;
import java.sql.*;

public class Controleur{

	//attributs de Classe
	public static DB dataBase;

	/*--Methodes--*/

	/*--DB--*/

	//ouverture / fermeture
	public static void ouvrirConnection(){
		Controleur.dataBase = new DB();
	}
	public static void couperConnection(){
		Controleur.dataBase.couperConnection();
	}

	//requete
	public static ArrayList<Intervenant> getIntervenants(){
		return Controleur.dataBase.getIntervenants();
	}
	public static ResultSet getIntervenant_final(){
		return Controleur.dataBase.getIntervenant_final();
	}
	public static ArrayList<CategorieIntervenant> getCategorieInter(){
		return Controleur.dataBase.getCategorieInter();
	}
	public static ArrayList<CategorieHeure> getCategorieHeure(){
		return Controleur.dataBase.getCategorieHeure();
	}

	//insertion
	public static void insertIntervenant(Intervenant inter){
		Controleur.dataBase.insertIntervenant(inter);
	}
}