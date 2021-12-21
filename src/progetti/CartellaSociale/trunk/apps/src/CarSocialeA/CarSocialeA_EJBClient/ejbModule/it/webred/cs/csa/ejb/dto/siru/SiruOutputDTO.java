package it.webred.cs.csa.ejb.dto.siru;

import java.io.Serializable;
import java.util.Date;

public class SiruOutputDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String comuneNascitaId;

	private String nazioneNascitaId;
	
	private String cittadinanza;
	
	private String comuneResId;
	
	private String comuneDomId;

	private String condMercatoIngresso;
	
	private String durataRicerca;
	
	private String tipologiaLavoroId;
	
	private String tipoOrarioLavoroId;
	
	private String comuneAziendaId;
	
	private String codAtecoAnno;
	
	private String frmGiuridicaCod;
	
	private String dimensioneAziendaId;
	
	private String titoloStudio;
	
	private String codVulnerabilePa;
	
	private String statoPartecipante;
	
	private Character sesso;
	
	private Date dataNascita;
	
	private Integer flagResidenza;

	public String getComuneNascitaId() {
		return comuneNascitaId;
	}

	public void setComuneNascitaId(String comuneNascitaId) {
		this.comuneNascitaId = comuneNascitaId;
	}

	public String getNazioneNascitaId() {
		return nazioneNascitaId;
	}

	public void setNazioneNascitaId(String nazioneNascitaId) {
		this.nazioneNascitaId = nazioneNascitaId;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public String getComuneResId() {
		return comuneResId;
	}

	public void setComuneResId(String comuneResId) {
		this.comuneResId = comuneResId;
	}

	public String getComuneDomId() {
		return comuneDomId;
	}

	public void setComuneDomId(String comuneDomId) {
		this.comuneDomId = comuneDomId;
	}

	public String getCondMercatoIngresso() {
		return condMercatoIngresso;
	}

	public void setCondMercatoIngresso(String condMercatoIngresso) {
		this.condMercatoIngresso = condMercatoIngresso;
	}

	public String getDurataRicerca() {
		return durataRicerca;
	}

	public void setDurataRicerca(String durataRicerca) {
		this.durataRicerca = durataRicerca;
	}

	public String getTipologiaLavoroId() {
		return tipologiaLavoroId;
	}

	public void setTipologiaLavoroId(String tipologiaLavoroId) {
		this.tipologiaLavoroId = tipologiaLavoroId;
	}

	public String getTipoOrarioLavoroId() {
		return tipoOrarioLavoroId;
	}

	public void setTipoOrarioLavoroId(String tipoOrarioLavoroId) {
		this.tipoOrarioLavoroId = tipoOrarioLavoroId;
	}

	public String getComuneAziendaId() {
		return comuneAziendaId;
	}

	public void setComuneAziendaId(String comuneAziendaId) {
		this.comuneAziendaId = comuneAziendaId;
	}

	public String getCodAtecoAnno() {
		return codAtecoAnno;
	}

	public void setCodAtecoAnno(String codAtecoAnno) {
		this.codAtecoAnno = codAtecoAnno;
	}

	public String getFrmGiuridicaCod() {
		return frmGiuridicaCod;
	}

	public void setFrmGiuridicaCod(String frmGiuridicaCod) {
		this.frmGiuridicaCod = frmGiuridicaCod;
	}

	public String getDimensioneAziendaId() {
		return dimensioneAziendaId;
	}

	public void setDimensioneAziendaId(String dimensioneAziendaId) {
		this.dimensioneAziendaId = dimensioneAziendaId;
	}

	public String getTitoloStudio() {
		return titoloStudio;
	}

	public void setTitoloStudio(String titoloStudio) {
		this.titoloStudio = titoloStudio;
	}

	public String getCodVulnerabilePa() {
		return codVulnerabilePa;
	}

	public void setCodVulnerabilePa(String codVulnerabilePa) {
		this.codVulnerabilePa = codVulnerabilePa;
	}

	public String getStatoPartecipante() {
		return statoPartecipante;
	}

	public void setStatoPartecipante(String statoPartecipante) {
		this.statoPartecipante = statoPartecipante;
	}

	public Character getSesso() {
		return sesso;
	}

	public void setSesso(Character sesso) {
		this.sesso = sesso;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Integer getFlagResidenza() {
		return flagResidenza;
	}

	public void setFlagResidenza(Integer flagResidenza) {
		this.flagResidenza = flagResidenza;
	}
}
