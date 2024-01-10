package controleur;

import metier.*;
import ihm.*;

import java.util.*;
import java.sql.*;

public class Controleur
{
	// Attributs de Classe

	private static DB dataBase;
	public static boolean connecter = true;
	public static Integer anneeActuelle = 0;

	/*-- Methodes DB --*/

	// Méthode de connexion à la base de donnée

	public static void ouvrirConnection() { Controleur.dataBase = new DB(); }
	public static void couperConnection() { Controleur.dataBase.couperConnection(); }

	// Méthode requête à la base de donnée

	public static ArrayList<Intervenant> 	getIntervenants()						             { return Controleur.dataBase.getIntervenants(); }
	public static ResultSet 				getIntervenant_final() 					             { return Controleur.dataBase.getIntervenant_final(); }
	public static ResultSet 				getCategorieInter()						             { return Controleur.dataBase.getCategorieInter(); }
	public static ArrayList<CategorieHeure> getCategorieHeure()						             { return Controleur.dataBase.getCategorieHeure(); }
	public static ArrayList<Integer> 		getCodInter(String nomInter)		                 { return Controleur.dataBase.getCodInter(nomInter); }
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
	public static Integer                   getCodCatInter(String nomCatInter)                   { return Controleur.dataBase.getCodCatInter(nomCatInter);}
	public static ResultSet                 getInformationsInter(int codInter)                   { return Controleur.dataBase.getInformationsInter(codInter);}
	public static ResultSet                 getNomTypeMod(String codMod)                         { return Controleur.dataBase.getNomTypeMod(codMod);}
	public static int                       gethMin(int numCat)                                  { return Controleur.dataBase.gethMin(numCat);}
	public static int                       gethMax(int numCat)                                  { return Controleur.dataBase.gethMax(numCat);}
	public static ArrayList<String>         getNomCatInter()                                     { return Controleur.dataBase.getNomCatInter();}
	public static ResultSet                 getIntervenant_complet()                             { return Controleur.dataBase.getIntervenant_complet(); }

	// Insertion

	public static void insertIntervenant(Intervenant inter)          { Controleur.dataBase.insertIntervenant(inter); }
	public static void insertAffectationRessource(Affectation affec) { Controleur.dataBase.insertAffectationRessource(affec); }
	public static void insertAffectationAutre(Affectation affec)     { Controleur.dataBase.insertAffectationAutre(affec); }
	public static void insertModRessources(Modules nouveauModules)   { Controleur.dataBase.insertModRessources(nouveauModules); }
	public static void insertModSAE(Modules nouveauModules)          { Controleur.dataBase.insertModSAE(nouveauModules); }
	public static void insertModStage(Modules nouveauModules)        { Controleur.dataBase.insertModStage(nouveauModules); }
	public static void insertModPPP(Modules nouveauModules)          { Controleur.dataBase.insertModPPP(nouveauModules); }
	public static void insertCategorieIntervenant(CategorieIntervenant categorie) { Controleur.dataBase.insertCategorieIntervenant(categorie); }

	// Modification

	public static void updateInter(Intervenant nouveauInter, Integer nomCat)						   { Controleur.dataBase.updateInter(nouveauInter, nomCat); }
	public static void updateSem(String textFieldId, String intitule, int newVal)                  { Controleur.dataBase.updateSem(textFieldId, intitule, newVal); }
	public static void updateMod(Modules nouveauModules, String nomTypMod, String codMod)          { Controleur.dataBase.updateMod(nouveauModules, nomTypMod, codMod); }
	public static void updateBool(boolean newVal, String codMod)                                   { Controleur.dataBase.updateBool(newVal, codMod); }
	public static void updateAffectation(Affectation affectation) 								   { Controleur.dataBase.updateAffectation(affectation); }
	public static void updateCategorieIntervenant(CategorieIntervenant categorie, String nomCat)   { Controleur.dataBase.updateCategorieIntervenant(categorie, nomCat); }

	// Suppression

	public static void supprInter(Integer codInter, Integer annee)                                           { Controleur.dataBase.supprInter(codInter, annee); }
	public static void supprMod(String codMod, Integer annee)                                                { Controleur.dataBase.supprMod(codMod, annee); }
	public static void supprAffectation(String codMod, Integer annee, Integer codInter, Integer codCatHeure) { Controleur.dataBase.supprAffectation(codMod, annee, codInter, codCatHeure); }
	public static void supprimerAnnee(Integer annee)                                                             { Controleur.dataBase.supprimerAnnee(annee); }
	public static void supprimerCategorieIntervenant(String nomCat)                            { Controleur.dataBase.supprCategorieIntervenant(nomCat); }

	// AUTRE

	public static void clonage(int annee_source,int annee_destination) { Controleur.dataBase.clonage(annee_source, annee_destination); }

	//Setteur

	public static void setAnneeActuelle() { anneeActuelle = Controleur.dataBase.anneeActuelle(); }

	// Méthode de chargement IHM

	public static void chargement(double pourcentage) { Chargement.charger(pourcentage); }
}