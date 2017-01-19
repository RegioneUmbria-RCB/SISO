package it.webred.ss.data.model;


import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the SS_UFFICIO database table.
 * 
 */
@Entity
@Table(name="SS_UFFICIO")
@NamedQuery(name="SsUfficio.findAll", query="SELECT c FROM SsUfficio c")
public class SsUfficio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	private String nome;
	private Boolean abilitato;
	
	@Column(name="ESCLUDI_AM_GROUP ")
	private String escludiAmGroup;
	
	@Column(name="REQ_STRANIERI")
	private Boolean reqStranieri;
	
	@Column(name="REQ_BISOGNI")
	private Boolean reqBisogni;
	
	@Column(name="REQ_SERVIZI")
	private Boolean reqServizi;
	
	@Column(name="VIS_INTERVENTI")
	private Boolean visInterventi;
	
	@Column(name="N_ORD")
	private Integer nOrd;
	
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

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}

	public String getEscludiAmGroup() {
		return escludiAmGroup;
	}

	public void setEscludiAmGroup(String escludiAmGroup) {
		this.escludiAmGroup = escludiAmGroup;
	}

	public Boolean getReqStranieri() {
		return reqStranieri;
	}

	public Boolean getReqBisogni() {
		return reqBisogni;
	}

	public Boolean getReqServizi() {
		return reqServizi;
	}

	public void setReqStranieri(Boolean reqStranieri) {
		this.reqStranieri = reqStranieri;
	}

	public void setReqBisogni(Boolean reqBisogni) {
		this.reqBisogni = reqBisogni;
	}

	public void setReqServizi(Boolean reqServizi) {
		this.reqServizi = reqServizi;
	}

	public Integer getnOrd() {
		return nOrd;
	}

	public void setnOrd(Integer nOrd) {
		this.nOrd = nOrd;
	}

	public Boolean getVisInterventi() {
		return visInterventi;
	}

	public void setVisInterventi(Boolean visInterventi) {
		this.visInterventi = visInterventi;
	}

}