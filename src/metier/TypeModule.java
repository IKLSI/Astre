package metier;

public class TypeModule {

	//attributs d'instances
	private int    codTypMod;
	private String nomTypMod;

	//constructeur
	public TypeModule(int codTypMod, String nomTypMod){
		this.codTypMod = codTypMod;
		this.nomTypMod = nomTypMod;
	}

	// getteurs
	public int getCodTypMod(){
		return this.codTypMod;
	}
	public String getNomTypMod(){
		return this.nomTypMod;
	}
}