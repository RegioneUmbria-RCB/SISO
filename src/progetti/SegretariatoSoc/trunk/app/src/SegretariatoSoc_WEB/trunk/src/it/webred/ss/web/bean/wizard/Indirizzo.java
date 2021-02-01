package it.webred.ss.web.bean.wizard;

import it.webred.cs.csa.ejb.client.AccessTableComuniSessionBeanRemote;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.jsf.manbean.NazioneResidenzaMan;
import it.webred.cs.jsf.manbean.comuneNazione.ComuneNazioneGenericMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;
import it.webred.ss.data.model.SsIndirizzo;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;

import javax.naming.NamingException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

public class Indirizzo extends SegretariatoSocBaseBean{
	
	protected static Logger logger = Logger.getLogger("segretariatosoc.log");
	
	private Long id;
	private String via;
	//private String numero;
	private String strutturaAccoglienza;
	
	private ComuneNazioneGenericMan comuneNazioneMan;
	private String tipoComune;
	
	
	public void initFromIndirizzo(Indirizzo indirizzo){
		via = indirizzo.getVia();
	
		if(indirizzo.getComuneNazioneMan().isComune())
			this.setComune(indirizzo.getComuneNazioneMan().getComuneMan().getComune());
		else
			this.setNazione(indirizzo.getComuneNazioneMan().getNazioneMan().getNazione());
		
		strutturaAccoglienza=indirizzo.getStrutturaAccoglienza();
	}
	
	public Indirizzo(String tipoComune){
		this.via = null;
		this.tipoComune = tipoComune;
		this.comuneNazioneMan = new ComuneNazioneGenericMan(tipoComune);
	}
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}

  public ComuneBean getComuneByBelfiore(String belfiore){
		try {
			LuoghiService bean = (LuoghiService) ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "LuoghiServiceBean");
			AmTabComuni comune = bean.getComuneItaByBelfiore(belfiore);
			return new ComuneBean(comune);
						
		} catch (NamingException e) {
			logger.error(e);
		}
		return null;
	}
	
	public static ComuneBean getComune(String id){
		try {
			AccessTableComuniSessionBeanRemote bean = (AccessTableComuniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableComuniSessionBean");
			AmTabComuni comune = bean.getComuneByIstat(id);
			if(comune!=null)
				return new ComuneBean(comune);
		} catch (NamingException e) {
			logger.error(e);
		}
		return null;
	}


	
	
	public void setTipoComune(String tipoComune){
		this.tipoComune = tipoComune;
	}


	public void reset(String tipo){
		//numero=null;
		via = null;
		comuneNazioneMan = new ComuneNazioneGenericMan(tipoComune);
		comuneNazioneMan.setComuneValue();
	}


	public void fillModel(SsIndirizzo model) {
		model.setId(id);
		model.setVia(via);
		model.setStrutturaAccoglienza(strutturaAccoglienza);
		//model.setNumero(numero);
		
		//Tolta la pulizia dei campi dato che non c'è più nessuna condizione che lo richieda task 907
		
//		if((this.tipoComune.equalsIgnoreCase(DataModelCostanti.TipoIndirizzo.RESIDENZA) ) 
//	    || (this.tipoComune.equalsIgnoreCase(DataModelCostanti.TipoIndirizzo.DOMICILIO)) 
//		){
//			model.setProvCod(null);
//			model.setProvCod(null);
//			model.setComuneCod(null);
//			model.setComuneDes(null);
//			model.setStatoDes(null);
//			model.setStatoCod(null);
//		}else{
			AmTabNazioni nazione;
			if (comuneNazioneMan.isComune()) {
				//comune italiano
				ComuneBean comune = comuneNazioneMan.getComuneMan().getComune();
				nazione = NazioneResidenzaMan.getCurrNazione();
				model.setProvCod  (comune == null ? null : comune.getSiglaProv());
				model.setComuneCod(comune == null ? null : comune.getCodIstatComune());
				model.setComuneDes(comune == null ? null : comune.getDenominazione());
				model.setStatoCod(nazione == null ? null : nazione.getCodIstatNazione());
				model.setStatoDes(nazione == null ? null : nazione.getNazione());
			} else if (comuneNazioneMan.isNazione()) {
				//stato estero
				nazione = comuneNazioneMan.getNazioneMan().getNazione();
				model.setStatoCod(nazione == null ? null : nazione.getCodIstatNazione());
				model.setStatoDes(nazione == null ? null : nazione.getNazione());
				model.setProvCod(null);
				model.setComuneCod(null);
				model.setComuneDes(null);
			}
		}
	//}


	public void initFromModel(SsIndirizzo model, boolean copia) {
		if(!copia) id = model.getId();
		via = model.getVia();
		strutturaAccoglienza = model.getStrutturaAccoglienza();
		
		if("ITALIA".equals(model.getStatoDes()) || model.getStatoCod()==null) {
			if(model.getComuneCod()!=null){
				ComuneBean comuneBean = new ComuneBean(model.getComuneCod(), model.getComuneDes(), model.getProvCod());
				this.comuneNazioneMan.setComuneValue();
				this.comuneNazioneMan.getComuneGenericMan().setComune(comuneBean);
			}
		} else {
			if(model.getStatoCod()!=null){
				AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(model.getStatoCod(), model.getStatoDes());
				this.comuneNazioneMan.setNazioneValue();
				this.comuneNazioneMan.getNazioneMan().setNazione(amTabNazioni);
			}
		}
	}

	public ComuneNazioneGenericMan getComuneNazioneMan() {
		return comuneNazioneMan;
	}

	public String getTipoComune() {
		return tipoComune;
	}

	public void setComuneNazioneMan(ComuneNazioneGenericMan comuneNazioneMan) {
		this.comuneNazioneMan = comuneNazioneMan;
	}

	public void setComune(ComuneBean comuneBean){
		this.comuneNazioneMan.setComuneValue();
		this.comuneNazioneMan.getComuneGenericMan().setComune(comuneBean);
	}
	
	public void setNazione(AmTabNazioni amTabNazioni){
		//AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(codIstatNazione, desStato);
		this.comuneNazioneMan.setNazioneValue();
		this.comuneNazioneMan.getNazioneMan().setNazione(amTabNazioni);
	}
	
	public boolean validaIndirizzo(){
		boolean ok = true;
		
		if (this.getComuneNazioneMan() != null){
			if( comuneNazioneMan.isComune() && 
				(getComuneNazioneMan().getComuneMan().getComune() == null
				|| getComuneNazioneMan().getComuneMan().getComune().getCodIstatComune() == null
				|| "".equals(getComuneNazioneMan().getComuneMan().getComune().getCodIstatComune().trim()))) {
				addErrorMessage(getLabelOrganizzazione()+" di "+this.tipoComune+" è un campo obbligatorio",""); 
				ok = false;
			}
			if(comuneNazioneMan.isNazione() && 
					(getComuneNazioneMan().getNazioneMan().getNazione() == null
					|| getComuneNazioneMan().getNazioneMan().getNazione().getCodIstatNazione() == null
					|| "".equals(getComuneNazioneMan().getNazioneMan().getNazione().getCodIstatNazione().trim()))) {
					addErrorMessage("Nazione di "+this.tipoComune+" è un campo obbligatorio","");
					ok = false;
				}
		
			if (via == null || "".equals(via.trim())) {
				addErrorMessage("Via di "+this.tipoComune+" è un campo obbligatorio", "");
				ok = false;
			}
		
		}else{
			addErrorMessage("Comune/Nazione di "+this.tipoComune+" è un campo obbligatorio","");
			ok = false;
		}
		
			
		return ok;
	}

	public String getStrutturaAccoglienza() {
		return strutturaAccoglienza;
	}

	public void setStrutturaAccoglienza(String strutturaAccoglienza) {
		this.strutturaAccoglienza = strutturaAccoglienza;
	}
	
	public void onLoadIndirizzoStruttura(){
		Indirizzo indStruttura = new Indirizzo(this.getTipoComune());
		//TODO:Recupero indirizzo struttura
		if(indStruttura.getVia()!=null && indStruttura.getComuneNazioneMan()!=null)
			try {
				indStruttura.setId(id); //Per modificare il record già presente, se esiste.
				initFromIndirizzo(indStruttura);
			} catch (Exception e) {
				SegretariatoSocBaseBean.logger.error(e);
			} 
	}
	
	public boolean isValorizzato(){
		return !StringUtils.isBlank(via) || !StringUtils.isBlank(this.strutturaAccoglienza)|| 
		(this.getComuneNazioneMan().getComuneMan().getComune()!=null   && !StringUtils.isBlank(this.getComuneNazioneMan().getComuneMan().getComune().getCodIstatComune()))||
		(this.getComuneNazioneMan().getNazioneMan().getNazione()!=null && !StringUtils.isBlank(this.getComuneNazioneMan().getNazioneMan().getNazione().getCodIstatNazione()));
	}
	

	public String getDescrizioneIndirizzo() {
		String descrizione = "";
		String comuneStato = "";
			
		descrizione += !StringUtils.isEmpty(this.getStrutturaAccoglienza()) ? "(Struttura di accoglienza: "+this.getStrutturaAccoglienza()+") " :"";
		
		ComuneBean cb = this.getComuneNazioneMan().getComuneMan().getComune();
		comuneStato =  cb!=null ? cb.getDenominazione()+" ("+cb.getSiglaProv()+")" : "" ;
		
		AmTabNazioni naz = this.getComuneNazioneMan().getNazioneMan().getNazione();
		comuneStato += naz!=null ? naz.getNazione() : "";
		
		descrizione += this.getVia() != null ? this.getVia() : ""; 
		descrizione += (!descrizione.isEmpty() && !comuneStato.isEmpty()) ? ", " : "";
		descrizione+= !comuneStato.isEmpty() ?  comuneStato : "";
		
		return descrizione;
	}
	
}
