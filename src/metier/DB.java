package metier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class DB
{
	// Attribut de classe
	private static Connection connec;

	// Attribut requête Select
	private PreparedStatement psSelectIntervenants;
	private PreparedStatement psSelectIntervenant_final;
	private PreparedStatement psSelectIntervenant_complet;
	private PreparedStatement psSelect1Intervenant_final;
	private PreparedStatement psSelectCodInter;
	private PreparedStatement psSelectCategorieIntervenant;
	private PreparedStatement psSelectCategorieHeure;
	private PreparedStatement psSelectListeModule;
	private PreparedStatement psSelectListeSemestre;
	private PreparedStatement psSelectTypeModules;
	private PreparedStatement psSelectModuleParIntervenant;
	private PreparedStatement psSelectModulePar1Intervenant;
	private PreparedStatement psSelectPreviModuleRessource;
	private PreparedStatement psSelectAffectModuleRessource;
	private PreparedStatement psSelectModule;

	// Attribut requête Insert
	private PreparedStatement psInstertIntervenant;
	private PreparedStatement psInstertAffectation;

	// Attribut requête Delete
	private PreparedStatement psDeleteInter;

	// Attribut requête Update
	private PreparedStatement psUpdateInter;
	private PreparedStatement psUpdateModRessources;
	private PreparedStatement psUpdateModSAE;
	private PreparedStatement psUpdateModStage;
	private PreparedStatement psUpdateModPPP;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	// Ici on gère la connexion et on prépare les requêtes
	public DB()
	{
		try
		{
			// Ouverture de la connexion à la data base
			DB.connec = DriverManager.getConnection("jdbc:postgresql://localhost/lk210125","lk210125","Kyliann.0Bado");

			// Préparation des Requêtes
			this.psSelectIntervenants          = DB.connec.prepareStatement("SELECT * FROM Intervenant");
			this.psSelectIntervenant_final     = DB.connec.prepareStatement("SELECT * FROM intervenant_final");
			this.psSelectIntervenant_complet   = DB.connec.prepareStatement("SELECT * FROM intervenant_complet");
			this.psSelect1Intervenant_final    = DB.connec.prepareStatement("SELECT * FROM intervenant_final WHERE codInter = ?");
			this.psSelectCategorieIntervenant  = DB.connec.prepareStatement("SELECT * FROM CategorieIntervenant");
			this.psSelectCodInter              = DB.connec.prepareStatement("SELECT codInter FROM Intervenant WHERE nom = ?");
			this.psSelectCategorieHeure        = DB.connec.prepareStatement("SELECT * FROM CategorieHeure");
			this.psSelectListeSemestre         = DB.connec.prepareStatement("SELECT nbGrpTD, nbGrpTP, nbEtd, nbSemaines FROM Semestre WHERE codSem = ?");
			this.psSelectListeModule           = DB.connec.prepareStatement("SELECT * FROM liste_module WHERE codSem = ?");
			this.psSelectTypeModules           = DB.connec.prepareStatement("SELECT * FROM TypeModule");
			this.psSelectModuleParIntervenant  = DB.connec.prepareStatement("SELECT * FROM affectation_final");
			this.psSelectModulePar1Intervenant = DB.connec.prepareStatement("SELECT * FROM affectation_final WHERE ?");
			this.psSelectModule                = DB.connec.prepareStatement("SELECT codmod,codsem,liblong,libcourt FROM module WHERE codMod = ?");
			this.psSelectPreviModuleRessource  = DB.connec.prepareStatement("SELECT * FROM module_final WHERE codTypMod = ?");
			this.psSelectAffectModuleRessource = DB.connec.prepareStatement("SELECT * FROM affectation_final WHERE codMod = ?;");

			// Préparation des Insertions
			this.psInstertIntervenant = DB.connec.prepareStatement("INSERT INTO Intervenant (nom, prenom, codCatInter, hServ, maxHeure)  VALUES(?,?,?,?,?)");
			this.psInstertAffectation = DB.connec.prepareStatement("INSERT INTO Affectation (codMod, codInter, codCatHeure, commentaire) VALUES(?,?,?,?)");

			// Préparation des Updates
			this.psUpdateInter         = DB.connec.prepareStatement("UPDATE Intervenant SET nom = ?, prenom = ?, hServ = ?, maxHeure = ? WHERE codInter = ?;");
			this.psUpdateModRessources = DB.connec.prepareStatement("UPDATE Module SET libLong = ?, libCourt = ?, valid = ?, nbHPnCM = ?, nbHPnTD = ?, nbHPnTP = ?, nbSemaineTD = ?, nbSemaineTP = ?, nbSemaineCM = ?, nbHParSemaineTD = ?, nbHParSemaineTP = ?, nbHParSemaineCM = ?, hPonctuelle = ?  WHERE codMod = ?");
			this.psUpdateModSAE 	   = DB.connec.prepareStatement("UPDATE Module SET libLong = ?, libCourt = ?, valid = ?, nbHPnSaeParSemestre = ?, nbHPnTutParSemestre = ?, nbHSaeParSemestre = ?, nbHTutParSemestre = ? WHERE codMod = ?");
			this.psUpdateModStage      = DB.connec.prepareStatement("UPDATE Module SET libLong = ?, libCourt = ?, valid = ?, nbHPnREH = ?, nbHPnTut = ?, nbHREH = ?, nbHTut = ? WHERE codMod = ?");
			this.psUpdateModPPP        = DB.connec.prepareStatement("UPDATE Module SET libLong = ?, libCourt = ?, valid = ?, nbHPnCM = ?, nbHPnTD = ?, nbHPnTP = ?, nbHParSemaineTD = ?, nbHParSemaineTP = ?, nbHParSemaineCM = ?, hPonctuelle = ?, nbHPnTut = ?, nbHTut = ?, nbHPnHTut = ? WHERE codMod = ?");


			// Preparation des Deletes
			this.psDeleteInter = DB.connec.prepareStatement("DELETE FROM Intervenant WHERE codInter = ?");

		}
		catch (SQLException e) { System.out.println("Problème de connexion à la base de donnée"); }
	}

	// Stoppe la connexion à la base de donnée
	public void couperConnection()
	{
		try
		{
			DB.connec.close();
		}
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
				int codInter    = rs.getInt("codInter");
				String nom      = rs.getString("nom");
				String prenom   = rs.getString("prenom");
				int codCatInter = rs.getInt("codCatInter");
				int hServ       = rs.getInt("hServ");
				int maxHeure    = rs.getInt("maxHeure");

				lstInterv.add(new Intervenant(nom, prenom, codCatInter, hServ, maxHeure));
			}
		}
		catch (Exception e) { e.printStackTrace(); }

		return lstInterv;
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
				int codTypMod = rs.getInt("codTypMod");
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

		try { resultSet = this.psSelectIntervenant_final.executeQuery(); }
		catch (Exception e) { e.printStackTrace(); }

		return resultSet;
	}

	public ResultSet getIntervenant_final(int codInter)
	{
		ResultSet resultSet = null;

		try {
			this.psSelect1Intervenant_final.setInt(1,codInter);
			 resultSet = this.psSelect1Intervenant_final.executeQuery(); }
		catch (Exception e) { e.printStackTrace(); }

		return resultSet;
	}

	public ResultSet getModulParIntervenant(int codInter)
	{
		ResultSet resultSet = null;

		try { 
			this.psSelectModulePar1Intervenant.setInt(1, codInter);
			resultSet = this.psSelectModulePar1Intervenant.executeQuery(); }
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

		try { resultSet = this.psSelectCategorieIntervenant.executeQuery(); }
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
			ResultSet rs = this.psSelectListeModule.executeQuery();

			while(rs.next())
			{
				String codSem = rs.getString("codSem");
				String codMod = rs.getString("codMod");
				String libLong = rs.getString("libLong");
				String hAP = rs.getString("hAP");
				boolean valid = rs.getBoolean("valid");
				
				lstModule.add(new Modules(codSem,codMod,libLong,hAP,valid));
			}
		}
		catch (Exception e){ e.printStackTrace(); }

		return lstModule;
	}

	public ResultSet getModule(String codMod)
	{
		ResultSet resultSet = null;

		try {
			this.psSelectModule.setString(1, codMod);
			resultSet = this.psSelectModule.executeQuery(); }
		catch (Exception e) { e.printStackTrace(); }

		return resultSet;
	}

	public HashMap<String,ArrayList<String>> getPreviModuleRessource(String codMod) //PAS TEST
	{
		HashMap<String,ArrayList<String>> lstVal = new HashMap<String,ArrayList<String>>();
		try
		{
			this.psSelectPreviModuleRessource.setString(1,codMod);
			ResultSet rs = this.psSelectPreviModuleRessource.executeQuery();
			rs.next();
			for(int cpt = 1; cpt <= rs.getMetaData().getColumnCount(); cpt++)
			{
				lstVal.put(rs.getMetaData().getColumnName(cpt),new ArrayList<String>());
			}
			
			while(rs.next())
			{
				for(String col : lstVal.keySet()) 

					lstVal.get(col).add(rs.getString(col));
			}

			for(String col : lstVal.keySet())
				if(verifNull(lstVal.get(col)))
					lstVal.remove(col);
		}
		catch(Exception e){ e.printStackTrace(); };
		return lstVal;
	}
	

	public boolean verifNull(ArrayList<String> lstVal) //PAS TEST
	{
		for(String str : lstVal)
			if(str != null) return false;
		return true;
	}
	

	// Récupère les affectations d'un module.
	public ResultSet getAffectationRessource(String codMod)
	{
		ResultSet resultSet = null;
		try
		{
			this.psSelectAffectModuleRessource.setString(1, codMod);
			ResultSet rs = this.psSelectAffectModuleRessource.executeQuery();
		}
		catch (Exception e) { e.printStackTrace(); }
		return resultSet;
	}
	

	// Récupère le code d'un intervenant
	public ArrayList<Integer> getCodInter(String nomInter)
	{
		ArrayList<Integer> lstCodInter = new ArrayList<Integer>();
		try
		{
			this.psSelectCodInter.setString(1, nomInter);
			ResultSet rs = this.psSelectCodInter.executeQuery();

			while (rs.next())
				lstCodInter.add(rs.getInt("codInter"));
		}
		catch (SQLException e) { e.printStackTrace(); }

		return lstCodInter;
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

			this.psInstertIntervenant.executeUpdate();
			this.psInstertIntervenant.close(); //jsp ?
		}
		catch (SQLException e) { e.printStackTrace(); }
	}

	public void insertAffectation(Affectation affec)
	{
		try
		{
			this.psInstertAffectation.setString(1,affec.getCodMod());
			this.psInstertAffectation.setInt(2,affec.getCodInter());
			this.psInstertAffectation.setInt(3,affec.getCodCatHeure());
			this.psInstertAffectation.setString(4,affec.getCommentaire());
			this.psInstertAffectation.executeUpdate();
		}
		catch (SQLException e) { e.printStackTrace(); }
	}

	// Méthode de mise à jour
	public void updateInter(Intervenant nouveauInter)
	{
		try
		{
			this.psUpdateInter.setString(1,nouveauInter.getNom());
			this.psUpdateInter.setString(2,nouveauInter.getPrenom());
			this.psUpdateInter.setInt(3,nouveauInter.gethServ());
			this.psUpdateInter.setInt(4,nouveauInter.getMaxHeure());
			this.psUpdateInter.setInt(5, nouveauInter.getcodInter());
			this.psUpdateInter.executeUpdate();
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
		} catch (SQLException e) { e.printStackTrace(); }
	}

	// public void updateMod(Modules nouveauModules, String codModTyp)
	// {
	// 	try
	// 	{
    // 		switch(codModTyp)
	// 		{
	// 			case "Ressources": 
	// 				this.psUpdateModRessources.setString(1,nouveauModules.getLibLong);
	// 				this.psUpdateModRessources.setString(2,nouveauModules.getLibCourt);
	// 				this.psUpdateModRessources.setBoolean(3,nouveauModules.getValid);
	// 				this.psUpdateModRessources.setInt(4,nouveauModules.getNbHPnCM);
	// 				this.psUpdateModRessources.setInt(5,nouveauModules.getNbHPnTD);
	// 				this.psUpdateModRessources.setInt(6,nouveauModules.getNbHPnTP);
	// 				this.psUpdateModRessources.setInt(7,nouveauModules.getNbSemaineTD);
	// 				this.psUpdateModRessources.setInt(8,nouveauModules.getNbSemaineTP);
	// 				this.psUpdateModRessources.setInt(9,nouveauModules.getNbSemaineCM);
	// 				this.psUpdateModRessources.setInt(10,nouveauModules.getNbHParSemaineTD);
	// 				this.psUpdateModRessources.setInt(11,nouveauModules.getNbHParSemaineTP);
	// 				this.psUpdateModRessources.setInt(12,nouveauModules.getNbHParSemaineCM);
	// 				this.psUpdateModRessources.setInt(13,nouveauModules.getHPonctuelle)
	// 				this.psUpdateModRessources.setString(14,nouveauModules.getCodMod);
	//              this.psUpdateModRessources.executeUpdate();
	//				break;
	//
	//          case "SAE": 
	// 				this.psUpdateModSAE.setString(1,nouveauModules.getLibLong);
	// 				this.psUpdateModSAE.setString(2,nouveauModules.getLibCourt);
	// 				this.psUpdateModSAE.setBoolean(3,nouveauModules.getValid);
	// 				this.psUpdateModSAE.setInt(4,nouveauModules.getNbHPnSaeParSemestre);
	// 				this.psUpdateModSAE.setInt(5,nouveauModules.getNbHPnTutParSemestre);
	// 				this.psUpdateModSAE.setInt(6, nouveauModules.getNbHSaeParSemestre);
	// 				this.psUpdateModSAE.setInt(7,nouveauModules.getNbHTutParSemestre);
	// 				this.psUpdateModSAE.setString(8,nouveauModules.getCodMod);
	//              this.psUpdateModSAE.executeUpdate();
	//				break;
	//
	//          case "Stage": 
	// 				this.psUpdateModStage.setString(1,nouveauModules.getLibLong);
	// 				this.psUpdateModStage.setString(2,nouveauModules.getLibCourt);
	// 				this.psUpdateModStage.setBoolean(3,nouveauModules.getValid);
	// 				this.psUpdateModStage.setInt(4,nouveauModules.getNbHPnREH);
	// 				this.psUpdateModStage.setInt(5,nouveauModules.getNbHPnTut);
	// 				this.psUpdateModStage.setInt(6,nouveauModules.getNbHREH);
	// 				this.psUpdateModStage.setInt(7,nouveauModules.getNbHTut);
	// 				this.psUpdateModStage.setString(8,nouveauModules.getCodMod);
	//              this.psUpdateModStage.executeUpdate();
	//				break;
	//
	//          case "PPP": 
	// 				this.psUpdateModPPP.setString(1,nouveauModules.getLibLong);
	// 				this.psUpdateModPPP.setString(2,nouveauModules.getLibCourt);
	// 				this.psUpdateModPPP.setBoolean(3,nouveauModules.getValid);
	// 				this.psUpdateModPPP.setInt(4,nouveauModules.getNbHPnCM);
	// 				this.psUpdateModPPP.setInt(5,nouveauModules.getNbHPnTD);
	// 				this.psUpdateModPPP.setInt(6,nouveauModules.getNbHPnTP);
	// 				this.psUpdateModPPP.setInt(7,nouveauModules.getNbHParSemaineTD);
	// 				this.psUpdateModPPP.setInt(8,nouveauModules.getNbHParSemaineTP);
	// 				this.psUpdateModPPP.setInt(9,nouveauModules.getNbHParSemaineCM);
	// 				this.psUpdateModPPP.setInt(10,nouveauModules.getHPonctuelle)
	// 				this.psUpdateModPPP.setInt(11,nouveauModules.getNbHPnTut);
	// 				this.psUpdateModPPP.setInt(12,nouveauModules.getNbHTut);	
	// 				this.psUpdateModPPP.setInt(13,nouveauModules.getNbHPnHTut);
	// 				this.psUpdateModPPP.setString(14,nouveauModules.getCodMod);
	//              this.psUpdateModPPP.executeUpdate();
	//				break;
	// 		}
	// 	}
	// 	catch (SQLException e) { e.printStackTrace(); }
	// }

	// Méthode de suppression
	public void supprInter(int codInter)
	{
		try
		{
			this.psDeleteInter.setInt(1,codInter);
			this.psDeleteInter.executeUpdate();
			this.psDeleteInter.close();
		}
		catch (SQLException e) { e.printStackTrace(); }
	}
}