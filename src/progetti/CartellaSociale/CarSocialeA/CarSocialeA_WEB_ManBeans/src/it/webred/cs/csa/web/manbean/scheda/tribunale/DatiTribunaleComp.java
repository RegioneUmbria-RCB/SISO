package it.webred.cs.csa.web.manbean.scheda.tribunale;

import it.webred.cs.csa.web.manbean.scheda.SchedaValiditaCompUtils;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsTbMicroSegnal;
import it.webred.cs.jsf.interfaces.IDatiTribunale;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

public class DatiTribunaleComp extends SchedaValiditaCompUtils implements IDatiTribunale {

	private List<String> selStrutture = new ArrayList<String>();
	
	private boolean infoNonReperibili;
	private Long idMacroSegnalazione;
	private Long idMicroSegnalazione;
	private Long idMotivoSegnalazione;
	private Date primoContattoAG;
	private String numeroProtocollo;	

	
	private List<CsTbMicroSegnal> listaMicroDaDb = new ArrayList<CsTbMicroSegnal>();
	private List<SelectItem> lstMacroSegnalazioni;
	private List<SelectItem> lstMicroSegnalazioni;
	private List<SelectItem> lstMotiviSegnalazioni;
	private List<SelectItem> lstStrutture;
	
	@Override
	public boolean isInfoNonReperibili() {
		return infoNonReperibili;
	}

	public void setInfoNonReperibili(boolean infoNonReperibili) {
		this.infoNonReperibili = infoNonReperibili;
	}

	@Override
	public Long getIdMacroSegnalazione() {
		return idMacroSegnalazione;
	}
	
	public void setIdMacroSegnalazione(Long idMacroSegnalazione) {
		this.idMacroSegnalazione = idMacroSegnalazione;
	}
	
	@Override
	public Long getIdMicroSegnalazione() {
		return idMicroSegnalazione;
	}
	
	public void setIdMicroSegnalazione(Long idMicroSegnalazione) {
		this.idMicroSegnalazione = idMicroSegnalazione;
	}
	
	@Override
	public Long getIdMotivoSegnalazione() {
		return idMotivoSegnalazione;
	}
	
	public void setIdMotivoSegnalazione(Long idMotivoSegnalazione) {
		this.idMotivoSegnalazione = idMotivoSegnalazione;
	}
	
	@Override
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	
	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	@Override
	public List<SelectItem> getLstMacroSegnalazioni() {
		return lstMacroSegnalazioni;
	}
	
	public void setLstMacroSegnalazioni(List<SelectItem> lstMacroSegnalazioni) {
		this.lstMacroSegnalazioni = lstMacroSegnalazioni;
	}
	
	@Override
	public List<SelectItem> getLstMicroSegnalazioni() {

		if(!this.listaMicroDaDb.isEmpty() && this.idMacroSegnalazione != null && this.idMacroSegnalazione != Long.parseLong("0")){
			lstMicroSegnalazioni = new ArrayList<SelectItem>();
			lstMicroSegnalazioni.add(new SelectItem(null, "- seleziona -"));
			for (CsTbMicroSegnal obj : listaMicroDaDb) {
				if(this.idMacroSegnalazione == obj.getIdMacro())
				lstMicroSegnalazioni.add(new SelectItem(obj.getId(), obj.getDescrizione()));
			}
		}
		return lstMicroSegnalazioni;
	}
	
	public void setLstMicroSegnalazioni(List<SelectItem> lstMicroSegnalazioni) {
		this.lstMicroSegnalazioni = lstMicroSegnalazioni;
	}
	
	public void nonReperibile(){
		if(this.infoNonReperibili){
			idMacroSegnalazione = null;
			idMicroSegnalazione = null;
			idMotivoSegnalazione = null;
			primoContattoAG = null;
			lstMicroSegnalazioni = null;
			numeroProtocollo = null;
			selStrutture = new ArrayList<String>();
		}
		
	}
	
	@Override
	public List<SelectItem> getLstMotiviSegnalazioni() {
		return lstMotiviSegnalazioni;
	}
	
	
	@Override
	public List<SelectItem> getLstStrutture() {
		return lstStrutture;
	}
	
	
	public void setLstMotiviSegnalazioni(List<SelectItem> lstMotiviSegnalazioni) {
		this.lstMotiviSegnalazioni = lstMotiviSegnalazioni;
	}

	public Date getPrimoContattoAG() {
		return primoContattoAG;
	}

	public void setPrimoContattoAG(Date primoContattoAG) {
		this.primoContattoAG = primoContattoAG;
	}

	public List<String> getSelStrutture() {
		return selStrutture;
	}

	public void setSelStrutture(List<String> selStrutture) {
		this.selStrutture = selStrutture;
	}

	public void setLstStrutture(List<SelectItem> lstStrutture) {
		this.lstStrutture = lstStrutture;
	}

	public List<CsTbMicroSegnal> getListaMicroDaDb() {
		return listaMicroDaDb;
	}

	public void setListaMicroDaDb(List<CsTbMicroSegnal> listaMicroDaDb) {
		this.listaMicroDaDb = listaMicroDaDb;
	}

	public void aggiungiStruttura(String s){
		if(this.selStrutture==null) this.selStrutture=new ArrayList<String>();
		this.selStrutture.add(s);
	}
	
	public boolean isTMCivile() {
		return selStrutture.contains(DataModelCostanti.StrutturaTribunale.TM_CIVILE);
	}

	public boolean isTMAmministrativo() {
		return selStrutture.contains(DataModelCostanti.StrutturaTribunale.TM_AMMINISTRATIVO);
	}

	public boolean isPenaleMinorile() {
		return selStrutture.contains(DataModelCostanti.StrutturaTribunale.PENALE_MINORILE);
	}

	public boolean isTO() {
		return selStrutture.contains(DataModelCostanti.StrutturaTribunale.TO);
	}

	public Boolean isProcuraOrdinaria() {
		return selStrutture.contains(DataModelCostanti.StrutturaTribunale.PROCURA_ORDINARIA);
	}

	public boolean isProcuraMinorile() {
		return selStrutture.contains(DataModelCostanti.StrutturaTribunale.PROCURA_MINORILE);
	}

	public boolean isCorteAppello() {
		return selStrutture.contains(DataModelCostanti.StrutturaTribunale.CORTE_APPELLO);
	}

	public Boolean isNIS() {
		return selStrutture.contains(DataModelCostanti.StrutturaTribunale.NIS);
	}
}
