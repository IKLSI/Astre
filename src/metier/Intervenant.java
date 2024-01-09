package metier;

public class Intervenant
{
	// Attributs d'instance

	private Integer codInter;
	private String nom;
	private String prenom;
	private Integer codCatInter;
	private Integer hServ;
	private Integer maxHeure;
	private Integer annee;

	// Constructeur

	public Intervenant( Integer codInter, String nom, String prenom, Integer hServ, Integer maxHeure , Integer annee)
	{
		this.codInter = codInter;
		this.nom      = nom;
		this.prenom   = prenom;
		this.hServ    = hServ;
		this.maxHeure = maxHeure;
		this.annee    = annee;
	}

	public Intervenant( String nom, String prenom, Integer codCatInter, Integer hServ, Integer maxHeure, Integer annee)
	{
		this.codInter    = 1;
		this.nom         = nom;
		this.prenom      = prenom;
		this.codCatInter = codCatInter;
		this.hServ       = hServ;
		this.maxHeure    = maxHeure;
		this.annee       = annee;
	}

	// Méthode getteur setteur

	public Integer getcodInter()             { return this.codInter; }
	public void    setcodInter(int codInter) { this.codInter = codInter; }
	public String  getNom()                  { return this.nom; }
	public String  getPrenom()               { return this.prenom; }
	public Integer getCodCatInter()          { return this.codCatInter; }
	public Integer gethServ()                { return this.hServ; }
	public Integer getMaxHeure()             { return this.maxHeure; }
	public Integer getAnnee()                { return this.annee; }

	public void setNom(String nom)                  { this.nom = nom; }
	public void setPrenon(String prenom)            { this.prenom = prenom; }
	public void setCodCatInter(Integer codCatInter) { this.codCatInter = codCatInter; }
	public void sethServ(Integer hServ)             { this.hServ = hServ; }
	public void setMaxHeure(Integer maxHeure)       { this.maxHeure = maxHeure; }
	public void setAnnee(Integer annee)             { this.annee = annee;}
}