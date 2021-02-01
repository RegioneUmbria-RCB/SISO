package it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ver1.tabs;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDDiarioDoc;
import it.webred.cs.data.model.CsLoadDocumento;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.jsf.manbean.DiarioDocsMan;
import it.webred.cs.jsf.manbean.UploadFileMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

public class PrescrizioniSpecialisticheMan extends CsUiCompBaseBean {

	public static final String NAME = "Prescrizioni Specialistiche";
	private List<String> lstvalutazionePsc;
	private List<String> lstPsicoterapia;
	private List<String> lstValCapacitiva;
	private List<SelectItem> lstRelazioni;
	
	private boolean removeFile;
	private boolean uploadDisabled;
	private DiarioDocsMan diarioDocsMan;
    private CsLoadDocumento csLoadDocumento;
	
	public PrescrizioniSpecialisticheMan()
	{
		diarioDocsMan = new DiarioDocsMan();
		
		setRemoveFile(false);
		uploadDisabled = false;
		diarioDocsMan = new DiarioDocsMan();
		diarioDocsMan.getuFileMan().setExternalSave(true);
		csLoadDocumento = new CsLoadDocumento();
		
		loadListe();
	}
	
	public void loadDocumento(CsDDiario diario){
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(diario);
		//csLoadDocumento = diarioService.findDocDiario(dto);
		Set<CsDDiarioDoc> lstDocumenti = diario.getCsDDiarioDocs();
		try{
		csLoadDocumento = (lstDocumenti!=null && !lstDocumenti.isEmpty()) ? diario.getCsDDiarioDocs().iterator().next().getCsLoadDocumento() : null ;
		if(csLoadDocumento!=null && csLoadDocumento.getId()>0)
			uploadDisabled=true;
		else
			uploadDisabled=false;
		}catch(Exception e){
			logger.error("Errore nel caricamento dei documenti associati al provvedimento del tribunale");
		}
	}

	// /MERHODS - PRIVATE
	private void loadListe()
	{
		if (this.lstvalutazionePsc == null) {
			this.lstvalutazionePsc=new  LinkedList<String>();
			this.lstvalutazionePsc.add("Minore");
			this.lstvalutazionePsc.add("Genitori");
			this.lstvalutazionePsc.add("Padre");
			this.lstvalutazionePsc.add("Madre");
			this.lstvalutazionePsc.add("Altri Adulti");
			this.lstvalutazionePsc.add("Sulla relazione adulti/minore");
		}
		if (this.lstPsicoterapia == null) {
			this.lstPsicoterapia=new  LinkedList<String>();
			this.lstPsicoterapia.add("Minore");
			this.lstPsicoterapia.add("Adulto");
			this.lstPsicoterapia.add("Madre");
			this.lstPsicoterapia.add("Padre");
		}
		if (this.lstValCapacitiva == null) {
			this.lstValCapacitiva=new  LinkedList<String>();
			this.lstValCapacitiva.add("Genitoriale");
			this.lstValCapacitiva.add("Parentali");
			this.lstValCapacitiva.add("Madre");
			this.lstValCapacitiva.add("Padre");
		}
		if(this.lstRelazioni == null){
			lstRelazioni = new ArrayList<SelectItem>();
			
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			AccessTableConfigurazioneSessionBeanRemote confService = 
					(AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");  
			
			List<CsTbTipoRapportoCon> lstParenti =    confService.getTipoRapportoConParenti(bo);
			List<CsTbTipoRapportoCon> lstConoscenti = confService.getTipoRapportoConConoscenti(bo);
			
			if(!lstParenti.isEmpty()){
				SelectItemGroup parGroup = new SelectItemGroup("Parenti");
				List<SelectItem> lst = new ArrayList<SelectItem>();
				for(CsTbTipoRapportoCon rapporto : lstParenti)
					lst.add(new SelectItem(rapporto.getId().toString(),rapporto.getDescrizione()));
				
				parGroup.setSelectItems(lst.toArray(new SelectItem[lst.size()]));
				lstRelazioni.add(parGroup);
			}
			
			if(!lstConoscenti.isEmpty()){
				SelectItemGroup conGroup = new SelectItemGroup("Conoscenti");
				List<SelectItem> lst = new ArrayList<SelectItem>();
				for(CsTbTipoRapportoCon rapporto : lstConoscenti)
					lst.add(new SelectItem(rapporto.getId().toString(),rapporto.getDescrizione()));
				
				conGroup.setSelectItems(lst.toArray(new SelectItem[lst.size()]));
				lstRelazioni.add(conGroup);
			}
			  
		}
	}
	
	// //****GETTER AND SETTER
	public String getTabName()
	{
		return NAME;
	}

	public List<String> getLstvalutazionePsc() {
		return lstvalutazionePsc;
	}

	public void setLstvalutazionePsc(List<String> lstvalutazionePsc) {
		this.lstvalutazionePsc = lstvalutazionePsc;
	}

	public List<String> getLstPsicoterapia() {
		return lstPsicoterapia;
	}

	public void setLstPsicoterapia(List<String> lstPsicoterapia) {
		this.lstPsicoterapia = lstPsicoterapia;
	}

	public List<String> getLstValCapacitiva() {
		return lstValCapacitiva;
	}

	public void setLstValCapacitiva(List<String> lstValCapacitiva) {
		this.lstValCapacitiva = lstValCapacitiva;
	}

	public void salvaDocumento(CsDDiario diario){
		if(removeFile){
			//Ho rimosso il file presente o sono passata ad altra modalità di redazione
			Set<CsDDiarioDoc> lstDocs = diario.getCsDDiarioDocs();
			Iterator<CsDDiarioDoc> iter = lstDocs.iterator();
			if(lstDocs!=null && !lstDocs.isEmpty()){
				while(iter.hasNext()){
					CsLoadDocumento doc  = (iter.next()).getCsLoadDocumento();
					diarioDocsMan.deleteDocumentoFromDB(doc.getId());
				}
				diario.setCsDDiarioDocs(null);
			}
		}
		
		diarioDocsMan.getuFileMan().setIdDiario(diario.getId());
		for(CsLoadDocumento loadDoc: diarioDocsMan.getuFileMan().getDocumentiUploaded())
			diarioDocsMan.getuFileMan().salvaDocumento(loadDoc);
		
		uploadDisabled=true;
		removeFile=false;
	}
	
	public void eliminaDocumento(){
		uploadDisabled=false;
		removeFile=true;
		diarioDocsMan = new DiarioDocsMan();
		diarioDocsMan.getuFileMan().setExternalSave(true);
	}

	public boolean isUploadDisabled() {
		return uploadDisabled;
	}

	public void setUploadDisabled(boolean uploadDisabled) {
		this.uploadDisabled = uploadDisabled;
	}

	public DiarioDocsMan getDiarioDocsMan() {
		return diarioDocsMan;
	}

	public void setDiarioDocsMan(DiarioDocsMan diarioDocsMan) {
		this.diarioDocsMan = diarioDocsMan;
	}

	public boolean isRemoveFile() {
		return removeFile;
	}

	public void setRemoveFile(boolean removeFile) {
		this.removeFile = removeFile;
	}

	public CsLoadDocumento getCsLoadDocumento() {
		return csLoadDocumento;
	}

	public void setCsLoadDocumento(CsLoadDocumento csLoadDocumento) {
		this.csLoadDocumento = csLoadDocumento;
	}

	public List<SelectItem> getLstRelazioni() {
		return lstRelazioni;
	}

	public void setLstRelazioni(List<SelectItem> lstRelazioni) {
		this.lstRelazioni = lstRelazioni;
	}

	public void eliminaDocumenti(CsDDiario csDDiario) {
		this.setRemoveFile(true);
		this.salvaDocumento(csDDiario);
	}
	
}
