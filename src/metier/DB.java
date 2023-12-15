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
	private PreparedStatement psSelectCodInter;
	private PreparedStatement psSelectCategorieIntervenant;
	private PreparedStatement psSelectCategorieHeure;

	//instert
	private PreparedStatement psInstertIntervenant;

	//delete
	private PreparedStatement psDeleteInter;

	//update
	private PreparedStatement psUpdateInter;

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
			this.psSelectCodInter = DB.connec.prepareStatement("SELECT codInter FROM Intervenant WHERE nom = ?");
			this.psSelectCategorieHeure = DB.connec.prepareStatement("SELECT * FROM CategorieHeure");

			//préparation des insertions
			this.psInstertIntervenant = DB.connec.prepareStatement("INSERT INTO Intervenant (nom, prenom, codCatInter, hServ, maxHeure) VALUES(?,?,?,?,?)");

			//préparation des update
			this.psUpdateInter = DB.connec.prepareStatement("UPDATE Intervenant SET nom = ?, prenom = ?, codCatInter = ?, hServ = ?, maxHeure = ? WHERE nom = ?");

			//preparation des delete
			this.psDeleteInter = DB.connec.prepareStatement("DELETE FROM Intervenant WHERE codInter = ?");

		} catch (SQLException e) {
			System.out.println("ECHEC connection");
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
	public ResultSet getCategorieInter(){
		ResultSet resultSet = null;
		try {
			resultSet = this.psSelectCategorieIntervenant.executeQuery();
		} catch (Exception e) {e.printStackTrace();}
		return resultSet;
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

	//recuper le code d'un intervenant
	public ArrayList<Integer> getCodInter(String nomInter){
		ArrayList<Integer> lstCodInter = new ArrayList<Integer>();
		try {
			this.psSelectCodInter.setString(1, nomInter);
			ResultSet rs = this.psSelectCodInter.executeQuery();
			while (rs.next()) {
				lstCodInter.add(rs.getInt("codInter"));
			}
		} catch (SQLException e) {e.printStackTrace();}
		return lstCodInter;
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

	/*
	 * Méthode d'Update
	 */

	public void updateInter(Intervenant nouveauInter, String ancienNom){
		try {
			this.psUpdateInter.setString(1,nouveauInter.getNom());
			this.psUpdateInter.setString(2,nouveauInter.getPrenom());
			this.psUpdateInter.setInt(3,nouveauInter.getCodCatInter());
			this.psUpdateInter.setInt(4,nouveauInter.gethServ());
			this.psUpdateInter.setInt(5,nouveauInter.getMaxHeure());
			this.psUpdateInter.setString(6,ancienNom);

			this.psUpdateInter.executeUpdate();
			//this.psUpdateInter.close();
		} catch (SQLException e) {e.printStackTrace();}
	}

	/*
	 * Méthode de Suppression
	 */

	public void supprInter(int codInter){
		try {
			this.psDeleteInter.setInt(1,codInter);
			this.psDeleteInter.executeUpdate();
			this.psDeleteInter.close();
		} catch (SQLException e) {e.printStackTrace();}
	}
}
