package metier;

public class Affectation
{
	
	// Attributs de Classe
	private String codMod;
	private int codInter;
	private int codCatHeure;
	private String commentaire;
	private String nom;
	private String type;
	private int nbSem;
	private int nbGrp;
	private int totalEqTd;
	private int nbH;

	// Constructeur
	public Affectation(String codMod, int codInter, int codCatHeure, String commentaire, String nom, String type, int nbSem, int nbGrp, int totalEqTd, int nbH)
	{
		this.codMod = codMod;
		this.codInter = codInter;
		this.codCatHeure = codCatHeure;
		this.commentaire = commentaire;
		this.nom = nom;
		this.type = type;
		this.nbSem = nbSem;
		this.nbGrp = nbGrp;
		this.totalEqTd = totalEqTd;
		this.nbH = nbH;
	}

	// Getters et Setters
	public String getNom() { return this.nom; }
	public String getType() { return this.type; }
	public int getNbSem() { return this.nbSem; }
	public int getNbGrp() { return this.nbGrp; }
	public int getTotalEqTd() { return this.totalEqTd; }
	public String getCodMod() { return this.codMod; }
	public int getCodInter() { return this.codInter; }
	public int getCodCatHeure() { return this.codCatHeure; }
	public String getCommentaire() { return this.commentaire; }
	public int getNbH() { return this.nbH; }

	public void setNom(String nom) { this.nom = nom; }
	public void setType(String type) { this.type = type; }
	public void setNbSem(int nbSem) { this.nbSem = nbSem; }
	public void setNbGrp(int nbGrp) { this.nbGrp = nbGrp; }
	public void setTotalEqTd(int totalEqTd) { this.totalEqTd = totalEqTd; }
	public void setCodMod(String codMod) { this.codMod = codMod; }
	public void setCodInter(int codInter) { this.codInter = codInter; }
	public void setCodCatHeure(int codCatHeure) { this.codCatHeure = codCatHeure; }
	public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
	public void setNbH(int nbH) { this.nbH = nbH; }

}