package it.webred.cs.csa.ejb.dto.pai.sal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsPaiSalDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long diarioPaiId;
    
	//Fase Preliminare
	private String tipoSoggetto;
	
	private String inviante;

	private String iscrizioneCpi;

	private String legge10492;

	private String legge6899;
	
	private String richiedenteAsilo;
	
	private String riferimentoInviante;
	
	private String presaCaricoInviante;
	

	private Date annoUltimaEsperienza;

	private String attitudiniPersonali;
	
	private String caratteristichePersonali;
	
	private String desideriAspettative;

    private Long idTitoloStudio;
	
	private String noteCvScolastico;
    
	private String altriEleFormativi;
	
	private String competenzeCapacita;

	private String esperienze;
	
	private String noteCvLavorativo;
	
	private String questioniPersFam;
	//Orientamento
	private String operatoreAccomp;
	
	private String soggettiCoinvolti;
	
	private String tipoEsperienza;
	
//	//Progettazione
	private String propostaPresFamiglia;
	private String durataEsperienza;
	private String dettaglioEsperienza;
	private Long tempoPienoParziale;
	private String nomeContesto;
	private String presenzaTutorContesto;
	private String tutorContesto;
	private String vicinanzaAbitazione;
	
	//Attivazione/Strumenti
	private String elementiVerifica;
	private String periodoProva;
	private String durataPeriodoProva;
	private String mansione;
	
	//Valutazione finale
	private String competenzeCapacitaAcquisite;
	private String esito;	
	
	private List<CsPaiSALFaseDTO> fasiSAL = new ArrayList<CsPaiSALFaseDTO>();
	private List<CsPaiSALStoricoDTO> storicoSAL = new ArrayList<CsPaiSALStoricoDTO>();
	private List<CsPaiSALStoricoDTO> lstStoricoTutor = new ArrayList<CsPaiSALStoricoDTO>();
	private List<CsPaiSALStoricoDTO> lstStoricoMansioni = new ArrayList<CsPaiSALStoricoDTO>();
	
	public CsPaiSalDTO() {
		super();
	}

	public CsPaiSalDTO(PaiSALFaseEnum fase) {
		CsPaiSALFaseDTO faseIniziale = new CsPaiSALFaseDTO(fase.getValore(), fase.getDescrizione());
		fasiSAL.add(faseIniziale);
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
	
	public String getTipoSoggetto() {
		return tipoSoggetto;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}

	public String getInviante() {
		return inviante;
	}
	
	public void setInviante(String inviante) {
		this.inviante = inviante;
	}
	
	public String getPresaCaricoInviante() {
		return presaCaricoInviante;
	}

	public void setPresaCaricoInviante(String presaCaricoInviante) {
		this.presaCaricoInviante = presaCaricoInviante;
	}

	public String getIscrizioneCpi() {
		return iscrizioneCpi;
	}

	public void setIscrizioneCpi(String iscrizioneCpi) {
		this.iscrizioneCpi = iscrizioneCpi;
	}

	public String getLegge10492() {
		return legge10492;
	}

	public void setLegge10492(String legge10492) {
		this.legge10492 = legge10492;
	}

	public String getLegge6899() {
		return legge6899;
	}

	public void setLegge6899(String legge6899) {
		this.legge6899 = legge6899;
	}

	
	public String getRichiedenteAsilo() {
		return richiedenteAsilo;
	}

	public void setRichiedenteAsilo(String richiedenteAsilo) {
		this.richiedenteAsilo = richiedenteAsilo;
	}

	public String getRiferimentoInviante() {
		return riferimentoInviante;
	}

	public void setRiferimentoInviante(String riferimentoInviante) {
		this.riferimentoInviante = riferimentoInviante;
	}
	
	
	public Date getAnnoUltimaEsperienza() {
		return annoUltimaEsperienza;
	}

	public void setAnnoUltimaEsperienza(Date annoUltimaEsperienza) {
		this.annoUltimaEsperienza = annoUltimaEsperienza;
	}

	public String getAttitudiniPersonali() {
		return attitudiniPersonali;
	}

	public void setAttitudiniPersonali(String attitudiniPersonali) {
		this.attitudiniPersonali = attitudiniPersonali;
	}

	public String getCaratteristichePersonali() {
		return caratteristichePersonali;
	}

	public void setCaratteristichePersonali(String caratteristichePersonali) {
		this.caratteristichePersonali = caratteristichePersonali;
	}

	public String getDesideriAspettative() {
		return desideriAspettative;
	}

	public void setDesideriAspettative(String desideriAspettative) {
		this.desideriAspettative = desideriAspettative;
	}

	public Long getIdTitoloStudio() {
		return idTitoloStudio;
	}

	public void setIdTitoloStudio(Long idTitoloStudio) {
		this.idTitoloStudio = idTitoloStudio;
	}

	public String getNoteCvScolastico() {
		return noteCvScolastico;
	}

	public void setNoteCvScolastico(String noteCvScolastico) {
		this.noteCvScolastico = noteCvScolastico;
	}

	public String getAltriEleFormativi() {
		return altriEleFormativi;
	}

	public void setAltriEleFormativi(String altriEleFormativi) {
		this.altriEleFormativi = altriEleFormativi;
	}

	public String getCompetenzeCapacita() {
		return competenzeCapacita;
	}

	public void setCompetenzeCapacita(String competenzeCapacita) {
		this.competenzeCapacita = competenzeCapacita;
	}

	public String getEsperienze() {
		return esperienze;
	}

	public void setEsperienze(String esperienze) {
		this.esperienze = esperienze;
	}

	public String getNoteCvLavorativo() {
		return noteCvLavorativo;
	}

	public void setNoteCvLavorativo(String noteCvLavorativo) {
		this.noteCvLavorativo = noteCvLavorativo;
	}

	public String getQuestioniPersFam() {
		return questioniPersFam;
	}

	public void setQuestioniPersFam(String questioniPersFam) {
		this.questioniPersFam = questioniPersFam;
	}
	
	public String getOperatoreAccomp() {
		return operatoreAccomp;
	}

	public void setOperatoreAccomp(String operatoreAccomp) {
		this.operatoreAccomp = operatoreAccomp;
	}

	public String getSoggettiCoinvolti() {
		return soggettiCoinvolti;
	}

	public void setSoggettiCoinvolti(String soggettiCoinvolti) {
		this.soggettiCoinvolti = soggettiCoinvolti;
	}

	public String getTipoEsperienza() {
		return tipoEsperienza;
	}

	public void setTipoEsperienza(String tipoEsperienza) {
		this.tipoEsperienza = tipoEsperienza;
	}
	//**********************************************

	

	public String getDurataEsperienza() {
		return durataEsperienza;
	}

	public String getPropostaPresFamiglia() {
		return propostaPresFamiglia;
	}

	public void setPropostaPresFamiglia(String propostaPresFamiglia) {
		this.propostaPresFamiglia = propostaPresFamiglia;
	}

	public void setDurataEsperienza(String durataEsperienza) {
		this.durataEsperienza = durataEsperienza;
	}

	public String getDettaglioEsperienza() {
		return dettaglioEsperienza;
	}

	public void setDettaglioEsperienza(String dettaglioEsperienza) {
		this.dettaglioEsperienza = dettaglioEsperienza;
	}

	public Long getTempoPienoParziale() {
		return tempoPienoParziale;
	}

	public void setTempoPienoParziale(Long tempoPienoParziale) {
		this.tempoPienoParziale = tempoPienoParziale;
	}

	public String getNomeContesto() {
		return nomeContesto;
	}

	public void setNomeContesto(String nomeContesto) {
		this.nomeContesto = nomeContesto;
	}

	public String getPresenzaTutorContesto() {
		return presenzaTutorContesto;
	}

	public void setPresenzaTutorContesto(String presenzaTutorContesto) {
		this.presenzaTutorContesto = presenzaTutorContesto;
	}

	public String getTutorContesto() {
		return tutorContesto;
	}

	public void setTutorContesto(String tutorContesto) {
		this.tutorContesto = tutorContesto;
	}

	public String getVicinanzaAbitazione() {
		return vicinanzaAbitazione;
	}

	public void setVicinanzaAbitazione(String vicinanzaAbitazione) {
		this.vicinanzaAbitazione = vicinanzaAbitazione;
	}

	public String getElementiVerifica() {
		return elementiVerifica;
	}

	public void setElementiVerifica(String elementiVerifica) {
		this.elementiVerifica = elementiVerifica;
	}

	public String getPeriodoProva() {
		return periodoProva;
	}

	public void setPeriodoProva(String periodoProva) {
		this.periodoProva = periodoProva;
	}

	public String getDurataPeriodoProva() {
		return durataPeriodoProva;
	}

	public void setDurataPeriodoProva(String durataPeriodoProva) {
		this.durataPeriodoProva = durataPeriodoProva;
	}

	public String getMansione() {
		return mansione;
	}

	public void setMansione(String mansione) {
		this.mansione = mansione;
	}

	public String getCompetenzeCapacitaAcquisite() {
		return competenzeCapacitaAcquisite;
	}

	public void setCompetenzeCapacitaAcquisite(String competenzeCapacitaAcquisite) {
		this.competenzeCapacitaAcquisite = competenzeCapacitaAcquisite;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public void setFasiSAL(List<CsPaiSALFaseDTO> fasiSAL) {
		this.fasiSAL = fasiSAL;
	}

	
	public List<CsPaiSALFaseDTO> getFasiSAL() {
		return fasiSAL;
	}
	
	public List<CsPaiSALStoricoDTO> getStoricoSAL() {
		return storicoSAL;
	}

	public void setStoricoSAL(List<CsPaiSALStoricoDTO> storicoSAL) {
		this.storicoSAL = storicoSAL;
	}

	public Integer getCodiceFaseAttuale(){

		Integer toReturn = null;

		for (CsPaiSALFaseDTO fase : fasiSAL) {
			if (fase.getDataA() == null) {
				toReturn = fase.getCodice();
				break;
			}
		}

		return toReturn;
	}
	public Date getDataFaseSALAttuale() {

		Date toReturn = null;

		for (CsPaiSALFaseDTO stato : fasiSAL) {
			if (stato.getDataA() == null) {
				toReturn = stato.getDataFaseSal();
				break;
			}
		}

		return toReturn;
	}

	public List<CsPaiSALStoricoDTO> getLstStoricoTutor() {
		return lstStoricoTutor;
	}

	public void setLstStoricoTutor(List<CsPaiSALStoricoDTO> lstStoricoTutor) {
		this.lstStoricoTutor = lstStoricoTutor;
	}

	public List<CsPaiSALStoricoDTO> getLstStoricoMansioni() {
		return lstStoricoMansioni;
	}

	public void setLstStoricoMansioni(List<CsPaiSALStoricoDTO> lstStoricoMansioni) {
		this.lstStoricoMansioni = lstStoricoMansioni;
	}
	

}
