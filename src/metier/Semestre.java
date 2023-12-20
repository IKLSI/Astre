package metier;

public class Semestre
{
	// Attributs d'instances

	private String codSem;
	private int nbGrpTD;
	private int nbGrpTP;
	private int nbEtd;
	private int nbSemaines;

	// Constructeur

	public Semestre(String codSem, int nbGrpTD, int nbGrpTP, int nbEtd, int nbSemaines)
	{
		this.codSem = codSem;
		this.nbGrpTD = nbGrpTD;
		this.nbGrpTP = nbGrpTP;
		this.nbEtd = nbEtd;
		this.nbSemaines = nbSemaines;
	}

	// Getter

	public String getcodSem() { return codSem; }
	public int getNbGrpTD() { return nbGrpTD; }
	public int getNbGrpTP() { return nbGrpTP; }
	public int getNbEtd() { return nbEtd; }
	public int getNbSemaines() { return nbSemaines; }

	// Setter

	public void setcodSem(String codSem) { this.codSem = codSem; }
	public void setNbGrpTD(int nbGrpTD) { this.nbGrpTD = nbGrpTD; }
	public void setNbGrpTP(int nbGrpTP) { this.nbGrpTP = nbGrpTP; }
	public void setNbEtd(int nbEtd) { this.nbEtd = nbEtd; }
	public void setNbSemaines(int nbSemaines) { this.nbSemaines = nbSemaines; }
}