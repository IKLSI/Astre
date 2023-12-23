package metier;

public class Affectation
{
	// Attributs de Classe

	private String codMod;
	private Integer codInter;
	private Integer codCatHeure;
	private String commentaire;
	private String nom;
	private String type;
	private Integer nbSem;
	private Integer nbGrp;
	private Integer totalEqTd;
	private Integer nbH;
	private Integer annee;

	// Constructeur

	public Affectation(String codMod, Integer codInter, Integer codCatHeure, String commentaire, String nom, String type,
					   Integer nbSem, Integer nbGrp, Integer totalEqTd, Integer nbH, Integer annee)
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
		this.annee = annee;
	}

	// Méthode Get

	public String getNom()          { return this.nom; }
	public String getType()         { return this.type; }
	public Integer getNbSem()       { return this.nbSem; }
	public Integer getNbGrp()       { return this.nbGrp; }
	public Integer getTotalEqTd()   { return this.totalEqTd; }
	public String getCodMod()       { return this.codMod; }
	public Integer getCodInter()    { return this.codInter; }
	public Integer getCodCatHeure() { return this.codCatHeure; }
	public String getCommentaire()  { return this.commentaire; }
	public Integer getNbH()         { return this.nbH; }
	public Integer getAnnee()       { return this.annee; }

	// Méthode Set

	public void setNom(String nom) { this.nom = nom; }
	public void setType(String type) { this.type = type; }
	public void setNbSem(Integer nbSem) { this.nbSem = nbSem; }
	public void setNbGrp(Integer nbGrp) { this.nbGrp = nbGrp; }
	public void setTotalEqTd(Integer totalEqTd) { this.totalEqTd = totalEqTd; }
	public void setCodMod(String codMod) { this.codMod = codMod; }
	public void setCodInter(Integer codInter) { this.codInter = codInter; }
	public void setCodCatHeure(Integer codCatHeure) { this.codCatHeure = codCatHeure; }
	public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
	public void setNbH(Integer nbH) { this.nbH = nbH; }
	public void setAnnee(Integer annee) { this.annee = annee;}
}