package it.webred.cs.csa.ejb.dto.fascicolo.colloquio;

import it.webred.cs.csa.ejb.dto.fascicolo.DatiInterceptorDTO;

import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;

public class ListaDatiColloquioDTO extends DatiInterceptorDTO{
	
	public ListaDatiColloquioDTO(Long secondoLivello, Long idSettoreDiario) {
		super(secondoLivello, idSettoreDiario);
	}

	private String operatoreDaChi;
	private Long diarioId;
	private Long casoId;
	
	private String nome;
	private String cognome;
	private boolean riservato;
	private boolean abilitato4riservato;
	private String testoDiario;
	private String descrizioneTipoColloquio;
	private String descrizioneDiarioDove;
	private String descrizioneDiarioConChi;
	private String diarioConChiAltro;
	
	private Date dtAmministrativa;
	
	private Date dataUltimaModifica;
	private String userIns;
	private String userMod;
	
	public String getOperatoreDaChi() {
		return operatoreDaChi;
	}
	public void setOperatoreDaChi(String operatoreDaChi) {
		this.operatoreDaChi = operatoreDaChi;
	}
	public Long getDiarioId() {
		return diarioId;
	}
	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public boolean isRiservato() {
		return riservato;
	}
	public void setRiservato(boolean riservato) {
		this.riservato = riservato;
	}
	public boolean isAbilitato4riservato() {
		return abilitato4riservato;
	}
	public void setAbilitato4riservato(boolean abilitato4riservato) {
		this.abilitato4riservato = abilitato4riservato;
	}
	public String getTestoDiario() {
		return testoDiario;
	}
	public void setTestoDiario(String testoDiario) {
		this.testoDiario = testoDiario;
	}
	public String getDescrizioneTipoColloquio() {
		return descrizioneTipoColloquio;
	}
	public void setDescrizioneTipoColloquio(String descrizioneTipoColloquio) {
		this.descrizioneTipoColloquio = descrizioneTipoColloquio;
	}
	public String getDescrizioneDiarioDove() {
		return descrizioneDiarioDove;
	}
	public void setDescrizioneDiarioDove(String descrizioneDiarioDove) {
		this.descrizioneDiarioDove = descrizioneDiarioDove;
	}
	public String getDescrizioneDiarioConChi() {
		return descrizioneDiarioConChi;
	}
	public void setDescrizioneDiarioConChi(String descrizioneDiarioConChi) {
		this.descrizioneDiarioConChi = descrizioneDiarioConChi;
	}
	public String getDiarioConChiAltro() {
		return diarioConChiAltro;
	}
	public void setDiarioConChiAltro(String diarioConChiAltro) {
		this.diarioConChiAltro = diarioConChiAltro;
	}
	public Date getDtAmministrativa() {
		return dtAmministrativa;
	}
	public void setDtAmministrativa(Date dtAmministrativa) {
		this.dtAmministrativa = dtAmministrativa;
	}
	public Date getDataUltimaModifica() {
		return dataUltimaModifica;
	}
	public void setDataUltimaModifica(Date dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}
	public Long getCasoId() {
		return casoId;
	}
	public void setCasoId(Long casoId) {
		this.casoId = casoId;
	}
	public String getUserIns() {
		return userIns;
	}
	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}
	public String getUserMod() {
		return userMod;
	}
	public void setUserMod(String userMod) {
		this.userMod = userMod;
	}
	
	
	public String getCampoTestoRid() {
		String campoTesto = StringEscapeUtils.unescapeHtml(testoDiario);
		campoTesto = campoTesto.replaceAll("\\<[^>]*>",""); //("\\<.*?>", "");
		if( campoTesto != null && campoTesto.length() > 20 ) 
			return campoTesto.substring(0, 20) + "...";
		else return campoTesto;
	}
}
