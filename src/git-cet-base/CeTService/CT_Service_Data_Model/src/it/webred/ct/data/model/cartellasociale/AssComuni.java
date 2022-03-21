package it.webred.ct.data.model.cartellasociale;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ASS_COMUNI database table.
 * 
 */
@Entity
@Table(name="ASS_COMUNI")
public class AssComuni implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String belfiore;

	private String denominazione;

    public AssComuni() {
    }

	public String getBelfiore() {
		return this.belfiore;
	}

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

}