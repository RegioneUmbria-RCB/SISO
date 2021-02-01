package eu.smartpeg.rilevazionipresenze.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import eu.smartpeg.rilevazionepresenze.AnagraficaSessionBeanRemote;
import eu.smartpeg.rilevazionepresenze.data.model.TipoDocumento;
import eu.smartpeg.rilevazionepresenze.datautil.DataModelCostanti;
import eu.smartpeg.utility.ejb.EjbClientUtility;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsTbCondLavoro;
import it.webred.cs.data.model.CsTbGVulnerabile;
import it.webred.cs.data.model.CsTbTipoScuola;
import it.webred.cs.data.model.CsTbTitoloStudio;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class ListeSelezioneHelper {
		
	public static List<SelectItem> costruisciListaGruppoVulnerabile() {
		List<SelectItem> lista = new ArrayList<SelectItem>();
		//lista.add(new SelectItem(null, "- seleziona -"));
		CeTBaseObject bo = new CeTBaseObject();
		CeTHelper.fillEnte(bo);
		//TODO: sostituire lookup con @EJB con path
		String remoteName = AccessTableConfigurazioneSessionBeanRemote.class.getCanonicalName();
		AccessTableConfigurazioneSessionBeanRemote accessTableConfigurazioneSessionBeanRemote = (AccessTableConfigurazioneSessionBeanRemote) EjbClientUtility
				.getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean", remoteName);
		List<CsTbGVulnerabile> lst = accessTableConfigurazioneSessionBeanRemote.getGruppiVulnerab(bo);
		if (lst != null) {
			for (CsTbGVulnerabile p : lst) {
				SelectItem fa = new SelectItem(p.getId(), p.getDescrizione());
				fa.setDisabled(!"1".equals(p.getAbilitato()));
				lista.add(fa);
			}
		}
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
		String remoteName = AccessTableConfigurazioneSessionBeanRemote.class.getCanonicalName();
		AccessTableConfigurazioneSessionBeanRemote accessTableConfigurazioneSessionBeanRemote = (AccessTableConfigurazioneSessionBeanRemote) EjbClientUtility
				.getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean", remoteName);
		listaOrg = accessTableConfigurazioneSessionBeanRemote.getOrganizzazioniByCodCatastale(bto);
		for (CsOOrganizzazione co : listaOrg) {
			res.add(new SelectItem(co.getId(), co.getNome()));
		}
		return res;
	}
	
	public static List<SelectItem> costruisciListaTipoScuola() {
		List<SelectItem> res = new ArrayList<SelectItem>();
		List<CsTbTipoScuola> listaTipoScuoleEntity = new ArrayList<CsTbTipoScuola>();
		CeTBaseObject cet = new CeTBaseObject();
		CeTHelper.fillEnte(cet);
		String remoteName = AccessTableConfigurazioneSessionBeanRemote.class.getCanonicalName();
		AccessTableConfigurazioneSessionBeanRemote accessTableConfigurazioneSessionBeanRemote = (AccessTableConfigurazioneSessionBeanRemote) EjbClientUtility
				.getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean", remoteName);
		listaTipoScuoleEntity = accessTableConfigurazioneSessionBeanRemote.getTipoScuole(cet);
		for (CsTbTipoScuola cs : listaTipoScuoleEntity) {
			res.add(new SelectItem(cs.getId(), cs.getDescrizione()));
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
                    if("1".equals(obj.getAbilitato()))
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
		AccessTableConfigurazioneSessionBeanRemote accessTableConfigurazioneSessionBeanRemote = (AccessTableConfigurazioneSessionBeanRemote) EjbClientUtility
				.getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean", remoteName);
		List<SelectItem> lista = new ArrayList<SelectItem>();
		lista.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			CeTHelper.fillEnte(bo);
			List<CsTbTitoloStudio> lst = accessTableConfigurazioneSessionBeanRemote.getTitoliStudio(bo);
			if (lst != null) {
				for (CsTbTitoloStudio obj : lst) {
					lista.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}
		return lista;
	}	
}
