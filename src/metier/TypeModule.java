package metier;

public class TypeModule
{
	// Attributs d'instances

	private int    codTypMod;
	private String nomTypMod;

	// Constructeur

	public TypeModule(int codTypMod, String nomTypMod)
	{
		this.codTypMod = codTypMod;
		this.nomTypMod = nomTypMod;
	}

	// Getteurs

	public int getCodTypMod() { return this.codTypMod; }
	public String getNomTypMod() { return this.nomTypMod; }

	// Setteurs

	public void setCodTypMod(int codTypMod) { this.codTypMod = codTypMod; }
	public void setNomTypMod(String nomTypMod) { this.nomTypMod = nomTypMod; }
}