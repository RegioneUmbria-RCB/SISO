package it.webred.cs.csa.web.bean.util;

import it.webred.amprofiler.ejb.perm.LoginBeanService;
import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmUser;
import it.webred.cs.csa.ejb.client.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsRelSettoreCatsoc;
import it.webred.cs.jsf.manbean.IterInfoStatoMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.parameters.comune.ComuneService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean
@SessionScoped
public class UserBean extends CsUiCompBaseBean {
	
	private String username;
	private String denominazione;
	private String ente;
	
	private Long idOrganizzazione;
	private Long idSettore;
	private List<SelectItem> listaOrganizzazioni;
	private List<SelectItem> listaSettori;
	private List<CsOOperatoreSettore> listaCsOOperatoreSettore;
	private DefaultTreeNode listaInfoSettore;
	private Map<Long, List<SelectItem>> mappaOrgSettoriSI;
	
	private IterInfoStatoMan lastIterStepInfo;
	
	private AccessTableOperatoreSessionBeanRemote opService = (AccessTableOperatoreSessionBeanRemote) getCarSocialeEjb("AccessTableOperatoreSessionBean");

	private AccessTableCatSocialeSessionBeanRemote catSocialeService = (AccessTableCatSocialeSessionBeanRemote) getCarSocialeEjb("AccessTableCatSocialeSessionBean");
	
	private LoginBeanService loginService = (LoginBeanService) getEjb("AmProfiler", "AmProfilerEjb", "LoginBean");
	
	private ComuneService comuneService = (ComuneService) getEjb("CT_Service", "CT_Config_Manager", "ComuneServiceBean");
	
	@PostConstruct
	public void init() {
		caricaSettori();
	}
	
	public String getUsername() {
		
		if (getRequest().getUserPrincipal()==null || getRequest().getUserPrincipal().getName()==null)
			return "";
		
		username = getRequest().getUserPrincipal().getName();
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEnte() {
		
		String es = getRequest().getParameter("es");
		if(es != null && !es.equals("") && getUser() != null){
			AmComune am = comuneService.getComune(getUser().getCurrentEnte());
			ente = am != null? am.getDescrizione(): "";
			ente = ente.substring(0,1).toUpperCase() + ente.substring(1).toLowerCase();
		}
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public List<SelectItem> getListaSettori() {
		
		//inizializzo settori e gruppi
		if(listaSettori == null)
			caricaSettori();

		return listaSettori;
	}
	
	public List<SelectItem> getListaOrganizzazioni() {
		
		//inizializzo settori e gruppi
		if(listaOrganizzazioni == null){
			caricaSettori();
		
			Comparator<SelectItem> comparator = new Comparator<SelectItem>() {
			    @Override
			    public int compare(SelectItem s1, SelectItem s2) {
			        // the items must be compared based on their value (assuming String or Integer value here)
			        return s1.getLabel().compareTo(s2.getLabel());
			    }
			};
			// now that you have the comparator, sort the list using it :
			if(listaOrganizzazioni!=null)
				Collections.sort(listaOrganizzazioni, comparator);
		}
		return listaOrganizzazioni;
	}
	
	private List<CsOOperatoreSettore> loadListaOpSettoreAttivi(List<CsOOperatoreSettore> lst){
		List<CsOOperatoreSettore> lstOut = new ArrayList<CsOOperatoreSettore>();
		
		for(CsOOperatoreSettore os: lst){
			Date oggi = new Date();
			if(!os.getDataInizioApp().after(oggi) && !os.getDataFineApp().before(oggi))
				lstOut.add(os);
		}
		
		return lstOut;
	}
	
	public void caricaSettori() {
		List<CsOSettore> listaSettoriOrg;
		
		try {
			String username = getUsername();
			if (!StringUtils.isEmpty(username)){
				OperatoreDTO opDto = new OperatoreDTO();
				fillEnte(opDto);
				opDto.setUsername(username);
				CsOOperatore operatore = opService.findOperatoreByUsername(opDto);
				if(operatore != null){
					//carico lista organizzazioni e settori dell'utente
					listaSettori = new ArrayList<SelectItem>();
					listaOrganizzazioni = new ArrayList<SelectItem>();
					
					AmUser amUser = getUserByUsername(username);
					if(amUser.getAmGroups() != null)
						getSession().setAttribute("gruppi", amUser.getAmGroups());
					
					HashMap<Long, List<CsOSettore>> mappaOrgSettori = new HashMap<Long, List<CsOSettore>>();
					Map<Long, CsOOrganizzazione>    mappaOrg = new HashMap<Long, CsOOrganizzazione>();
					listaCsOOperatoreSettore = loadListaOpSettoreAttivi(new ArrayList<CsOOperatoreSettore>(operatore.getCsOOperatoreSettores()));
					if(listaCsOOperatoreSettore.size() > 0) {
						for(CsOOperatoreSettore os: listaCsOOperatoreSettore){
						/* Verifico che l'operatore settore sia attivo */
							CsOSettore settore = os.getCsOSettore();
							if(mappaOrgSettori.containsKey(settore.getCsOOrganizzazione().getId()))
								listaSettoriOrg = mappaOrgSettori.get(settore.getCsOOrganizzazione().getId());
							else {
								listaSettoriOrg = new ArrayList<CsOSettore>();
								mappaOrg.put(settore.getCsOOrganizzazione().getId(), settore.getCsOOrganizzazione());
							}
							aggiungiSettore(listaSettoriOrg, settore);
							mappaOrgSettori.put(settore.getCsOOrganizzazione().getId(), listaSettoriOrg);
							
						}
						
						//Valorizzare mappaOrgSettoriSI dai valori precedentemente ordinati
						mappaOrgSettoriSI = new HashMap<Long, List<SelectItem>>();
						Iterator<Long> iterMOS = mappaOrgSettori.keySet().iterator();
						while(iterMOS.hasNext()){
							Long idOrg = iterMOS.next();
							List<CsOSettore> lstSett = mappaOrgSettori.get(idOrg);
							List<CsOSettore> lstOrd = this.ordinaListaSettori(lstSett);
							List<SelectItem> lstSI = new ArrayList<SelectItem>();
							for(CsOSettore s : lstOrd)
								lstSI.add(new SelectItem(s.getId(), s.getNome()));
							mappaOrgSettoriSI.put(idOrg, lstSI);
						}
						
						getSession().setAttribute("tempListaSett", listaCsOOperatoreSettore);
						
						
						idOrganizzazione = null;
						for(CsOOrganizzazione org: mappaOrg.values()) {
							SelectItem si = new SelectItem(org.getId(), org.getNome());
							if(org.getCodRouting()==null || isPermessoCarSocialeEnte(org.getCodRouting(), amUser.getAmGroups()))
								listaOrganizzazioni.add(si);
								
							String codRouting = org.getCodRouting();
							if(codRouting != null && codRouting.equals(opDto.getEnteId())) 
								idOrganizzazione = org.getId();
						}
						if(idOrganizzazione == null && !listaOrganizzazioni.isEmpty()) 
							idOrganizzazione = (Long) listaOrganizzazioni.get(0).getValue();
						
						if(idOrganizzazione!=null)
							listaSettori = mappaOrgSettoriSI.get(idOrganizzazione);
					
						// carico permessi
						if(!listaSettori.isEmpty())
							idSettore = (Long) listaSettori.get(0).getValue();
						for(CsOOperatoreSettore opsettore: listaCsOOperatoreSettore){
							if(idSettore.equals(opsettore.getCsOSettore().getId())){
								getSession().setAttribute("operatoresettore", opsettore);
								setPermessiTreeFromGruppo(opsettore);
								break;
							}
						}
					}
					
					
					/* 
					boolean trovato = false;
					int os=0;
					while(!trovato && os<listaCsOOperatoreSettore.size()) {
						CsOOperatoreSettore opSettore = listaCsOOperatoreSettore.get(os);
						if(opSettore.getCsOSettore().getCsOOrganizzazione().getId()==idOrganizzazione){
							getSession().setAttribute("operatoresettore", opSettore);
							setPermessiFromGruppo(opSettore);
						}
						os++;
					}*/
					
					RequestContext.getCurrentInstance().update("frmUser:menuGenerale");
					
				}else
					addError("Configurazione","Operatore non configurato per accedere alla cartella sociale");
			}
			
		} catch(Exception e) {
			addError("general", "caricamento.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	private void aggiungiSettore(List<CsOSettore> lst , CsOSettore s){
		boolean trovato = false;
		int i = 0;
		while(!trovato && i<lst.size()){
			if(lst.get(i).getId().longValue()==s.getId().longValue()) trovato = true;
			i++;
		}
		
		if(!trovato) lst.add(s);
	}
	
	private List<CsOSettore> ordinaListaSettori(List<CsOSettore> lst){
		List<CsOSettore> lstNoOrd = new ArrayList<CsOSettore>();
		List<CsOSettore> lstNull = new ArrayList<CsOSettore>();
		List<Integer> ord = new ArrayList<Integer>();
		
		for(CsOSettore s : lst){
			if(s.getnOrd()==null) lstNull.add(s);
			else{ 
				lstNoOrd.add(s);
				ord.add(s.getnOrd());
			}
		}
		
		if(!lstNoOrd.isEmpty()){
			CsOSettore[] lstOrd = new CsOSettore[lstNoOrd.size()];
			
			Collections.sort(ord);
			for(CsOSettore s : lstNoOrd){
				int index  = getFirstIndexLibero(ord, lstOrd, s.getnOrd());
				lstOrd[index] = s;
			}
				
			lstNull.addAll(0,Arrays.asList(lstOrd));
		}
		
		return lstNull;
	}
	
	private int getFirstIndexLibero(List<Integer> ord, CsOSettore[] dest, int nOrd){
		int fstIndex = ord.indexOf(nOrd);
		int lstIndex = ord.lastIndexOf(nOrd);
		
		boolean trovato = false;
		while(!trovato && fstIndex<=lstIndex){
			if(dest[fstIndex]==null) trovato = true;
			else fstIndex++;
		}
		return fstIndex;
	}
	
	public void setListaSettori(List<SelectItem> listaSettori) {
		this.listaSettori = listaSettori;
	}
	
	public void handleChangeSettore(AjaxBehaviorEvent event) {
			
		Long id = (Long) ((javax.faces.component.UIInput)event.getComponent()).getValue();
		for(CsOOperatoreSettore opsettore: listaCsOOperatoreSettore){
			if(id.equals(opsettore.getCsOSettore().getId())){
				idSettore = id;
				getSession().setAttribute("operatoresettore", opsettore);
				setPermessiTreeFromGruppo(opsettore);
				break;
			}
		}
		
		try {
			getSession().setAttribute("navigationHistory", "");
			FacesContext.getCurrentInstance().getExternalContext().redirect("home.faces");
		} catch (IOException e) {
			addError("Errore", "Errore durante il reindirizzamento alla Homepage");
		}
	}
	
	public void handleChangeOrganizzazione(AjaxBehaviorEvent event) {
		
		Long id = (Long) ((javax.faces.component.UIInput)event.getComponent()).getValue();
		
		//Resetto l'ente di CeTUSER
		AccessTableConfigurazioneSessionBeanRemote cservice = (AccessTableConfigurazioneSessionBeanRemote)getCarSocialeEjb("AccessTableConfigurazioneSessionBean");
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(id);
		CsOOrganizzazione o = cservice.getOrganizzazioneById(dto);
		String codRouting = o.getCodRouting();
		if(codRouting!=null && !codRouting.trim().isEmpty()) 
			aggiornaEnteCeTUser(codRouting);
		
		listaSettori = mappaOrgSettoriSI.get(id);
		if(!listaSettori.isEmpty())
			idSettore = (Long) listaSettori.get(0).getValue();
		for(CsOOperatoreSettore opsettore: listaCsOOperatoreSettore){
			if(idSettore.equals(opsettore.getCsOSettore().getId())){
				getSession().setAttribute("operatoresettore", opsettore);
				setPermessiTreeFromGruppo(opsettore);
				break;
			}
		}
		
		try {
			getSession().setAttribute("navigationHistory", "");
			FacesContext.getCurrentInstance().getExternalContext().redirect("home.faces");
		} catch (IOException e) {
			addError("Errore", "Errore durante il reindirizzamento alla Homepage");
		}
	}
	
	private void setPermessiTreeFromGruppo(CsOOperatoreSettore opsettore){
		
			//Recupero le cat.sociali configurate per il settore corrente
			BaseDTO bDto = new BaseDTO();
			fillEnte(bDto);
			bDto.setObj(opsettore.getCsOSettore().getId());
			List<CsRelSettoreCatsoc> listaCatSociali = catSocialeService.findRelSettoreCatsocBySettore(bDto);

			
			//listaInfoSettore descrive i permessi e categorie soc derivanti dal settore scelto
			//listaInfoSettore = new ArrayList<KeyValuePairBean<String, Boolean>>();
			
			listaInfoSettore = new DefaultTreeNode("Root", null);
			listaInfoSettore.setExpanded(true);
			
			if(listaCatSociali != null && !listaCatSociali.isEmpty()) {
				TreeNode nodeCatSoc = new DefaultTreeNode("CATEGORIE SOCIALI", listaInfoSettore);
				nodeCatSoc.setExpanded(true);
				for(CsRelSettoreCatsoc settCatsoc: listaCatSociali) {
					nodeCatSoc.getChildren().add(new DefaultTreeNode(settCatsoc.getCsCCategoriaSociale().getTooltip()));
				}
			}
			
			String gruppi = opsettore.getAmGroup();
			if(gruppi != null) {
				String[] arrGruppi = gruppi.split("\\|");
				List<String> listaGruppi = new ArrayList<String>(Arrays.asList(arrGruppi));
				HashMap<String, String> permessiGruppoSettore = new HashMap<String, String>();

				for(String gruppo: listaGruppi) {
					@SuppressWarnings("unchecked")
					HashMap<String, String> permessi = loginService.getPermissionsByGroup(gruppo, getUser().getCurrentEnte());
					permessiGruppoSettore.putAll(permessi);
					
					TreeNode nodePermessiGruppo = new DefaultTreeNode("Permessi Gruppo: " + gruppo, listaInfoSettore);
					nodePermessiGruppo.setExpanded(true);
					Map<String, String> permessiTreeMap = new TreeMap<String, String>(permessi);
					HashMap<String,List<String>> mappaItemPermessi = new HashMap<String, List<String>>();
					for(String permesso: permessiTreeMap.values()) {
						String[] ps = permesso.split("@-@");
						String sitem = ps[2];
						String spermesso = ps[3];
						
						List<String> itemPerm = mappaItemPermessi.get(sitem);
						if(itemPerm==null) itemPerm = new ArrayList<String>();
						itemPerm.add(spermesso);
						mappaItemPermessi.put(sitem, itemPerm);
						
					}
					
					Iterator<String> it = mappaItemPermessi.keySet().iterator();
					while(it.hasNext()){
						String key = (String)it.next();
						TreeNode nodeItem = new DefaultTreeNode(key, nodePermessiGruppo);
						nodeItem.setExpanded(true);
						for(String s : mappaItemPermessi.get(key))
						nodeItem.getChildren().add(new DefaultTreeNode(s));
					}
					
					
					
				}
				getSession().setAttribute("permessiGruppoSettore", permessiGruppoSettore);

			} else getSession().setAttribute("permessiGruppoSettore", null);
	        
		
	}
	
/*	private void setPermessiFromGruppo(CsOOperatoreSettore opsettore) {
		
		//Recupero le cat.sociali configurate per il settore corrente
		BaseDTO bDto = new BaseDTO();
		fillEnte(bDto);
		bDto.setObj(opsettore.getCsOSettore().getId());
		List<CsRelSettoreCatsoc> listaCatSociali = catSocialeService.findRelSettoreCatsocBySettore(bDto);

		
		//listaInfoSettore descrive i permessi e categorie soc derivanti dal settore scelto
		listaInfoSettore = new ArrayList<KeyValuePairBean<String, Boolean>>();
		if(listaCatSociali != null &&
				!listaCatSociali.isEmpty()) {
			listaInfoSettore.add(new KeyValuePairBean<String, Boolean>("CATEGORIE SOCIALI", true));
			for(CsRelSettoreCatsoc settCatsoc: listaCatSociali) {
				listaInfoSettore.add(new KeyValuePairBean<String, Boolean>(settCatsoc.getCsCCategoriaSociale().getTooltip(), false));
			}
		}
		
		String gruppi = opsettore.getAmGroup();
		if(gruppi != null) {
			String[] arrGruppi = gruppi.split("\\|");
			List<String> listaGruppi = new ArrayList<String>(Arrays.asList(arrGruppi));
			HashMap<String, String> permessiGruppoSettore = new HashMap<String, String>();

			for(String gruppo: listaGruppi) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> permessi = loginService.getPermissionsByGroup(gruppo, getUser().getCurrentEnte());
				permessiGruppoSettore.putAll(permessi);
				
				listaInfoSettore.add(new KeyValuePairBean<String, Boolean>("Permessi Gruppo: " + gruppo, true));
				Map<String, String> permessiTreeMap = new TreeMap<String, String>(permessi);
				for(String permesso: permessiTreeMap.values()) {
					String[] ps = permesso.split("@-@");
					listaInfoSettore.add(new KeyValuePairBean<String, Boolean>(ps[3], false));
				}
			}
			getSession().setAttribute("permessiGruppoSettore", permessiGruppoSettore);

		} else getSession().setAttribute("permessiGruppoSettore", null);
	}*/

	public Long getIdSettore() {
		return idSettore;
	}

	public void setIdSettore(Long idSettore) {
		this.idSettore = idSettore;
	}

	public Long getIdOrganizzazione() {
		return idOrganizzazione;
	}

	public void setIdOrganizzazione(Long idOrganizzazione) {
		this.idOrganizzazione = idOrganizzazione;
	}

	public String getDenominazione() {
		if(denominazione == null) {
			String username = getUsername();
			AmAnagrafica amAna = getAnagraficaByUsername(username);
			denominazione = amAna.getCognome() + " " + amAna.getNome();
		}
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	
	
	public DefaultTreeNode getListaInfoSettore() {
		return listaInfoSettore;
	}

	public void setListaInfoSettore(DefaultTreeNode listaInfoSettore) {
		this.listaInfoSettore = listaInfoSettore;
	}

	/**
	 * @return the lastIterStepInfo
	 */
	public IterInfoStatoMan getLastIterStepInfo() {
		return lastIterStepInfo;
	}

	
	/**
	 * @param lastIterStepInfo the lastIterStepInfo to set
	 */
	public void setLastIterStepInfo(IterInfoStatoMan lastIterStepInfo) {
		this.lastIterStepInfo = lastIterStepInfo;
	}

	
}
