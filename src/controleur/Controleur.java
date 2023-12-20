package controleur;
import metier.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.*;

public class Controleur
{
	// Attributs de Classe
	private static DB dataBase;

	/*-- Methodes DB --*/
	// Méthode de connexion à la base de donnée
	public static void ouvrirConnection() { Controleur.dataBase = new DB(); }
	public static void couperConnection() { Controleur.dataBase.couperConnection(); }

	// Méthode requête à la base de donnée
	public static ArrayList<Intervenant> 	getIntervenants()						             { return Controleur.dataBase.getIntervenants(); }
	public static ResultSet 				getIntervenant_final() 					             { return Controleur.dataBase.getIntervenant_final(); }
	public static ResultSet 				getIntervenant_final(int codInter) 	              	 { return Controleur.dataBase.getIntervenant_final(codInter); }
	public static ResultSet 				getCategorieInter()						             { return Controleur.dataBase.getCategorieInter(); }
	public static ArrayList<CategorieHeure> getCategorieHeure()						             { return Controleur.dataBase.getCategorieHeure(); }
	public static ArrayList<Integer> 		getCodInter(String nomInter)			             { return Controleur.dataBase.getCodInter(nomInter); }
	public static ArrayList<Semestre> 		getSemestre(String nomSem) 				             { return Controleur.dataBase.getSemestre(nomSem); }
	public static ArrayList<TypeModule> 	getNomCategorieModules() 				             { return Controleur.dataBase.getNomCategorieModules(); }
	public static ArrayList<Modules> 		getListModule(String semestre)			             { return Controleur.dataBase.getListModule(semestre);}
	public static ResultSet 				getModuleParIntervenant(int codInter) 	             { return Controleur.dataBase.getModulParIntervenant(codInter);}
	public static ResultSet 				getModule(String codMod)				             { return Controleur.dataBase.getModule(codMod);}
	public static ResultSet                 getAffectation(String codMod)                        { return Controleur.dataBase.getAffectation(codMod);}
	public static ArrayList<String>         getNomModule()                                       { return Controleur.dataBase.getNomModule();}
	public static ArrayList<String>         getNomSemestre()                                     { return Controleur.dataBase.getNomSemestre();}
	public static boolean                   intervenantExist(String nomInter,String prenomInter) { return Controleur.dataBase.intervenantExist(nomInter,prenomInter);}
	public static HashMap<String, String>   getPreviModule(String codMod)                        { return Controleur.dataBase.getPreviModule(codMod);}
	public static ArrayList<String>         getAnnee()                                           { return Controleur.dataBase.getAnnee();}

	// Insertion
	public static void insertIntervenant(Intervenant inter) { Controleur.dataBase.insertIntervenant(inter); }
	public static void insertAffectation(Affectation affec) { Controleur.dataBase.insertAffectation(affec); }
	public static void insertModRessources(Modules nouveauModules) { Controleur.dataBase.insertModRessources(nouveauModules); }

	// Modification
	public static void updateInter(Intervenant nouveauInter)						               { Controleur.dataBase.updateInter(nouveauInter); }
	public static void updateSem(String textFieldId, String intitule, int newVal)                  { Controleur.dataBase.updateSem(textFieldId, intitule, newVal); }
	public static void updateMod(Modules nouveauModules, String nomTypMod, String codMod)          { Controleur.dataBase.updateMod(nouveauModules, nomTypMod, codMod); }
	public static void updateBool(boolean newVal, String codMod)                                   { Controleur.dataBase.updateBool(newVal, codMod); }

	// Suppression
	public static void supprInter(int codInter)               { Controleur.dataBase.supprInter(codInter); }
	public static void supprMod(Integer codMod, String annee) { Controleur.dataBase.supprMod(codMod, annee); }
}