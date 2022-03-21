package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class SettoreDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	private OrganizzazioneDTO organizzazione;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public OrganizzazioneDTO getOrganizzazione() {
		return organizzazione;
	}
	public void setOrganizzazione(OrganizzazioneDTO organizzazione) {
		this.organizzazione = organizzazione;
	}
	public String getDescrizione(){
		return nome + (organizzazione!=null ? "(Org. "+organizzazione.getNome()+")" : "");
	}
}

