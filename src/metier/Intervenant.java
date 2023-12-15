package metier;

public class Intervenant {

	// attributs d'instance
	private int codInter;
	private String nom;
	private String prenom;
	private int codCatInter;
	private int hServ;
	private int maxHeure;

	//Constructeur
	public Intervenant( String nom, String prenom, int codCatInter, int hServ, int maxHeure) {
		this.codInter = 1;
		this.nom = nom;
		this.prenom = prenom;
		this.codCatInter = codCatInter;
		this.hServ = hServ;
		this.maxHeure = maxHeure;
	}
		public Intervenant( String nom, String prenom, int hServ, int maxHeure) {
		this.codInter = 1;
		this.nom = nom;
		this.prenom = prenom;
		this.codCatInter = 1;
		this.hServ = hServ;
		this.maxHeure = maxHeure;
	}

	//methode getteur setteur
	public int getcodInter() {
		return this.codInter;
	}

	public void setcodInter(int codInter) {
		this.codInter = codInter;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenon(String prenom) {
		this.prenom = prenom;
	}

	public int getCodCatInter() {
		return codCatInter;
	}

	public void setCodCatInter(int codCatInter) {
		this.codCatInter = codCatInter;
	}

	public int gethServ() {
		return hServ;
	}

	public void sethServ(int hServ) {
		this.hServ = hServ;
	}

	public int getMaxHeure() {
		return maxHeure;
	}

	public void setMaxHeure(int maxHeure) {
		this.maxHeure = maxHeure;
	}
}
