import metier.*;
import metier.Affectation;

import java.util.ArrayList;
import java.sql.*;

public class Controleur
{
	// Attributs de Classe
	public static DB dataBase;

	/*--Methodes--*/
	/*--DB--*/
	// Méthode de connexion à la base de donnée
	public static void ouvrirConnection() { Controleur.dataBase = new DB(); }
	public static void couperConnection() { Controleur.dataBase.couperConnection(); }

	// Méthode requête à la base de donnée
	public static ArrayList<Intervenant> 	getIntervenants()						{ return Controleur.dataBase.getIntervenants(); }
	public static ResultSet 				getIntervenant_final() 					{ return Controleur.dataBase.getIntervenant_final(); }
	public static ResultSet 				getIntervenant_final(int codInter) 		{ return Controleur.dataBase.getIntervenant_final(codInter); }
	public static ResultSet 				getCategorieInter()						{ return Controleur.dataBase.getCategorieInter(); }
	public static ArrayList<CategorieHeure> getCategorieHeure()						{ return Controleur.dataBase.getCategorieHeure(); }
	public static ArrayList<Integer> 		getCodInter(String nomInter)			{ return Controleur.dataBase.getCodInter(nomInter); }
	public static ArrayList<Semestre> 		getSemestre(String nomSem) 				{ return Controleur.dataBase.getSemestre(nomSem); }
	public static ArrayList<TypeModule> 	getNomCategorieModules() 				{ return Controleur.dataBase.getNomCategorieModules(); }
	public static ArrayList<Modules> 		getListModule(String semestre)			{ return Controleur.dataBase.getListModule(semestre);}
	public static ResultSet 				getModuleParIntervenant(int codInter) 	{ return Controleur.dataBase.getModulParIntervenant(codInter);}
	public static ResultSet 				getModule(String codMod)				{ return Controleur.dataBase.getModule(codMod);}
	public static ResultSet                 getAffectationRessource(String codMod)  { return Controleur.dataBase.getAffectationRessource(codMod);}

	// Insertion

	// Insertion
	public static void insertIntervenant(Intervenant inter) { Controleur.dataBase.insertIntervenant(inter); }
	public static void insertAffectation(Affectation affec) { Controleur.dataBase.insertAffectation(affec); }

	// Modification
	public static void updateInter(Intervenant nouveauInter)						{ Controleur.dataBase.updateInter(nouveauInter); }
	public static void updateSem(String textFieldId, String intitule, int newVal)   { Controleur.dataBase.updateSem(textFieldId, intitule, newVal); }
	//public static void updateMod(Modules nouveauModules, String codModTyp)          { Controleur.dataBase.updateMod(nouveauModules, codModTyp); }

	// Suppression
	public static void supprInter(int codInter){ Controleur.dataBase.supprInter(codInter); }
}