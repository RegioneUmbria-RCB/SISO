package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableParentiGitSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.ConfigurazioneDAO;
import it.webred.cs.csa.ejb.dao.ParentiDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsAComponenteGit;
import it.webred.cs.data.model.CsAFamigliaGruppoGit;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.ComponenteFamigliaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Session Bean implementation class AccessTableDataMediciSessionBean
 */
@Stateless
public class AccessTableParentiGitSessionBean extends CarSocialeBaseSessionBean implements AccessTableParentiGitSessionBeanRemote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String ITALIA = "ITALIA";

	@Autowired
	private ParentiDAO parentiDAO;
	
	@Autowired
	private ConfigurazioneDAO configurazioneDAO;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/AnagrafeServiceBean")
	protected AnagrafeService anagrafeService;
	
	/**
     * Default constructor. 
     */
    public AccessTableParentiGitSessionBean() {
        // TODO Auto-generated constructor stub
    }
    
    public CsAFamigliaGruppoGit getFamigliaGruppoGit(BaseDTO dto) {
    	return parentiDAO.getFamigliaGruppoGit((Long) dto.getObj());
    }
    
    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    public List<CsAFamigliaGruppoGit> getFamigliaGruppoGitAggiornate(BaseDTO dto) {
    	return parentiDAO.getFamiglieGruppoGitAggiornate();
    }
    
    public void createFamigliaGruppoGit(BaseDTO dto) {
    	
    	CsASoggettoLAZY csASoggetto = (CsASoggettoLAZY) dto.getObj();
    	CsAFamigliaGruppoGit famigliaGit = new CsAFamigliaGruppoGit();
    	famigliaGit.setCsASoggetto(csASoggetto);
    	famigliaGit.setDataFineApp(DataModelCostanti.END_DATE);
    	famigliaGit.setDataInizioApp(new Date());
    	famigliaGit.setDataInizioSys(new Date());
    	famigliaGit.setUserIns(dto.getUserId());
    	famigliaGit.setDtIns(new Date());
    	    	
    	famigliaGit.setCsAComponenteGits(null);
    	famigliaGit = parentiDAO.saveFamigliaGruppoGit(famigliaGit);
    	
    	RicercaSoggettoAnagrafeDTO rsaDto = new RicercaSoggettoAnagrafeDTO();
		rsaDto.setEnteId(dto.getEnteId());
		rsaDto.setSessionId(dto.getSessionId());
		rsaDto.setCodFis(csASoggetto.getCsAAnagrafica().getCf());
		List<ComponenteFamigliaDTO> lista = anagrafeService.getListaCompFamigliaInfoAggiuntiveByCf(rsaDto);
    	
		/* Ricerco il soggetto titolare della cartella e verifico se è intestatario della scheda anagrafica*/
		boolean parentelaValida = false;
		boolean trovato = false;
		int i=0;
		while(i<lista.size() && !trovato){
			ComponenteFamigliaDTO componenteDto = lista.get(i);
			if(componenteDto.getPersona().getCodfisc() != null && componenteDto.getPersona().getCodfisc().equalsIgnoreCase(csASoggetto.getCsAAnagrafica().getCf())){
				trovato = true;
				CsTbTipoRapportoCon rapp = this.mappaRelazioneParentale(componenteDto.getRelazPar(), dto.getEnteId());
				if(rapp.getId()==DataModelCostanti.INTESTATARIO_SCHEDA_REL_ID) parentelaValida = true;
			}
			i++;
		}
		
		/* Valorizzo i familiari (escludendo il soggetto stesso) */
    	for(ComponenteFamigliaDTO componenteDto: lista) {
    		
    		if(componenteDto.getPersona().getCodfisc() != null && componenteDto.getPersona().getCodfisc().equals(csASoggetto.getCsAAnagrafica().getCf()))
    			continue;
    		
    		CsAComponenteGit componenteGit = new CsAComponenteGit();
    		
    		componenteGit.setCsAFamigliaGruppoGit(famigliaGit);
    		componenteGit.setUserIns(dto.getUserId());
    		componenteGit.setDtIns(new Date());
    		
    		componenteGit.setCognome(componenteDto.getPersona().getCognome());
    		componenteGit.setNome(componenteDto.getPersona().getNome());
    		componenteGit.setSesso(componenteDto.getPersona().getSesso());
    		componenteGit.setCf(componenteDto.getPersona().getCodfisc());
    		componenteGit.setDataNascita(componenteDto.getPersona().getDataNascita());
    		componenteGit.setDataDecesso(componenteDto.getPersona().getDataMor());
    		
    		componenteGit.setComuneNascitaCod(componenteDto.getCodComNas());
    		componenteGit.setComuneNascitaDes(componenteDto.getDesComNas());
    		componenteGit.setProvNascitaCod(componenteDto.getSiglaProvNas());
    		
    		if(!ITALIA.equalsIgnoreCase(componenteDto.getDesStatoNas())){
    			componenteGit.setStatoNascitaCod(componenteDto.getIstatStatoNas());
    			componenteGit.setStatoNascitaDes(componenteDto.getDesStatoNas());
    		}
    		
    		componenteGit.setIndirizzoRes(componenteDto.getIndirizzoResidenza());
    		componenteGit.setNumCivRes(componenteDto.getCivicoResidenza());
    		componenteGit.setComResCod(componenteDto.getCodComRes());
    		componenteGit.setComResDes(componenteDto.getDesComRes());
    		componenteGit.setProvResCod(componenteDto.getSiglaProvRes());
    		componenteGit.setCsTbTipoRapportoCon(mappaRelazioneParentale(componenteDto.getRelazPar(), dto.getEnteId()));
			componenteGit.setParentelaValida(parentelaValida);
			
    		parentiDAO.saveComponenteGit(componenteGit);
    		
    	}

    }
    
    public CsTbTipoRapportoCon mappaRelazioneParentale(String relaz, String ente){
    	if(relaz != null)
			return configurazioneDAO.getTipoRapportoDaRelazPar(relaz, ente);
    	else
    		return null;
    }
    
	public void updateComponenteGit(BaseDTO dto) {
		parentiDAO.updateComponenteGit((CsAComponenteGit) dto.getObj());
	}
	
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
	public void updateFamigliaGruppoGit(BaseDTO dto) {
		parentiDAO.updateFamigliaGruppoGit((CsAFamigliaGruppoGit) dto.getObj());
	}
	
	
}
