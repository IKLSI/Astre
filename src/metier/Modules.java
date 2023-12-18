package metier;

public class Modules
{
	private String codSem;
	private String codMod;
	private String libLong;
	private String hAP;
	private boolean valid;

	// Ajoutez des getters et des setters appropriés

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

	public String getCodSem() { return codSem; }

	public void setCodSem(String codSem) { this.codSem = codSem; }

	public String getCodMod() { return this.codMod; }

	public void setCodMod(String codMod) { this.codMod = codMod; }

	public String getLibLong() { return libLong; }

	public void setLibLong(String libLong) { this.libLong = libLong; }

	public String getHAP() { return hAP; }

	public void setHAP(String hAP) { this.hAP = hAP; }

	public boolean getValid() { return valid; }

	public void setValid(boolean b) { this.valid = b; }
}