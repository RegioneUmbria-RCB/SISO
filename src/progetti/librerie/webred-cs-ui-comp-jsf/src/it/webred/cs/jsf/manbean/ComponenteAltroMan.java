package it.webred.cs.jsf.manbean;

import it.webred.cs.csa.ejb.client.AccessTableComuniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsAComponente;
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
	
	private AccessTableSchedaSessionBeanRemote schedaService = (AccessTableSchedaSessionBeanRemote) getCarSocialeEjb("AccessTableSchedaSessionBean");
	
	public ComponenteAltroMan(){
		comuneResidenzaMan = new ComuneResidenzaMan();
	}

	public ComponenteAltroMan(Long soggettoId){
		this();
		this.soggettoId = soggettoId;
		this.initiListaParenti();
	}
	
	public ComponenteAltroMan(Long soggettoId, List<CsAComponente> listaComponenti){
		this();
		this.soggettoId = soggettoId;
		this.initListaParenti(listaComponenti);
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
	
	private void initListaParenti(List<CsAComponente> listaComponenti){
		if(listaComponenti != null){
	
		    listaParenti = new ArrayList<SelectItem>();
			listaParenti.add(new SelectItem(null, "- seleziona -"));
			
			for(CsAComponente c : listaComponenti){
				CsAAnagrafica ana = c.getCsAAnagrafica();
				String descrizione = ana.getDenominazione()+" ("+c.getCsTbTipoRapportoCon().getDescrizione()+")";
				listaParenti.add(new SelectItem(c.getId(),descrizione));
			}

		}else
			initiListaParenti();
	}
	
	private void initiListaParenti(){
		try {
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			List<CsAComponente> lstC = CsUiCompBaseBean.caricaParenti(soggettoId, dtRif);
			initListaParenti(lstC);
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	private CsAComponente getComponente(Long id){
		if(id!=null){
			BaseDTO bo = new BaseDTO();
			fillEnte(bo);
			bo.setObj(id);
			return schedaService.findComponenteById(bo);
		}
		return null;
	}
	
	@Override
	public void aggiornaComponente() {
		if(idComponente!=null)
			this.componenteSel = this.getComponente(idComponente);
		else
			this.componenteSel=null;
	}

	public Long getIdComponente() {
		idComponente = idComponente!=null && idComponente> 0 ? idComponente : null;
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
