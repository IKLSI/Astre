package metier;

public class Modules
{
	private String codSem;
	private String codMod;
	private String libLong;
	private String hAP;
	private boolean valid;
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
	private Integer nbHPnSaeParSemestre;
	private Integer nbHPnTutParSemestre;
	private Integer nbHSaeParSemestre;
	private Integer nbHTutParSemestre;
	private Integer nbHPnREH;
	private Integer nbHPnTut;
	private Integer nbHREH;
	private Integer nbHTut;
	private Integer nbHPnHTut;
	private Integer codTypMod;

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

	//Constructeur global
	public Modules (String codMod, String codSem, Integer codTypMod, String libLong, String libCourt, boolean valid, Integer nbHPnCM, 
					Integer nbHPnTD, Integer nbHPnTP, Integer nbSemaineTD, Integer nbSemaineTP, Integer nbSemaineCM, Integer nbHParSemaineTD,
					Integer nbHParSemaineTP, Integer nbHParSemaineCM, Integer hPonctuelle, Integer nbHPnSaeParSemestre, Integer nbHPnTutParSemestre,
					Integer nbHSaeParSemestre, Integer nbHTutParSemestre, Integer nbHPnREH, Integer nbHPnTut, Integer nbHREH, Integer nbHTut,
					Integer nbHPnHTut)
	{
		this.codTypMod = codTypMod;
		this.codSem = codSem;
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
	public void setNbHPnCM(Integer nbHPnCM) {  this.nbHPnCM = nbHPnCM; }
	public void setNbHPnTD(Integer nbHPnTD) {  this.nbHPnTD = nbHPnTD; }
	public void setNbHPnTP(Integer nbHPnTP) {  this.nbHPnTP= nbHPnTP; }
	public void setNbSemaineTD(Integer nbSemaineTD) {  this.nbSemaineTD = nbSemaineTD; }
	public void setNbSemaineTP(Integer nbSemaineTP) {  this.nbSemaineTP = nbSemaineTP; }
	public void setNbSemaineCM(Integer nbSemaineCM) {  this.nbSemaineCM = nbSemaineCM; }
	public void setNbHParSemaineTD(Integer nbHParSemaineTD) {  this.nbHParSemaineTD = nbHParSemaineTD; }
	public void setNbHParSemaineTP(Integer nbHParSemaineTP) {  this.nbHParSemaineTP = nbHParSemaineTP; }
	public void setNbHParSemaineCM(Integer nbHParSemaineCM) {  this.nbHParSemaineCM = nbHParSemaineCM; }
	public void setHPonctuelle(Integer hPonctuelle) {  this.hPonctuelle = hPonctuelle; }
	public void setNbHPnSaeParSemestre(Integer nbHPnSaeParSemestre) {  this.nbHPnSaeParSemestre = nbHPnSaeParSemestre; }
	public void setNbHPnTutParSemestre(Integer nbHPnTutParSemestre) {  this.nbHPnTutParSemestre = nbHPnTutParSemestre; }
	public void setNbHSaeParSemestre(Integer nbHSaeParSemestre) {  this.nbHSaeParSemestre = nbHSaeParSemestre; }
	public void setNbHTutParSemestre(Integer nbHTutParSemestre) {  this.nbHTutParSemestre = nbHTutParSemestre; }
	public void setNbHPnREH(Integer nbHPnREH) {  this.nbHPnREH = nbHPnREH; }
	public void setNbHPnTut(Integer nbHPnTut) {  this.nbHPnTut = nbHPnTut; }
	public void setNbHREH(Integer nbHREH) {  this.nbHREH = nbHREH; }
	public void setNbHTut(Integer nbHTut) {  this.nbHTut = nbHTut; }
	public void setNbHPnHTut(Integer nbHPnHTut) { this.nbHPnHTut = nbHPnHTut; }
	public void setCodTypMod(Integer codTypMod) { this.codTypMod = codTypMod; }

	public String getCodSem() { return this.codSem; }
	public String getCodMod() { return this.codMod; }
	public String getLibLong() { return this.libLong; }
	public String getHAP() { return this.hAP; }
	public Boolean getValid() { return this.valid; }
	public String getLibCourt() { return this.libCourt; }
	public Integer getNbHPnCM() { return this.nbHPnCM; }
	public Integer getNbHPnTD() { return this.nbHPnTD; }
	public Integer getNbHPnTP() { return this.nbHPnTP; }
	public Integer getNbSemaineTD() { return this.nbSemaineTD; }
	public Integer getNbSemaineTP() { return this.nbSemaineTP; }
	public Integer getNbSemaineCM() { return this.nbSemaineCM; }
	public Integer getNbHParSemaineTD() { return this.nbHParSemaineTD; }
	public Integer getNbHParSemaineTP() { return this.nbHParSemaineTP; }
	public Integer getNbHParSemaineCM() { return this.nbHParSemaineCM; }
	public Integer getHPonctuelle() { return this.hPonctuelle; }
	public Integer getNbHPnSaeParSemestre() { return this.nbHPnSaeParSemestre; }
	public Integer getNbHPnTutParSemestre() { return this.nbHPnTutParSemestre; }
	public Integer getNbHSaeParSemestre() { return this.nbHSaeParSemestre; }
	public Integer getNbHTutParSemestre() { return this.nbHTutParSemestre; }
	public Integer getNbHPnREH() { return this.nbHPnREH; }
	public Integer getNbHPnTut() { return this.nbHPnTut; }
	public Integer getNbHREH() { return this.nbHREH; }
	public Integer getNbHTut() { return this.nbHTut; }
	public Integer getNbHPnHTut() { return this.nbHPnHTut; }
	public Integer getCodTypMod() { return this.codTypMod; }
}
