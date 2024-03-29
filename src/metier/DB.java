package metier;

import controleur.Controleur;
import ihm.Intervenants;

import java.sql.*;
import java.util.*;
import java.io.*;

public class DB
{
	// Attribut de classe

	private static Connection connec;

	// Attribut requête Select

	private PreparedStatement psSelectIntervenants;
	private PreparedStatement psSelectSemestre;
	private PreparedStatement psSelectIntervenant_final;
	private PreparedStatement psSelectCodInter;
	private PreparedStatement psSelectCategorieIntervenant;
	private PreparedStatement psSelectCategorieHeure;
	private PreparedStatement psSelectListeModule;
	private PreparedStatement psSelectListeSemestre;
	private PreparedStatement psSelectTypeModules;
	private PreparedStatement psSelectModulePar1Intervenant;
	private PreparedStatement psSelectPreviModuleRessource;
	private PreparedStatement psSelectAffectModuleRessource;
	private PreparedStatement psSelectModule;
	private PreparedStatement psSelectNomModule;
	private PreparedStatement psSelectNomSemestre;
	private PreparedStatement psSelectNomInter;
	private PreparedStatement psSelectAnnee;
	private PreparedStatement psSelectCodCatInter;
	private PreparedStatement psSelectInformationsInter;
	private PreparedStatement psSelectNomTypeMod;
	private PreparedStatement psSelecthMin;
	private PreparedStatement psSelecthMax;
	private PreparedStatement psSelectNomCatInter;
	private PreparedStatement psSelectIntervenant_complet;
	private PreparedStatement psSelectCategorieHeureSpe;

	// Attribut requête Insert

	private PreparedStatement psInstertIntervenant;
	private PreparedStatement psInstertAffectationRessource;
	private PreparedStatement psInstertAffectationAutre;
	private PreparedStatement psInsertModRessources;
	private PreparedStatement psInsertModSAE;
	private PreparedStatement psInsertModStage;
	private PreparedStatement psInsertModPPP;
	private PreparedStatement psInsertCategorieIntervenant;

	// Attribut requête Delete

	private PreparedStatement psDeleteInter;
	private PreparedStatement psDeleteMod;
	private PreparedStatement psDeleteAffectation;
	private PreparedStatement psDeleteCategorieIntervenant;

	// Attribut requête Update

	private PreparedStatement psUpdateInter;
	private PreparedStatement psUpdateBool;
	private PreparedStatement psUpdateModRessources;
	private PreparedStatement psUpdateModSAE;
	private PreparedStatement psUpdateModStage;
	private PreparedStatement psUpdateModPPP;
	private PreparedStatement psUpdateAffectation;
	private PreparedStatement psUpdateCategorieIntervenant;

	// Autre

	private PreparedStatement psClone;
	private PreparedStatement psSupprAnnee;
	private PreparedStatement psAnneeActuelle;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	// Gère la connexion à la base de donnée et prépare les requêtes

	public DB()
	{
		try
		{
			FileReader fr = new FileReader("../config/login.data");
			BufferedReader br = new BufferedReader(fr);
			String login = br.readLine();
			String mdp = br.readLine();
			br.close();
			fr.close();

			// Ouverture de la connexion à la base de donnée

			DB.connec = DriverManager.getConnection("jdbc:postgresql://localhost/" + login, login, mdp);

			// Préparation des Requêtes
			this.psSelectIntervenants          = DB.connec.prepareStatement("SELECT * FROM Intervenant");
			this.psSelectSemestre              = DB.connec.prepareStatement("SELECT * FROM Semestre");
			this.psSelectIntervenant_final     = DB.connec.prepareStatement("SELECT * FROM intervenant_final WHERE annee = ?");
			this.psSelectCategorieIntervenant  = DB.connec.prepareStatement("SELECT * FROM CategorieIntervenant");
			this.psSelectCodInter              = DB.connec.prepareStatement("SELECT codInter FROM Intervenant WHERE nom = ? AND annee = ?");
			this.psSelectCategorieHeure        = DB.connec.prepareStatement("SELECT * FROM CategorieHeure");
			this.psSelectListeSemestre         = DB.connec.prepareStatement("SELECT nbGrpTD, nbGrpTP, nbEtd, nbSemaines FROM Semestre WHERE codSem = ?");
			this.psSelectListeModule           = DB.connec.prepareStatement("SELECT * FROM liste_module WHERE codSem = ? AND annee = ?");
			this.psSelectTypeModules           = DB.connec.prepareStatement("SELECT * FROM TypeModule");
			this.psSelectModulePar1Intervenant = DB.connec.prepareStatement("SELECT * FROM affectation_final WHERE codInter = ?");
			this.psSelectModule                = DB.connec.prepareStatement("SELECT codmod,codsem,liblong,libcourt FROM module WHERE codMod = ?");
			this.psSelectPreviModuleRessource  = DB.connec.prepareStatement("SELECT * FROM module_final WHERE codMod = ?");
			this.psSelectAffectModuleRessource = DB.connec.prepareStatement("SELECT * FROM affectation_final WHERE codMod = ? AND annee = ?;");
			this.psSelectNomModule             = DB.connec.prepareStatement("SELECT codMod, libCourt FROM Module");
			this.psSelectNomSemestre           = DB.connec.prepareStatement("SELECT codSem FROM SEMESTRE");
			this.psSelectNomInter              = DB.connec.prepareStatement("SELECT * FROM Intervenant WHERE nom = ? AND prenom = ?");
			this.psSelectAnnee                 = DB.connec.prepareStatement("SELECT annee FROM Annee");
			this.psClone                       = DB.connec.prepareStatement("SELECT clonage(?,?)");
			this.psSupprAnnee                  = DB.connec.prepareStatement("SELECT supprimer_annee(?)");
			this.psAnneeActuelle               = DB.connec.prepareStatement("SELECT MAX(annee) FROM annee");
			this.psSelectCodCatInter           = DB.connec.prepareStatement("SELECT codCatInter FROM CategorieIntervenant WHERE nomcat = ?");
			this.psSelectInformationsInter     = DB.connec.prepareStatement("SELECT * FROM Intervenant WHERE codInter = ?");
			this.psSelectNomTypeMod            = DB.connec.prepareStatement("Select nomTypMod From TypeModule t Join Module m on t.codTypMod = m.codTypMod Where codMod = ?");
			this.psSelecthMin                  = DB.connec.prepareStatement("SELECT service FROM CategorieIntervenant WHERE codCatInter = ?");
			this.psSelecthMax                  = DB.connec.prepareStatement("SELECT maxHeure FROM CategorieIntervenant WHERE codCatInter = ?");
			this.psSelectNomCatInter           = DB.connec.prepareStatement("SELECT nomCat FROM CategorieIntervenant");
			this.psSelectIntervenant_complet   = DB.connec.prepareStatement("SELECT *      FROM intervenant_complet WHERE annee = ?");
			this.psSelectCategorieHeureSpe     = DB.connec.prepareStatement("SELECT codCatHeure FROM CategorieHeure WHERE nomCatHeure = ?");

			// Préparation des Insertions
			this.psInstertIntervenant           = DB.connec.prepareStatement("INSERT INTO Intervenant (nom, prenom, codCatInter, hServ, maxHeure, annee)  VALUES(?,?,?,?,?,?)");
			this.psInstertAffectationRessource  = DB.connec.prepareStatement("INSERT INTO Affectation (codMod, codInter, codCatHeure, commentaire, nbSem, nbGrp, annee) VALUES(?,?,?,?,?,?,?)");
			this.psInstertAffectationAutre      = DB.connec.prepareStatement("INSERT INTO Affectation (codMod, codInter, codCatHeure, commentaire, nbH, annee) VALUES(?,?,?,?,?,?)");
			this.psInsertModRessources          = DB.connec.prepareStatement("INSERT INTO Module (codMod, codSem, codTypMod, libLong, libCourt, valide, nbHPnCM, nbHPnTD, nbHPnTP, nbSemaineTD, nbSemaineTP, nbSemaineCM, nbHParSemaineTD, nbHParSemaineTP, nbHParSemaineCM, hPonctuelle, annee) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			this.psInsertModSAE                 = DB.connec.prepareStatement("INSERT INTO Module (codMod, codTypMod, codSem, libLong, libCourt, valide, nbHPnSaeParSemestre, nbHPnTutParSemestre, nbHSaeParSemestre, nbHTutParSemestre, annee) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			this.psInsertModStage               = DB.connec.prepareStatement("INSERT INTO Module (codMod, codSem, codTypMod, libLong, libCourt, valide, nbHREH, nbHTut, nbHPnREH, nbHPnTut, annee) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			this.psInsertModPPP                 = DB.connec.prepareStatement("INSERT INTO Module (codMod, codSem, codTypMod, libLong, libCourt, valide, nbHPnCM, nbHPnTD, nbHPnTP, nbHParSemaineTD, nbHParSemaineTP, nbHParSemaineCM, hPonctuelle, nbHPnHTut, nbHTut, annee) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			this.psInsertCategorieIntervenant   = DB.connec.prepareStatement("INSERT INTO CategorieIntervenant(nomCat,service,maxHeure,ratioTPCatInterNum,ratioTPCatInterDen) VALUES (?,?,?,?,?);" );

			// Préparation des Updates
			this.psUpdateInter         = DB.connec.prepareStatement("UPDATE Intervenant SET nom = ?, prenom = ?, hServ = ?, maxHeure = ?, codCatInter = ? WHERE codInter = ? AND annee = ?;");
			this.psUpdateBool          = DB.connec.prepareStatement("UPDATE Module SET valide = ? WHERE codMod  = ?;");
			this.psUpdateModRessources = DB.connec.prepareStatement("UPDATE Module SET codMod = ?, libLong = ?, libCourt = ?, valide = ?, nbHPnCM = ?, nbHPnTD = ?, nbHPnTP = ?, nbSemaineTD = ?, nbSemaineTP = ?, nbSemaineCM = ?, nbHParSemaineTD = ?, nbHParSemaineTP = ?, nbHParSemaineCM = ?, hPonctuelle = ?  WHERE codMod = ? AND annee = ?");
			this.psUpdateModSAE 	   = DB.connec.prepareStatement("UPDATE Module SET codMod = ?, libLong = ?, libCourt = ?, valide = ?, nbHPnSaeParSemestre = ?, nbHPnTutParSemestre = ?, nbHSaeParSemestre = ?, nbHTutParSemestre = ? WHERE codMod = ? AND annee = ?");
			this.psUpdateModStage      = DB.connec.prepareStatement("UPDATE Module SET codMod = ?, libLong = ?, libCourt = ?, valide = ?, nbHPnREH = ?, nbHPnTut = ?, nbHREH = ?, nbHTut = ? WHERE codMod = ? AND annee = ?");
			this.psUpdateModPPP        = DB.connec.prepareStatement("UPDATE Module SET codMod = ?, libLong = ?, libCourt = ?, valide = ?, nbHPnCM = ?, nbHPnTD = ?, nbHPnTP = ?, nbHParSemaineTD = ?, nbHParSemaineTP = ?, nbHParSemaineCM = ?, hPonctuelle = ?, nbHPnTut = ?, nbHTut = ?, nbHPnHTut = ? WHERE codMod = ? AND annee = ?");
			this.psUpdateAffectation   = DB.connec.prepareStatement("UPDATE Affectation SET codCatHeure = ?, commentaire = ?, nbSem = ?, nbGrp = ?, nbH = ? WHERE codInter = ? AND annee = ? AND codMod = ?");
			this.psUpdateCategorieIntervenant = DB.connec.prepareStatement("UPDATE CategorieIntervenant SET nomCat = ?, service = ?, maxHeure = ?, ratioTPCatInterNum = ?, ratioTPCatInterDen = ? WHERE nomCat = ?");

			// Preparation des Deletes
			this.psDeleteInter = DB.connec.prepareStatement("DELETE FROM Intervenant WHERE codInter = ? AND annee = ?");
			this.psDeleteMod   = DB.connec.prepareStatement("DELETE FROM Module WHERE codMod = ? AND annee = ?");
			this.psDeleteAffectation = DB.connec.prepareStatement("DELETE FROM Affectation WHERE codMod = ? AND annee = ? AND codInter = ? AND codCatHeure = ?");
			this.psDeleteCategorieIntervenant = DB.connec.prepareStatement("DELETE FROM CategorieIntervenant WHERE nomCat = ?");
		}
		catch (IOException e) { e.printStackTrace(); }
		catch (SQLException e) { Controleur.connecter = false; }
	}

	// Stoppe la connexion à la base de donnée
	public void couperConnection()
	{
		try { DB.connec.close(); }
		catch (Exception e)	{ e.printStackTrace(); }
	}

	// Méthode requête à la base de donnée

	// Récupère tous les intervenants

	public ArrayList<Intervenant> getIntervenants()
	{
		ArrayList<Intervenant> lstInterv = new ArrayList<Intervenant>();

		try
		{
			ResultSet rs = this.psSelectIntervenants.executeQuery();
			while(rs.next())
			{
				String  nom         = rs.getString("nom");
				String  prenom      = rs.getString("prenom");
				Integer codCatInter = rs.getInt("codCatInter");
				Integer hServ       = rs.getInt("hServ");
				Integer maxHeure    = rs.getInt("maxHeure");
				Integer annee       = rs.getInt("annee");

				lstInterv.add(new Intervenant(nom, prenom, codCatInter, hServ, maxHeure, annee));
			}
		}
		catch (Exception e) { e.printStackTrace(); }

		return lstInterv;
	}

	//psSelectNomTypeMod

	public ResultSet getNomTypeMod(String codMod)
	{
		ResultSet rs = null;
		try
		{
			this.psSelectNomTypeMod.setString(1, codMod);
			rs = this.psSelectNomTypeMod.executeQuery();
		}
		catch (Exception e) { e.printStackTrace(); }

		return rs;
	}

	public Integer getCodCatInter(String nomCatInter)
	{
		int cod = 5;
		try
		{
			this.psSelectCodCatInter.setString(1, nomCatInter);

			ResultSet rs = this.psSelectCodCatInter.executeQuery();
			if (rs.next())
				cod = rs.getInt("codCatInter");

		}
		catch (Exception e) { e.printStackTrace(); }

		return cod;
	}

	public ResultSet getSemestre()
	{
		ResultSet rs = null;
		try
		{
			rs = this.psSelectSemestre.executeQuery();
		}
		catch (Exception e) { e.printStackTrace(); }

		return rs;
	}

	// Récupère le nom d'un module les categories d'heure

	public ArrayList<TypeModule> getNomCategorieModules()
	{
		ArrayList<TypeModule> lst = new ArrayList<TypeModule>();

		try
		{
			ResultSet rs = this.psSelectTypeModules .executeQuery();
			while(rs.next())
			{
				Integer codTypMod = rs.getInt("codTypMod");
				String nomTypMod = rs.getString("nomTypMod");
				lst.add(new TypeModule(codTypMod,nomTypMod));
			}
		}
		catch (Exception e) { e.printStackTrace(); }

		return lst;
	}

	// Récupère les intervenants finaux

	public ResultSet getIntervenant_final()
	{
		ResultSet resultSet = null;

		try
		{
			this.psSelectIntervenant_final.setInt(1, Controleur.anneeActuelle);
			resultSet = this.psSelectIntervenant_final.executeQuery();
		}
		catch (Exception e) { e.printStackTrace(); }

		return resultSet;
	}

	public ResultSet getModulParIntervenant(int codInter)
	{
		ResultSet resultSet = null;

		try
		{
			this.psSelectModulePar1Intervenant.setInt(1, codInter);
			resultSet = this.psSelectModulePar1Intervenant.executeQuery();
		}
		catch (Exception e) { e.printStackTrace(); }

		return resultSet;
	}

	// Récupère toutes les categories d'intervenants

	public ResultSet getCategorieInter()
	{
		ResultSet resultSet = null;

		try { resultSet = this.psSelectCategorieIntervenant.executeQuery(); }
		catch (Exception e) { e.printStackTrace(); }

		return resultSet;
	}

	public ResultSet getIntervenant_complet()
	{
		ResultSet resultSet = null;

		try {
			this.psSelectIntervenant_complet.setInt(1, Controleur.anneeActuelle);
			resultSet = this.psSelectIntervenant_complet.executeQuery();
			}
		catch (Exception e) { e.printStackTrace(); }

		return resultSet;
	}

	// Récupère toutes les categories d'heure
	public ArrayList<CategorieHeure> getCategorieHeure()
	{
		ArrayList<CategorieHeure> lst = new ArrayList<CategorieHeure>();

		try
		{
			ResultSet rs = this.psSelectCategorieHeure.executeQuery();

			while(rs.next())
			{
				int    codCatHeure = rs.getInt("codCatHeure");
				String nomCatHeure = rs.getString("nomCatHeure");
				int    coeffNum    = rs.getInt("coeffNum");
				int    coeffDen    = rs.getInt("coeffDen");

				lst.add(new CategorieHeure(codCatHeure,nomCatHeure,coeffNum,coeffDen));
			}
		}
		catch (Exception e) { e.printStackTrace(); }

		return lst;
	}

	public ArrayList<Semestre> getSemestre(String nomSem)
	{
		ArrayList<Semestre> lstSemestre = new ArrayList<Semestre>();

		try
		{
			this.psSelectListeSemestre.setString(1,nomSem);
			ResultSet rs = this.psSelectListeSemestre.executeQuery();

			while(rs.next())
			{
				int nbGrpTD = rs.getInt("nbGrpTD");
				int nbGrpTP = rs.getInt("nbGrpTP");
				int nbEtd = rs.getInt("nbEtd");
				int nbSemaine = rs.getInt("nbSemaines");

				lstSemestre.add(new Semestre(nomSem,nbGrpTD,nbGrpTP,nbEtd,nbSemaine));
			}
		}
		catch (Exception e) { e.printStackTrace(); }

		return lstSemestre;
	}


	public ArrayList<Modules> getListModule(String semestre)
	{
		ArrayList<Modules> lstModule = new ArrayList<Modules>();

		try
		{
			this.psSelectListeModule.setString(1,semestre);
			this.psSelectListeModule.setInt(2,Controleur.anneeActuelle);
			ResultSet rs = this.psSelectListeModule.executeQuery();

			while(rs.next())
			{
				String codSem = rs.getString("codSem");
				String codMod = rs.getString("codMod");
				String libLong = rs.getString("libLong");
				String hAP = rs.getString("hAP");
				boolean valide = rs.getBoolean("valide");

				lstModule.add(new Modules(codSem,codMod,libLong,hAP,valide));
			}
		}
		catch (Exception e){ e.printStackTrace(); }

		return lstModule;
	}

	public ResultSet getModule(String codMod)
	{
		ResultSet resultSet = null;

		try
		{
			this.psSelectModule.setString(1, codMod);
			resultSet = this.psSelectModule.executeQuery();
		}
		catch (Exception e) { e.printStackTrace(); }

		return resultSet;
	}

	public HashMap<String, String> getPreviModule(String codMod)
	{
		HashMap<String, String> lstVal = new HashMap<String, String>();
		try
		{
			this.psSelectPreviModuleRessource.setString(1, codMod);
			ResultSet rs = this.psSelectPreviModuleRessource.executeQuery();
			rs.next();

			for (int cpt = 1; cpt <= rs.getMetaData().getColumnCount(); cpt++)
				lstVal.put(rs.getMetaData().getColumnName(cpt), null);

			do
			{
				for (String col : lstVal.keySet())
					lstVal.put(col,rs.getString(col));
			} while (rs.next());

			List<String> keysToRemove = new ArrayList<String>();
			for (String col : lstVal.keySet())
				if (lstVal.get(col) == null)
					keysToRemove.add(col);

			for (String key : keysToRemove)
				lstVal.remove(key);

		}
		catch (SQLException e) { return null; }

		return lstVal;
	}

	public ArrayList<String> getNomCatInter()
	{
		ArrayList<String> lstNom = new ArrayList<String>();
		try
		{
			ResultSet rs = this.psSelectNomCatInter.executeQuery();
			while (rs.next())
				lstNom.add(rs.getString("nomCat"));

		} catch (SQLException e) { Intervenants.notifications("Il n'y a plus de categorie d'intervenants"); }

		return lstNom;
	}

	public ArrayList<String> getNomModule()
	{
		ArrayList<String> lstModule = new ArrayList<String>();
		try
		{
			ResultSet rs = this.psSelectNomModule.executeQuery();
			while (rs.next())
				lstModule.add(rs.getString("codMod") + " " + rs.getString("libCourt"));
		}
		catch (SQLException e) {e.printStackTrace();}

		return lstModule;
	}

	public ArrayList<String> getNomSemestre()
	{
		ArrayList<String> lstSem = new ArrayList<String>();
		try
		{
			ResultSet rs = this.psSelectNomSemestre.executeQuery();
			while (rs.next())
				lstSem.add(rs.getString("codSem"));
		}
		catch (SQLException e) {e.printStackTrace();}

		return lstSem;
	}

	public int gethMin(int numCat)
	{
		try
		{
			this.psSelecthMin.setInt(1,numCat);
			ResultSet rs = psSelecthMin.executeQuery();
			if(rs.next())
				return rs.getInt("service");
		}
		catch (SQLException e) { e.printStackTrace(); }
		return 0;
	}

	public int gethMax(int numCat)
	{
		try
		{
			this.psSelecthMax.setInt(1,numCat);
			ResultSet rs = psSelecthMax.executeQuery();
			if(rs.next())
				return rs.getInt("maxHeure");
		}
		catch (SQLException e) {e.printStackTrace();}
		return 0;
	}

	public boolean intervenantExist(String nomInter,String prenomInter)
	{
		try
		{
			this.psSelectNomInter.setString(1, nomInter);
			this.psSelectNomInter.setString(2, prenomInter);
			ResultSet rs = psSelectNomInter.executeQuery();
			if(rs.next())
				return true;
		}
		catch (SQLException e) {e.printStackTrace();}

		return false;
	}

	// Récupère les affectations d'un module

	public ResultSet getAffectation(String codMod)
	{
		ResultSet rs = null;
		try
		{
			this.psSelectAffectModuleRessource.setString(1, codMod);
			this.psSelectAffectModuleRessource.setInt(2, Controleur.anneeActuelle);
			rs = this.psSelectAffectModuleRessource.executeQuery();
		}
		catch (Exception e) { e.printStackTrace(); }

		return rs;
	}


	// Récupère le code d'un intervenant

	public ArrayList<Integer> getCodInter(String nomInter)
	{
		ArrayList<Integer> lstCodInter = new ArrayList<Integer>();
		try
		{
			this.psSelectCodInter.setString(1, nomInter);
			this.psSelectCodInter.setInt(2, Controleur.anneeActuelle);
			ResultSet rs = this.psSelectCodInter.executeQuery();

			while (rs.next())
				lstCodInter.add(rs.getInt("codInter"));
		}
		catch (SQLException e) { e.printStackTrace(); }

		return lstCodInter;
	}

	public ArrayList<String> getAnnee()
	{
		ArrayList<String> lstAnnee = new ArrayList<String>();

		try
		{
			ResultSet rs = psSelectAnnee.executeQuery();
			while (rs.next())
				lstAnnee.add(rs.getString("annee"));
		}
		catch (SQLException e) {e.printStackTrace();}

		return lstAnnee;
	}

	public ResultSet getInformationsInter(int codInter)
	{
		ResultSet rs = null;
		try
		{
			this.psSelectInformationsInter.setInt(1, codInter);
			rs = this.psSelectInformationsInter.executeQuery();
		}
		catch (Exception e) { e.printStackTrace(); }

		return rs;
	}

	// Méthode d'insertion

	public void insertIntervenant(Intervenant inter)
	{
		try
		{
			this.psInstertIntervenant.setString(1,inter.getNom());
			this.psInstertIntervenant.setString(2,inter.getPrenom());
			this.psInstertIntervenant.setInt(3,inter.getCodCatInter());
			this.psInstertIntervenant.setInt(4,inter.gethServ());
			this.psInstertIntervenant.setInt(5,inter.getMaxHeure());
			this.psInstertIntervenant.setInt(6,inter.getAnnee());

			this.psInstertIntervenant.executeUpdate();
			Intervenants.notifications("Intervenant ajouté");
		}
		catch (SQLException e)
		{
			Intervenants.notifications("Impossible l'intervenant " + inter.getPrenom() + " " + inter.getNom() + " existe déjà !");
		}
	}

	public void insertAffectationRessource(Affectation affec)
	{
		try
		{
			this.psInstertAffectationRessource.setString(1,affec.getCodMod());
			this.psInstertAffectationRessource.setInt(2,affec.getCodInter());
			this.psInstertAffectationRessource.setInt(3,affec.getCodCatHeure());
			this.psInstertAffectationRessource.setString(4,affec.getCommentaire());
			this.psInstertAffectationRessource.setInt(5,affec.getNbSem());
			this.psInstertAffectationRessource.setInt(6,affec.getNbGrp());
			this.psInstertAffectationRessource.setInt(7,affec.getAnnee());
			this.psInstertAffectationRessource.executeUpdate();
		}
		catch (SQLException e) { e.printStackTrace(); }
	}

	public void insertAffectationAutre(Affectation affec)
	{
		try
		{
			this.psInstertAffectationAutre.setString(1,affec.getCodMod());
			this.psInstertAffectationAutre.setInt(2,affec.getCodInter());
			this.psInstertAffectationAutre.setInt(3,affec.getCodCatHeure());
			this.psInstertAffectationAutre.setString(4,affec.getCommentaire());
			this.psInstertAffectationAutre.setInt(5,affec.getNbH());
			this.psInstertAffectationAutre.setInt(6,affec.getAnnee());
			this.psInstertAffectationAutre.executeUpdate();
		}
		catch (SQLException e) {e.printStackTrace();}
	}

	public void insertModRessources(Modules nouveauModules)
	{
		try
		{
			this.psInsertModRessources.setString(1,nouveauModules.getCodMod());
			this.psInsertModRessources.setString(2,nouveauModules.getCodSem());
			this.psInsertModRessources.setInt(3,nouveauModules.getCodTypMod());
			this.psInsertModRessources.setString(4,nouveauModules.getLibLong());
			this.psInsertModRessources.setString(5,nouveauModules.getLibCourt());
			this.psInsertModRessources.setBoolean(6,nouveauModules.getValid());
			this.psInsertModRessources.setInt(7,nouveauModules.getNbHPnCM());
			this.psInsertModRessources.setInt(8,nouveauModules.getNbHPnTD());
			this.psInsertModRessources.setInt(9,nouveauModules.getNbHPnTP());
			this.psInsertModRessources.setInt(10,nouveauModules.getNbSemaineTD());
			this.psInsertModRessources.setInt(11,nouveauModules.getNbSemaineTP());
			this.psInsertModRessources.setInt(12,nouveauModules.getNbSemaineCM());
			this.psInsertModRessources.setInt(13,nouveauModules.getNbHParSemaineTD());
			this.psInsertModRessources.setInt(14,nouveauModules.getNbHParSemaineTP());
			this.psInsertModRessources.setInt(15,nouveauModules.getNbHParSemaineCM());
			this.psInsertModRessources.setInt(16,nouveauModules.getHPonctuelle());
			this.psInsertModRessources.setInt(17,nouveauModules.getAnnee());
			this.psInsertModRessources.executeUpdate();
		}
		catch (SQLException e) { Intervenants.notifications("Erreur de saisie dans le module ressource"); }
	}

	public void insertModSAE(Modules nouveauModules)
	{
		try
		{
			this.psInsertModSAE.setString(1,nouveauModules.getCodMod());
			this.psInsertModSAE.setInt(2,nouveauModules.getCodTypMod());
			this.psInsertModSAE.setString(3,nouveauModules.getCodSem());
			this.psInsertModSAE.setString(4,nouveauModules.getLibLong());
			this.psInsertModSAE.setString(5,nouveauModules.getLibCourt());
			this.psInsertModSAE.setBoolean(6,nouveauModules.getValid());
			this.psInsertModSAE.setInt(7,nouveauModules.getNbHPnSaeParSemestre());
			this.psInsertModSAE.setInt(8,nouveauModules.getNbHPnTutParSemestre());
			this.psInsertModSAE.setInt(9,nouveauModules.getNbHSaeParSemestre());
			this.psInsertModSAE.setInt(10,nouveauModules.getNbHTutParSemestre());
			this.psInsertModSAE.setInt(11,nouveauModules.getAnnee());
			this.psInsertModSAE.executeUpdate();
		}
		catch (SQLException e) { Intervenants.notifications("Erreur de saisie dans le module"); }
	}

	public void insertModStage(Modules nouveauModules)
	{
		try
		{
			this.psInsertModStage.setString(1,nouveauModules.getCodMod());
			this.psInsertModStage.setString(2,nouveauModules.getCodSem());
			this.psInsertModStage.setInt(3,nouveauModules.getCodTypMod());
			this.psInsertModStage.setString(4,nouveauModules.getLibLong());
			this.psInsertModStage.setString(5,nouveauModules.getLibCourt());
			this.psInsertModStage.setBoolean(6,nouveauModules.getValid());
			this.psInsertModStage.setInt(7,nouveauModules.getNbHREH());
			this.psInsertModStage.setInt(8,nouveauModules.getNbHTut());
			this.psInsertModStage.setInt(9,nouveauModules.getNbHPnREH());
			this.psInsertModStage.setInt(10,nouveauModules.getNbHPnTut());
			this.psInsertModStage.setInt(11,nouveauModules.getAnnee());
			this.psInsertModStage.executeUpdate();
		}
		catch (SQLException e) { Intervenants.notifications("Erreur de saisie dans le module Stage"); }
	}

	public void insertModPPP(Modules nouveauModules)
	{
		try
		{
			this.psInsertModPPP.setString(1,nouveauModules.getCodMod());
			this.psInsertModPPP.setString(2,nouveauModules.getCodSem());
			this.psInsertModPPP.setInt(3,nouveauModules.getCodTypMod());
			this.psInsertModPPP.setString(4,nouveauModules.getLibLong());
			this.psInsertModPPP.setString(5,nouveauModules.getLibCourt());
			this.psInsertModPPP.setBoolean(6,nouveauModules.getValid());
			this.psInsertModPPP.setInt(7,nouveauModules.getNbHPnCM());
			this.psInsertModPPP.setInt(8,nouveauModules.getNbHPnTD());
			this.psInsertModPPP.setInt(9,nouveauModules.getNbHPnTP());
			this.psInsertModPPP.setInt(10,nouveauModules.getNbHParSemaineTD());
			this.psInsertModPPP.setInt(11,nouveauModules.getNbHParSemaineTP());
			this.psInsertModPPP.setInt(12,nouveauModules.getNbHParSemaineCM());
			this.psInsertModPPP.setInt(13,nouveauModules.getHPonctuelle());
			this.psInsertModPPP.setInt(14,nouveauModules.getNbHPnHTut());
			this.psInsertModPPP.setInt(15,nouveauModules.getNbHTut());
			this.psInsertModPPP.setInt(16,nouveauModules.getAnnee());
			this.psInsertModPPP.executeUpdate();
		}
		catch (SQLException e) { Intervenants.notifications("Erreur de saisie dans le module PPP"); }
	}

	public void insertCategorieIntervenant(CategorieIntervenant catInter)
	{
		try
		{
			this.psInsertCategorieIntervenant.setString(1,catInter.getNomCat());
			this.psInsertCategorieIntervenant.setInt(2,catInter.getService());
			this.psInsertCategorieIntervenant.setInt(3,catInter.getMaxHeure());
			this.psInsertCategorieIntervenant.setInt(4,catInter.getRatioTPCatInterNum());
			this.psInsertCategorieIntervenant.setInt(5,catInter.getRatioTPCatInterDen());
			this.psInsertCategorieIntervenant.executeUpdate();
		}
		catch (SQLException e) { e.printStackTrace(); }
	}

	// Méthode de mise à jour

	public void updateInter(Intervenant nouveauInter, Integer nomCat)
	{
		try
		{
			this.psUpdateInter.setString(1,nouveauInter.getNom());
			this.psUpdateInter.setString(2,nouveauInter.getPrenom());
			this.psUpdateInter.setInt(3,nouveauInter.gethServ());
			this.psUpdateInter.setInt(4,nouveauInter.getMaxHeure());
			this.psUpdateInter.setInt(5, nomCat);
			this.psUpdateInter.setInt(6, nouveauInter.getcodInter());
			this.psUpdateInter.setInt(7, nouveauInter.getAnnee());
			this.psUpdateInter.executeUpdate();
			Intervenants.notifications("Intervenant " + nouveauInter.getPrenom() + " " + nouveauInter.getNom() + " modifié");
		}
		catch (SQLException e) { Intervenants.notifications("Erreur de saisie dans l'intervenant"); }
	}

	public void updateBool(boolean newVal, String codMod)
	{
		try
		{
			this.psUpdateBool.setBoolean(1, newVal);
			this.psUpdateBool.setString(2, codMod);
			this.psUpdateBool.executeUpdate();
		}
		catch (SQLException e) { e.printStackTrace(); }
	}

	public void updateSem(String textFieldId, String intitule, int newVal)
	{
		try
		{
			String query = "UPDATE Semestre SET " + textFieldId + " = " + newVal + " WHERE codSem = '" + intitule + "'";
			Statement statement = DB.connec.createStatement();
			statement.executeUpdate(query);
			statement.close();
		}
		catch (SQLException e) { Intervenants.notifications("La valeur saisie est trop grande !");}
	}

	public void updateMod(Modules nouveauModules, String nomTypMod, String codMod)
	{
		try
		{
			switch(nomTypMod)
			{
				case "Ressources":
					this.psUpdateModRessources.setString(1, nouveauModules.getCodMod());
					this.psUpdateModRessources.setString(2,nouveauModules.getLibLong());
					this.psUpdateModRessources.setString(3,nouveauModules.getLibCourt());
					this.psUpdateModRessources.setBoolean(4,nouveauModules.getValid());
					this.psUpdateModRessources.setInt(5,nouveauModules.getNbHPnCM());
					this.psUpdateModRessources.setInt(6,nouveauModules.getNbHPnTD());
					this.psUpdateModRessources.setInt(7,nouveauModules.getNbHPnTP());
					this.psUpdateModRessources.setInt(8,nouveauModules.getNbSemaineTD());
					this.psUpdateModRessources.setInt(9,nouveauModules.getNbSemaineTP());
					this.psUpdateModRessources.setInt(10,nouveauModules.getNbSemaineCM());
					this.psUpdateModRessources.setInt(11,nouveauModules.getNbHParSemaineTD());
					this.psUpdateModRessources.setInt(12,nouveauModules.getNbHParSemaineTP());
					this.psUpdateModRessources.setInt(13,nouveauModules.getNbHParSemaineCM());
					this.psUpdateModRessources.setInt(14,nouveauModules.getHPonctuelle());
					this.psUpdateModRessources.setString(15,codMod);
					this.psUpdateModRessources.setInt(16,nouveauModules.getAnnee());
	             	this.psUpdateModRessources.executeUpdate();
					break;

	         case "SAE":
					this.psUpdateModSAE.setString(1,nouveauModules.getCodMod());
					this.psUpdateModSAE.setString(2,nouveauModules.getLibLong());
					this.psUpdateModSAE.setString(3,nouveauModules.getLibCourt());
					this.psUpdateModSAE.setBoolean(4,nouveauModules.getValid());
					this.psUpdateModSAE.setInt(5,nouveauModules.getNbHPnSaeParSemestre());
					this.psUpdateModSAE.setInt(6,nouveauModules.getNbHPnTutParSemestre());
					this.psUpdateModSAE.setInt(7, nouveauModules.getNbHSaeParSemestre());
					this.psUpdateModSAE.setInt(8,nouveauModules.getNbHTutParSemestre());
					this.psUpdateModSAE.setString(9,codMod);
					this.psUpdateModSAE.setInt(10,nouveauModules.getAnnee());
	             	this.psUpdateModSAE.executeUpdate();
					break;

	         case "Stage":
					this.psUpdateModStage.setString(1,nouveauModules.getCodMod());
					this.psUpdateModStage.setString(2,nouveauModules.getLibLong());
					this.psUpdateModStage.setString(3,nouveauModules.getLibCourt());
					this.psUpdateModStage.setBoolean(4,nouveauModules.getValid());
					this.psUpdateModStage.setInt(5,nouveauModules.getNbHPnREH());
					this.psUpdateModStage.setInt(6,nouveauModules.getNbHPnTut());
					this.psUpdateModStage.setInt(7,nouveauModules.getNbHREH());
					this.psUpdateModStage.setInt(8,nouveauModules.getNbHTut());
					this.psUpdateModStage.setString(9,codMod);
					this.psUpdateModStage.setInt(10,nouveauModules.getAnnee());
	             	this.psUpdateModStage.executeUpdate();
					break;

	         case "PPP":
					this.psUpdateModPPP.setString(1,nouveauModules.getCodMod());
					this.psUpdateModPPP.setString(2,nouveauModules.getLibLong());
					this.psUpdateModPPP.setString(3,nouveauModules.getLibCourt());
					this.psUpdateModPPP.setBoolean(4,nouveauModules.getValid());
					this.psUpdateModPPP.setInt(5,nouveauModules.getNbHPnCM());
					this.psUpdateModPPP.setInt(6,nouveauModules.getNbHPnTD());
					this.psUpdateModPPP.setInt(7,nouveauModules.getNbHPnTP());
					this.psUpdateModPPP.setInt(8,nouveauModules.getNbHParSemaineTD());
					this.psUpdateModPPP.setInt(9,nouveauModules.getNbHParSemaineTP());
					this.psUpdateModPPP.setInt(10,nouveauModules.getNbHParSemaineCM());
					this.psUpdateModPPP.setInt(11,nouveauModules.getHPonctuelle());
					this.psUpdateModPPP.setInt(12,nouveauModules.getNbHPnTut());
					this.psUpdateModPPP.setInt(13,nouveauModules.getNbHTut());
					this.psUpdateModPPP.setInt(14,nouveauModules.getNbHPnHTut());
					this.psUpdateModPPP.setString(15,codMod);
					this.psUpdateModPPP.setInt(16,nouveauModules.getAnnee());
					this.psUpdateModPPP.executeUpdate();
					break;
			}
		}
		catch (SQLException e) {Intervenants.notifications("Erreur de saisie");}
	}

	public void updateAffectation(Affectation affec)
	{
		try
		{
			this.psUpdateAffectation.setInt(1,affec.getCodCatHeure());
			this.psUpdateAffectation.setString(2,affec.getCommentaire());
			this.psUpdateAffectation.setInt(3,affec.getNbSem());
			this.psUpdateAffectation.setInt(4,affec.getNbGrp());
			this.psUpdateAffectation.setInt(5,affec.getNbH());
			this.psUpdateAffectation.setInt(6,affec.getCodInter());
			this.psUpdateAffectation.setInt(7,affec.getAnnee());
			this.psUpdateAffectation.setString(8,affec.getCodMod());
			System.out.println(this.psUpdateAffectation);

			this.psUpdateAffectation.executeUpdate();
		}
		catch (SQLException e) { e.printStackTrace(); }
	}

	public void updateCategorieIntervenant(CategorieIntervenant catInter, String nomCat)
	{
		try
		{
			this.psUpdateCategorieIntervenant.setString(1,catInter.getNomCat());
			this.psUpdateCategorieIntervenant.setInt(2,catInter.getService());
			this.psUpdateCategorieIntervenant.setInt(3,catInter.getMaxHeure());
			this.psUpdateCategorieIntervenant.setInt(4,catInter.getRatioTPCatInterNum());
			this.psUpdateCategorieIntervenant.setInt(5,catInter.getRatioTPCatInterDen());
			this.psUpdateCategorieIntervenant.setString(6,nomCat);
			this.psUpdateCategorieIntervenant.executeUpdate();
		}
		catch (SQLException e) { Intervenants.notifications("Valeur trop grande ou inférieur à 0 dans la catégorie " + nomCat); }
	}

	// Méthode de suppression
	public void supprInter(Integer codInter, Integer annee)
	{
		try
		{
			this.psDeleteInter.setInt(1,codInter);
			this.psDeleteInter.setInt(2,annee);
			this.psDeleteInter.executeUpdate();
			Intervenants.notifications("Intervenant " + codInter + " supprimé");
		}
		catch (SQLException e) { Intervenants.notifications("Impossible de supprimer l'intervenant " + codInter + " car il possède des affectations liés à lui"); }
	}

	public void supprMod(String codMod, Integer annee)
	{
		try
		{
			this.psDeleteMod.setString(1,codMod);
			this.psDeleteMod.setInt(2,annee);
			this.psDeleteMod.executeUpdate();
			Intervenants.notifications("Module " + codMod + " supprimé");
		}
		catch (SQLException e) { Intervenants.notifications("Impossible de supprimer " + codMod + " car il est dépendant"); }
	}

	public void supprAffectation(String codMod, Integer annee, Integer codInter, Integer codCatHeure)
	{
		try
		{
			this.psDeleteAffectation.setString(1,codMod);
			this.psDeleteAffectation.setInt(2,annee);
			this.psDeleteAffectation.setInt(3,codInter);
			this.psDeleteAffectation.setInt(4,codCatHeure);
			this.psDeleteAffectation.executeUpdate();
		}
		catch (SQLException e) { Intervenants.notifications("Impossible de supprimer l'affectation entre " + codMod + " et " + codInter);}
	}

	public void supprCategorieIntervenant(String nomCat)
	{
		try
		{
			this.psDeleteCategorieIntervenant.setString(1,nomCat);
			this.psDeleteCategorieIntervenant.executeUpdate();
		}
		catch (SQLException e) { Intervenants.notifications("Impossible de supprimer la catégorie " + nomCat); }
	}

	//Autre
	public void clonage(int annee_source,int annee_destination)
	{
		try
		{
			this.psClone.setInt(1,annee_source);
			this.psClone.setInt(2,annee_destination);
			this.psClone.executeQuery();
		}
		catch (SQLException e) { Intervenants.notifications("Impossible de dupliquer " + annee_source + " en " + annee_destination);}
	}

	public void supprimerAnnee(int annee)
	{
		try
		{
			this.psSupprAnnee.setInt(1,annee);
			this.psSupprAnnee.executeQuery();
		}
		catch (SQLException e) { Intervenants.notifications("Impossible de supprimer " + annee);}
	}

	public Integer anneeActuelle()
	{
		Integer anneActuelle = 0;
		try
		{
			ResultSet rs = this.psAnneeActuelle.executeQuery();
			rs.next();
			anneActuelle = rs.getInt("max");
		}
		catch (SQLException e) { Intervenants.notifications("Il n'exite aucune année"); }

		return anneActuelle;
	}

	public Integer getCodCatHeure(String nomCatHeure) 
	{
		Integer codCatHeure = 1;
		try
		{
			this.psSelectCategorieHeureSpe.setString(1,nomCatHeure);
			ResultSet rs = this.psSelectCategorieHeureSpe.executeQuery();
			rs.next();
			codCatHeure = rs.getInt("codCatHeure");
		}
		catch (SQLException e) { e.printStackTrace();Intervenants.notifications("Impossible"); }
		
		return codCatHeure;
	}
}