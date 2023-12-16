package metier;

public class Affectation
{
	// Attributs d'instance

	private int    codAffec;
	private String codMod;
	private int    codInter;
	private int    codCatHeure;
	private String commentaire;
	private int    nbSem;
	private int    nbGrp;
	private int    nbH;

	// Constructeur

	public Affectation(int codAffec, String codMod, int codInter, int codCatHeure, String commentaire, int nbSem, int nbGrp, int nbH)
	{
		this.codAffec = codAffec;
		this.codMod = codMod;
		this.codInter = codInter;
		this.codCatHeure = codCatHeure;
		this.commentaire = commentaire;
		this.nbSem = nbSem;
		this.nbGrp = nbGrp;
		this.nbH = nbH;
	}

	// Getter
	public int getcodAffec() { return this.codAffec; }

	public String getCodMod() { return this.codMod; }

	public int getCodInter() { return this.codInter; }

	public int getCodCatHeure() { return this.codCatHeure; }

	public String getCommentaire() { return this.commentaire; }

	public int getNbSem() { return this.nbSem; }

	public int getNbGrp() { return this.nbGrp; }

	public int getNbH() { return this.nbH; }

	// Setter

	public void setcodAffec(int codAffec) { this.codAffec = codAffec; }

	public void setCodMod(String codMod) { this.codMod = codMod; }

	public void setCodInter(int codInter) { this.codInter = codInter; }

	public void setCodCatHeure(int codCatHeure) { this.codCatHeure = codCatHeure; }

	public void setCommentaire(String commentaire) { this.commentaire = commentaire; }

	public void setNbSem(int nbSem) { this.nbSem = nbSem; }

	public void setNbGrp(int nbGrp) { this.nbGrp = nbGrp; }

	public void setNbH(int nbH) { this.nbH = nbH; }
}