package it.webred.cs.csa.ejb.dto.cartella;

import java.io.Serializable;
import java.util.Date;

public class ValiditaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Date dataInizio;
	private Date dataFine;
	private String descrizione;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	public Date getDataFine() {
		return dataFine;
	}
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
}
