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
	private PreparedStatement psSelect1Intervenant_final;
	private PreparedStatement psSelectCodInter;
	private PreparedStatement psSelectCategorieIntervenant;
	private PreparedStatement psSelectCategorieHeure;
	private PreparedStatement psSelectListeModule;
	private PreparedStatement psSelectListeSemestre;
	private PreparedStatement psSelectTypeModules;
	private PreparedStatement psSelectModuleParIntervenant;
	private PreparedStatement psSelectPreviModuleRessource;
	private PreparedStatement psSelectAffectModuleRessource;

	// Attribut requête Insert

	private PreparedStatement psInstertIntervenant;

	// Attribut requête Delete

	private PreparedStatement psDeleteInter;

	// Attribut requête Update

	private PreparedStatement psUpdateInter;
	private PreparedStatement psUpdateMod;

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
			this.psSelectIntervenant_final     = DB.connec.prepareStatement("SELECT * FROM intervenant_final WHERE codInter = ?");
			this.psSelectCategorieIntervenant  = DB.connec.prepareStatement("SELECT * FROM CategorieIntervenant");
			this.psSelectCodInter              = DB.connec.prepareStatement("SELECT codInter FROM Intervenant WHERE nom = ?");
			this.psSelectCategorieHeure        = DB.connec.prepareStatement("SELECT * FROM CategorieHeure");
			this.psSelectListeSemestre         = DB.connec.prepareStatement("SELECT nbGrpTD, nbGrpTP, nbEtd, nbSemaines FROM Semestre WHERE codSem = ?");
			this.psSelectListeModule           = DB.connec.prepareStatement("SELECT * FROM liste_module WHERE codSem = ?");
			this.psSelectTypeModules           = DB.connec.prepareStatement("SELECT * FROM TypeModule");
			this.psSelectModuleParIntervenant  = DB.connec.prepareStatement("SELECT * FROM affectation_final");
			this.psSelectPreviModuleRessource  = DB.connec.prepareStatement("SELECT * FROM module_final WHERE codMod = ?");
			this.psSelectAffectModuleRessource = DB.connec.prepareStatement("SELECT * FROM affectation_final WHERE codMod = ?;");

			// Préparation des Insertions
			this.psInstertIntervenant = DB.connec.prepareStatement("INSERT INTO Intervenant (nom, prenom, codCatInter, hServ, maxHeure) VALUES(?,?,?,?,?)");

			// Préparation des Updates
			this.psUpdateInter      = DB.connec.prepareStatement("UPDATE Intervenant SET nom = ?, prenom = ?, hServ = ?, maxHeure = ? WHERE codInter = ?;");
			this.psUpdateMod        = DB.connec.prepareStatement("UPDATE Module SET !!TODO!! WHERE codMod = ?");

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

		try { resultSet = this.psSelect1Intervenant_final.executeQuery(); }
		catch (Exception e) { e.printStackTrace(); }

		return resultSet;
	}

	public ResultSet getModulParIntervenant(int codInter)
	{
		ResultSet resultSet = null;

		try { 
			this.psSelectModuleParIntervenant.setInt(1, codInter);
			resultSet = this.psSelectModuleParIntervenant.executeQuery(); }
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

	// Récupère toutes les categories d'heure
	public ArrayList<CategorieHeure> getCategorieHeure()
	{
		ArrayList<CategorieHeure> lst = new ArrayList<CategorieHeure>();

		try
		{
			ResultSet rs = this.psSelectIntervenants.executeQuery();
			
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
	
	/*
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
			while(rs.next)
				for(int cpt = 1; cpt <= rs.getMetaData().getColumnCount(); cpt++) 
					lstVal.get(lstCol.get(cpt)).add(rs.getString(cpt));
		}
		catch(Exception e){ e.printStackTrace(); };
		return lstVal;
	}
	*/

	// Récupère les affectations d'un module.
	public ResultSet getAffectationRessource(String codMod)
	{
		ResultSet resultSet = null;
		try
		{
			this.psSelectCodInter.setString(1, codMod);
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
			this.psInstertIntervenant.close();
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

	// public void updateMod(String champModif)
	// { // PEUX ETRE FAIRE DES CASE SELON SI le codtypmod ET DONC 4 PREPAREDSTATEMENT
	// 	try
	// 	{
	// 		this.psUpdateMod.setString(1,codMod);
	// 		this.psUpdateMod.setString(1,libLong);
	// 		this.psUpdateMod.setString(1,libCourt);
	// 		this.psUpdateMod.setString(1,valid);
	// 		this.psUpdateMod.setString(1,nbHPnCM);
	// 		this.psUpdateMod.setString(1,nbHPnTD);
	// 		this.psUpdateMod.setString(1,nbHPnTP);
	// 		this.psUpdateMod.setString(1,nbSemaineTD);
	// 		this.psUpdateMod.setString(1,nbSemaineTP);
	// 		this.psUpdateMod.setString(1,nbSemaineCM);
	// 		this.psUpdateMod.setString(1,nbHParSemaineTD);
	// 		this.psUpdateMod.setString(1,nbHParSemaineTP);
	// 		this.psUpdateMod.setString(1,nbHParSemaineCM);
	// 		this.psUpdateMod.setString(1,hPonctuelle);
	// 		this.psUpdateMod.setString(1,nbHPnSaeParSemestre);
	// 		this.psUpdateMod.setString(1,nbHPnTutParSemestre);
	// 		this.psUpdateMod.setString(1,nbHSaeParSemestre);
	// 		this.psUpdateMod.setString(1,nbHTutParSemestre);
	// 		this.psUpdateMod.setString(1,nbHPnREH);
	// 		this.psUpdateMod.setString(1,nbHPnTut);
	// 		this.psUpdateMod.setString(1,nbHREH);
	// 		this.psUpdateMod.setString(1,nbHTut);
	// 		this.psUpdateMod.setString(1,nbHPnHTut);
	// 		this.psUpdateMod.executeUpdate();
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