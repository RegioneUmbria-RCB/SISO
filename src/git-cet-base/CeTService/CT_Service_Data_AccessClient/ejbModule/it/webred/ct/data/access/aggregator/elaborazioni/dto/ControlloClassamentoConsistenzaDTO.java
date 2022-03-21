package it.webred.ct.data.access.aggregator.elaborazioni.dto;

import it.webred.ct.data.access.basic.catasto.dto.ParametriCatastaliDTO;

import java.util.Date;
import java.util.List;

import javax.faces.component.html.HtmlInputText;

public class ControlloClassamentoConsistenzaDTO extends ParametriCatastaliDTO {
	
	private static final long serialVersionUID = 1L;

	private String protocolloDocfa;
	private Date   fornitura;
	private String identificativoImmobile = "";
	private String zona;
	private Date   dataVariazione;
	private Date   dataRegistrazione;
	private String categoria;
	private String classe;
	private String classeRif;
	private Boolean mostraClasseMaggiormenteFrequente = false;
	
	private Integer classeMin = 0;
	private String consistenza = "";
	private Double superfMediaMin = 0d;
	private Double superfMediaMax = 0d;
	private Double consisAnomalia = 0d;
	private Double superficie = 0d;
	private Double rendita = 0d;
	private Double renditaX100 = 0d;
	private Double renditaX105 = 0d;
	private Double valoreCommerciale = 0d;
	private Double valComSuRen100 = 0d;
	private Double valComSuRen100Rif = 0d;
	private Double valComSuRen105 = 0d;
	private Double tariffa = 0d;
	private Double mediaAttesa = 0d;
	private Double mediaAttesaPerVano = 0d;
	private Double rapporto = 0d;
	private Boolean classamentoCompatibile = false;
	private String colore = "";
	private Object rapportoParam = null;
	
	//Messaggi segnalazione errori
	private boolean erroreZoneOmi;
	private String msgZoneOmi;
	private List<ControlloClassamentoConsistenzaDTO> lstPerZcDiverse;
	
	public ControlloClassamentoConsistenzaDTO(){
		super();
	}
	
	public ControlloClassamentoConsistenzaDTO(ControlloClassamentoConsistenzaDTO c){
		super(c);
		protocolloDocfa = c.getProtocolloDocfa();
		fornitura = c.getFornitura();
		zona = c.getZona();
		dataVariazione = c.getDataVariazione();
		dataRegistrazione = c.getDataRegistrazione();
		categoria = c.getCategoria();
		classe = c.getClasse();
		classeRif = c.getClasseRif();
		mostraClasseMaggiormenteFrequente = c.getMostraClasseMaggiormenteFrequente();
		classeMin = c.getClasseMin();
		consistenza = c.getConsistenza();
		superfMediaMin = c.getSuperfMediaMin();
		superfMediaMax = c.getSuperfMediaMax();
		consisAnomalia = c.getConsisAnomalia();
		superficie = c.getSuperficie();
		rendita = c.getRendita();
		renditaX100 = c.getRenditaX100();
		renditaX105 = c.getRenditaX105();
		valoreCommerciale = c.getValoreCommerciale();
		valComSuRen100 = c.getValComSuRen100();
		valComSuRen100Rif = c.getValComSuRen100Rif();
		valComSuRen105 = c.getValComSuRen105();
		tariffa = c.getTariffa();
		mediaAttesa = c.getMediaAttesa();
		mediaAttesaPerVano = c.getMediaAttesaPerVano();
		rapporto = c.getRapporto();
		classamentoCompatibile = c.getClassamentoCompatibile();
		colore = c.getColore();
		rapportoParam = c.getRapportoParam();
	}
	
	public Double getSuperfMediaMin() {
		return superfMediaMin;
	}
	public void setSuperfMediaMin(Double superfMediaMin) {
		this.superfMediaMin = superfMediaMin;
	}
	public Double getSuperfMediaMax() {
		return superfMediaMax;
	}
	public void setSuperfMediaMax(Double superfMediaMax) {
		this.superfMediaMax = superfMediaMax;
	}
	public Double getConsisAnomalia() {
		return consisAnomalia;
	}
	public void setConsisAnomalia(Double consisAnomalia) {
		this.consisAnomalia = consisAnomalia;
	}
	public Double getValoreCommerciale() {
		return valoreCommerciale;
	}
	public void setValoreCommerciale(Double valoreCommerciale) {
		this.valoreCommerciale = valoreCommerciale;
	}
	public Double getValComSuRen100() {
		return valComSuRen100;
	}
	public void setValComSuRen100(Double valComSuRen100) {
		this.valComSuRen100 = valComSuRen100;
	}
	public Double getValComSuRen100Rif() {
		return valComSuRen100Rif;
	}
	public void setValComSuRen100Rif(Double valComSuRen100Rif) {
		this.valComSuRen100Rif = valComSuRen100Rif;
	}
	public Double getValComSuRen105() {
		return valComSuRen105;
	}
	public void setValComSuRen105(Double valComSuRen105) {
		this.valComSuRen105 = valComSuRen105;
	}
	public Double getTariffa() {
		return tariffa;
	}
	public void setTariffa(Double tariffa) {
		this.tariffa = tariffa;
	}
	public Double getMediaAttesa() {
		return mediaAttesa;
	}
	public void setMediaAttesa(Double mediaAttesa) {
		this.mediaAttesa = mediaAttesa;
	}
	public Double getMediaAttesaPerVano() {
		return mediaAttesaPerVano;
	}
	public void setMediaAttesaPerVano(Double mediaAttesaPerVano) {
		this.mediaAttesaPerVano = mediaAttesaPerVano;
	}
	public Double getRapporto() {
		return rapporto;
	}
	public void setRapporto(Double rapporto) {
		this.rapporto = rapporto;
	}
	public Boolean getClassamentoCompatibile() {
		return classamentoCompatibile;
	}
	public void setClassamentoCompatibile(Boolean classamentoCompatibile) {
		this.classamentoCompatibile = classamentoCompatibile;
	}
	public String getColore() {
		return colore;
	}
	public void setColore(String colore) {
		this.colore = colore;
	}
	public void setClasseMin(Integer classeMin) {
		this.classeMin = classeMin;
	}
	public void setSuperficie(Double superficie) {
		this.superficie = superficie;
	}
	public void setRendita(Double rendita) {
		this.rendita = rendita;
	}
	public boolean isErroreZoneOmi() {
		return erroreZoneOmi;
	}
	public void setErroreZoneOmi(boolean erroreZoneOmi) {
		this.erroreZoneOmi = erroreZoneOmi;
	}
	public String getMsgZoneOmi() {
		return msgZoneOmi;
	}
	public void setMsgZoneOmi(String msgZoneOmi) {
		this.msgZoneOmi = msgZoneOmi;
	}
	public void setRenditaX100(Double renditaX100) {
		this.renditaX100 = renditaX100;
	}
	public void setRenditaX105(Double renditaX105) {
		this.renditaX105 = renditaX105;
	}
	public String getProtocolloDocfa() {
		return protocolloDocfa;
	}
	public void setProtocolloDocfa(String protocolloDocfa) {
		this.protocolloDocfa = protocolloDocfa;
	}
	public Date getFornitura() {
		return fornitura;
	}
	public void setFornitura(Date fornitura) {
		this.fornitura = fornitura;
	}
	public Date getDataVariazione() {
		return dataVariazione;
	}
	public void setDataVariazione(Date dataVariazione) {
		this.dataVariazione = dataVariazione;
	}

	public Double getSuperficie() {
		return superficie;
	}
	public Double getRendita() {
		return rendita;
	}
	public Double getRenditaX100() {
		return renditaX100;
	}
	public Double getRenditaX105() {
		return renditaX105;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setConsistenza(String consistenza) {
		this.consistenza = consistenza;
	}
	public String getConsistenza() {
		return consistenza;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public String getZona() {
		return zona;
	}
	public void setMostraClasseMaggiormenteFrequente(
			Boolean mostraClasseMaggiormenteFrequente) {
		this.mostraClasseMaggiormenteFrequente = mostraClasseMaggiormenteFrequente;
	}
	public Boolean getMostraClasseMaggiormenteFrequente() {
		return mostraClasseMaggiormenteFrequente;
	}
	public void setClasseMin(int classeMin) {
		this.classeMin = classeMin;
	}
	public int getClasseMin() {
		return classeMin;
	}
	public void setClasseRif(String classeRif) {
		this.classeRif = classeRif;
	}
	public String getClasseRif() {
		return classeRif;
	}
	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}
	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}
	public void setRapportoParam(Object rapportoParam) {
		this.rapportoParam = rapportoParam;
	}
	public Object getRapportoParam() {
		return rapportoParam;
	}
	public List<ControlloClassamentoConsistenzaDTO> getLstPerZcDiverse() {
		return lstPerZcDiverse;
	}
	public void setLstPerZcDiverse(List<ControlloClassamentoConsistenzaDTO> lstPerZcDiverse) {
		this.lstPerZcDiverse = lstPerZcDiverse;
	}

	public String getIdentificativoImmobile() {
		return identificativoImmobile;
	}

	public void setIdentificativoImmobile(String identificativoImmobile) {
		this.identificativoImmobile = identificativoImmobile;
	}
	
	
}
