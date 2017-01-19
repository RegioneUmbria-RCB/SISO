package it.webred.cs.csa.web.manbean.fascicolo;

import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIndirizzoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSocialeBASIC;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.jsf.bean.DatiCasoBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.util.ArrayList;
import java.util.List;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

public class FascicoloCompBaseBean extends CsUiCompBaseBean {

	protected AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableDiarioSessionBean");
	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");
	protected AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
	protected AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");
	protected AccessTableConfigurazioneSessionBeanRemote configService = (AccessTableConfigurazioneSessionBeanRemote)getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	protected AccessTableOperatoreSessionBeanRemote operatoreService = (AccessTableOperatoreSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableOperatoreSessionBean");	
	protected AccessTableSchedaSessionBeanRemote schedaService = (AccessTableSchedaSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSessionBean");	
	protected AccessTableIndirizzoSessionBeanRemote indirizzoService = (AccessTableIndirizzoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableIndirizzoSessionBean");
	
	
	protected Long idCaso;
	protected Long idSoggetto;
	protected CsASoggettoLAZY csASoggetto;
	protected List<CsCCategoriaSocialeBASIC> catsocCorrenti;
	
	protected boolean readOnly;
	
	public void initialize(CsASoggettoLAZY soggetto, List<CsCCategoriaSocialeBASIC> catsocCorrenti) {
		
		try{
			csASoggetto = soggetto;
			idSoggetto = soggetto.getAnagraficaId();
			idCaso = soggetto.getCsACaso().getId();
			if(catsocCorrenti!=null)
				this.catsocCorrenti = catsocCorrenti;
			else
				this.catsocCorrenti = loadCatSocialiAttuali(soggetto);
			
			initializeData();
		}catch(Exception e){
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}

	}
	
	public void initialize(CsASoggettoLAZY soggetto) {
		
		try{
			initialize(soggetto, null);
		}catch(Exception e){
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}

	}
	
	public void initialize(DatiCasoBean selectedCaso){
		try{
			BaseDTO b = new BaseDTO();
			fillEnte(b);
			b.setObj(selectedCaso.getSoggetto().getAnagraficaId());
			
			csASoggetto = soggettoService.getSoggettoById(b);

			idSoggetto = csASoggetto.getAnagraficaId();
			idCaso = csASoggetto.getCsACaso().getId();
			
			catsocCorrenti=loadCatSocialiAttuali(csASoggetto);
			
			initializeDataSel(selectedCaso);
		}catch(Exception e){
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	private List<CsCCategoriaSocialeBASIC> loadCatSocialiAttuali(CsASoggettoLAZY soggetto){
		//Recupero la categoria sociale
		BaseDTO b = new BaseDTO();
		fillEnte(b);
		b.setObj(soggetto.getAnagraficaId());
		List<CsCCategoriaSocialeBASIC> catsocCorrenti = soggettoService.getCatSocAttualiBySoggetto(b);
		return catsocCorrenti;
	}
	
	protected List<Long> getLstIdCatSoc(){
		List<Long> lst = new ArrayList<Long>();
		if(this.catsocCorrenti!=null && !this.catsocCorrenti.isEmpty()){
			for(CsCCategoriaSocialeBASIC c : catsocCorrenti)
				lst.add(c.getId());
		}
		return lst;
	}
	
	public String getDescCatSocialiCorrenti(){
		String s="";
		if(this.catsocCorrenti!=null && !this.catsocCorrenti.isEmpty()){
			for(CsCCategoriaSocialeBASIC c : catsocCorrenti)
				s+=","+c.getTooltip();
			s=s.substring(1);
		}
		return s;
	}

	protected void initializeData() {}
	
	protected void initializeDataSel(DatiCasoBean selectedCaso){}
	
	public Long getIdCaso() {
		return idCaso;
	}

	public void setIdCaso(Long idCaso) {
		this.idCaso = idCaso;
	}

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public List<CsCCategoriaSocialeBASIC> getCatsocCorrenti() {
		return catsocCorrenti;
	}

	public void setCatsocCorrenti(List<CsCCategoriaSocialeBASIC> catsocCorrenti) {
		this.catsocCorrenti = catsocCorrenti;
	}

	public CsASoggettoLAZY getCsASoggetto() {
		return csASoggetto;
	}

	public void setCsASoggetto(CsASoggettoLAZY csASoggetto) {
		this.csASoggetto = csASoggetto;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	public static Object getBean(String beanName){
	    Object bean = null;
	    FacesContext fc = FacesContext.getCurrentInstance();
	    if(fc!=null){
	         ELContext elContext = fc.getELContext();
	         bean = elContext.getELResolver().getValue(elContext, null, beanName);
	    }

	    return bean;
	}
	
	protected CsOOperatoreBASIC getOperResponsabileCaso() throws Exception {
		CsOOperatoreBASIC operatoreResponsabile = null;
		
	/*	IterDTO itDto = new IterDTO();
		fillEnte(itDto);
		itDto.setIdCaso(idCaso);
		CsACaso csACaso = casoService.findCasoById(itDto);*/
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(idCaso);
		operatoreResponsabile = casoService.findResponsabileBASIC(dto);
		
		return operatoreResponsabile;
	}
	
	protected CsOOperatore getOperCreatoreCaso() throws Exception {
		CsOOperatore operatoreResponsabile = null;
		
		IterDTO itDto = new IterDTO();
		fillEnte(itDto);
		itDto.setIdCaso(idCaso);
		CsACaso csACaso = casoService.findCasoById(itDto);
		
		//creatore
		OperatoreDTO opDto = new OperatoreDTO();
		fillEnte(opDto);
		opDto.setUsername(csACaso.getUserIns());
		operatoreResponsabile = operatoreService.findOperatoreByUsername(opDto);

		return operatoreResponsabile;
	}
	
}
