package it.webred.cs.csa.ejb.dto;

import it.webred.cs.data.model.CsCDiarioConchi;
import it.webred.cs.data.model.CsDPai;
import it.webred.cs.data.model.CsDRelazione;
import it.webred.cs.data.model.CsDTriage;
import it.webred.cs.data.model.CsLoadDocumento;
import it.webred.cs.data.model.CsTbMicroAttivita;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.List;

@SuppressWarnings("serial")
public class RelazioneDTO extends CeTBaseObject {

	private CsDRelazione relazione;
	private CsLoadDocumento csLaodDocumento;
	private List<PaiDTOExt> listaPaiDTO;
	private boolean containsDoc;
	private CsDTriage triage;
	
	

	public RelazioneDTO(){
		this.relazione = new CsDRelazione();
		this.triage = new CsDTriage();
	}
	public RelazioneDTO(CsDRelazione relazione, CsLoadDocumento documento, List<PaiDTOExt> listaPai) {
		this.relazione = relazione;
		this.csLaodDocumento = documento;
		this.listaPaiDTO = listaPai;
		this.containsDoc = this.csLaodDocumento != null;
		this.triage = relazione.getCsDTriage();
	}
	
	public RelazioneDTO(CsDRelazione relazione, CsLoadDocumento documento, List<PaiDTOExt> listaPai, CsDTriage triage) {
		this.relazione = relazione;
		this.csLaodDocumento = documento;
		this.listaPaiDTO = listaPai;
		this.containsDoc = this.csLaodDocumento != null;
		this.triage = triage;
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

	public String getLabelConcatAll()
	{
		if(relazione!=null)
		{
			return relazione.getSituazioneAmb() + ", " +
					relazione.getSituazioneParentale() + ", " +
					relazione.getSituazioneSanitaria() + ", " +
					relazione.getProposta();
		}
		return null;
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
	
	public String getFlagRilevazioneProblematiche()
	{		
		String ret = relazione.getFlagRilevazioneProblematiche();
		if(ret==null)
		{
			ret = "NULL";
		}
		return ret;
	}
	
	public void setFlagRilevazioneProblematiche(String flagRilevazioneProblematiche)
	{
		if(flagRilevazioneProblematiche!=null && flagRilevazioneProblematiche.equals("NULL"))
		{
			flagRilevazioneProblematiche = null;
		}
		relazione.setFlagRilevazioneProblematiche(flagRilevazioneProblematiche);
	}
	public CsDTriage getTriage() {
		return triage;
	}
	public void setTriage(CsDTriage triage) {
		this.triage = triage;
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
	
}
