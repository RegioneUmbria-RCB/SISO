package it.webred.cs.csa.ejb.dto.pai.affido;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsPaiAffidoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private Long id;
	
	private Long diarioPaiId;
	
	private String numeroDecreto;
	
	private Date dataDecreto;
	
	private String codiceSituazioniParticolari;
	
	private String codiceCollocamento;
	
	private String codiceEntitaAffido;
	
	private String descEntitaAffidoParziale;
	
	private String codiceTipoAccoglienza;
	
	private String codiceNaturaAccoglienza;
	
	private Boolean affidamentoProfessionale=false;
	
	private String codiceAdottabile;
	
	private Boolean presenzaRetiDiFamiglie=false;
	
	private String codiceFrequenzaContattiMinore;
	
	private String codiceEsitoAffido;
	
	private String altroEsitoAffido;
	
	private Boolean minoreStranieroNonAccompagnato=false;
	
	private Boolean disabilitaDuranteAffido=false;
	
	private Boolean minoreStranieroAffidatriStessaCultura=false;
	
	private String codiceConvivenzaOrigineAffidataria;
	
	private String impegnoFamigliaOrigine;
	
	private String impegnoFamigliaAffidataria;
	
	private String impegnoMinore;
	
	private String impegnoServizioSociale;
	
	private String impegnoAltriSoggetti;
	
	private String codiceTipoAffido;
	
	private CsPaiAffidoFamigliaOrigineDTO famigliaOrigine = new CsPaiAffidoFamigliaOrigineDTO();
	
	private CsPaiAffidoFamigliaAffidatariaDTO famigliaAffidataria = new CsPaiAffidoFamigliaAffidatariaDTO();
	
	private List<CsPaiAffidoStatoDTO> statiAffido = new ArrayList<CsPaiAffidoStatoDTO>();
	
	private List<CsPaiAffidoSoggettoDTO> soggettiAffido = new ArrayList<CsPaiAffidoSoggettoDTO>();
	
	public CsPaiAffidoDTO(){
		super();
	}

	public CsPaiAffidoDTO(PaiAffidoStatoEnum stato, String codiceNaturaAccoglienza) {
		CsPaiAffidoStatoDTO statoIniziale = new CsPaiAffidoStatoDTO(stato.getValore(),stato.getDescrizione());
		this.codiceNaturaAccoglienza = codiceNaturaAccoglienza;
		statiAffido.add(statoIniziale);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDiarioPaiId() {
		return diarioPaiId;
	}

	public void setDiarioPaiId(Long diarioPaiId) {
		this.diarioPaiId = diarioPaiId;
	}

	public String getNumeroDecreto() {
		return numeroDecreto;
	}

	public void setNumeroDecreto(String numeroDecreto) {
		this.numeroDecreto = numeroDecreto;
	}

	public Date getDataDecreto() {
		return dataDecreto;
	}

	public void setDataDecreto(Date dataDecreto) {
		this.dataDecreto = dataDecreto;
	}

	public String getCodiceSituazioniParticolari() {
		return codiceSituazioniParticolari;
	}

	public void setCodiceSituazioniParticolari(String codiceSituazioniParticolari) {
		this.codiceSituazioniParticolari = codiceSituazioniParticolari;
	}

	public String getCodiceCollocamento() {
		return codiceCollocamento;
	}

	public void setCodiceCollocamento(String codiceCollocamento) {
		this.codiceCollocamento = codiceCollocamento;
	}

	public String getCodiceEntitaAffido() {
		return codiceEntitaAffido;
	}

	public void setCodiceEntitaAffido(String codiceEntitaAffido) {
		this.codiceEntitaAffido = codiceEntitaAffido;
	}

	public String getDescEntitaAffidoParziale() {
		return descEntitaAffidoParziale;
	}

	public void setDescEntitaAffidoParziale(String descEntitaAffidoParziale) {
		this.descEntitaAffidoParziale = descEntitaAffidoParziale;
	}

	public String getCodiceTipoAccoglienza() {
		return codiceTipoAccoglienza;
	}

	public void setCodiceTipoAccoglienza(String codiceTipoAccoglienza) {
		this.codiceTipoAccoglienza = codiceTipoAccoglienza;
	}

	public String getCodiceNaturaAccoglienza() {
		return codiceNaturaAccoglienza;
	}

	public void setCodiceNaturaAccoglienza(String codiceNaturaAccoglienza) {
		this.codiceNaturaAccoglienza = codiceNaturaAccoglienza;
	}

	public Boolean getAffidamentoProfessionale() {
		return affidamentoProfessionale;
	}

	public void setAffidamentoProfessionale(Boolean affidamentoProfessionale) {
		this.affidamentoProfessionale = affidamentoProfessionale;
	}

	public String getCodiceAdottabile() {
		return codiceAdottabile;
	}

	public void setCodiceAdottabile(String codiceAdottabile) {
		this.codiceAdottabile = codiceAdottabile;
	}

	public Boolean getPresenzaRetiDiFamiglie() {
		return presenzaRetiDiFamiglie;
	}

	public void setPresenzaRetiDiFamiglie(Boolean presenzaRetiDiFamiglie) {
		this.presenzaRetiDiFamiglie = presenzaRetiDiFamiglie;
	}

	public String getCodiceFrequenzaContattiMinore() {
		return codiceFrequenzaContattiMinore;
	}

	public void setCodiceFrequenzaContattiMinore(
			String codiceFrequenzaContattiMinore) {
		this.codiceFrequenzaContattiMinore = codiceFrequenzaContattiMinore;
	}

	public String getCodiceEsitoAffido() {
		return codiceEsitoAffido;
	}

	public void setCodiceEsitoAffido(String codiceEsitoAffido) {
		this.codiceEsitoAffido = codiceEsitoAffido;
	}
	
	public String getAltroEsitoAffido() {
		return altroEsitoAffido;
	}

	public void setAltroEsitoAffido(String altroEsitoAffido) {
		this.altroEsitoAffido = altroEsitoAffido;
	}

	public Boolean getMinoreStranieroNonAccompagnato() {
		return minoreStranieroNonAccompagnato;
	}

	public void setMinoreStranieroNonAccompagnato(
			Boolean minoreStranieroNonAccompagnato) {
		this.minoreStranieroNonAccompagnato = minoreStranieroNonAccompagnato;
	}

	public Boolean getDisabilitaDuranteAffido() {
		return disabilitaDuranteAffido;
	}

	public void setDisabilitaDuranteAffido(Boolean disabilitaDuranteAffido) {
		this.disabilitaDuranteAffido = disabilitaDuranteAffido;
	}

	public Boolean getMinoreStranieroAffidatriStessaCultura() {
		return minoreStranieroAffidatriStessaCultura;
	}

	public void setMinoreStranieroAffidatriStessaCultura(
			Boolean minoreStranieroAffidatriStessaCultura) {
		this.minoreStranieroAffidatriStessaCultura = minoreStranieroAffidatriStessaCultura;
	}

	public String getCodiceConvivenzaOrigineAffidataria() {
		return codiceConvivenzaOrigineAffidataria;
	}

	public void setCodiceConvivenzaOrigineAffidataria(
			String codiceConvivenzaOrigineAffidataria) {
		this.codiceConvivenzaOrigineAffidataria = codiceConvivenzaOrigineAffidataria;
	}

	public String getImpegnoFamigliaOrigine() {
		return impegnoFamigliaOrigine;
	}

	public void setImpegnoFamigliaOrigine(String impegnoFamigliaOrigine) {
		this.impegnoFamigliaOrigine = impegnoFamigliaOrigine;
	}

	public String getImpegnoFamigliaAffidataria() {
		return impegnoFamigliaAffidataria;
	}

	public void setImpegnoFamigliaAffidataria(String impegnoFamigliaAffidataria) {
		this.impegnoFamigliaAffidataria = impegnoFamigliaAffidataria;
	}

	public String getImpegnoMinore() {
		return impegnoMinore;
	}

	public void setImpegnoMinore(String impegnoMinore) {
		this.impegnoMinore = impegnoMinore;
	}

	public String getImpegnoServizioSociale() {
		return impegnoServizioSociale;
	}

	public void setImpegnoServizioSociale(String impegnoServizioSociale) {
		this.impegnoServizioSociale = impegnoServizioSociale;
	}

	public String getImpegnoAltriSoggetti() {
		return impegnoAltriSoggetti;
	}

	public void setImpegnoAltriSoggetti(String impegnoAltriSoggetti) {
		this.impegnoAltriSoggetti = impegnoAltriSoggetti;
	}
	
	public String getCodiceTipoAffido() {
		if(codiceTipoAffido == null){
			codiceTipoAffido = PaiAffidoDominiEnum.TIPO_AFFIDO.name() + "_ACCOGLIENZA";
		}
		return codiceTipoAffido;
	}

	public void setCodiceTipoAffido(String codiceTipoAffido) {
		this.codiceTipoAffido = codiceTipoAffido;
	}

	public CsPaiAffidoFamigliaOrigineDTO getFamigliaOrigine() {
		return famigliaOrigine;
	}

	public void setFamigliaOrigine(CsPaiAffidoFamigliaOrigineDTO famigliaOrigine) {
		this.famigliaOrigine = famigliaOrigine;
	}

	public CsPaiAffidoFamigliaAffidatariaDTO getFamigliaAffidataria() {
		return famigliaAffidataria;
	}

	public void setFamigliaAffidataria(
			CsPaiAffidoFamigliaAffidatariaDTO famigliaAffidataria) {
		this.famigliaAffidataria = famigliaAffidataria;
	}

	public List<CsPaiAffidoStatoDTO> getStatiAffido() {
		return statiAffido;
	}

	public void setStatiAffido(List<CsPaiAffidoStatoDTO> statiAffido) {
		this.statiAffido = statiAffido;
	}

	public List<CsPaiAffidoSoggettoDTO> getSoggettiAffido() {
		return soggettiAffido;
	}

	public void setSoggettiAffido(List<CsPaiAffidoSoggettoDTO> soggettiAffido) {
		this.soggettiAffido = soggettiAffido;
	}

	public Integer getCodiceStatoAttuale(){
		
		Integer toReturn = null;
		
		for (CsPaiAffidoStatoDTO stato : statiAffido) {
			if(stato.getDataA() == null){
				toReturn = stato.getCodice();
				break;
			}
		}
		
		return toReturn;
	}
	
}
