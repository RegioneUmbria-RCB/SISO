package it.webred.amprofiler.ejb;

import it.webred.amprofiler.ejb.anagrafica.AnagraficaService;
import it.webred.amprofiler.ejb.anagrafica.dto.AnagraficaSearchCriteria;
import it.webred.amprofiler.ejb.itemrole.ItemRoleService;
import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmUser;
import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.model.AmInstance;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.config.parameters.application.ApplicationService;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.AnagraficaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class AmProfilerConfigSessionFacadeBean
 */
@Stateless
public class AmProfilerSessionFacadeBean implements
		AmProfilerSessionFacadeBeanRemote, AmProfilerSessionFacadeBeanLocal {


	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/AnagraficaServiceBean")
	protected AnagraficaService anagraficaService;
	
	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/UserServiceBean")
	protected UserService userService;
	
	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/ItemRoleServiceBean")
	protected ItemRoleService itemRoleService;
		
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/AnagrafeServiceBean")
	protected AnagrafeService anagrafeService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Config_Manager/ComuneServiceBean")
	protected ComuneService comuneService;

	@EJB(mappedName = "java:global/CT_Service/CT_Config_Manager/LuoghiServiceBean")
	protected LuoghiService luoghiService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Config_Manager/ApplicationServiceBean")
	protected ApplicationService applicationService;
	
	/**
	 * Default constructor.
	 */
	public AmProfilerSessionFacadeBean() {
	}

	@SuppressWarnings("unchecked")
	/** Recupera l'anagrafica partendo dallo User Name alla quale è collegata.
	 * @param username User name dell'anagrafica da recuperare.
	 * @return
	 */
	@Override
	public AmAnagrafica findAmAnagraficaByUserName(String userName) {
		
		return anagraficaService.findAnagraficaByUserName(userName);
	
	}

	@Override
	public boolean createAmAnagrafica(AmAnagrafica amAnagrafica) {
		return anagraficaService.createAnagrafica(amAnagrafica);
	}

	@Override
	public boolean updateAmAnagrafica(AmAnagrafica amAnagrafica) {
		AmAnagrafica anagOK = anagraficaService.findAnagraficaByUserName(amAnagrafica.getAmUser().getName());
		anagOK.setCapResidenza(amAnagrafica.getCapResidenza());
		anagOK.setCittadinanza(amAnagrafica.getCittadinanza());
		anagOK.setCodiceFiscale(amAnagrafica.getCodiceFiscale());
		anagOK.setCognome(amAnagrafica.getCognome());
		anagOK.setComuneNascita(amAnagrafica.getComuneNascita());
		anagOK.setComuneResidenza(amAnagrafica.getComuneResidenza());
		anagOK.setDataNascita(amAnagrafica.getDataNascita());
		anagOK.setViaResidenza(amAnagrafica.getViaResidenza());
		anagOK.setNome(amAnagrafica.getNome());
		anagOK.setCivicoResidenza(amAnagrafica.getCivicoResidenza());
		anagOK.setProvinciaNascita(amAnagrafica.getProvinciaNascita());
		anagOK.setProvinciaResidenza(amAnagrafica.getProvinciaResidenza());
		anagOK.setSesso(amAnagrafica.getSesso());
		anagOK.setDtUpd(new Date());
		anagOK.setCapDomicilio(amAnagrafica.getCapDomicilio());
		anagOK.setCellulareResidenza(amAnagrafica.getCellulareResidenza());
		anagOK.setCellulareDomicilio(amAnagrafica.getCellulareDomicilio());
		anagOK.setCivicoDomicilio(amAnagrafica.getCivicoDomicilio());
		anagOK.setComuneDomicilio(amAnagrafica.getComuneDomicilio());
		anagOK.setFaxResidenza(amAnagrafica.getFaxResidenza());
		anagOK.setFaxDomicilio(amAnagrafica.getFaxDomicilio());
		anagOK.setProvinciaDomicilio(amAnagrafica.getProvinciaDomicilio());
		anagOK.setStatoEsteroNascita(amAnagrafica.getStatoEsteroNascita());
		anagOK.setTelefonoResidenza(amAnagrafica.getTelefonoResidenza());
		anagOK.setTelefonoDomicilio(amAnagrafica.getTelefonoDomicilio());
		anagOK.setViaDomicilio(amAnagrafica.getViaDomicilio());
		return anagraficaService.updateAnagrafica(anagOK);
	}
	
	@Override
	public boolean createAmUser(AmUser amUser) {
		return userService.createUser(amUser);
	}

	@Override
	public boolean deleteAmUser(String username) {
		return userService.deleteUser(username);
	}
	
	@Override
	public boolean deleteAmUserByApplication(String username, String application, String comune) {
		AmInstance instance = applicationService.getInstanceByApplicationComune(application, comune);
		if(instance != null)
			return userService.deleteUserByIstance(username, instance.getName(), comune);
		return false;
	}

	@Override
	public List<AmUser> findAmUser(String amApplicationName,
			List<String> gruppi, HashMap<String, Object> filtroAnagrafica,
			int fromIndex, int maxResults) {
		
		AnagraficaSearchCriteria criteria = new AnagraficaSearchCriteria();
		criteria.setUserName((String) filtroAnagrafica.get("username"));
		criteria.setCap((String) filtroAnagrafica.get("cap"));
		criteria.setCittadinanza((String) filtroAnagrafica.get("cittadinanza"));
		criteria.setCodiceFiscale((String) filtroAnagrafica.get("codicefiscale"));
		criteria.setCognome((String) filtroAnagrafica.get("cognome"));
		criteria.setNome((String) filtroAnagrafica.get("nome"));
		criteria.setIndirizzo((String) filtroAnagrafica.get("indirizzo"));
		criteria.setSesso((String) filtroAnagrafica.get("sesso"));
		criteria.setComuneNascita((String) filtroAnagrafica.get("comunenascita"));
		criteria.setComuneResidenza((String) filtroAnagrafica.get("comuneresidenza"));
		criteria.setProvinciaNascita((String) filtroAnagrafica.get("provincianascita"));
		criteria.setProvinciaResidenza((String) filtroAnagrafica.get("provinciaresidenza"));
		criteria.setDataNascita((String) filtroAnagrafica.get("datanascita"));
		
		return userService.findAmUser(amApplicationName, gruppi, criteria, fromIndex, maxResults); 
	}

	@Override
	public AmUser findAmUserByCodiceFiscale(String codiceFiscale) {
		AnagraficaSearchCriteria criteria = new AnagraficaSearchCriteria();
		if(codiceFiscale != null && !"".equals(codiceFiscale.trim())){
			criteria.setCodiceFiscale(codiceFiscale);
			List<AmAnagrafica> listaAna = anagraficaService.getListaAnagrafica(criteria);
			if(listaAna.size() > 0)
				return listaAna.get(0).getAmUser();
		}
		return null;
	}

	@Override
	public AmUser findAmUserByName(String username) {
		return userService.getUserByName(username);
	}

	@Override
	public List<String> findApplicationRoles(String ente, String amApplicationName,
			String username) {
		return itemRoleService.findApplicationRoles(ente, amApplicationName, username);
	}

	@Override
	public boolean isAmItemEnabled(String ente, String username, String applicationItem) {
		return itemRoleService.isAmItemEnabled(ente, username, applicationItem);
	}

	@Override
	public boolean updateAmUser(AmUser amUser) {
		return userService.updateUser(amUser);
	}

	@Override
	public List<AnagraficaDTO> findAnagraficaCeTByCodiceFiscale(String ente,
			String codiceFiscale) {

		RicercaSoggettoDTO ricercaDto = new RicercaSoggettoDTO();
		ricercaDto.setEnteId(ente);
		ricercaDto.setCodFis(codiceFiscale);
		List<AnagraficaDTO> lista = anagrafeService.getAnagrafeByCF(ricercaDto);
		for(AnagraficaDTO anagrafica: lista){
			if(anagrafica.getComuneResidenza() != null && anagrafica.getComuneResidenza().getCodIstat() != null){
				AmTabComuni com = luoghiService.getComuneItaByIstat(anagrafica.getComuneResidenza().getCodIstat());
				if(com != null){
					anagrafica.setCapResidenza(com.getCap());
					anagrafica.setProvinciaResidenza(com.getSiglaProv());
				}
			}
		}
		return lista;
	}
	
	@Override
	public List<AnagraficaDTO> findAnagraficaFamiliareCeTByCodiceFiscale(String ente, String username,
			String codiceFiscale) {

		List<AnagraficaDTO> listaAnagrafeFam = new ArrayList<AnagraficaDTO>();
		AnagraficaDTO anagrafeFam = null;
		AmAnagrafica ana = anagraficaService.findAnagraficaByUserName(username);
		RicercaSoggettoDTO rDto = new RicercaSoggettoDTO();
		rDto.setEnteId(ente);
		rDto.setCodFis(ana.getCodiceFiscale());
		List<AnagraficaDTO> anagrafeUser = anagrafeService.getAnagrafeByCF(rDto);
		if(anagrafeUser.size() > 0){
			RicercaSoggettoAnagrafeDTO ricercaDto = new RicercaSoggettoAnagrafeDTO();
			ricercaDto.setEnteId(ente);
			ricercaDto.setCodFis(codiceFiscale);
			ricercaDto.setIdVarSogg(anagrafeUser.get(0).getPersona().getId());
			anagrafeFam = anagrafeService.getPersonaFamigliaByCF(ricercaDto);
			if(anagrafeFam != null && anagrafeFam.getPersona() != null)
				listaAnagrafeFam.add(anagrafeFam);
		}
		return listaAnagrafeFam;
	}

	@Override
	public List<String> findPermissionByEnteItemUsername(String ente,
			String applicationItem, String username) {
		return itemRoleService.findPermissionByEnteItemUsername(ente, applicationItem, username);
	}
	
	@Override
	public List<String> findPermissionByEnteItemUsername(String ente,
			String applicationItem, String username, List<String> gruppi) {
		return itemRoleService.findPermissionByEnteItemUsername(ente, applicationItem, username, gruppi);
	}

	@Override
	public String getNomeEnte(String ente) {
		AmComune am = comuneService.getComune(ente);
		return am != null?am.getDescrizione(): null; 
	}

	@Override
	public List<AmComune> getListaComune() {
		return comuneService.getListaComune();
	}

	@Override
	public List<AmComune> getListaComuneByData(Date data) {
		return comuneService.getListaComuneByData(data);
	}
	
	@Override
	public List<AmTabComuni> getListaComuniIta(){
		return luoghiService.getComuniIta();
	}
	
	@Override
	public List<AmTabNazioni> getListaNazioni(){
		return luoghiService.getNazioni();
	}
	
	@Override
	public AmTabComuni findComuneByDenominazione(String denominazione){
		return luoghiService.getComuneItaByDenominazione(denominazione);
	}
	
	@Override
	public AmTabComuni findComuneByDenominazioneProvincia(String denominazione, String provincia){
		return luoghiService.getComuneItaByDenominazioneProvincia(denominazione, provincia);
	}
	
	@Override
	public boolean verificaAggiornaAvvocato(String username, String ente) {
		return userService.verificaAggiornaAvvocato(username, ente);
	}
	
}
