package it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ver1.tabs;

import it.webred.cs.json.dto.JsonBaseBean;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class IntSpecialistiTempiChiusureBean extends JsonBaseBean{
	private boolean servSocialiComunali;
	private boolean cps;
	private boolean uonpia;
	private boolean serD;
	//private Long selConsultorio;     --Rimosso in SISO-358
	private boolean consultorio;
	private boolean privatiIndDecreto;
	private boolean ctu;
	private boolean altro;
	private String altroDescrizione;

	private Integer tempiAdempimento;
	private String tempiAdempimentoUM;
	private Date    scadenzaAdempimento;
	private Integer tempiAggiornamento;
	private String tempiAggiornamentoUM;
	private Date    scadenzaAggiornamento;

	private String propostaChiusura;
	private String chiusuraProvv;
	private boolean minoreAdottato;
	private boolean nonProvvExArt330;
	private boolean nonProvvAdottabilita;

	private Date relazioneServizio;
	private String selContenutoRelazione;
	private Date dataRispostaTM;

	public boolean isServSocialiComunali() {
		return servSocialiComunali;
	}

	public void setServSocialiComunali(boolean servSocialiComunali) {
		this.servSocialiComunali = servSocialiComunali;
	}

	public boolean isCps() {
		return cps;
	}

	public void setCps(boolean cps) {
		this.cps = cps;
	}

	public boolean isUonpia() {
		return uonpia;
	}

	public void setUonpia(boolean uonpia) {
		this.uonpia = uonpia;
	}

	public boolean isSerD() {
		return serD;
	}

	public void setSerD(boolean serD) {
		this.serD = serD;
	}

	public boolean isPrivatiIndDecreto() {
		return privatiIndDecreto;
	}

	public void setPrivatiIndDecreto(boolean privatiIndDecreto) {
		this.privatiIndDecreto = privatiIndDecreto;
	}

	public boolean isCtu() {
		return ctu;
	}

	public void setCtu(boolean ctu) {
		this.ctu = ctu;
	}

	public boolean isAltro() {
		return altro;
	}

	public void setAltro(boolean altro) {
		this.altro = altro;
	}

	public String getAltroDescrizione() {
		return altroDescrizione;
	}

	public void setAltroDescrizione(String altroDescrizione) {
		this.altroDescrizione = altroDescrizione;
	}

	public Date getScadenzaAggiornamento() {
		return scadenzaAggiornamento;
	}

	public void setScadenzaAggiornamento(Date scadenzaAggiornamento) {
		this.scadenzaAggiornamento = scadenzaAggiornamento;
	}

	public String getPropostaChiusura() {
		return propostaChiusura;
	}

	public void setPropostaChiusura(String propostaChiusura) {
		this.propostaChiusura = propostaChiusura;
	}

	public String getChiusuraProvv() {
		return chiusuraProvv;
	}

	public void setChiusuraProvv(String chiusuraProvv) {
		this.chiusuraProvv = chiusuraProvv;
	}

	public boolean isMinoreAdottato() {
		return minoreAdottato;
	}

	public void setMinoreAdottato(boolean minoreAdottato) {
		this.minoreAdottato = minoreAdottato;
	}

	public boolean isNonProvvExArt330() {
		return nonProvvExArt330;
	}

	public void setNonProvvExArt330(boolean nonProvvExArt330) {
		this.nonProvvExArt330 = nonProvvExArt330;
	}

	public boolean isNonProvvAdottabilita() {
		return nonProvvAdottabilita;
	}

	public void setNonProvvAdottabilita(boolean nonProvvAdottabilita) {
		this.nonProvvAdottabilita = nonProvvAdottabilita;
	}

	public Date getRelazioneServizio() {
		return relazioneServizio;
	}

	public void setRelazioneServizio(Date relazioneServizio) {
		this.relazioneServizio = relazioneServizio;
	}

	public String getSelContenutoRelazione() {
		return selContenutoRelazione;
	}

	public void setSelContenutoRelazione(String selContenutoRelazione) {
		this.selContenutoRelazione = selContenutoRelazione;
	}

	@Override
	public List<String> checkObbligatorieta() {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDataRispostaTM() {
		return dataRispostaTM;
	}

	public void setDataRispostaTM(Date dataRispostaTM) {
		this.dataRispostaTM = dataRispostaTM;
	}

	public Integer getTempiAdempimento() {
		return tempiAdempimento;
	}

	public void setTempiAdempimento(Integer tempiAdempimento) {
		this.tempiAdempimento = tempiAdempimento;
	}

	public Date getScadenzaAdempimento() {
		return scadenzaAdempimento;
	}

	public void setScadenzaAdempimento(Date scadenzaAdempimento) {
		this.scadenzaAdempimento = scadenzaAdempimento;
	}

	public Integer getTempiAggiornamento() {
		return tempiAggiornamento;
	}

	public void setTempiAggiornamento(Integer tempiAggiornamento) {
		this.tempiAggiornamento = tempiAggiornamento;
	}

	public String getTempiAdempimentoUM() {
		return tempiAdempimentoUM;
	}

	public void setTempiAdempimentoUM(String tempiAdempimentoUM) {
		this.tempiAdempimentoUM = tempiAdempimentoUM;
	}

	public String getTempiAggiornamentoUM() {
		return tempiAggiornamentoUM;
	}

	public void setTempiAggiornamentoUM(String tempiAggiornamentoUM) {
		this.tempiAggiornamentoUM = tempiAggiornamentoUM;
	}

	public boolean isConsultorio() {
		return consultorio;
	}

	public void setConsultorio(boolean consultorio) {
		this.consultorio = consultorio;
	}
}
