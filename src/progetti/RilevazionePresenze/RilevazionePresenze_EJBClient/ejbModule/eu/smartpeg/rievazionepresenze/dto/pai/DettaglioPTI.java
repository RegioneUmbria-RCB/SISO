package eu.smartpeg.rievazionepresenze.dto.pai;

import java.io.Serializable;
import java.util.Date;

public class DettaglioPTI implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7656701790116048885L;
	private Date dataRedazione;
	private boolean flgEmergenza;
	private boolean flgCondVerifPresenzaAdulti;
	private boolean flgCoinvFamiglia;
	private boolean flgDisabilita;
	private Date periodoInsPianificatoDa;
	private Date periodoInsPianificatoA;
	private boolean flgInterventiDisabili;
	private boolean flgGravidanza;
	private boolean flgNeonato;
	private boolean flgAreaPenale;
	private String caseManager;
	private boolean flgProrRichMagg;
	private boolean flgProrLimiteEta;
	private boolean flgEsisteEduPeda;
	private boolean flgInvioSegnTM;
	private Long faseAttuale;
	private String descTipoMinore;

	public DettaglioPTI() {

	}

	public Date getDataRedazione() {
		return dataRedazione;
	}

	public void setDataRedazione(Date dataRedazione) {
		this.dataRedazione = dataRedazione;
	}

	public boolean isFlgEmergenza() {
		return flgEmergenza;
	}

	public void setFlgEmergenza(boolean flgEmergenza) {
		this.flgEmergenza = flgEmergenza;
	}

	public boolean isFlgCondVerifPresenzaAdulti() {
		return flgCondVerifPresenzaAdulti;
	}

	public void setFlgCondVerifPresenzaAdulti(boolean flgCondVerifPresenzaAdulti) {
		this.flgCondVerifPresenzaAdulti = flgCondVerifPresenzaAdulti;
	}

	public boolean isFlgCoinvFamiglia() {
		return flgCoinvFamiglia;
	}

	public void setFlgCoinvFamiglia(boolean flgCoinvFamiglia) {
		this.flgCoinvFamiglia = flgCoinvFamiglia;
	}

	public boolean isFlgDisabilita() {
		return flgDisabilita;
	}

	public void setFlgDisabilita(boolean flgDisabilita) {
		this.flgDisabilita = flgDisabilita;
	}

	public Date getPeriodoInsPianificatoDa() {
		return periodoInsPianificatoDa;
	}

	public void setPeriodoInsPianificatoDa(Date periodoInsPianificatoDa) {
		this.periodoInsPianificatoDa = periodoInsPianificatoDa;
	}

	public Date getPeriodoInsPianificatoA() {
		return periodoInsPianificatoA;
	}

	public void setPeriodoInsPianificatoA(Date periodoInsPianificatoA) {
		this.periodoInsPianificatoA = periodoInsPianificatoA;
	}

	public boolean isFlgInterventiDisabili() {
		return flgInterventiDisabili;
	}

	public void setFlgInterventiDisabili(boolean flgInterventiDisabili) {
		this.flgInterventiDisabili = flgInterventiDisabili;
	}

	public boolean isFlgGravidanza() {
		return flgGravidanza;
	}

	public void setFlgGravidanza(boolean flgGravidanza) {
		this.flgGravidanza = flgGravidanza;
	}

	public boolean isFlgNeonato() {
		return flgNeonato;
	}

	public void setFlgNeonato(boolean flgNeonato) {
		this.flgNeonato = flgNeonato;
	}

	public boolean isFlgAreaPenale() {
		return flgAreaPenale;
	}

	public void setFlgAreaPenale(boolean flgAreaPenale) {
		this.flgAreaPenale = flgAreaPenale;
	}

	public String getCaseManager() {
		return caseManager;
	}

	public void setCaseManager(String caseManager) {
		this.caseManager = caseManager;
	}

	public boolean isFlgProrRichMagg() {
		return flgProrRichMagg;
	}

	public void setFlgProrRichMagg(boolean flgProrRichMagg) {
		this.flgProrRichMagg = flgProrRichMagg;
	}

	public boolean isFlgProrLimiteEta() {
		return flgProrLimiteEta;
	}

	public void setFlgProrLimiteEta(boolean flgProrLimiteEta) {
		this.flgProrLimiteEta = flgProrLimiteEta;
	}

	public boolean isFlgEsisteEduPeda() {
		return flgEsisteEduPeda;
	}

	public void setFlgEsisteEduPeda(boolean flgEsisteEduPeda) {
		this.flgEsisteEduPeda = flgEsisteEduPeda;
	}

	public boolean isFlgInvioSegnTM() {
		return flgInvioSegnTM;
	}

	public void setFlgInvioSegnTM(boolean flgInvioSegnTM) {
		this.flgInvioSegnTM = flgInvioSegnTM;
	}

	public Long getFaseAttuale() {
		return faseAttuale;
	}

	public void setFaseAttuale(Long faseAttuale) {
		this.faseAttuale = faseAttuale;
	}

	public String getDescTipoMinore() {
		return descTipoMinore;
	}

	public void setDescTipoMinore(String descTipoMinore) {
		this.descTipoMinore = descTipoMinore;
	}

}
