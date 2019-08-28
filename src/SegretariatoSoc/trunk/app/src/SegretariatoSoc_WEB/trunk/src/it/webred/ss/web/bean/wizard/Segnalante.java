package it.webred.ss.web.bean.wizard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.convert.Converter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import it.webred.cs.csa.ejb.client.AccessTableComuniSessionBeanRemote;
import it.webred.cs.jsf.manbean.ComuneNazioneNascitaMan;
import it.webred.cs.jsf.manbean.common.CommonDatiAnaBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;
import it.webred.jsf.bean.SessoBean;
import it.webred.jsf.interfaces.IComune;
import it.webred.jsf.utils.ComuneConverter;
import it.webred.ss.data.model.SsSchedaSegnalante;

public class Segnalante implements IComune, CommonDatiAnaBean {
	
	protected static Logger logger = Logger.getLogger("segretariatosoc.log");
	
	private Long id;
	private String nome;
	private String cognome;
	private String cf;
	private boolean reqCf = false;
	private String ente;
	private String ruolo;
	private String telefono;
	private String cellulare;
	private String email;
	private String indirizzo;
	private Integer numero;
	private ComuneBean comune;
	private Date dataNascita;
	private ComuneNazioneNascitaMan comuneNazioneNascitaMan=new ComuneNazioneNascitaMan();
	private SessoBean datiSesso=new SessoBean();
	private String codStatoCivile;
	private Long codRelazione;
	private Long settoreId;		//SISO-346
	
	public SsSchedaSegnalante fillModel(){
		SsSchedaSegnalante model=null;
		if(!this.isEmpty()){
			model = new SsSchedaSegnalante();
			model.setId(id);
			model.setNome(nome);
			model.setCognome(cognome);
			model.setCf(cf);
			model.setEnte_servizio(ente);
			model.setRuolo(ruolo);
			model.setRelazioneId((codRelazione!=null && codRelazione>0) ? codRelazione : null);
			model.setTelefono(telefono);
			model.setCel(cellulare);
			model.setEmail(email);
			model.setVia(indirizzo);
			if(comune != null)
				model.setComune(comune.getCodIstatComune());
			model.setDataNascita(dataNascita);
			model.setCodStatoCivile(codStatoCivile);
			model.setSesso(datiSesso.getSesso());	
			ComuneBean cb = this.comuneNazioneNascitaMan.getComuneNascitaMan().getComune();
			AmTabNazioni nb = this.comuneNazioneNascitaMan.getNazioneNascitaMan().getNazione();
			model.setComuneNascitaCod(cb!=null ? cb.getCodIstatComune() : null);
			model.setComuneNascitaDes(cb!=null ? cb.getDenominazione() : null);
			model.setProvNascitaCod(cb!=null ? cb.getSiglaProv():null);
			model.setStatoNascitaCod(nb!=null ? nb.getCodIstatNazione() : null);
			model.setStatoNascitaDes(nb!=null ? nb.getNazione(): null);
			model.setCsOSettoreId((settoreId!=null && settoreId>0) ? settoreId : null);	//SISO-346
		}
		return model;
	}
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
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
	public String getEnte() {
		return ente;
	}
	public void setEnte(String ente) {
		this.ente = ente;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public Long getCodRelazione() {
		return codRelazione;
	}
	public void setCodRelazione(Long codRelazione) {
		this.codRelazione = codRelazione;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCellulare() {
		return cellulare;
	}
	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	public void initFromModel(SsSchedaSegnalante segnalante, boolean copia) {
		if(segnalante != null){
			
			if(!copia)
				id = segnalante.getId();
			
			nome = segnalante.getNome();
			cognome = segnalante.getCognome();
			cf = segnalante.getCf();
			ente = segnalante.getEnte_servizio();
			ruolo = segnalante.getRuolo();
			codRelazione = segnalante.getRelazioneId();
			telefono = segnalante.getTelefono();
			cellulare = segnalante.getCel();
			email = segnalante.getEmail();
			indirizzo = segnalante.getVia();
			comune = getComune(segnalante.getComune());
			dataNascita = segnalante.getDataNascita();
			codStatoCivile = segnalante.getCodStatoCivile();
			datiSesso.setSesso(segnalante.getSesso());
			
			if(segnalante.getComuneNascitaCod()!=null){
		    	ComuneBean comuneBean = new ComuneBean(segnalante.getComuneNascitaCod(), segnalante.getComuneNascitaDes(), segnalante.getProvNascitaCod());
		    	this.comuneNazioneNascitaMan.getComuneNascitaMan().setComune(comuneBean);
		    }else if(segnalante.getStatoNascitaCod()!=null){
		    	AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(segnalante.getStatoNascitaCod(), segnalante.getStatoNascitaDes());
				comuneNazioneNascitaMan.getNazioneNascitaMan().setNazione(amTabNazioni);
				comuneNazioneNascitaMan.setValue(comuneNazioneNascitaMan.getNazioneValue());
			}

			settoreId = segnalante.getCsOSettoreId();	//SISO-346
		}
	}
	
	@Override
	public ArrayList<ComuneBean> getLstComuni(String query) {
		ArrayList<ComuneBean> lstComuni = new ArrayList<ComuneBean>();		
		try {
			AccessTableComuniSessionBeanRemote bean = (AccessTableComuniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableComuniSessionBean");
			List<AmTabComuni> beanLstComuni = bean.getComuniByDenomContains(query,true);
			if (beanLstComuni != null) {
				for(AmTabComuni comune : beanLstComuni) { 
					ComuneBean cb = new ComuneBean(comune);
					lstComuni.add(cb);
				}
			}			
		} catch (NamingException e) {
			logger.error(e);
		}
		return lstComuni;
	}
	
	public ComuneBean getComune(String id){
		try {
			AccessTableComuniSessionBeanRemote bean = (AccessTableComuniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableComuniSessionBean");
			AmTabComuni comune = bean.getComuneByIstat(id);
			if(comune!=null)
				return new ComuneBean(comune.getCodIstatComune(),comune.getDenominazione(), comune.getSiglaProv());
			
		} catch (NamingException e) {
			logger.error(e);
		}
		return null;
	}

	@Override
	public void handleChangeComune(AjaxBehaviorEvent event) {
	}

	@Override
	public Converter getComuneConverter() {
		return new ComuneConverter();
	}

	@Override
	public String getTipoComune() {
		return "Residenza";
	}

	@Override
	public ComuneBean getComune() {
		return comune;
	}

	@Override
	public void setComune(ComuneBean comune) {
		this.comune = comune;
	}

	@Override
	public String getWidgetVar() {
		return "wdgTestComune";
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public SessoBean getDatiSesso() {
		return datiSesso;
	}

	public void setDatiSesso(SessoBean datiSesso) {
		this.datiSesso = datiSesso;
	}

	public String getCodStatoCivile() {
		return codStatoCivile;
	}

	public void setCodStatoCivile(String codStatoCivile) {
		this.codStatoCivile = codStatoCivile;
	}

	public ComuneNazioneNascitaMan getComuneNazioneNascitaMan() {
		return comuneNazioneNascitaMan;
	}

	public void setComuneNazioneNascitaMan(
			ComuneNazioneNascitaMan comuneNazioneNascitaMan) {
		this.comuneNazioneNascitaMan = comuneNazioneNascitaMan;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public Long getSettoreId() {
		return settoreId;
	}

	public void setSettoreId(Long settoreId) {
		this.settoreId = settoreId;
	}

	public boolean isReqCf() {
		return reqCf;
	}

	public void setReqCf(boolean reqCf) {
		this.reqCf = reqCf;
	}
	
	public boolean isEmpty(){
		return nome==null && cognome==null && cf==null &&
		ente==null && ruolo==null && telefono==null &&
		cellulare==null && email==null && indirizzo==null && numero==null &&
		comune==null && dataNascita==null &&
		codStatoCivile==null && codRelazione==null && settoreId==null;
	}

	@Override
	public String getCodiceFiscale() {
		return cf;
	}

	@Override
	public void setCodiceFiscale(String codiceFiscale) {
		this.cf=codiceFiscale;
		
	}

	@Override
	public String getCittadinanza() {		
		return null;
	}
	
	
}
