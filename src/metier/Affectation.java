package metier;

public class Affectation
{
	private String nom;
	private String type;
	private int nbSem;
	private int nbGp;
	private int totalEqTd;

	public Affectation(String nom, String type, int nbSem, int nbGp, int totalEqTd)
	{
		this.nom = nom;
		this.type = type;
		this.nbSem = nbSem;
		this.nbGp = nbGp;
		this.totalEqTd = totalEqTd;
	}

	public String getNom() { return nom; }
	public String getType() { return type; }
	public int getNbSem() { return nbSem; }
	public int getNbGp() { return nbGp; }
	public int getTotalEqTd() { return totalEqTd; }

	public void setNom(String nom) { this.nom = nom; }
	public void setType(String type) { this.type = type; }
	public void setNbSem(int nbSem) { this.nbSem = nbSem; }
	public void setNbGp(int nbGp) { this.nbGp = nbGp; }
	public void setTotalEqTd(int totalEqTd) { this.totalEqTd = totalEqTd; }
}