package metier;

public class Intervenant
{
	// Attributs d'instance
	private int codInter;
	private String nom;
	private String prenom;
	private int codCatInter;
	private int hServ;
	private int maxHeure;

	// Constructeur
	public Intervenant( int codInter, String nom, String prenom, int hServ, int maxHeure)
	{
		this.codInter = codInter;
		this.nom = nom;
		this.prenom = prenom;
		this.hServ = hServ;
		this.maxHeure = maxHeure;
	}

	public Intervenant( String nom, String prenom, int codCatInter, int hServ, int maxHeure)
	{
		this.codInter = 1;
		this.nom = nom;
		this.prenom = prenom;
		this.codCatInter = codCatInter;
		this.hServ = hServ;
		this.maxHeure = maxHeure;
	}

	// Méthode getteur setteur
	public int getcodInter() { return this.codInter; }
	public void setcodInter(int codInter) { this.codInter = codInter; }
	public String getNom() { return this.nom; }
	public String getPrenom() { return this.prenom; }
	public int getCodCatInter() { return this.codCatInter; }
	public int gethServ() { return this.hServ; }
	public int getMaxHeure() { return this.maxHeure; }

	public void setNom(String nom) { this.nom = nom; }
	public void setPrenon(String prenom) { this.prenom = prenom; }
	public void setCodCatInter(int codCatInter) { this.codCatInter = codCatInter; }
	public void sethServ(int hServ) { this.hServ = hServ; }
	public void setMaxHeure(int maxHeure) { this.maxHeure = maxHeure; }
}
