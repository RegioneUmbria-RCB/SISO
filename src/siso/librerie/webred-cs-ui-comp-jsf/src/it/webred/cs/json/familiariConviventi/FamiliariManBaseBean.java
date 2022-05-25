package it.webred.cs.json.familiariConviventi;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import it.webred.classfactory.WebredClassFactory;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsTbGVulnerabile;
import it.webred.cs.data.model.CsTbTipologiaFamiliare;
import it.webred.cs.json.SchedaValutazioneManBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

public abstract class FamiliariManBaseBean extends SchedaValutazioneManBean implements IFamConviventi{
	
	private static final long serialVersionUID = 1L;
	
	protected List<SelectItem> lstGruppoVulnerabile;
	protected boolean validaGVulnerabileMigrante=false;
	
	public static IFamConviventi initByVersion(String defaultVersion)
	{
		IFamConviventi interfaccia = null;
		try {
			interfaccia = (IFamConviventi) WebredClassFactory.newInstance("", IFamConviventi.class, defaultVersion!=null ? defaultVersion : "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return interfaccia;
	}
	

	public static IFamConviventi initByModel(CsDValutazione val) throws Exception{
		IFamConviventi interfaccia = null;
		if (val != null) {
			String className = val.getVersioneScheda();

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			interfaccia = (IFamConviventi) WebredClassFactory.newInstance(className, IFamConviventi.class, defaultVersion);
			
			// Initialize scheda barthel
			interfaccia.init(null, val);
		}
		return interfaccia;
	}
	
	public static IFamConviventi init(IFamConviventi man){
		IFamConviventi interfaccia = initByVersion("");
		interfaccia.init(man);
		return interfaccia;
	}
	
	protected CsTbGVulnerabile getGruppoVulnerabile(String codice){
		BaseDTO d = new BaseDTO();
		if(codice!=null && !codice.isEmpty()){
			fillEnte(d);
			d.setObj(codice);
			return confService.getGrVulnerabileById(d);
		}
		return null;
	}
	
	protected CsTbTipologiaFamiliare getTipoNucleo(int codice){
		BaseDTO d = new BaseDTO();
		fillEnte(d);
		d.setObj(Long.valueOf(codice));
		return confService.getTipologiaFamiliareById(d);	
	}
	
	
	public List<SelectItem> getListaGruppoVulnerabile() {
		if(lstGruppoVulnerabile == null){
			lstGruppoVulnerabile = new ArrayList<SelectItem>();
			lstGruppoVulnerabile.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<KeyValueDTO> lst = confService.getGruppiVulnerabili(bo);
			lstGruppoVulnerabile = convertiLista(lst);
		}
		return lstGruppoVulnerabile;
	}

	@Override
	public void setValidaGVulnerabileMigrante(boolean valida){
		this.validaGVulnerabileMigrante = valida;
	}
}
