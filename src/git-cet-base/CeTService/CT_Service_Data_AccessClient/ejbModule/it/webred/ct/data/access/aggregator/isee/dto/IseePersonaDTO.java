package it.webred.ct.data.access.aggregator.isee.dto;

import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class IseePersonaDTO extends CeTBaseObject implements Serializable {
		
	private SitDPersona persona;
	private String cittadinanza = "Italiana";
	private String comuneNascita;
	private String provinciaNascita;
	private String comuneResidenza;
	private String provinciaResidenza;
	private String viaResidenza;
	private String civicoResidenza;
	private String capResidenza;
	private String ruoloNucleo;
	private String attivitaSoggetto;
	private BigDecimal patrimonioMobiliare;
	private BigDecimal redditiIrap;
	private BigDecimal redditiIrpef;
	private List<IseeRedditoDTO> listaRedditi;
	private List<IseeRedditoModelloDTO> listaRedditiByModello;
	private List<IseeCespiteDTO> listaCespiti;
	private List<IseeLocazioneDTO> listaLocazioni;
	
	public IseePersonaDTO() {
		super();
	}

	public SitDPersona getPersona() {
		return persona;
	}

	public void setPersona(SitDPersona persona) {
		this.persona = persona;
	}

	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getRuoloNucleo() {
		return ruoloNucleo;
	}

	public void setRuoloNucleo(String ruoloNucleo) {
		this.ruoloNucleo = ruoloNucleo;
	}

	public String getAttivitaSoggetto() {
		return attivitaSoggetto;
	}

	public void setAttivitaSoggetto(String attivitaSoggetto) {
		this.attivitaSoggetto = attivitaSoggetto;
	}

	public BigDecimal getPatrimonioMobiliare() {
		return patrimonioMobiliare;
	}

	public void setPatrimonioMobiliare(BigDecimal patrimonioMobiliare) {
		this.patrimonioMobiliare = patrimonioMobiliare;
	}

	public BigDecimal getRedditiIrap() {
		return redditiIrap;
	}

	public void setRedditiIrap(BigDecimal redditiIrap) {
		this.redditiIrap = redditiIrap;
	}

	public BigDecimal getRedditiIrpef() {
		return redditiIrpef;
	}

	public void setRedditiIrpef(BigDecimal redditiIrpef) {
		this.redditiIrpef = redditiIrpef;
	}

	public List<IseeRedditoDTO> getListaRedditi() {
		return listaRedditi;
	}

	public void setListaRedditi(List<IseeRedditoDTO> listaRedditi) {
		this.listaRedditi = listaRedditi;
	}

	public List<IseeCespiteDTO> getListaCespiti() {
		return listaCespiti;
	}

	public void setListaCespiti(List<IseeCespiteDTO> listaCespiti) {
		this.listaCespiti = listaCespiti;
	}

	public String getComuneResidenza() {
		return comuneResidenza;
	}

	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public String getViaResidenza() {
		return viaResidenza;
	}

	public void setViaResidenza(String viaResidenza) {
		this.viaResidenza = viaResidenza;
	}

	public String getCivicoResidenza() {
		return civicoResidenza;
	}

	public void setCivicoResidenza(String civicoResidenza) {
		this.civicoResidenza = civicoResidenza;
	}

	public String getCapResidenza() {
		return capResidenza;
	}

	public void setCapResidenza(String capResidenza) {
		this.capResidenza = capResidenza;
	}

	public String getProvinciaNascita() {
		return provinciaNascita;
	}

	public void setProvinciaNascita(String provinciaNascita) {
		this.provinciaNascita = provinciaNascita;
	}

	public String getProvinciaResidenza() {
		return provinciaResidenza;
	}

	public void setProvinciaResidenza(String provinciaResidenza) {
		this.provinciaResidenza = provinciaResidenza;
	}

	public List<IseeLocazioneDTO> getListaLocazioni() {
		return listaLocazioni;
	}

	public void setListaLocazioni(List<IseeLocazioneDTO> listaLocazioni) {
		this.listaLocazioni = listaLocazioni;
	}

	public List<IseeRedditoModelloDTO> getListaRedditiByModello() {
		return listaRedditiByModello;
	}

	public void setListaRedditiByModello(
			List<IseeRedditoModelloDTO> listaRedditiByModello) {
		this.listaRedditiByModello = listaRedditiByModello;
	}

}
