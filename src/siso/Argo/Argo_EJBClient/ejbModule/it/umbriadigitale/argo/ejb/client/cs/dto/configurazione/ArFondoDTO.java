package it.umbriadigitale.argo.ejb.client.cs.dto.configurazione;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArFondoDTO implements Serializable {

	private static final long serialVersionUID = 3538579262771050082L;
	
	private Long id;
	private String codiceMemo;
	private String descrizione;
	private Date dataUltimaModifica;
	private String userUltimaModifica;
	private boolean abilitato;

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodiceMemo() {
		return codiceMemo;
	}

	public void setCodiceMemo(String codiceMemo) {
		this.codiceMemo = codiceMemo;
	}

	public Date getDataUltimaModifica() {
		return dataUltimaModifica;
	}

	public void setDataUltimaModifica(Date dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

	public String getUserUltimaModifica() {
		return userUltimaModifica;
	}

	public void setUserUltimaModifica(String userUltimaModifica) {
		this.userUltimaModifica = userUltimaModifica;
	}

	public boolean isAbilitato() {
		return abilitato;
	}

	public void setAbilitato(boolean abilitato) {
		this.abilitato = abilitato;
	}
}
