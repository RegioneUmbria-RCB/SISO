package it.webred.cs.csa.ejb.dto.udc;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsSsSchedaSegr;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DatiSchedaAccessoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	protected Long id;
	protected Date dataAccesso;
	protected String cognome = "-";
	protected String nome = "";
	protected String cf = "";
	//protected Date dataNascita;
	protected String tipo = "-"; // "Tipo Intervento"
	
	protected String operatore = "-";
	protected String ufficio = "-";
	
	// SISO-938
	private List<String> categoriaSociale; //Categoria
	
	//Dati CS_SS_SCHEDA_SEGR
	protected Long csSsId;
	protected boolean esistente;
	protected Long soggettoId;
	protected Long casoId;
	protected String stato;
	protected String notaStato;
	protected String provenienza;
	protected String provenienzaDesc;
	protected String provenienzaTooltip;
	
	public void aggiornaValoriCsSsSchedaSegr(CsSsSchedaSegr csSs){
		if(csSs!=null){
			this.csSsId = csSs.getId();
			this.esistente = csSs.getFlgEsistente()!=null ? csSs.getFlgEsistente() : Boolean.FALSE;
			this.setStato(csSs.getStato());
			this.notaStato = csSs.getNotaStato();
			
			CsASoggettoLAZY soggetto = csSs.getCsASoggetto();
			if(soggetto!=null){
				this.soggettoId = soggetto.getAnagraficaId();
				this.casoId = soggetto.getCsACaso()!=null ? csSs.getCsASoggetto().getCsACaso().getId() : null;
			}
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCsSsId() {
		return csSsId;
	}

	public void setCsSsId(Long csSsId) {
		this.csSsId = csSsId;
	}

	public Date getDataAccesso() {
		return dataAccesso;
	}

	public void setDataAccesso(Date dataAccesso) {
		this.dataAccesso = dataAccesso;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

/*	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
*/
	public String getIntervento() {
		return tipo;
	}

	public void setIntervento(String intervento) {
		this.tipo = intervento;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getOperatore() {
		return operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	public String getUfficio() {
		return ufficio;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}

	public List<String> getCategoriaSociale() {
		return categoriaSociale;
	}

	public void setCategoriaSociale(List<String> categoriaSociale) {
		this.categoriaSociale = categoriaSociale;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

	public String getProvenienza() {
		return provenienza;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isEsistente() {
		return esistente;
	}

	public void setEsistente(boolean esistente) {
		this.esistente = esistente;
	}

	public boolean isVista() {
		return DataModelCostanti.SchedaSegr.STATO_VISTA.equalsIgnoreCase(stato);
	}

	public boolean isRespinta() {
		return DataModelCostanti.SchedaSegr.STATO_RESPINTA.equalsIgnoreCase(stato);
	}

	public boolean isCreata() {
		return DataModelCostanti.SchedaSegr.STATO_CREATA.equalsIgnoreCase(stato);
	}
	
	public boolean isSoggettoAssociato() {
		return (this.soggettoId!=null && this.soggettoId>0);
	}

	public String getNotaStato() {
		return notaStato;
	}

	public void setNotaStato(String notaStato) {
		this.notaStato = notaStato;
	}

	public String getProvenienzaDesc() {
		return provenienzaDesc;
	}

	public void setProvenienzaDesc(String provenienzaDesc) {
		this.provenienzaDesc = provenienzaDesc;
	}

	public String getProvenienzaTooltip() {
		return provenienzaTooltip;
	}

	public void setProvenienzaTooltip(String provenienzaTooltip) {
		this.provenienzaTooltip = provenienzaTooltip;
	}

	public Long getSoggettoId() {
		return soggettoId;
	}

	public void setSoggettoId(Long soggettoId) {
		this.soggettoId = soggettoId;
	}
	
	public Long getCasoId() {
		return casoId;
	}

	public void setCasoId(Long casoId) {
		this.casoId = casoId;
	}

	public boolean isProvenienzaUDC(){
		return DataModelCostanti.SchedaSegr.PROVENIENZA_SS.equalsIgnoreCase(this.provenienza);
	}
	
	public boolean isPropostaPIC(){
		return this.csSsId!=null && this.csSsId.longValue()>0;
	}

}
