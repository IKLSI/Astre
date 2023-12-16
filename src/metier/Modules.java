package metier;

public class Modules
{
	private String codSem;
	private String codMod;
	private String libLong;	
	private String libCourt;
	private Integer nbHPnCM;
	private Integer nbHPnTD;	
	private Integer nbHPnTP;
	private Integer nbSemaineTD;
	private Integer nbSemaineTP;
	private Integer nbSemaineCM;
	private Integer nbHParSemaineTD;
	private Integer nbHParSemaineTP;
	private Integer nbHParSemaineCM;
	private Integer hPonctuelle;
	private String hAP;
	private boolean valid;
	private Integer nbHPnSaeParSemestre;
	private Integer nbHPnTutParSemestre;
	private Integer nbHSaeParSemestre;
	private Integer nbHTutParSemestre;
	private Integer nbHPnREH;
	private Integer nbHPnTut;
	private Integer nbHREH;
	private Integer nbHTut;
	private Integer nbHPnHTut;

	public Modules (String codMod, String libLong, String hAP, boolean valid)
	{
		this.codSem = "";
		this.codMod = codMod;
		this.libLong = libLong;
		this.hAP = hAP;
		this.valid = valid;
	}

	public Modules (String codSem,String codMod, String libLong, String hAP, boolean valid)
	{
		this.codSem = codSem;
		this.codMod = codMod;
		this.libLong = libLong;
		this.hAP = hAP;
		this.valid = valid;
	}

	public Modules (String codMod, String libLong, String libCourt, boolean valid, int nbHPnCM, int nbHPnTD, int nbHPnTP, int nbSemaineTD, int nbSemaineTP, int nbSemaineCM, int nbHParSemaineTD, int nbHParSemaineTP, int nbHParSemaineCM, int hPonctuelle, int nbHPnSaeParSemestre, int nbHPnTutParSemestre, int nbHSaeParSemestre, int nbHTutParSemestre, int nbHPnREH, int nbHPnTut, int nbHREH, int nbHTut, int nbHPnHTut)
	{
		this.codMod = codMod;
		this.libLong = libLong;
		this.libCourt = libCourt;
		this.valid = valid;
		this.nbHPnCM = nbHPnCM;
		this.nbHPnTD = nbHPnTD;
		this.nbHPnTP = nbHPnTP;
		this.nbSemaineTD = nbSemaineTD;
		this.nbSemaineTP = nbSemaineTP;
		this.nbSemaineCM = nbSemaineCM;
		this.nbHParSemaineTD = nbHParSemaineTD;
		this.nbHParSemaineTP = nbHParSemaineTP;
		this.nbHParSemaineCM = nbHParSemaineCM;
		this.hPonctuelle = hPonctuelle;
		this.nbHPnSaeParSemestre = nbHPnSaeParSemestre;
		this.nbHPnTutParSemestre = nbHPnTutParSemestre;
		this.nbHSaeParSemestre = nbHSaeParSemestre;
		this.nbHTutParSemestre = nbHTutParSemestre;
		this.nbHPnREH = nbHPnREH;
		this.nbHPnTut = nbHPnTut;
		this.nbHREH = nbHREH;
		this.nbHTut = nbHTut;
		this.nbHPnHTut = nbHPnHTut;
	}

	public void setCodSem(String codSem) { this.codSem = codSem; }
	public void setCodMod(String codMod) { this.codMod = codMod; }
	public void setLibLong(String libLong) { this.libLong = libLong; }
	public void setHAP(String hAP) { this.hAP = hAP; }
	public void setValid(boolean b) { this.valid = b; }
	public void setLibCourt(String String) { this.libCourt = String; }
	public void setNbHPnCM(int nbHPnCM) {  this.nbHPnCM = nbHPnCM; }
	public void setNbHPnTD(int nbHPnTD) {  this.nbHPnTD = nbHPnTD; }
	public void setNbHPnTP(int nbHPnTP) {  this.nbHPnTP= nbHPnTP; }
	public void setNbSemaineTD(int nbSemaineTD) {  this.nbSemaineTD = nbSemaineTD; }
	public void setNbSemaineTP(int nbSemaineTP) {  this.nbSemaineTP = nbSemaineTP; }
	public void setNbSemaineCM(int nbSemaineCM) {  this.nbSemaineCM = nbSemaineCM; }
	public void setNbHParSemaineTD(int nbHParSemaineTD) {  this.nbHParSemaineTD = nbHParSemaineTD; }
	public void setNbHParSemaineTP(int nbHParSemaineTP) {  this.nbHParSemaineTP = nbHParSemaineTP; }
	public void setNbHParSemaineCM(int nbHParSemaineCM) {  this.nbHParSemaineCM = nbHParSemaineCM; }
	public void setHPonctuelle(int hPonctuelle) {  this.hPonctuelle = hPonctuelle; }
	public void setNbHPnSaeParSemestre(int nbHPnSaeParSemestre) {  this.nbHPnSaeParSemestre = nbHPnSaeParSemestre; }
	public void setNbHPnTutParSemestre(int nbHPnTutParSemestre) {  this.nbHPnTutParSemestre = nbHPnTutParSemestre; }
	public void setNbHSaeParSemestre(int nbHSaeParSemestre) {  this.nbHSaeParSemestre = nbHSaeParSemestre; }
	public void setNbHTutParSemestre(int nbHTutParSemestre) {  this.nbHTutParSemestre = nbHTutParSemestre; }
	public void setNbHPnREH(int nbHPnREH) {  this.nbHPnREH = nbHPnREH; }
	public void setNbHPnTut(int nbHPnTut) {  this.nbHPnTut = nbHPnTut; }
	public void setNbHREH(int nbHREH) {  this.nbHREH = nbHREH; }
	public void setNbHTut(int nbHTut) {  this.nbHTut = nbHTut; }
	public void setNbHPnHTut(int nbHPnHTut) { this.nbHPnHTut = nbHPnHTut; }

	public String getCodSem() { return this.codSem; }
	public String getCodMod() { return this.codMod; }
	public String getLibLong() { return this.libLong; }
	public String getHAP() { return this.hAP; }
	public Boolean getValid() { return this.valid; }
	public String getLibCourt() { return this.libCourt; }
	public int getNbHPnCM() { return this.nbHPnCM; }
	public int getNbHPnTD() { return this.nbHPnTD; }
	public int getNbHPnTP() { return this.nbHPnTP; }
	public int getNbSemaineTD() { return this.nbSemaineTD; }
	public int getNbSemaineTP() { return this.nbSemaineTP; }
	public int getNbSemaineCM() { return this.nbSemaineCM; }
	public int getNbHParSemaineTD() { return this.nbHParSemaineTD; }
	public int getNbHParSemaineTP() { return this.nbHParSemaineTP; }
	public int getNbHParSemaineCM() { return this.nbHParSemaineCM; }
	public int getHPonctuelle() { return this.hPonctuelle; }
	public int getNbHPnSaeParSemestre() { return this.nbHPnSaeParSemestre; }
	public int getNbHPnTutParSemestre() { return this.nbHPnTutParSemestre; }
	public int getNbHSaeParSemestre() { return this.nbHSaeParSemestre; }
	public int getNbHTutParSemestre() { return this.nbHTutParSemestre; }
	public int getNbHPnREH() { return this.nbHPnREH; }
	public int getNbHPnTut() { return this.nbHPnTut; }
	public int getNbHREH() { return this.nbHREH; }
	public int getNbHTut() { return this.nbHTut; }
	public int getNbHPnHTut() { return this.nbHPnHTut; }
}