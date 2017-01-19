package it.webred.cs.csa.web.manbean.report.dto.cartella;

import it.webred.cs.csa.web.manbean.report.dto.BasePdfDTO;

public class ParentePdfDTO extends BasePdfDTO {

	private String cognomeP = EMPTY_VALUE;
	private String nomeP = EMPTY_VALUE;
	private String dataNascitaP = EMPTY_VALUE;
	private	String luogoNascitaP = EMPTY_VALUE;
	private String sessoP = EMPTY_VALUE;
	private String codFiscaleP = EMPTY_VALUE;
	private String cittadinanzaP = EMPTY_VALUE;
	private String residenzaP = EMPTY_VALUE;
	private String telefonoP = EMPTY_VALUE;
	private String cellulareP = EMPTY_VALUE;
	private String emailP = EMPTY_VALUE;
	private String parentelaP = EMPTY_VALUE;
	private String decessoP = EMPTY_VALUE;
	
	public String getCognomeP() {
		return cognomeP;
	}
	public void setCognomeP(String cognomeP) {
		this.cognomeP = format(cognomeP);
	}
	public String getNomeP() {
		return nomeP;
	}
	public void setNomeP(String nomeP) {
		this.nomeP = format(nomeP);
	}
	public String getDataNascitaP() {
		return dataNascitaP;
	}
	public void setDataNascitaP(String dataNascitaP) {
		this.dataNascitaP = format(dataNascitaP);
	}
	public String getLuogoNascitaP() {
		return luogoNascitaP;
	}
	public void setLuogoNascitaP(String luogoNascitaP) {
		this.luogoNascitaP = format(luogoNascitaP);
	}
	public String getSessoP() {
		return sessoP;
	}
	public void setSessoP(String sessoP) {
		this.sessoP = format(sessoP);
	}
	public String getCodFiscaleP() {
		return codFiscaleP;
	}
	public void setCodFiscaleP(String codFiscaleP) {
		this.codFiscaleP = format(codFiscaleP);
	}
	public String getCittadinanzaP() {
		return cittadinanzaP;
	}
	public void setCittadinanzaP(String cittadinanzaP) {
		this.cittadinanzaP = format(cittadinanzaP);
	}
	public String getResidenzaP() {
		return residenzaP;
	}
	public void setResidenzaP(String residenzaP) {
		this.residenzaP = format(residenzaP);
	}
	public String getTelefonoP() {
		return telefonoP;
	}
	public void setTelefonoP(String telefonoP) {
		this.telefonoP = format(telefonoP);
	}
	public String getCellulareP() {
		return cellulareP;
	}
	public void setCellulareP(String cellulareP) {
		this.cellulareP = format(cellulareP);
	}
	public String getEmailP() {
		return emailP;
	}
	public void setEmailP(String emailP) {
		this.emailP = format(emailP);
	}
	public String getParentelaP() {
		return parentelaP;
	}
	public void setParentelaP(String parentelaP) {
		this.parentelaP = format(parentelaP);
	}
	public String getDecessoP() {
		return decessoP;
	}
	public void setDecessoP(String decessoP) {
		this.decessoP = format(decessoP);
	}
}
