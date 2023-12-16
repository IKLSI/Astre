package metier;

public class CategorieHeure
{
	// Attributs d'instances

	private int    codCatHeure;
	private String nomCatHeure;
	private int    coeffNum;
	private int    coeffDen;

	// Constructeur

	public CategorieHeure(int codCatHeure, String nomCatHeure, int coeffNum, int coeffDen)
	{
		this.codCatHeure = codCatHeure;
		this.nomCatHeure = nomCatHeure;
		this.coeffNum = coeffNum;
		this.coeffDen = coeffDen;
	}

	// Getter

	public int getCodCatHeure() { return this.codCatHeure; }

	public String getNomCatHeure() { return this.nomCatHeure; }

	public int getCoeffNum() { return this.coeffNum; }

	public int getCoeffDen() { return this.coeffDen; }

	// Setter

	public void setCodCatHeure(int codCatHeure) { this.codCatHeure = codCatHeure; }

	public void setNomCatHeure(String nomCatHeure) { this.nomCatHeure = nomCatHeure; }

	public void setCoeffNum(int coeffNum) { this.coeffNum = coeffNum; }

	public void setCoeffDen(int coeffDen) { this.coeffDen = coeffDen; }
}