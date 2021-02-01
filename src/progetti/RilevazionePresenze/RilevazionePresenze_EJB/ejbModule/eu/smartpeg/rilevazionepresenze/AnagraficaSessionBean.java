package eu.smartpeg.rilevazionepresenze;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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
	public List<Anagrafica> searchAnagraficaRPBySoggetto(RpSearchCriteria dto) {
		return anagraficaDAO.searchAnagraficaBySoggettoSegretariato(dto);
	}
   
   @Override
   public Anagrafica findByID(long idAnagrafica) {
	   return anagraficaDAO.findByID(idAnagrafica);
   }

   @Override
   public Anagrafica findAnagraficaById(Long idAnagrafica){
   	return anagraficaDAO.findAnagraficaById(idAnagrafica);
   	
   }
   @Override
   public Anagrafica findAnagraficaByCf(String cf){
   	return anagraficaDAO.findAnagraficaByCf(cf);
   }
   
   @Override
   public DocumentiAnag findDocumentoByID(Long idDocumento){
   	return anagraficaDAO.findDocumentoById(idDocumento);
   	
   }
}
