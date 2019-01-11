package it.webred.cs.json.abitazione;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import it.webred.classfactory.WebredClassFactory;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsTbAbitGestProprietario;
import it.webred.cs.data.model.CsTbAbitTitoloGodimento;
import it.webred.cs.data.model.CsTbTipoAbitazione;
import it.webred.cs.json.SchedaValutazioneManBean;

public abstract class AbitazioneManBaseBean extends SchedaValutazioneManBean implements IAbitazione{
	
	private static final long serialVersionUID = 1L;
	
	protected AccessTableConfigurazioneSessionBeanRemote configService = 
			(AccessTableConfigurazioneSessionBeanRemote)getCarSocialeEjb( "AccessTableConfigurazioneSessionBean");
	
	private List<SelectItem> lstItemsAbTipo=new ArrayList<SelectItem>();
	private List<SelectItem> lstItemsAbTitolo=new ArrayList<SelectItem>();
	private List<SelectItem> lstItemsAbProprietario=new ArrayList<SelectItem>();

	public static IAbitazione initByVersion(String defaultVersion)
	{
		IAbitazione interfaccia = null;
		try {
			interfaccia = (IAbitazione) WebredClassFactory.newInstance("", IAbitazione.class, defaultVersion!=null ? defaultVersion : "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return interfaccia;
	}
	
	public static IAbitazione init(IAbitazione man){
		IAbitazione interfaccia = initByVersion("");
		interfaccia.init(man);
		return interfaccia;
	}
	
	public static IAbitazione initByModel(CsDValutazione val) throws Exception{
		IAbitazione interfaccia = null;
		if (val != null) {
			String jsonString = val.getCsDDiario().getCsDClob().getDatiClob();
			String className = val.getVersioneScheda();

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			interfaccia = (IAbitazione) WebredClassFactory.newInstance(className, IAbitazione.class, defaultVersion);
			
			// Initialize scheda barthel
			interfaccia.init(null, val);
		}
		return interfaccia;
	}
	
	protected void loadListeAbitazione(BaseDTO dto){
		List<CsTbTipoAbitazione> l1 = configService.getListaTipoAbitazione(dto);
		this.lstItemsAbTipo = new ArrayList<SelectItem>();
		for(CsTbTipoAbitazione t: l1){
			SelectItem si = new SelectItem(t.getDescrizione());
			si.setDisabled(!t.getAbilitato());
			this.lstItemsAbTipo.add(si);
		}
		
		List<CsTbAbitTitoloGodimento> l2 = configService.getListaTitoloGod(dto);
		this.lstItemsAbTitolo = new ArrayList<SelectItem>();
		for(CsTbAbitTitoloGodimento t: l2){
			SelectItem si = new SelectItem(t.getDescrizione());
			si.setDisabled(!t.getAbilitato());
			this.lstItemsAbTitolo.add(si);
		}
		
		List<CsTbAbitGestProprietario> l3 = configService.getListaGestProprietario(dto);
		this.lstItemsAbProprietario = new ArrayList<SelectItem>();
		for(CsTbAbitGestProprietario t: l3){
			SelectItem si = new SelectItem(t.getDescrizione());
			si.setDisabled(!t.getAbilitato());
			this.lstItemsAbProprietario.add(si);
		}
	}

	public List<SelectItem> getLstItemsAbTipo() {
		return lstItemsAbTipo;
	}

	public void setLstItemsAbTipo(List<SelectItem> lstItemsAbTipo) {
		this.lstItemsAbTipo = lstItemsAbTipo;
	}

	public List<SelectItem> getLstItemsAbTitolo() {
		return lstItemsAbTitolo;
	}

	public void setLstItemsAbTitolo(List<SelectItem> lstItemsAbTitolo) {
		this.lstItemsAbTitolo = lstItemsAbTitolo;
	}
	
	public List<SelectItem> getLstItemsAbProprietario() {
		return lstItemsAbProprietario;
	}

	public void setLstItemsAbProprietario(List<SelectItem> lstItemsAbProprietario) {
		this.lstItemsAbProprietario = lstItemsAbProprietario;
	}
	
}
