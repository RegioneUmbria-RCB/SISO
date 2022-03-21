package it.webred.ct.data.access.basic.docfa.dto;

import it.webred.ct.data.access.basic.catasto.dto.CatastoBaseDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RicercaOggettoDocfaDTO extends CatastoBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Date dataRif; 
	private String sezione;
	private String foglio;
	private String particella;
	private String unimm;
	/*
	 * estraiTutteLeUIse true: nella conposizione della lista UI coinvolte da ciascun docfa estratto, considera anche quelle 
	 * nelle altre particelle [fino ad ora vengono selezionati solo i sub della particella]
	 */
	private boolean estraiTutteLeUI; 
	
	private Date fornitura;
	private String protocollo;
	private String nrProg;
	private String dataRegistrazione;
	
	private String fornituraDa;
	private String fornituraA;
	private Date dataRegistrazioneDa;
	private Date dataRegistrazioneA;
	
	private String flgCommiFin2005;
	private String causali;
	private List<String> listaCausali = new ArrayList<String>();
	
	private String tipoOperDocfa;
	private String nomePlan;
	private BigDecimal progressivo;
	
	private RicercaCivicoDTO indirizzo;
	
	private Integer limit = 0;
	
	public RicercaOggettoDocfaDTO(){
		indirizzo = new RicercaCivicoDTO();
	}
	
	public List<String> getListaCausali() {
		return listaCausali;
	}

	public void setListaCausali(List<String> listaCausali) {
		this.listaCausali = listaCausali;
	}

	public String getFoglio() {
		return foglio;
	}
	
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getParticella() {
		return particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getUnimm() {
		return unimm;
	}

	public void setUnimm(String unimm) {
		this.unimm = unimm;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}	
	
	public Date getFornitura() {
		return fornitura;
	}

	public void setFornitura(Date fornitura) {
		this.fornitura = fornitura;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public String getNrProg() {
		return nrProg;
	}

	public void setNrProg(String nrProg) {
		this.nrProg = nrProg;
	}

	public String getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(String dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public Date getDataRif() {
		return dataRif;
	}

	public void setDataRif(Date dataRif) {
		this.dataRif = dataRif;
	}

	public String getFornituraDa() {
		return fornituraDa;
	}

	public void setFornituraDa(String fornituraDa) {
		this.fornituraDa = fornituraDa;
	}

	public String getFornituraA() {
		return fornituraA;
	}

	public void setFornituraA(String fornituraA) {
		this.fornituraA = fornituraA;
	}

	public Date getDataRegistrazioneDa() {
		return dataRegistrazioneDa;
	}

	public void setDataRegistrazioneDa(Date dataRegistrazioneDa) {
		this.dataRegistrazioneDa = dataRegistrazioneDa;
	}

	public Date getDataRegistrazioneA() {
		return dataRegistrazioneA;
	}

	public void setDataRegistrazioneA(Date dataRegistrazioneA) {
		this.dataRegistrazioneA = dataRegistrazioneA;
	}

	public void setIndirizzo(RicercaCivicoDTO indirizzo) {
		this.indirizzo = indirizzo;
	}

	public RicercaCivicoDTO getIndirizzo() {
		return indirizzo;
	}

	public void setCausali(String causali) {
		this.causali = causali;
	}

	public String getCausali() {
		return causali;
	}

	public void setFlgCommiFin2005(String flgCommiFin2005) {
		this.flgCommiFin2005 = flgCommiFin2005;
	}

	public String getFlgCommiFin2005() {
		return flgCommiFin2005;
	}

	public void setTipoOperDocfa(String tipoOperDocfa) {
		this.tipoOperDocfa = tipoOperDocfa;
	}

	public String getTipoOperDocfa() {
		return tipoOperDocfa;
	}

	public String getNomePlan() {
		return nomePlan;
	}

	public void setNomePlan(String nomePlan) {
		this.nomePlan = nomePlan;
	}

	public BigDecimal getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(BigDecimal progressivo) {
		this.progressivo = progressivo;
	}

	public boolean isEstraiTutteLeUI() {
		return estraiTutteLeUI;
	}

	public void setEstraiTutteLeUI(boolean estraiTutteLeUI) {
		this.estraiTutteLeUI = estraiTutteLeUI;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
