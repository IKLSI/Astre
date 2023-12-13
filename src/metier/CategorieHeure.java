package metier;

public class CategorieHeure {

	//attributs d'instances
	private int    codCatHeure;
	private String nomCatHeure;
	private int    coeffNum;
	private int    coeffDen;

	// Constructor
	public CategorieHeure(int codCatHeure, String nomCatHeure, int coeffNum, int coeffDen) {
		this.codCatHeure = codCatHeure;
		this.nomCatHeure = nomCatHeure;
		this.coeffNum = coeffNum;
		this.coeffDen = coeffDen;
	}

	// Getter methods
	public int getCodCatHeure() {
		return codCatHeure;
	}

	public String getNomCatHeure() {
		return nomCatHeure;
	}

	public int getCoeffNum() {
		return coeffNum;
	}

	public int getCoeffDen() {
		return coeffDen;
	}

	// Setter methods
	public void setCodCatHeure(int codCatHeure) {
		this.codCatHeure = codCatHeure;
	}

	public void setNomCatHeure(String nomCatHeure) {
		this.nomCatHeure = nomCatHeure;
	}

	public void setCoeffNum(int coeffNum) {
		this.coeffNum = coeffNum;
	}

	public void setCoeffDen(int coeffDen) {
		this.coeffDen = coeffDen;
	}
}