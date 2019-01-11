package it.webred.cs.json.familiariConviventi;

import it.webred.cs.csa.utils.bean.report.dto.StoricoPdfDTO;

public class DatiSocialiFamiliariConviventiPdfDTO extends StoricoPdfDTO {

	private String versioneFamConv = null;
	
	private String tipologiaNucleo = EMPTY_VALUE;
	private Boolean haFigli = false;
	private String numeroMinori = EMPTY_VALUE;
	private String gruppoVulnerabile = EMPTY_VALUE;
	private String gruppoVulnerabile2 = EMPTY_VALUE;
	private String gruppoVulnerabile3 = EMPTY_VALUE;
	
	private String numeroFigli = EMPTY_VALUE;
	private Boolean coniugeOCompagno = true;
	private String genitoriOAffidatari = EMPTY_VALUE;
	private String altriParenti = EMPTY_VALUE;
	private String altriConviventiItaliani = EMPTY_VALUE;
	private String altriConviventiStranieri = EMPTY_VALUE;
	
	private int minItaM = 0;
	private int minItaF = 0;
	private int minExtM = 0;
	private int minExtF = 0;
	private int magItaM = 0;
	private int magItaF = 0;
	private int magExtM = 0;
	private int magExtF = 0;


	public String getTipologiaNucleo() {
		return tipologiaNucleo;
	}



	public void setTipologiaNucleo(String tipologiaNucleo) {
		this.tipologiaNucleo = tipologiaNucleo;
	}



	public Boolean getHaFigli() {
		return haFigli;
	}



	public void setHaFigli(Boolean haFigli) {
		this.haFigli = haFigli;
	}



	public String getNumeroMinori() {
		return numeroMinori;
	}



	public void setNumeroMinori(String numeroMinori) {
		this.numeroMinori = numeroMinori;
	}



	public String getGruppoVulnerabile() {
		return gruppoVulnerabile;
	}



	public void setGruppoVulnerabile(String gruppoVulnerabile) {
		this.gruppoVulnerabile = gruppoVulnerabile;
	}



	public String getNumeroFigli() {
		return numeroFigli;
	}



	public void setNumeroFigli(String numeroFigli) {
		this.numeroFigli = numeroFigli;
	}



	public Boolean getConiugeOCompagno() {
		return coniugeOCompagno;
	}



	public void setConiugeOCompagno(Boolean coniugeOCompagno) {
		this.coniugeOCompagno = coniugeOCompagno;
	}



	public String getGenitoriOAffidatari() {
		return genitoriOAffidatari;
	}



	public void setGenitoriOAffidatari(String genitoriOAffidatari) {
		this.genitoriOAffidatari = genitoriOAffidatari;
	}



	public String getAltriParenti() {
		return altriParenti;
	}



	public void setAltriParenti(String altriParenti) {
		this.altriParenti = altriParenti;
	}



	public String getAltriConviventiItaliani() {
		return altriConviventiItaliani;
	}



	public void setAltriConviventiItaliani(String altriConviventiItaliani) {
		this.altriConviventiItaliani = altriConviventiItaliani;
	}



	public String getAltriConviventiStranieri() {
		return altriConviventiStranieri;
	}



	public void setAltriConviventiStranieri(String altriConviventiStranieri) {
		this.altriConviventiStranieri = altriConviventiStranieri;
	}



	public String getVersioneFamConv() {
		return versioneFamConv;
	}

	public void setVersioneFamConv(String versioneFamConv) {
		this.versioneFamConv = versioneFamConv;
	}



	public int getMinItaM() {
		return minItaM;
	}



	public void setMinItaM(int minItaM) {
		this.minItaM = minItaM;
	}



	public int getMinItaF() {
		return minItaF;
	}



	public void setMinItaF(int minItaF) {
		this.minItaF = minItaF;
	}



	public int getMinExtM() {
		return minExtM;
	}



	public void setMinExtM(int minExtM) {
		this.minExtM = minExtM;
	}



	public int getMinExtF() {
		return minExtF;
	}



	public void setMinExtF(int minExtF) {
		this.minExtF = minExtF;
	}



	public int getMagItaM() {
		return magItaM;
	}



	public void setMagItaM(int magItaM) {
		this.magItaM = magItaM;
	}



	public int getMagItaF() {
		return magItaF;
	}



	public void setMagItaF(int magItaF) {
		this.magItaF = magItaF;
	}



	public int getMagExtM() {
		return magExtM;
	}



	public void setMagExtM(int magExtM) {
		this.magExtM = magExtM;
	}



	public int getMagExtF() {
		return magExtF;
	}



	public void setMagExtF(int magExtF) {
		this.magExtF = magExtF;
	}



	public String getGruppoVulnerabile2() {
		return gruppoVulnerabile2;
	}



	public void setGruppoVulnerabile2(String gruppoVulnerabile2) {
		this.gruppoVulnerabile2 = gruppoVulnerabile2;
	}



	public String getGruppoVulnerabile3() {
		return gruppoVulnerabile3;
	}



	public void setGruppoVulnerabile3(String gruppoVulnerabile3) {
		this.gruppoVulnerabile3 = gruppoVulnerabile3;
	}
	
	

}
