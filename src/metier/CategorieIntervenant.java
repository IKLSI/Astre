package metier;

public class CategorieIntervenant
{
	// Attributs d'instance

	private int codCatInter;
	private String nomCat;
	private int service;
	private int maxHeure;
	private int ratioTPCatInterNum;
	private int ratioTPCatInterDen;

	// Constructeur

	public CategorieIntervenant( int codCatInter,String nomCat, int service, int maxHeure, int ratioTPCatInterNum, int ratioTPCatInterDen)
	{
		this.codCatInter = codCatInter;
		this.nomCat = nomCat;
		this.service = service;
		this.maxHeure = maxHeure;
		this.ratioTPCatInterNum = ratioTPCatInterNum;
		this.ratioTPCatInterDen = ratioTPCatInterDen;
	}

	// Getter

	public int getCodCatInter() { return this.codCatInter; }

	public String getNomCat() { return this.nomCat; }

	public int getService() { return this.service; }

	public int getMaxHeure() { return this.maxHeure; }

	public int getRatioTPCatInterNum() { return this.ratioTPCatInterNum; }

	public int getRatioTPCatInterDen() { return this.ratioTPCatInterDen; }

	// Setter

	public void setCodCatInter(int codCatInter) { this.codCatInter = codCatInter; }

	public void setNomCat(String nomCat) { this.nomCat = nomCat; }

	public void setService(int service) { this.service = service; }

	public void setMaxHeure(int maxHeure) { this.maxHeure = maxHeure; }

	public void setRatioTPCatInterNum(int ratioTPCatInterNum) { this.ratioTPCatInterNum = ratioTPCatInterNum; }

	public void setRatioTPCatInterDen(int ratioTPCatInterDen) { this.ratioTPCatInterDen = ratioTPCatInterDen; }
}

