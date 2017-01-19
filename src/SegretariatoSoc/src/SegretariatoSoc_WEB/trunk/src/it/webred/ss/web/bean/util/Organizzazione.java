package it.webred.ss.web.bean.util;

import it.webred.ss.data.model.SsOOrganizzazione;

import java.io.Serializable;

public class Organizzazione implements Serializable{
	
	private String belfiore;
	private String nome;
	private Long id;
	
	public Organizzazione(){
		belfiore = null;
		nome="";
		id=null;
	}
	
	public Organizzazione(Long id, String belfiore, String nome){
		this.belfiore =belfiore;
		this.nome=nome;
		this.id=id;
	}
	
	public Organizzazione(SsOOrganizzazione o){
		if(o!=null){
			this.belfiore=o.getBelfiore();
			this.nome=o.getNome();
			this.id=o.getId();
		}
	}
	
	public String getBelfiore() {
		return belfiore;
	}
	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void fillModel(SsOOrganizzazione model){
		model.setId(this.id);
		model.setBelfiore(this.belfiore);
		model.setNome(this.getNome());
	}
	
}
