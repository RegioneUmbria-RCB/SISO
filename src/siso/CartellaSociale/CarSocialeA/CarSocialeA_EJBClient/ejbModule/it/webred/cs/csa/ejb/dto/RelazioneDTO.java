package it.webred.cs.csa.ejb.dto;

import it.webred.cs.data.model.CsCDiarioConchi;
import it.webred.cs.data.model.CsDPai;
import it.webred.cs.data.model.CsDRelSal;
import it.webred.cs.data.model.CsDRelazione;
import it.webred.cs.data.model.CsDTriage;
import it.webred.cs.data.model.CsLoadDocumento;
import it.webred.cs.data.model.CsRelRelazioneProbl;
import it.webred.cs.data.model.CsRelRelazioneTipoint;
import it.webred.cs.data.model.CsTbMicroAttivita;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class RelazioneDTO extends CeTBaseObject {

	private CsDRelazione relazione;
	private CsLoadDocumento csLaodDocumento;
	private List<PaiDTOExt> listaPaiDTO;
	private List<CsRelRelazioneProbl> listaProblematiche; //SISO-1223
	private List<CsRelRelazioneTipoint> listaInterventi; //SISO-1223
	private boolean containsDoc;
	private CsDTriage triage;
	private CsDRelSal sal; //SISO-1257
	private boolean attivitaSAL;
	private boolean casoSoggettoInteressato;

	public RelazioneDTO(){
		this.relazione = new CsDRelazione();
		this.triage = new CsDTriage();
		this.sal = new CsDRelSal();
		this.casoSoggettoInteressato = false;
	}
	
	public RelazioneDTO(CsDRelazione relazione, CsLoadDocumento documento, List<PaiDTOExt> listaPai) {
		this.casoSoggettoInteressato = false;
		this.relazione = relazione;
		this.csLaodDocumento = documento;
		this.listaPaiDTO = listaPai;
		this.containsDoc = this.csLaodDocumento != null;
		this.triage = relazione.getCsDTriage();
		this.sal = relazione.getCsDRelSal();
		
	}
	
	public RelazioneDTO(CsDRelazione relazione, CsLoadDocumento documento, List<PaiDTOExt> listaPai, List<CsRelRelazioneProbl> listaProblematiche, List<CsRelRelazioneTipoint> listaInterventi ) {
		this(relazione, documento, listaPai);
        this.listaProblematiche = listaProblematiche;
        this.listaInterventi = listaInterventi;
	}
	
	public RelazioneDTO(CsDRelazione relazione, CsLoadDocumento documento, List<PaiDTOExt> listaPai, CsDTriage triage) {//, List<CsRelRelazioneProbl> listaProblematiche) {
		this.casoSoggettoInteressato = false;
		this.relazione = relazione;
		this.csLaodDocumento = documento;
		this.listaPaiDTO = listaPai;
		this.containsDoc = this.csLaodDocumento != null;
		this.triage = triage;
//		this.listaProblematiche = listaProblematiche;
	}
	//SISO-1257
	public RelazioneDTO(CsDRelazione relazione, CsLoadDocumento documento, List<PaiDTOExt> listaPai, CsDRelSal sal) {
		this.casoSoggettoInteressato = false;
		this.relazione = relazione;
		this.csLaodDocumento = documento;
		this.listaPaiDTO = listaPai;
		this.containsDoc = this.csLaodDocumento != null;
		this.sal =sal;
	}
	
	public CsDRelSal getSal() {
		return sal;
	}

	public void setSal(CsDRelSal sal) {
		this.sal = sal;
	}

	public CsDRelazione getRelazione() {
		return relazione;
	}

	public CsLoadDocumento getCsLoadDocumento(){
		return csLaodDocumento;
	}
	
	public List<PaiDTOExt> getListaPaiDTO() {
		return listaPaiDTO;
	}
	
	public CsLoadDocumento getCsLaodDocumento() {
		return csLaodDocumento;
	}
	public boolean isContainsDoc() {
		return containsDoc;
	}
	
	//SISO 1223
	public List<CsRelRelazioneProbl> getListaProblematiche() {
		return listaProblematiche;
	}
	public void setListaProblematiche(List<CsRelRelazioneProbl> listaProblematiche) {
		this.listaProblematiche = listaProblematiche;
	}
	
	public List<CsRelRelazioneTipoint> getListaInterventi() {
		return listaInterventi;
	}

	public void setListaInterventi(List<CsRelRelazioneTipoint> listaInterventi) {
		this.listaInterventi = listaInterventi;
	}

	//FINO SISO 1223
	
	public boolean isRelatedToPai(CsDPai pai)
	{
		if(pai!=null)
		{
			for (PaiDTOExt p : listaPaiDTO)
			{
				return (p.getPai().getDiarioId().equals( pai.getDiarioId()))	;
			}
			
		}
		return false;
	}
		
	@Override	
	public boolean equals(Object obj) 
	{		
		if(relazione!=null && ((RelazioneDTO)obj).relazione!=null)
		{
			return relazione.getDiarioId()!=null && relazione.getDiarioId().equals(((RelazioneDTO)obj).relazione.getDiarioId());
		}
		return false;
	}
	
	public CsTbMicroAttivita getMicroAttivita() {
		if(relazione!=null)
			return relazione.getMicroAttivita();
		return null;
	}
	
	public void setMicroAttivita(CsTbMicroAttivita microAttivita) {
		if(relazione!=null)
		{
			relazione.setMicroAttivita (microAttivita);
			if(microAttivita!=null)
				relazione.setMacroAttivita (microAttivita.getMacroAttivita());	
		}		
	}
			
	public boolean isNew()
	{
		return relazione.getDiarioId()==null;
	}
	
	public boolean isRilevazioniProblematicheButtonDisabled()
	{
		if(!isNew())
		{
			if(relazione.getCsRelRelazioneProbl()!=null && !relazione.getCsRelRelazioneProbl().isEmpty() )
			{
				return true;	
			}			
		}
		
		return false;
	}
	public boolean isRilevazioniProblematicheReadOnly()
	{
		if(!isNew())
		{
			if(relazione.getCsRelRelazioneProblReverseRif()!=null && !relazione.getCsRelRelazioneProblReverseRif().isEmpty())
			{
				//ci sono delle problematiche di altre relazioni che si riferiscono a questa relazione
				return true;
			}
		}
		return false;
	}

	public CsDTriage getTriage() {
		return triage;
	}
	public void setTriage(CsDTriage triage) {
		this.triage = triage;
	}
	
	public boolean isCasoSoggettoInteressato() {
		return casoSoggettoInteressato;
	}

	public void setCasoSoggettoInteressato(boolean casoSoggettoInteressato) {
		this.casoSoggettoInteressato = casoSoggettoInteressato;
	}

	public List<Long> getListaIdsPaiCollegati(){
		List<Long> ids = new ArrayList<Long>();
		for(PaiDTOExt p : this.listaPaiDTO)
			ids.add(p.getPai().getDiarioId());
		return ids;
	}

	public boolean isRelazioneConChiAltro(){
		boolean isAltro = false;
		List<CsCDiarioConchi> lst = relazione.getLstConChi();
		long l = 9999;
			for(CsCDiarioConchi cs: lst){
				if(cs.getId() == l){
					isAltro = true;
				}
			}
		return isAltro;
	}

	//SISO-1257
		
		public boolean isAttivitaSAL() {
			return (this.getRelazione().getMicroAttivita().getFlagTipoForm().equals("4") ||
					this.getRelazione().getMicroAttivita().getFlagTipoForm().equals("5")||
					this.getRelazione().getMicroAttivita().getFlagTipoForm().equals("6"));
		}

		public void setAttivitaSAL(boolean attivitaSAL) {
			this.attivitaSAL = attivitaSAL;
		}
	
}
