package it.webred.cs.jsf.manbean.superc;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TipoIndirizzo;
import it.webred.cs.data.model.CsTbTipoIndirizzo;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.jsf.interfaces.IResidenza;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;

public abstract class ResidenzaMan extends CsUiCompBaseBean implements IResidenza {
	
	protected static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	public static enum TIPO_LUOGO{
		COMUNE("E", "COMUNE"),
		ALTRO("A", "Altro"),
		SENZA_FISSA_DIMORA("SDF", DataModelCostanti.SENZA_FISSA_DIMORA);
		
		String descrizione;
        String codice;
		
        private TIPO_LUOGO(String codice, String descrizione) {
			this.descrizione = descrizione;
			this.codice = codice;
		}

		public String getDescrizione() {return descrizione;}
		public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
		public String getCodice() {return codice;}
		public void setCodice(String codice) {this.codice = codice;}
	}
	
	
	protected List<CsTbTipoIndirizzo> listaTipoIndirizzo;
	
	public ResidenzaMan(){
		listaTipoIndirizzo = new ArrayList<CsTbTipoIndirizzo>();
		CeTBaseObject bo = new CeTBaseObject();
		fillEnte(bo);
		listaTipoIndirizzo = confService.getTipoIndirizzo(bo);
	}
	
	protected static long nullDateTime;
	static {
		try {
			nullDateTime = SDF.parse("31/12/9999").getTime();
		} catch (Exception e) {}		
	}
	
	public long getNullDateTime() {
		return nullDateTime;
	}
	
	public Date getToday() {
		Date today = null;
		try {
			today = SDF.parse(SDF.format(new Date()));
		} catch (Exception e) {}
		return today;
	}

	public abstract List<?> getLstIndirizzi();

	public abstract List<?> getLstIndirizziForKey(String key);
	
	public abstract void salvaIndirizzi();
	
	public String getPanelHeader() {
		return "Indirizzi inseriti";
	}
	
	public String getTextIfEmpty() {
		return "Nessun indirizzo inserito";
	}
	
	public String getTextIfActiveEmpty() {
		return "Nessun indirizzo attivo";
	}
	
	public String getActiveText() {
		return "ATTIVO";
	}
	
	public String getButtonTooltip() {
		return "Clicca per accedere alla gestione degli indirizzi";
	}
	
	public String getDialogHeader() {
		return "Gestione indirizzi";
	}
	
	public boolean isIndirizziEmpty() {
		List<?> lstIndirizzi = getLstIndirizzi();
		return lstIndirizzi == null || lstIndirizzi.size() == 0;
	}
	
	public abstract String getWidgetVar();
	
	protected CsTbTipoIndirizzo getTipoIndirizzo(String id){
		CsTbTipoIndirizzo tipo = null;
		if (!StringUtils.isBlank(id)) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(new Long(id));
			tipo = confService.getTipoIndirizzoById(dto);
		}
		return tipo;
	}
	
	@SuppressWarnings("unused")
	public ArrayList<SelectItem> getLstTipiIndirizzo() {
		ArrayList<SelectItem> lstTipiIndirizzo = new ArrayList<SelectItem>();
		for (CsTbTipoIndirizzo tipoIndirizzo : listaTipoIndirizzo) {
			lstTipiIndirizzo.add(new SelectItem(tipoIndirizzo.getId(), tipoIndirizzo.getDescrizione()));
		}
		return lstTipiIndirizzo;
	}
	
	public CsTbTipoIndirizzo getTipoIndirizzoResidenza() {
		CsTbTipoIndirizzo tipoIndirizzoResidenza = null;
		for (CsTbTipoIndirizzo tipoIndirizzo : listaTipoIndirizzo) {
			if (tipoIndirizzo.getDescrizione().equalsIgnoreCase(TipoIndirizzo.RESIDENZA)) {
				tipoIndirizzoResidenza = tipoIndirizzo;
				break;
			}
		}
		return tipoIndirizzoResidenza;
	}
	
	public CsTbTipoIndirizzo getTipoIndirizzoDomicilio() {
		CsTbTipoIndirizzo tipoIndirizzoResidenza = null;
		for (CsTbTipoIndirizzo tipoIndirizzo : listaTipoIndirizzo) {
			if (tipoIndirizzo.getDescrizione().equalsIgnoreCase(TipoIndirizzo.DOMICILIO)) {
				tipoIndirizzoResidenza = tipoIndirizzo;
				break;
			}
		}
		return tipoIndirizzoResidenza;
	}

	public abstract void annullaIndirizzo();
	
	public abstract void eliminaIndirizzo();

	public abstract void reset(AjaxBehaviorEvent event);
	
	protected abstract void resetPanel(String clientId);
	
	public abstract void accettaIndirizzoAnagrafe(AjaxBehaviorEvent event);
		
	public abstract void aggiungiIndirizzo();
	
	protected abstract List<?> resetListeIndirizzi(List<?> lstIndirizziFrom);

}
