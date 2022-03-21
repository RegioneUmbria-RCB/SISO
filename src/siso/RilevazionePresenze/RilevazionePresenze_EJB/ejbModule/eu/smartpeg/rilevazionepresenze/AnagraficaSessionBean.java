package eu.smartpeg.rilevazionepresenze;

import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import eu.smartpeg.rievazionepresenze.dto.AnagraficaDTO;
import eu.smartpeg.rilevazionepresenze.data.dto.RpSearchCriteria;
import eu.smartpeg.rilevazionepresenze.data.model.Anagrafica;
import eu.smartpeg.rilevazionepresenze.data.model.DocumentiAnag;
import eu.smartpeg.rilevazionepresenze.data.model.TipoDocumento;
import eu.smartpeg.rilevazionepresenze.ejb.dao.AnagraficaDAO;
/**
 * Session Bean implementation class AnagraficaSessionBean
 */

@Stateless
@LocalBean
public class AnagraficaSessionBean implements AnagraficaSessionBeanRemote, AnagraficaSessionBeanLocal {
	
	@Autowired private AnagraficaDAO anagraficaDAO;	

	@EJB(mappedName = "java:global/CT_Service/CT_Config_Manager/LuoghiServiceBean")
	protected LuoghiService luoghiService;
	
	
	protected static Logger logger = Logger.getLogger("AnagraficaSessionBean.log");
    /**
     * Default constructor. 
     */
    public AnagraficaSessionBean() {
        logger.info(" --- AnagraficaSessionBean Default constructor ---");
    }
    
    public List<Anagrafica> findAll(){
    	return anagraficaDAO.getAnagrafiche();
    	
    }
    public List<TipoDocumento> findTipoDocumento(){
    	return anagraficaDAO.getTipoDocumento();
    }
    
    public List<Anagrafica> findAllReferenti(){
    	return anagraficaDAO.getReferenti();
    	
    }
    public List<Anagrafica> getAppartenentiNucleoFamiliare(Long idReferente){
		return anagraficaDAO.getAppartenentiNucleoFamiliare(idReferente);
    	
    }    

	@Override
	public void eliminaDocumento(long idDocumento) {
		try {			
			anagraficaDAO.eliminaDocumento(idDocumento);			
		}
		catch (Exception e) {
			
		}
	}
	
	@Override
	public Long aggiungiDocumento(DocumentiAnag documento) {
		Long idDocumento = null;
		try {			
			idDocumento= anagraficaDAO.salvaDocumentoAnagrafica(documento);
		}
		catch (Exception e) {
			
		}
		return idDocumento;
	}
	
	@Override
	public String validaAnagrafica(Anagrafica anagrafica) {
		String messaggio = "";
		if (anagrafica.getDataInsediamento() == null) {
			return "La data insediamento è obbligatoria";
		}
		if (anagrafica.getCognome().isEmpty()) {
			return "Il cognome è obbligatorio";
		}
		if (anagrafica.getNome().isEmpty()) {
			return "Il nome è obbligatorio";
		}
		if (anagrafica.getGenere() == null || anagrafica.getGenere().isEmpty()) {
			return "Il genere è obbligatorio";
		}
		if (anagrafica.getDataNascita() == null) {
			return "La data di nascita è obbligatoria";
		}
		if (!anagrafica.getFlgReferente() && anagrafica.getIdReferente() == 0 && anagrafica.getIdParentela() == 0) {
			return "Il referente e il grado di parentela sono obbligatori";
		}

		if (anagrafica.getStruttura() == null) {
			return "Il villaggio è obbligatorio";
		}
		// la stringa che si inserisce nell'unità abitativa non deve contenere spazi:
		if (anagrafica.getUnitaAbitativa().isEmpty()) {
			return "Il codice dell'unità abitativa è obbligatorio";
		}
		// la stringa che si inserisce nell'unità abitativa non deve contenere spazi:
		if (anagrafica.getUnitaAbitativa().indexOf(' ') > 0) {
			return "Il codice dell'unità abitativa non deve contenere spazi";
		}

		return messaggio;

	}
	
	@Override
	public String validaEliminazioneAnagrafica(Anagrafica anagrafica) {
		String messaggio = "";
		if(anagrafica.getFlgReferente()) {
			//controllo se ha altre anagrafiche collegate al nucleo gfamiliare
			if (this.findAllAppartenentiNucleoFamiliare(anagrafica.getId()).size() >0) {
				return  "L'anagrafica non può essere eliminata in quanto referente di nucleo familiare non vuoto";
			}
				
		}
	return messaggio;
	
}
	
	@Override
	public Long salva(Anagrafica anagrafica) {
		
		try {
			return anagraficaDAO.saveAnagrafica(anagrafica) ;
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public void eliminaAnagrafica(Anagrafica anagrafica) {
		try {
			anagraficaDAO.eliminaAnagrafica(anagrafica) ;
				
		}
		catch (Exception e) {
			
		}
		
	}
	
	   
    public List<Anagrafica> findAllAppartenentiNucleoFamiliare(Long idReferente){
    	return anagraficaDAO.getAppartenentiNucleoFamiliare(idReferente);
    	
    }
    
    @Override
    public Anagrafica findReferenteByID(Long idReferente){
    	return anagraficaDAO.findReferenteByID(idReferente);
    	
    }

   @Override
	public List<AnagraficaDTO> searchAnagraficaRPBySoggetto(RpSearchCriteria dto) {
		List<Anagrafica> lstIn = anagraficaDAO.searchAnagraficaBySoggettoSegretariato(dto);
		List<AnagraficaDTO> lstOut = new ArrayList<AnagraficaDTO>();
		for(Anagrafica ana : lstIn){
			AnagraficaDTO out = this.initFromAnagRilevazionePresenze(ana, null);
			lstOut.add(out);
		}
		return lstOut;
	}
   
   @Override
   public Anagrafica findByID(long idAnagrafica) {
	   return anagraficaDAO.findByID(idAnagrafica);
   }

   @Override
   public AnagraficaDTO findAnagraficaById(Long idAnagrafica){
	   Anagrafica ana =  anagraficaDAO.findAnagraficaById(idAnagrafica);
	   return this.initFromAnagRilevazionePresenze(ana, idAnagrafica);
   }
   
   @Override
   public AnagraficaDTO findAnagraficaByCf(String cf){
   	Anagrafica ana =  anagraficaDAO.findAnagraficaByCf(cf);
   	return this.initFromAnagRilevazionePresenze(ana, null);
   }
   
   @Override
   public DocumentiAnag findDocumentoByID(Long idDocumento){
   	return anagraficaDAO.findDocumentoById(idDocumento);
   	
   }
   
   private AmTabNazioni getNazioneByIstat(String codice, String descrizione) {
		AmTabNazioni nazione = null;
		if(codice!=null && !codice.isEmpty()){
			codice = "100".equalsIgnoreCase(codice) ? "1" : codice;
			try{
				nazione = luoghiService.getNazioneByIstat(codice);
			}catch(Exception e){}
			if(nazione==null && descrizione!=null){
				logger.debug("Ricerco Nazione con cod.istat "+codice+ " --> NON TROVATA!");
			    nazione = new AmTabNazioni();
			    nazione.setCodIstatNazione(codice);
			    nazione.setNazione(descrizione);
			}
		}
		
		return nazione;
	}
   
   private AnagraficaDTO initFromAnagRilevazionePresenze(Anagrafica anagraficaRP, Long identificativo){
   		AnagraficaDTO pd = new AnagraficaDTO();
		//pd.setProvenienzaRicerca(tipoRicerca);
		pd.setId(identificativo != null ? identificativo : anagraficaRP.getId());
		pd.setCf(anagraficaRP.getCf());
		pd.setCognome(anagraficaRP.getCognome());
		pd.setNome(anagraficaRP.getNome());
		pd.setDataNascita(anagraficaRP.getDataNascita());
		//pd.setDefunto(false);// non abbiamo questa info
		pd.setSesso(anagraficaRP.getGenere());

		// Cittadinanza
		pd.setCittadinanza(anagraficaRP.getCittadinanza());
		
		// nascita
		String codIstat = anagraficaRP.getComuneNascitaCod() != null ? anagraficaRP.getComuneNascitaCod() : null;
		AmTabComuni comuneNascita = luoghiService.getComuneItaByIstat(codIstat);
		if (comuneNascita != null)
			pd.setComuneNascita(comuneNascita);
		else
			pd.setNazioneNascita(getNazioneByIstat(anagraficaRP.getStatoNascitaCod(), anagraficaRP.getStatoNascitaDes()));

		// Residenza
		pd.setIndirizzoResidenza(anagraficaRP.getIndirizzoResidenza());
		if (!StringUtils.isBlank(anagraficaRP.getComuneResidenzaCod())) {
			AmTabComuni comuneResidenza = luoghiService.getComuneItaByIstat(anagraficaRP.getComuneResidenzaCod());
			pd.setComuneResidenza(comuneResidenza);

		}
		// Domicilio
		pd.setIndirizzoDomicilio(anagraficaRP.getIndirizzoDomicilio());
		if (!StringUtils.isBlank(anagraficaRP.getComuneDomicilioCod())) {
			AmTabComuni comuneDomicilio = luoghiService.getComuneItaByIstat(anagraficaRP.getComuneDomicilioCod());
			pd.setComuneDomicilio(comuneDomicilio);
		}
		
		if (!StringUtils.isBlank(anagraficaRP.getComuneResidenzaCod())) {
			pd.setIndirizzoResidenza(anagraficaRP.getIndirizzoResidenza());
			//imposto il comuneDOmicilio uguale alla residenza
			AmTabComuni comuneDomicilio = luoghiService.getComuneItaByIstat(anagraficaRP.getComuneResidenzaCod());
			pd.setComuneResidenza(comuneDomicilio);
		}

		pd.setIdTitoloStudio(anagraficaRP.getIdTitoloStudio());
		pd.setIdVulnerabilita(anagraficaRP.getIdVulnerabilita());
		pd.setIdCondizioneLavorativa(anagraficaRP.getIdCondizioneLavorativa());
		pd.setIdStruttura(anagraficaRP.getStruttura().getId());
		pd.setIdArea(anagraficaRP.getIdAreaStruttura());
		pd.setUnitaAbitativa(anagraficaRP.getUnitaAbitativa());

		return pd;
	}
}
