package metier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB {
	//attribut de classe
	private static Connection connec;

	//attribut d'instance

	//requete
	private PreparedStatement psSelectIntervenants;
	private PreparedStatement psSelectIntervenant_final;
	private PreparedStatement psSelectCategorieIntervenant;
	private PreparedStatement psSelectCategorieHeure;

	//instert
	private PreparedStatement psInstertIntervenant;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	//ici on gère la connexion et on prépare les requêtes
	public DB(){

		try {
			//ouverture de la connesxion à la data base
			DB.connec = DriverManager.getConnection("jdbc:postgresql://localhost/lk210125","lk210125","Kyliann.0Bado");

			//préparation des requêtes
			this.psSelectIntervenants = DB.connec.prepareStatement("Select * From Intervenant");
			this.psSelectIntervenant_final = DB.connec.prepareStatement("SELECT * FROM intervenant_final");
			this.psSelectCategorieIntervenant = DB.connec.prepareStatement("SELECT * FROM CategorieIntervenant");
			this.psSelectCategorieHeure = DB.connec.prepareStatement("SELECT * FROM CategorieHeure");

			//préparation des insertions
			this.psInstertIntervenant = DB.connec.prepareStatement("INSERT INTO Intervenant (nom, prenom, codCatInter, hServ, maxHeure) VALUES(?,?,?,?,?)");

			//préparation des update

		} catch (SQLException e) {
			System.out.println("ECHEC connection");
			e.printStackTrace();
		}
	}

	//Ici on coupe la connection
	public void couperConnection(){
		try {
				DB.connec.close();
		}catch (Exception e){e.printStackTrace();}
	}

	/*
	 * Méthode requête à la base de donnée
	 */

	//recupere tous les intervenants
	public ArrayList<Intervenant> getIntervenants(){
		ArrayList<Intervenant> lstInterv = new ArrayList<Intervenant>();
		try {
			ResultSet rs = this.psSelectIntervenants.executeQuery();
			while(rs.next()){
				int codInter    = rs.getInt("codInter");
				String nom      = rs.getString("nom");
				String prenom   = rs.getString("prenom");
				int codCatInter = rs.getInt("codCatInter");
				int hServ       = rs.getInt("hServ");
				int maxHeure    = rs.getInt("maxHeure");

				lstInterv.add(new Intervenant(nom, prenom, codCatInter, hServ, maxHeure));
			}
		} catch (Exception e) {e.printStackTrace();}
		return lstInterv;
	}

	//recupere les intervenants finaux
	public ResultSet getIntervenant_final(){
		ResultSet resultSet = null;
		try {
			resultSet = this.psSelectIntervenant_final.executeQuery();
		} catch (Exception e) {e.printStackTrace();}
		return resultSet;
	}

	//recupere toutes les categories d'intervenants
	public ArrayList<CategorieIntervenant> getCategorieInter(){
		ArrayList<CategorieIntervenant> lst = new ArrayList<CategorieIntervenant>();
		try {
			ResultSet rs = this.psSelectIntervenants.executeQuery();
			while(rs.next()){
				int codCatInter = rs.getInt("codCatInter");;
				String nomCat   = rs.getString("nomCat");
				int service     = rs.getInt("service");
				int maxHeure    = rs.getInt("maxHeure");
				int ratioTPCatInterNum = rs.getInt("ratioTPCatInterNum");
				int ratioTPCatInterDen = rs.getInt("ratioTPCatInterDen");

				lst.add(new CategorieIntervenant(codCatInter, nomCat, service, maxHeure,ratioTPCatInterNum,ratioTPCatInterDen));
			}
		} catch (Exception e) {e.printStackTrace();}
		return lst;
	}

	//recupere toutes les categories d'heure
	public ArrayList<CategorieHeure> getCategorieHeure(){
		ArrayList<CategorieHeure> lst = new ArrayList<CategorieHeure>();
		try {
			ResultSet rs = this.psSelectIntervenants.executeQuery();
			while(rs.next()){
				int    codCatHeure = rs.getInt("codCatHeure");
				String nomCatHeure = rs.getString("nomCatHeure");
				int    coeffNum    = rs.getInt("coeffNum");
				int    coeffDen    = rs.getInt("coeffDen");

				lst.add(new CategorieHeure(codCatHeure,nomCatHeure,coeffNum,coeffDen));
			}
		} catch (Exception e) {e.printStackTrace();}
		return lst;
	}
	/*
	 * Méthode Insertion
	 */

	public void insertIntervenant(Intervenant inter){
		try {
			this.psInstertIntervenant.setString(1,inter.getNom());
			this.psInstertIntervenant.setString(2,inter.getPrenom());
			this.psInstertIntervenant.setInt(3,inter.getCodCatInter());
			this.psInstertIntervenant.setInt(4,inter.gethServ());
			this.psInstertIntervenant.setInt(5,inter.getMaxHeure());

			this.psInstertIntervenant.executeUpdate();
			this.psInstertIntervenant.close();
		} catch (SQLException e) {e.printStackTrace();}
	}
}
