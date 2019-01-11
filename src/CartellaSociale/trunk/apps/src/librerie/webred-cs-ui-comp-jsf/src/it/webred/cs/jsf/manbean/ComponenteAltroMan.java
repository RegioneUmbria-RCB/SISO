package it.webred.cs.jsf.manbean;

import it.webred.cs.csa.ejb.client.AccessTableComuniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsAFamigliaGruppo;
import it.webred.cs.jsf.interfaces.IDatiComponenteOAltro;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

@ManagedBean
@NoneScoped
public class ComponenteAltroMan extends CsUiCompBaseBean implements IDatiComponenteOAltro {


	protected Long soggettoId;
	private Date dtRif;
	
	private Long idComponente;
	private String compDenominazione;
	private String compIndirizzo;
	private String compCitta;
	private String compTelefono;
	
	private CsAComponente componenteSel;
	
	private ComuneResidenzaMan comuneResidenzaMan;
	private List<SelectItem> listaParenti;

	public ComponenteAltroMan(Long soggettoId){
		this.soggettoId = soggettoId;
		this.initiListaParenti();
		comuneResidenzaMan = new ComuneResidenzaMan();
	}
	
	//SISO-745
	public ComponenteAltroMan(Long soggettoId, List<SelectItem> listaParenti){
		this.soggettoId = soggettoId;
		this.initiListaParenti(listaParenti);
		comuneResidenzaMan = new ComuneResidenzaMan();
	}
	
	public Long getSoggettoId() {
		return soggettoId;
	}

	public void setSoggettoId(Long soggettoId) {
		this.soggettoId = soggettoId;
	}

	@Override
	public List<SelectItem> getLstParenti() {
		return listaParenti;
	}
	
	//SISO-745
	private void initiListaParenti(List<SelectItem> listaParenti){
		if(listaParenti != null && listaParenti.size() > 0){
			this.listaParenti = new ArrayList<SelectItem>();
			this.listaParenti.addAll(listaParenti);
		}else{
			initiListaParenti();
		}
	}
	
	private void initiListaParenti(){
		listaParenti = new ArrayList<SelectItem>();
		listaParenti.add(new SelectItem(null, "- seleziona -"));
		try {
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			List<CsAComponente> lstC = this.caricaParenti();
			for(CsAComponente c : lstC){
				CsAAnagrafica ana = c.getCsAAnagrafica();
				String descrizione = ana.getCognome()+" "+ana.getNome()+" ("+c.getCsTbTipoRapportoCon().getDescrizione()+")";
				listaParenti.add(new SelectItem(c.getId(),descrizione));
			}
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	private List<CsAComponente> caricaParenti() {
		List<CsAComponente> lstComponenti = new ArrayList<CsAComponente>();
		if(soggettoId!=null){
			BaseDTO bo = new BaseDTO();
			fillEnte(bo);
			bo.setObj(soggettoId);
			AccessTableSchedaSessionBeanRemote schedaService;
			try {
				schedaService = (AccessTableSchedaSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSessionBean");
				bo.setObj2(dtRif);
				bo.setObj3(true);
				CsAFamigliaGruppo famiglia = schedaService.findFamigliaAllaDataBySoggettoId(bo);
				if(famiglia != null)
					lstComponenti = famiglia.getCsAComponentes();
				
			
			} catch (NamingException e) {
				addErrorFromProperties("caricamento.error");
				logger.error(e.getMessage(),e);
			}
		}else
			logger.error("Impossibile caricare la lista di familiari: soggettoId non valorizzato");
		return lstComponenti;
	}

	private CsAComponente getComponente(Long id){
		List<CsAComponente> lst =  this.caricaParenti();
		CsAComponente componente = null;
		int i=0;
		boolean trovato = false;
		while(i<lst.size() && !trovato){
			Long idC = lst.get(i).getId();
			if(idC.compareTo(id)==0){
				trovato=true;
				componente = lst.get(i);
			}
			i++;
		}
		return componente;
	}
	
	@Override
	public void aggiornaComponente() {
		if(idComponente!=null)
			this.componenteSel = this.getComponente(idComponente);
		else
			this.componenteSel=null;
	}

	public Long getIdComponente() {
		return idComponente;
	}

	public void setIdComponente(Long idComponente) {
		this.idComponente = idComponente;
	}

	public String getCompDenominazione() {
		return compDenominazione;
	}

	public void setCompDenominazione(String compDenominazione) {
		this.compDenominazione = compDenominazione;
	}

	public String getCompIndirizzo() {
		return compIndirizzo;
	}

	public void setCompIndirizzo(String compIndirizzo) {
		this.compIndirizzo = compIndirizzo;
	}

	public String getCompCitta() {
		if(this.comuneResidenzaMan.getComune()!=null)
			this.compCitta = this.comuneResidenzaMan.getComune().getDenominazione()+"-"+this.comuneResidenzaMan.getComune().getSiglaProv();
		return compCitta;
	}

	public void setCompCitta(String compCitta) {
		this.compCitta = compCitta;
	}

	public String getCompTelefono() {
		return compTelefono;
	}

	public void setCompTelefono(String compTelefono) {
		this.compTelefono = compTelefono;
	}

	public CsAComponente getComponenteSel() {
		return componenteSel;
	}

	public void setComponenteSel(CsAComponente componenteSel) {
		this.componenteSel = componenteSel;
	}

	public ComuneResidenzaMan getComuneResidenzaMan() {
		return comuneResidenzaMan;
	}

	public void setComuneResidenzaMan(ComuneResidenzaMan comuneResidenzaMan) {
		this.comuneResidenzaMan = comuneResidenzaMan;
	}
	
	public void setComuneResidenzaMan(String citta, String prov){
		try{
		AccessTableComuniSessionBeanRemote bean = (AccessTableComuniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableComuniSessionBean");
		AmTabComuni amComune = bean.getComuneByDenominazioneProv(citta, prov);
		ComuneBean comune = new ComuneBean(amComune);
		comuneResidenzaMan.setComune(comune);
		}catch(Exception e){
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}

	public Date getDtRif() {
		return dtRif;
	}

	public void setDtRif(Date dtRif) {
		this.dtRif = dtRif;
	}
	
	public void reset(){
		this.compCitta=null;
		this.compDenominazione=null;
		this.compIndirizzo=null;
		this.componenteSel=null;
		this.compTelefono=null;
		this.idComponente = null;
		this.comuneResidenzaMan = new ComuneResidenzaMan();
	}

}
