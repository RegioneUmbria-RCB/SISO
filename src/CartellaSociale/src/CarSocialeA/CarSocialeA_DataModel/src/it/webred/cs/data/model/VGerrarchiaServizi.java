package it.webred.cs.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "V_GERARCHIA_SERVIZI")
@NamedQuery(name = "VGerrarchiaServizi.findAll", query = "SELECT s FROM VGerrarchiaServizi s")
public class VGerrarchiaServizi implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID")
	private BigDecimal id;
	@Column(name = "Q_CLASSE_ID")
	private BigDecimal qClasseId;
	@Column(name = "T_CLASSE_ID")
	private BigDecimal tClasseId;
	@Column(name = "NUM_LIVELLO")
	private int numLivello;
	@Column(name = "DESC_LIVELLO")
	private String descLivello;
	@Column(name = "CODICE_MEMO")
	private String codiceMemo;
	@Column(name = "DESCRIZIONE")
	private String descrizione;
	@Column(name = "FLAG_INTERVENTO")
	private char flagIntervento; 
	@Column(name = "ROOT_T_CLASSE_ID")
	private BigDecimal rootTclasseId;
	@Column(name = "ROOT_DESCRIZIONE")
	private String rootDescrizione; 
	@Column(name = "AREAT_ID")
	private BigDecimal areaTid; 

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getqClasseId() {
		return qClasseId;
	}

	public void setqClasseId(BigDecimal qClasseId) {
		this.qClasseId = qClasseId;
	}

	public BigDecimal gettClasseId() {
		return tClasseId;
	}

	public void settClasseId(BigDecimal tClasseId) {
		this.tClasseId = tClasseId;
	}

	public int getNumLivello() {
		return numLivello;
	}

	public void setNumLivello(int numLivello) {
		this.numLivello = numLivello;
	}

	public String getDescLivello() {
		return descLivello;
	}

	public void setDescLivello(String descLivello) {
		this.descLivello = descLivello;
	}

	public String getCodiceMemo() {
		return codiceMemo;
	}

	public void setCodiceMemo(String codiceMemo) {
		this.codiceMemo = codiceMemo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	
	public char getFlagIntervento() {
		return flagIntervento;
	}

	public void setFlagIntervento(char flagIntervento) {
		this.flagIntervento = flagIntervento;
	}

	public BigDecimal getRootTclasseId() {
		return rootTclasseId;
	}

	public void setRootTclasseId(BigDecimal rootTclasseId) {
		this.rootTclasseId = rootTclasseId;
	}

	public String getRootDescrizione() {
		return rootDescrizione;
	}

	public void setRootDescrizione(String rootDescrizione) {
		this.rootDescrizione = rootDescrizione;
	}

	public BigDecimal getAreaTid() {
		return areaTid;
	}

	public void setAreaTid(BigDecimal areaTid) {
		this.areaTid = areaTid;
	}
	
	
}
