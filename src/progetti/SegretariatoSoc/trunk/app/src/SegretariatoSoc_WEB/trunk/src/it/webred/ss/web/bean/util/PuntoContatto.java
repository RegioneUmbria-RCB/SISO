package it.webred.ss.web.bean.util;

import it.webred.ss.data.model.SsRelUffPcontOrg;

import java.io.Serializable;

public class PuntoContatto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long idPContatto;
	private String nomePContatto;
	
	private Ufficio ufficio;
	private Organizzazione organizzazione;
	
	public PuntoContatto(){
		organizzazione = new Organizzazione();
		ufficio = new Ufficio();
	}
	
	public void resetOrganizzazione(){
		this.organizzazione = new Organizzazione();
		this.resetUfficio();
	}
	
	public void resetUfficio(){
		this.ufficio = new Ufficio();
		this.resetPContatto();
	}
	
	public void resetPContatto(){
		this.idPContatto=null;
		this.nomePContatto=null;
	}


	public String getNomePContatto() {
		return nomePContatto;
	}

	public void setNomePContatto(String nomePContatto) {
		this.nomePContatto = nomePContatto;
	}

	public Ufficio getUfficio() {
		return ufficio;
	}

	public void setUfficio(Ufficio ufficio) {
		
		this.ufficio = ufficio;
		if(this.ufficio.getId()!=ufficio.getId())
			this.resetPContatto();
	}

	public Organizzazione getOrganizzazione() {
		return organizzazione;
	}

	public void setOrganizzazione(Organizzazione organizzazione) {
		this.organizzazione = organizzazione;
		this.resetUfficio();
	}
	
	public void initFromModel(SsRelUffPcontOrg rel){
		organizzazione = new Organizzazione();
		organizzazione.setId(rel.getSsOOrganizzazione().getId());
		organizzazione.setNome(rel.getSsOOrganizzazione().getNome());
		organizzazione.setBelfiore(rel.getSsOOrganizzazione().getCodRouting());
		
		ufficio = new Ufficio(rel.getSsUfficio());
		
		idPContatto = rel.getSsPuntoContatto().getId();
		nomePContatto = rel.getSsPuntoContatto().getNome();
	}

	public Long getIdPContatto() {
		return idPContatto;
	}

	public void setIdPContatto(Long idPContatto) {
		this.idPContatto = idPContatto;
	}
	
}
