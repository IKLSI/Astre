package metier;

public class Affectation {

	//attributs d'instance
	private int    codAffec;
	private String codMod;
	private int    codInter;
	private int    codCatHeure;
	private String commentaire;
	private int    nbSem;
	private int    nbGrp;
	private int    nbH;

	// Constructor
	public Affectation(int codAffec, String codMod, int codInter, int codCatHeure, String commentaire, int nbSem, int nbGrp, int nbH) {
		this.codAffec = codAffec;
		this.codMod = codMod;
		this.codInter = codInter;
		this.codCatHeure = codCatHeure;
		this.commentaire = commentaire;
		this.nbSem = nbSem;
		this.nbGrp = nbGrp;
		this.nbH = nbH;
	}

	// Getter methods
	public int getcodAffec() {
		return codAffec;
	}

	public String getCodMod() {
		return codMod;
	}

	public int getCodInter() {
		return codInter;
	}

	public int getCodCatHeure() {
		return codCatHeure;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public int getNbSem() {
		return nbSem;
	}

	public int getNbGrp() {
		return nbGrp;
	}

	public int getNbH() {
		return nbH;
	}

	// Setter methods
	public void setcodAffec(int codAffec) {
		this.codAffec = codAffec;
	}

	public void setCodMod(String codMod) {
		this.codMod = codMod;
	}

	public void setCodInter(int codInter) {
		this.codInter = codInter;
	}

	public void setCodCatHeure(int codCatHeure) {
		this.codCatHeure = codCatHeure;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public void setNbSem(int nbSem) {
		this.nbSem = nbSem;
	}

	public void setNbGrp(int nbGrp) {
		this.nbGrp = nbGrp;
	}

	public void setNbH(int nbH) {
		this.nbH = nbH;
	}
}
