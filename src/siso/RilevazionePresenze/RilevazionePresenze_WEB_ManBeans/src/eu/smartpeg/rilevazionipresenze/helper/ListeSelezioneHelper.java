package eu.smartpeg.rilevazionipresenze.helper;

import it.webred.cs.csa.ejb.client.configurazione.AccessTableConfigurazioneEnteSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsTbCondLavoro;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import eu.smartpeg.rilevazionepresenze.AnagraficaSessionBeanRemote;
import eu.smartpeg.rilevazionepresenze.data.model.TipoDocumento;
import eu.smartpeg.rilevazionepresenze.datautil.DataModelCostanti;
import eu.smartpeg.utility.ejb.EjbClientUtility;

public class ListeSelezioneHelper {
		
	public static List<SelectItem> costruisciListaGruppoVulnerabile() {
		List<SelectItem> lista = new ArrayList<SelectItem>();
		CeTBaseObject bo = new CeTBaseObject();
		CeTHelper.fillEnte(bo);
		//TODO: sostituire lookup con @EJB con path
		String remoteName = AccessTableConfigurazioneSessionBeanRemote.class.getCanonicalName();
		AccessTableConfigurazioneSessionBeanRemote accessTableConfigurazioneSessionBeanRemote = (AccessTableConfigurazioneSessionBeanRemote) EjbClientUtility
				.getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean", remoteName);
		List<KeyValueDTO> lst = accessTableConfigurazioneSessionBeanRemote.getGruppiVulnerabili(bo);
		lista = CsUiCompBaseBean.convertiLista(lst);
		return lista;
	}	
	
	public static List<SelectItem> costruisciListaMunicipi() {
		List<SelectItem> res = new ArrayList<SelectItem>();
		List<CsOOrganizzazione> listaOrg = new ArrayList<CsOOrganizzazione>();
		BaseDTO bto = new BaseDTO();
		CeTHelper.fillEnte(bto);
		bto.setObj(DataModelCostanti.CODICE_CATASTALE_MUNICIPI_ROMA);
		// TODO: sostituire lookup con injection @EJB eventualmente con path
		// nell'annotazione
		String remoteName = AccessTableConfigurazioneEnteSessionBeanRemote.class.getCanonicalName();
		AccessTableConfigurazioneEnteSessionBeanRemote configurationCsEnteBean = (AccessTableConfigurazioneEnteSessionBeanRemote) EjbClientUtility
				.getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneEnteSessionBean", remoteName);
		listaOrg = configurationCsEnteBean.getOrganizzazioniByCodCatastale(bto);
		for (CsOOrganizzazione co : listaOrg) {
			res.add(new SelectItem(co.getId(), co.getNome()));
		}
		return res;
	}
	
	public static List<SelectItem> costruisciListaTipoScuola() {
		List<SelectItem> res = new ArrayList<SelectItem>();
		CeTBaseObject cet = new CeTBaseObject();
		CeTHelper.fillEnte(cet);
		String remoteName = AccessTableConfigurazioneSessionBeanRemote.class.getCanonicalName();
		AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) EjbClientUtility
				.getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean", remoteName);
		for (KeyValueDTO cs : confService.getTipoScuole(cet)) {
			res.add(new SelectItem(cs.getCodice(), cs.getDescrizione()));
		}
		return res;
	}
	
	public static List<SelectItem> costruisciListaCondizioneLavorativa() {
		//TODO: sostituire con @EJB
		String remoteName = AccessTableConfigurazioneSessionBeanRemote.class.getCanonicalName();
		AccessTableConfigurazioneSessionBeanRemote accessTableConfigurazioneSessionBeanRemote = (AccessTableConfigurazioneSessionBeanRemote) EjbClientUtility
				.getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean", remoteName);
		
		List<SelectItem>  lista = new ArrayList<SelectItem>();
		lista.add(new SelectItem(null, "- seleziona -"));
		CeTBaseObject xo = new CeTBaseObject();
		CeTHelper.fillEnte(xo);
		TreeMap<String, List<CsTbCondLavoro>> tree = accessTableConfigurazioneSessionBeanRemote
				.getMappaCondLavoro(xo);
		for(String str : tree.keySet()){
            List<CsTbCondLavoro> lst = tree.get(str);
            if (lst != null && !lst.isEmpty()) {
                SelectItemGroup gr = new SelectItemGroup(lst.get(0).getCsTbIngMercato().getDescrizione());
                List<SelectItem> siList = new ArrayList<SelectItem>();
                for (CsTbCondLavoro obj : lst) {
                    SelectItem si = new SelectItem(obj.getId(), obj.getDescrizione());
                    boolean abilitato = obj.getAbilitato()!=null ? obj.getAbilitato().booleanValue() : Boolean.FALSE;
                    if(abilitato)
                      siList.add(si);
                }
                gr.setSelectItems(siList.toArray(new SelectItem[siList.size()]));
                lista.add(gr);
            }
		}

		return lista;
	}
	
	public static List<SelectItem> costruisciListaTipoDocumento() {
		String remoteName = AnagraficaSessionBeanRemote.class.getCanonicalName();
		AnagraficaSessionBeanRemote anagraficaEjb  = (AnagraficaSessionBeanRemote) EjbClientUtility
				.getEjb("RilevazionePresenze", "RilevazionePresenze_EJB", "AnagraficaSessionBean", remoteName);
		
		List<SelectItem> lista= new ArrayList<SelectItem>();
		List<TipoDocumento> listaTipoDocumento = anagraficaEjb.findTipoDocumento();

		for (TipoDocumento tipoDoc : listaTipoDocumento) {
			lista.add(new SelectItem(tipoDoc.getId(), tipoDoc.getDescrizione()));

		}
		return lista;
	}	
	
	public static List<SelectItem> costruisciListaTitoliDiStudio() {
		// TODO: con @EJB non funziona. Utilizzo lookup EJB
		String remoteName = AccessTableConfigurazioneSessionBeanRemote.class.getCanonicalName();
		AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) EjbClientUtility
				.getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean", remoteName);
		List<SelectItem> lista = new ArrayList<SelectItem>();
		CeTBaseObject bo = new CeTBaseObject();
		CeTHelper.fillEnte(bo);
		List<KeyValueDTO> lst = confService.getTitoliStudio(bo);
		lista = CsUiCompBaseBean.convertiLista(lst);
		return lista;
	}	
}
