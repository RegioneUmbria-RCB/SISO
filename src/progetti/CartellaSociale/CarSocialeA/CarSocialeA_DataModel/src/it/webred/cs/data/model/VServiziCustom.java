package it.webred.cs.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "V_SERVIZI_CUSTOM")
@NamedQueries(value={
		@NamedQuery(name = "VServiziCustom.findAll", query = "SELECT s FROM VServiziCustom s"),
	    @NamedQuery(
	      name = "VServiziCustom.findByInterventoCustomAndAreatId",
	      query="SELECT s FROM VServiziCustom s WHERE s.interventoCustomId = :interventoCustomId and s.areatId = :areatId") 
	})

public class VServiziCustom implements Serializable {
	 
	 
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "INTERVENTO_CUSTOM_ID")
	private Long interventoCustomId;
	
	@Column(name = "CODICE_MEMO")
	private String codiceMemo;
	
	@Column(name = "INTERVENTO_DESCRIZIONE")
	private String interventoDescrizione;
	
	@Column(name = "AREAT_ID")
	private int areatId;
	
	@Column(name = "AREAT_DESCRIZIONE")
	private String areatDescrizione;
	
	@Column(name = "CODICE_MEMO_AREAT")
	private String codiceMemoAreat;
	
	@Column(name = "INPS_CODICE")
	private String inpsCodice;
	
	@Column(name = "INPS_DENOMINAZIONE")
	private String inpsDenonimazione;
	
	@Column(name = "INPS_DESCRIZIONE")
	private String inpsDescrizione;
	
	@Column(name = "INPS_PSA")
	private int inpsPsa;

	public int getInpsPsa() {
		return inpsPsa;
	}

	public void setInpsPsa(int inpsPsa) {
		this.inpsPsa = inpsPsa;
	}

	public Long getInterventoCustomId() {
		return interventoCustomId;
	}

	public void setInterventoCustomId(Long interventoCustomId) {
		this.interventoCustomId = interventoCustomId;
	}

	public String getCodiceMemo() {
		return codiceMemo;
	}

	public void setCodiceMemo(String codiceMemo) {
		this.codiceMemo = codiceMemo;
	}

	public String getInterventoDescrizione() {
		return interventoDescrizione;
	}

	public void setInterventoDescrizione(String interventoDescrizione) {
		this.interventoDescrizione = interventoDescrizione;
	}

	public int getAreatId() {
		return areatId;
	}

	public void setAreatId(int areatId) {
		this.areatId = areatId;
	}

	public String getAreatDescrizione() {
		return areatDescrizione;
	}
	
	public String getCodiceMemoAreat() {
		return codiceMemoAreat;
	}

	public void setCodiceMemoAreat(String codiceMemoAreat) {
		this.codiceMemoAreat = codiceMemoAreat;
	}

	public void setAreatDescrizione(String areatDescrizione) {
		this.areatDescrizione = areatDescrizione;
	}

	public String getInpsCodice() {
		return inpsCodice;
	}

	public void setInpsCodice(String inpsCodice) {
		this.inpsCodice = inpsCodice;
	}

	public String getInpsDenonimazione() {
		return inpsDenonimazione;
	}

	public void setInpsDenonimazione(String inpsDenonimazione) {
		this.inpsDenonimazione = inpsDenonimazione;
	}

	public String getInpsDescrizione() {
		return inpsDescrizione;
	}

	public void setInpsDescrizione(String inpsDescrizione) {
		this.inpsDescrizione = inpsDescrizione;
	} 
	
	
	 
}
