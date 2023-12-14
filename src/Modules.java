public class Modules
{
	private String codMod;
	private String libLong;
	private String hAP;
	private String valid;

	// Ajoutez des getters et des setters appropriés

	public Modules(String codMod, String libLong, String hAP, String valid) {
		this.codMod = codMod;
		this.libLong = libLong;
		this.hAP = hAP;
		this.valid = valid;
	}

	public String getCodMod() {
		return codMod;
	}

	public void setCodMod(String codMod) {
		this.codMod = codMod;
	}

	public String getLibLong() {
		return libLong;
	}

	public void setLibLong(String libLong) {
		this.libLong = libLong;
	}

	public String gethAP() {
		return hAP;
	}

	public void sethAP(String hAP) {
		this.hAP = hAP;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
}