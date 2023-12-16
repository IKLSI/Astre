package metier;

public class Module
{
	// Attributs d'intance

	private String codMod;
	private int codTypMod;
	private String codSem;
	private String libLong;
	private String libCourt;
	private boolean valid;
	private int nbHParSemaineTD;
	private int nbHParSemaineTP;
	private int nbHParSemaineCM;
	private int nbHParSemaineHTut;
	private int nbHPnSaeParSemestre;
	private int nbHPnTutParSemestre;
	private int nbHREH;
	private int nbHTut;

	// Constructeur

	public Module(String codMod, int codTypMod, String codSem, String libLong, String libCourt, boolean valid,
				  int nbHParSemaineTD, int nbHParSemaineTP, int nbHParSemaineCM, int nbHParSemaineHTut,
				  int nbHPnSaeParSemestre, int nbHPnTutParSemestre, int nbHREH, int nbHTut)
	{
		this.codMod = codMod;
		this.codTypMod = codTypMod;
		this.codSem = codSem;
		this.libLong = libLong;
		this.libCourt = libCourt;
		this.valid = valid;
		this.nbHParSemaineTD = nbHParSemaineTD;
		this.nbHParSemaineTP = nbHParSemaineTP;
		this.nbHParSemaineCM = nbHParSemaineCM;
		this.nbHParSemaineHTut = nbHParSemaineHTut;
		this.nbHPnSaeParSemestre = nbHPnSaeParSemestre;
		this.nbHPnTutParSemestre = nbHPnTutParSemestre;
		this.nbHREH = nbHREH;
		this.nbHTut = nbHTut;
	}

	// Getter
	public String getCodMod() { return this.codMod; }

	public int getCodTypMod() { return this.codTypMod; }

	public String getcodSem() { return this.codSem; }

	public String getLibLong() { return this.libLong; }

	public String getLibCourt() { return this.libCourt; }

	public boolean isValid() { return this.valid; }

	public int getNbHParSemaineTD() { return this.nbHParSemaineTD; }

	public int getNbHParSemaineTP() { return this.nbHParSemaineTP; }

	public int getNbHParSemaineCM() { return this.nbHParSemaineCM; }

	public int getNbHParSemaineHTut() { return this.nbHParSemaineHTut; }

	public int getNbHPnSaeParSemestre() { return this.nbHPnSaeParSemestre; }

	public int getNbHPnTutParSemestre() { return this.nbHPnTutParSemestre; }

	public int getNbHREH() { return this.nbHREH; }

	public int getNbHTut() { return this.nbHTut; }

	// Setter

	public void setCodMod(String codMod) { this.codMod = codMod; }

	public void setCodTypMod(int codTypMod) { this.codTypMod = codTypMod; }

	public void setcodSem(String codSem) { this.codSem = codSem; }

	public void setLibLong(String libLong) { this.libLong = libLong; }

	public void setLibCourt(String libCourt) { this.libCourt = libCourt; }

	public void setValid(boolean valid) { this.valid = valid; }

	public void setNbHParSemaineTD(int nbHParSemaineTD) { this.nbHParSemaineTD = nbHParSemaineTD; }

	public void setNbHParSemaineTP(int nbHParSemaineTP) { this.nbHParSemaineTP = nbHParSemaineTP; }

	public void setNbHParSemaineCM(int nbHParSemaineCM) { this.nbHParSemaineCM = nbHParSemaineCM; }

	public void setNbHParSemaineHTut(int nbHParSemaineHTut) { this.nbHParSemaineHTut = nbHParSemaineHTut; }

	public void setNbHPnSaeParSemestre(int nbHPnSaeParSemestre) { this.nbHPnSaeParSemestre = nbHPnSaeParSemestre; }

	public void setNbHPnTutParSemestre(int nbHPnTutParSemestre) { this.nbHPnTutParSemestre = nbHPnTutParSemestre; }

	public void setNbHREH(int nbHREH) { this.nbHREH = nbHREH; }

	public void setNbHTut(int nbHTut) { this.nbHTut = nbHTut; }
}