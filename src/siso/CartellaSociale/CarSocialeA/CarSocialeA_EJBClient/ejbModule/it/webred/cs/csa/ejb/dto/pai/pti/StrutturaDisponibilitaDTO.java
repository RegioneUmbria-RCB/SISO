package it.webred.cs.csa.ejb.dto.pai.pti;

import java.io.Serializable;


public class StrutturaDisponibilitaDTO implements Serializable {

	/**
	 * DTO per join tra V_Struttura_Area e AR_CS_PAI_PTI_RICH_DISP
	 */
	private static final long serialVersionUID = 1L;

	// Struttura
	private Long idStruttura;

	private String abilitato;

	private String indirizzo;

	private String nomeStruttura;

	private String flagAttrezzato;

	private long tipoStruttura;

	private String tipologiaServizio;

	private String gestoreServizio;

	private String telefonoFissoGestore;

	private String mailGestore;

	private String pecGestore;

	private String coordinatore;

	private String telefonoFissoCoordinatore;

	private String mailCoordinatore;

	private String codComune;

	private String descrizioneComune;
	
	private String codBelfioreComune;
	
	private Long idMacroTipologiaServizio;

	private Long idTipologiaServizio;
	
	// Richiesta disponibilità
	private RichiestaDisponibilitaPaiPtiDTO richiesteDisponibilita;

	public Long getIdStruttura() {
		return idStruttura;
	}

	public void setIdStruttura(Long idStruttura) {
		this.idStruttura = idStruttura;
	}

	public String getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNomeStruttura() {
		return nomeStruttura;
	}

	public void setNomeStruttura(String nomeStruttura) {
		this.nomeStruttura = nomeStruttura;
	}

	public long getTipoStruttura() {
		return tipoStruttura;
	}

	public void setTipoStruttura(long tipoStruttura) {
		this.tipoStruttura = tipoStruttura;
	}

	public String getTipologiaServizio() {
		return tipologiaServizio;
	}

	public void setTipologiaServizio(String tipologiaServizio) {
		this.tipologiaServizio = tipologiaServizio;
	}

	public String getGestoreServizio() {
		return gestoreServizio;
	}

	public void setGestoreServizio(String gestoreServizio) {
		this.gestoreServizio = gestoreServizio;
	}

	public String getTelefonoFissoGestore() {
		return telefonoFissoGestore;
	}

	public void setTelefonoFissoGestore(String telefonoFissoGestore) {
		this.telefonoFissoGestore = telefonoFissoGestore;
	}

	public String getMailGestore() {
		return mailGestore;
	}

	public void setMailGestore(String mailGestore) {
		this.mailGestore = mailGestore;
	}

	public String getPecGestore() {
		return pecGestore;
	}

	public void setPecGestore(String pecGestore) {
		this.pecGestore = pecGestore;
	}

	public String getCoordinatore() {
		return coordinatore;
	}

	public void setCoordinatore(String coordinatore) {
		this.coordinatore = coordinatore;
	}

	public String getTelefonoFissoCoordinatore() {
		return telefonoFissoCoordinatore;
	}

	public void setTelefonoFissoCoordinatore(String telefonoFissoCoordinatore) {
		this.telefonoFissoCoordinatore = telefonoFissoCoordinatore;
	}

	public String getMailCoordinatore() {
		return mailCoordinatore;
	}

	public void setMailCoordinatore(String mailCoordinatore) {
		this.mailCoordinatore = mailCoordinatore;
	}

	public String getCodComune() {
		return codComune;
	}

	public void setCodComune(String codComune) {
		this.codComune = codComune;
	}

	public String getDescrizioneComune() {
		return descrizioneComune;
	}

	public void setDescrizioneComune(String descrizioneComune) {
		this.descrizioneComune = descrizioneComune;
	}

	public String getFlagAttrezzato() {
		return flagAttrezzato;
	}

	public void setFlagAttrezzato(String flagAttrezzato) {
		this.flagAttrezzato = flagAttrezzato;
	}

	public RichiestaDisponibilitaPaiPtiDTO getRichiesteDisponibilita() {
		return richiesteDisponibilita;
	}

	public void setRichiesteDisponibilita(RichiestaDisponibilitaPaiPtiDTO richiesteDisponibilita) {
		this.richiesteDisponibilita = richiesteDisponibilita;
	}
	
	public String getCodBelfioreComune() {
		return codBelfioreComune;
	}

	public void setCodBelfioreComune(String codBelfioreComune) {
		this.codBelfioreComune = codBelfioreComune;
	}

	public Long getIdMacroTipologiaServizio() {
		return idMacroTipologiaServizio;
	}

	public void setIdMacroTipologiaServizio(Long idMacroTipologiaServizio) {
		this.idMacroTipologiaServizio = idMacroTipologiaServizio;
	}

	public Long getIdTipologiaServizio() {
		return idTipologiaServizio;
	}

	public void setIdTipologiaServizio(Long idTipologiaServizio) {
		this.idTipologiaServizio = idTipologiaServizio;
	}

	@Override
	public String toString() {
		return "StrutturaDisponibilitaDTO [idStruttura=" + idStruttura + ", abilitato=" + abilitato + ", indirizzo="
				+ indirizzo + ", nomeStruttura=" + nomeStruttura + ", flagAttrezzato=" + flagAttrezzato
				+ ", tipoStruttura=" + tipoStruttura + ", tipologiaServizio=" + tipologiaServizio + ", gestoreServizio="
				+ gestoreServizio + ", telefonoFissoGestore=" + telefonoFissoGestore + ", mailGestore=" + mailGestore
				+ ", pecGestore=" + pecGestore + ", coordinatore=" + coordinatore + ", telefonoFissoCoordinatore="
				+ telefonoFissoCoordinatore + ", mailCoordinatore=" + mailCoordinatore + ", codComune=" + codComune
				+ ", descrizioneComune=" + descrizioneComune + ", richiesteDisponibilita=" + richiesteDisponibilita
				+ "]";
	}

	public boolean isAccettataFromCS() {
		if (richiesteDisponibilita != null) {
			if ("ACCETTATA".equalsIgnoreCase(richiesteDisponibilita.getStatoRichiesta())) {
				return true;
			}
		}
		return false;
	}

	public boolean isAccettataFromStrutt() {
		if (richiesteDisponibilita != null) {
			if ("ACCETTATA_STRUTTURA".equalsIgnoreCase(richiesteDisponibilita.getStatoRichiesta())) {
				return true;
			}
		}
		return false;
	}

	public boolean isRifiutataFromStrutt() {
		if (richiesteDisponibilita != null) {
			if ("RIFIUTATA_STRUTTURA".equalsIgnoreCase(richiesteDisponibilita.getStatoRichiesta())) {
				return true;
			}
		}
		return false;
	}

}
