package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class OrganizzazioneDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	private String codRouting;
	private String codCatastale;
	
	public OrganizzazioneDTO(){}
	
	public OrganizzazioneDTO(CsOOrganizzazione jpa){
		if(jpa!=null){
			id = jpa.getId();
			nome = jpa.getNome();
			codRouting = jpa.getCodRouting();
			codCatastale = jpa.getCodCatastale();
		}
	}
	
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
	public String getCodCatastale() {
		return codCatastale;
	}
	public void setCodCatastale(String codCatastale) {
		this.codCatastale = codCatastale;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getCodRouting() {
		return codRouting;
	}

	public void setCodRouting(String codRouting) {
		this.codRouting = codRouting;
	}

	public boolean isEnteComunale(){
		return !StringUtils.isBlank(codCatastale);
	}

}

